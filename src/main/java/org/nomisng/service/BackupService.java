package org.nomisng.service;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.nomisng.config.ApplicationProperties;
import org.nomisng.config.YAMLConfig;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class BackupService {

    private final YAMLConfig yamlConfig;
    private static final String BASE_DIR = "/backup/";
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private final JdbcTemplate jdbcTemplate;
    private final ApplicationProperties applicationProperties;
    private final DataSourceProperties databaseProperties;
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");
    SimpleDateFormat alternateDf = new SimpleDateFormat("yyyyMMdd");

    public void upload(InputStream inputStream) {
        try {
            String directory = applicationProperties.getTempDir() + BASE_DIR;
            new File(directory).mkdir();
            Date date1 = new Date();
            String fileName = "uploaded_backup_" + df.format(date1) + ".sql";
            IOUtils.copy(inputStream, Files.newOutputStream(Paths.get(directory + fileName)));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public ByteArrayOutputStream downloadBackup() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        String directory = applicationProperties.getTempDir() + BASE_DIR;

        Optional<String> file = listFilesUsingDirectoryStream(directory).stream()
                .filter(f -> f.endsWith(".sql"))
                .sorted((b1, b2) -> {
                    Date date1 = new Date();
                    Date date2 = new Date();
                    String backupName = "";
                    if (b1.contains("uploaded_backup_")) {
                        b1 = b1.replace("uploaded_backup_", "");
                        b2 = b2.replace("uploaded_backup_", "");
                    } else {
                        b1 = b1.replace("_backup_", "");
                        b2 = b2.replace("backup_", "");
                    }
                    try {
                        date1 = df.parse(b1.replace(".sql", ""));
                    } catch (ParseException e) {
                        try {
                            date1 = alternateDf.parse(b1.replace(".sql", ""));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                    }
                    try {
                        date2 = df.parse(b2.replace(".sql", ""));
                    } catch (ParseException e) {
                        try {
                            date2 = alternateDf.parse(b2.replace(".sql", ""));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                    }
                    return date2.compareTo(date1);
                })
                .limit(1)
                .findFirst();

        if (file.isPresent()) {
            try{
                IOUtils.copy(new FileInputStream(directory + file.get()), baos);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos;
    }

    public ByteArrayOutputStream downloadBackup(String databaseName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String directory = applicationProperties.getTempDir() + BASE_DIR;
        try {
            Optional<String> file = listFilesUsingDirectoryStream(directory).stream()
                    .filter(f -> f.equals(databaseName))
                    .limit(1)
                    .findFirst();
            if (file.isPresent()) {
                IOUtils.copy(new FileInputStream(directory + file.get()), baos);
            }
        } catch (NoSuchElementException exception) {
            exception.printStackTrace();
        }
        return baos;
    }

    public void backupPGSQL(boolean restore) {
        try {
            log.info("Backup started");
            String directory = applicationProperties.getTempDir() + BASE_DIR;
            new File(directory).mkdir();
            Map<String, String> mapper = getDatabaseProperties();

            Process p;
            ProcessBuilder pb;
            //We build a string with today's date (This will be the backup's filename)

            StringBuilder date = new StringBuilder();
            date.append(df.format(new Date()));
            File file = new File(directory);

            if (file.exists()) {
                log.info("Creating dump");
                String exec = "pg_dump";
                if (SystemUtils.IS_OS_WINDOWS) {
                    exec = TEMP_DIR + File.separator + exec + ".exe";
                }
                String buffer = directory + "backup_" + date.toString() + ".sql";
                if (restore) {
                    buffer = "restore.sql";
                }
                pb = new ProcessBuilder(exec, "-f", buffer,
                        "-F", "c", "-Z", "9", "-b", "-v", "-c", "-C", "-p", mapper.get("port"), "-h",
                        mapper.get("ipAddress"), "-U", mapper.get("username"), mapper.get("database"));
                pb.environment().put("PGPASSWORD", mapper.get("password"));
                pb.redirectErrorStream(true);
                p = pb.start();

                try {
                    InputStream is = p.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String ll;
                    while ((ll = br.readLine()) != null) {
                        log.info("inputStream status{} ", ll);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("error {} ", e.getMessage());
                }
                log.info("status{} ", "Backup completed");
            }
        } catch (IOException x) {
            x.printStackTrace();
            log.error("catch error {}", x.getMessage());
        }
    }

    public void restorePGSQL(String fileName) {
        backupPGSQL(true);
        try {
            log.info("restore status {} ", "Restore started");
            try {
                jdbcTemplate.execute("drop schema if exists public cascade; create schema public;");
                log.info("restore status {} ", "Restore started");
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
            String directory = applicationProperties.getTempDir() + BASE_DIR;
            Map<String, String> mapper = getDatabaseProperties();

            Process p;
            ProcessBuilder pb;
            String exec = "pg_restore";
            Optional<String> file = listFilesUsingDirectoryStream(directory).stream()
                    .filter(f -> f.equals(fileName))
                    .limit(1)
                    .findFirst();

            if (file.isPresent()) {
                log.info("restore file present");
                try {
                    if (SystemUtils.IS_OS_WINDOWS) {
                        exec = TEMP_DIR + File.separator + exec + ".exe";
                    }
                    pb = new ProcessBuilder(exec,
                            "-F", "c", "-c", "-C", "-v", "-p", mapper.get("port"), "-h", mapper.get("ipAddress"),
                            "-U", mapper.get("username"), "-d", mapper.get("database"), directory + fileName.trim()
                    );
                    pb.environment().put("PGPASSWORD", mapper.get("password"));
                    pb.redirectErrorStream(true);
                    p = pb.start();

                    InputStream inputStream = p.getInputStream();
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    while ((line = br.readLine()) != null) {
                        log.info("inputStream status{} ", line);
                    }
                    log.info("status {} ", "Restore completed");
                } catch (IOException e) {
                    log.error("error {}", e.getMessage());
                }
            }
        } catch (IOException x) {
            log.error("catch error{} ", x.getMessage());
        }
    }

    @SneakyThrows
    public List<String> backupAvailable() {
        String directory = applicationProperties.getTempDir() + BASE_DIR;
        return listFilesUsingDirectoryStream(directory).stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    private Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName().toString());
                }
            }
        }

        return fileList;
    }

    @SneakyThrows
    protected void cleanupBackup() {
        String directory = applicationProperties.getTempDir() + BASE_DIR;
        listFilesUsingDirectoryStream(directory).stream()
                .filter(f -> f.endsWith(".sql"))
                .forEach(f -> {
                    try {
                        Date date;
                        String backupName = "";
                        if (f.contains("uploaded_backup_")) {
                            backupName = f.replace("uploaded_backup_", "");
                        } else {
                            backupName = f.replace("backup_", "");
                        }
                        try {
                            date = df.parse(backupName.replace(".sql", ""));
                        } catch (ParseException e) {
                            date = alternateDf.parse(backupName.replace(".sql", ""));
                        }
                        if (LocalDateTime.now().minusDays(5).isAfter(convertToLocalDateTimeViaSqlTimestamp(date))) {
                            FileUtils.deleteQuietly(new File(directory + backupName));
                        }

                        if (LocalDateTime.now().minusHours(2).isAfter(convertToLocalDateTimeViaSqlTimestamp(date)) &&
                                LocalDateTime.now().toLocalDate().equals(convertToLocalDateTimeViaSqlTimestamp(date).toLocalDate())) {
                            FileUtils.deleteQuietly(new File(directory + backupName));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
    }

    private Map<String, String> getDatabaseProperties() {

        int index = yamlConfig.getUrl().indexOf("//") + 2;
        int index2 = yamlConfig.getUrl().lastIndexOf(":");
        int lastIndex = yamlConfig.getUrl().lastIndexOf("/") + 1;
        String dbase = yamlConfig.getUrl().substring(lastIndex);
        if (yamlConfig.getUrl().contains("?")) {
            int index3 = yamlConfig.getUrl().indexOf("?");
            dbase = yamlConfig.getUrl().substring(lastIndex, index3);
        }

        String ip = yamlConfig.getUrl().substring(index, index2);
        String user = yamlConfig.getUsername();
        String password = yamlConfig.getPassword();
        String port = yamlConfig.getUrl().substring(index2 + 1, lastIndex - 1);
        Map<String, String> mapper = new HashMap<>();
        mapper.put("ipAddress", ip);
        mapper.put("username", user);
        mapper.put("database", dbase);
        mapper.put("password", password);
        mapper.put("port", port);

        return mapper;
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void backupCleanup() {
        backupPGSQL(false);
        cleanupBackup();
    }

    private LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
        return new java.sql.Timestamp(
                dateToConvert.getTime()).toLocalDateTime();
    }

    static {
        String[] files = {"pg_dump.exe", "pg_restore.exe", "libcrypto-1_1-x64.dll",
                "libpq.dll", "libssl-1_1-x64.dll", "zlib1.dll", "libiconv-2.dll", "libintl-9.dll"};
        for (String file : files) {
            try (InputStream is = BackupService.class.getClassLoader().getResourceAsStream(file)) {
                FileOutputStream fos = new FileOutputStream(new File(TEMP_DIR + File.separator + file));
                IOUtils.copyLarge(is, fos);
                fos.close();
            } catch (IOException e) {
                log.info("Error message: {}", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public String getFileExtension(String fullName) {
        checkNotNull(fullName);
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

}

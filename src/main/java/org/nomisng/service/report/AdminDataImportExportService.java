package org.nomisng.service.report;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.nomisng.config.ApplicationProperties;
import org.nomisng.domain.entity.ApplicationCodeset;
import org.nomisng.domain.entity.DataLog;
import org.nomisng.domain.entity.Form;
import org.nomisng.domain.entity.ReportInfo;
import org.nomisng.repository.*;
import org.nomisng.service.UserService;
import org.nomisng.util.ZipFileUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;

import org.nomisng.util.converter.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminDataImportExportService {

    private final ApplicationCodesetRepository applicationCodesetRepository;
    private final FormRepository formRepository;
    private final OrganisationUnitRepository organisationUnitRepository;
    private final RegimenRepository regimenRepository;
    private final ReportInfoRepository reportInfoRepository;
    private final FlagRepository flagRepository;
    private final DataLogRepository dataLogRepository;
    private final UserService userService;
    private final ZipFileUtil zipFile;
    private final JdbcTemplate jdbcTemplate;
    private final ApplicationProperties applicationProperties;
    List<ApplicationCodeset> codesetList = new ArrayList<>();
    List<Form> formList = new ArrayList<>();
    List<ReportInfo> reportInfoList = new ArrayList<>();
    private final static String BASE_DIR = "/report/data/";
    private final static String TEMP_BASE_DIR = "/report/temp/";
    private final static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheeetml";
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    public void exportDataToExcel(List<String> tableNames) throws IOException {
//        Long cboProjectId = userService.getUserWithRoles().get().getCboProjectByCurrentCboProjectId().getId();
        List<DataLog> dataLogList = dataLogRepository.findAll();
        LocalDateTime lastUpdate = LocalDateTime.now().minusYears(10);
        for (String tableName : tableNames) {
            if (dataLogList.isEmpty()) {
                Optional<DataLog> dataLog = dataLogList.stream()
                        .filter(data -> data.getIdentifier().equalsIgnoreCase(tableName))
                        .findFirst();
                if (dataLog.isPresent()) {
                    lastUpdate = dataLog.get().getDateModified();
                }
            }
            switch (tableName) {
                case "application_codeset":
                    codesetList = applicationCodesetRepository.findByDateModified(lastUpdate);
                    if (!codesetList.isEmpty()) {
                        writeToExcelMultipleSheet("codeset.xlsx", "codeset", codesetList);
                    }
                    break;
                case "form":
                    formList = formRepository.findByDateModified(lastUpdate);
                    if (!formList.isEmpty()) {
                        writeToExcelMultipleSheet("form.xlsx", "form", formList);
                    }
                    break;
            }

        }
    }

    private void updateForm(Reader reader) {
        Type formType = new TypeToken<ArrayList<Form>>() {
        }.getType();
        Gson gson = getGsonBuilderTypeAdapter().setPrettyPrinting().create();
        formList = gson.fromJson(reader, formType);
        if (!formList.isEmpty()) {
            formRepository.saveAll(formList);
            log.info("Form updated successfully.");
        }
    }
    private void updateCodeset(Reader reader) {
        Type formType = new TypeToken<ArrayList<ApplicationCodeset>>() {
        }.getType();
        Gson gson = getGsonBuilderTypeAdapter().setPrettyPrinting().create();
        codesetList = gson.fromJson(reader, formType);
        if (!codesetList.isEmpty()) {
            applicationCodesetRepository.saveAll(codesetList);
            log.info("Codeset updated successfully.");
        }
    }
    private void updateReport(Reader reader) {
        Type formType = new TypeToken<ArrayList<ReportInfo>>() { }.getType();
        Gson gson = getGsonBuilderTypeAdapter().setPrettyPrinting().create();
        reportInfoList = gson.fromJson(reader, formType);
        if (!reportInfoList.isEmpty()) {
            reportInfoRepository.saveAll(reportInfoList);
            log.info("Report updated successfully.");
        }
    }

    public void importZipFile(MultipartFile file) throws IOException {
        String targetPath = applicationProperties.getTempDir() + TEMP_BASE_DIR;
        String sourceFileName = applicationProperties.getTempDir() + TEMP_BASE_DIR + file.getOriginalFilename();
        FileUtils.cleanDirectory(new File(targetPath));

        File inputFile = new File(sourceFileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(inputFile));
        byte[] data = file.getBytes();
        bos.write(data);
        bos.close();
        Path target = Paths.get(targetPath);
        Path source = Paths.get(sourceFileName);
        zipFile.unzipFile(source, target);
        final Set<String> fileList = listFiles(targetPath);

        for (String dt : fileList) {
            int lastIndex = dt.lastIndexOf(".");
            String dataName = dt.substring(0, lastIndex);
            String strPath = targetPath + dt;
            if (!dt.substring(lastIndex).equalsIgnoreCase(".zip")) {
                Reader reader = Files.newBufferedReader(Paths.get(strPath));
                if (dataName.equalsIgnoreCase("codeset")) {
                    updateCodeset(reader);
                } else if (dataName.equalsIgnoreCase("form")) {
                    updateForm(reader);
                } else if (dataName.equalsIgnoreCase("report")) {
                    updateReport(reader);
                }
                reader.close();
            }
        }
        FileUtils.forceDelete(new File(sourceFileName));

    }

    public void importJsonData(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String temp = applicationProperties.getTempDir() + TEMP_BASE_DIR + fileName;
        File inputFile = new File(temp);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(inputFile));
        byte[] data = file.getBytes();
        bos.write(data);
        bos.close();

        int lastIndex = fileName.lastIndexOf(".");
        String dataName = fileName.substring(0, lastIndex);
        Reader reader = Files.newBufferedReader(Paths.get(temp));
        if (dataName.equalsIgnoreCase("codeset")) {
            updateCodeset(reader);
        } else if (dataName.equalsIgnoreCase("form")) {
            updateForm(reader);
        } else if (dataName.equalsIgnoreCase("report")) {
            updateReport(reader);
        }
        reader.close();

    }

    public void exportDataToJson(List<String> tableNames) throws IOException {
        List<DataLog> dataLogList = dataLogRepository.findAll();
        LocalDateTime lastUpdate = LocalDateTime.now().minusYears(10);
        String filePath = applicationProperties.getTempDir() + BASE_DIR;
        String fileName = "";
        FileUtils.cleanDirectory(new File(filePath));
        for (String tableName : tableNames) {
            if (dataLogList.isEmpty()) {
                Optional<DataLog> dataLog = dataLogList.stream()
                        .filter(data -> data.getIdentifier().equalsIgnoreCase(tableName))
                        .findFirst();
                if (dataLog.isPresent()) {
                    lastUpdate = dataLog.get().getDateModified();
                }
            }
            if (tableName.equalsIgnoreCase("codeset")) {
                codesetList = applicationCodesetRepository.findByDateModified(lastUpdate);
                fileName = filePath + "codeset.json";
                writeToJsonFile(fileName, codesetList);
            } else if (tableName.equalsIgnoreCase("form")) {
                formList = formRepository.findByDateModified(lastUpdate);
                fileName = filePath + "form.json";
                writeToJsonFile(fileName, formList);
            } else if (tableName.equalsIgnoreCase("report")) {
                reportInfoList = reportInfoRepository.findByDateModified(lastUpdate);
                fileName = filePath + "report.json";
                writeToJsonFile(fileName, reportInfoList);
            }
        }
        String zipFileName = filePath + "data_export.zip";
        zipFile.zipDirectory(new File(filePath), zipFileName);
    }

    public <T> void writeToJsonFile(String fileName, List<T> data) throws IOException {
        if (fileName != null && !data.isEmpty()) {
            Gson gson = getGsonBuilderTypeAdapter().setPrettyPrinting().create();
            String jsonData = gson.toJson(data);
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(jsonData);
            fileWriter.close();
        }
    }

    private GsonBuilder getGsonBuilderTypeAdapter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());

        return gsonBuilder;
    }

    public <T> void writeToExcelMultipleSheet(final String fileName, final String sheetName,
                                              final List<T> data) {
        File file = null;
        OutputStream fos = null;
        XSSFWorkbook workbook = null;
        try {
            String filePath = applicationProperties.getTempDir() + BASE_DIR + fileName;
            file = new File(filePath);
            if (file.exists()) {
                workbook = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(file));
            } else {
                workbook = new XSSFWorkbook();
            }
            Sheet sheet = workbook.createSheet(sheetName);
            List<String> fieldNames = getFieldNamesForClass(data.get(0).getClass());
            int rowCount = 0;
            int columnCount = 0;
            Row row = sheet.createRow(rowCount++);
            for (String fieldName : fieldNames) {
                Cell cell = row.createCell(columnCount++);
                cell.setCellValue(fieldName);
            }
            Class<? extends Object> classz = data.get(0).getClass();
            for (T t : data) {
                row = sheet.createRow(rowCount++);
                columnCount = 0;
                for (String fieldName : fieldNames) {
                    Cell cell = row.createCell(columnCount);
                    Method method = null;
                    try {
                        method = classz.getMethod("get" + StringUtils.capitalize(fieldName));
                    } catch (NoSuchMethodException nme) {
                        method = classz.getMethod("get" + fieldName);
                    }
                    Object value = method.invoke(t, (Object[]) null);
                    if (value != null) {
                        if (value instanceof String) {
                            cell.setCellValue((String) value);
                        } else if (value instanceof Long) {
                            cell.setCellValue((Long) value);
                        } else if (value instanceof Integer) {
                            cell.setCellValue((Integer) value);
                        } else if (value instanceof Double) {
                            cell.setCellValue((Double) value);
                        } else if (value instanceof Date) {
                            cell.setCellValue((Date) value);
                        } else if (value instanceof UUID) {
                            cell.setCellValue(String.valueOf(value));
                        } else if (value instanceof Timestamp) {
                            cell.setCellValue((Timestamp) value);
                        } else if (value instanceof Object) {
                            cell.setCellValue(String.valueOf(value));
                        } else if (value instanceof Boolean) {
                            cell.setCellValue((Boolean) value);
                        }
                    }
                    columnCount++;
                }
            }
            fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
            }
        }
    }

    private List<Form> fetchFormByDateModified(LocalDateTime lastModified) {
        List<Form> formList = jdbcTemplate.query(ReportQuery.FORM_FETCH_QUERY, (resultSet, i) -> {
            Form form = new Form();
            form.setId(resultSet.getLong("id"));
            form.setName(resultSet.getString("name"));
            form.setCode(resultSet.getString("code"));
            form.setResourceObject(resultSet.getObject("resource_object"));
            form.setResourcePath(resultSet.getString("resource_path"));
            form.setCreatedBy(resultSet.getString("created_by"));
            form.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());
            form.setDateModified(resultSet.getTimestamp("date_modified").toLocalDateTime());
            form.setModifiedBy(resultSet.getString("modified_by"));
            form.setArchived(resultSet.getInt("archived"));
            form.setVersion(resultSet.getString("version"));
            form.setSupportServices(resultSet.getString("support_services"));
            form.setFormType(resultSet.getInt("form_type"));
            form.setUid(resultSet.getObject("uid", UUID.class));

            log.info("Resource object: {}", form.getResourceObject());
            return form;
        }, lastModified);

        return formList;
    }

    public boolean isExcelFormat(MultipartFile file) {
        return (!TYPE.equals(file.getContentType())) ? false : true;
    }

    private List<String> getFieldNamesForClass(Class<?> clazz) throws Exception {
        List<String> fieldNames = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fieldNames.add(fields[i].getName());
        }
        return fieldNames;
    }

    private static void printCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case BOOLEAN:
                System.out.print(cell.getBooleanCellValue());
                break;
            case STRING:
                System.out.print(cell.getRichStringCellValue().getString());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    System.out.print(cell.getDateCellValue());
                } else {
                    System.out.print(cell.getNumericCellValue());
                }
                break;
            case FORMULA:
                System.out.print(cell.getCellFormula());
                break;
            case BLANK:
            default:
                System.out.print("");
        }

        System.out.print("\t");
    }

    private <T> Object getCellData(Cell cell) {
        Object data;
        switch (cell.getCellType()) {
            case STRING:
                data = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    data = cell.getDateCellValue();
                } else {
                    data = cell.getNumericCellValue();
                }
                break;
            case BOOLEAN:
                data = cell.getBooleanCellValue();
                break;
            case FORMULA:
                data = cell.getCellFormula();
                break;
            default:
                data = "";
        }

        return data;
    }

    @SneakyThrows
    public ByteArrayOutputStream downloadFile(String file) {
        baos = new ByteArrayOutputStream();
        final String folder =  applicationProperties.getTempDir() + BASE_DIR;
        final Optional<String> fileToDownload = listFilesUsingDirectoryStream(folder).stream()
                .filter(f -> f.equals(file))
                .findFirst();
        fileToDownload.ifPresent(s -> {
            try (InputStream is = new FileInputStream(folder + s)) {
                IOUtils.copy(is, baos);
            } catch (IOException ignored) {
            }
        });

        return baos;
    }

    @SneakyThrows
    public Set<String> listFiles(String folderName) {
        String directory = applicationProperties.getTempDir() + BASE_DIR;
        if (folderName == null) {
            folderName = directory;
        }
        return listFilesUsingDirectoryStream(folderName);
    }

    private Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
        final Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                }
            }
        }

        return fileList;
    }

    private LocalDate convertToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    @SneakyThrows
    @PostConstruct
    public void init() {
        String folder = applicationProperties.getTempDir() + BASE_DIR;
        new File(folder).mkdirs();
        File directory = new File(folder);

        String tempFolder = applicationProperties.getTempDir() + TEMP_BASE_DIR;
        new File(tempFolder).mkdirs();
        File tempDirectory = new File(folder);
        if (!tempDirectory.exists() && !directory.exists()) {
            return;
        } else {
            FileUtils.cleanDirectory(directory);
            FileUtils.cleanDirectory(tempDirectory);
        }
    }

}

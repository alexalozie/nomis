package org.nomisng.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.util.Map;

@Configuration
public class YmlFile {
    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(YmlFile.class);
    //</editor-fold>
    public static String dbUrl;
    public static String dbUser;
    public static String dbPass;

    public static DatabaseProperties readYml(File ymlFile) throws IOException {
        BufferedReader in = null;
        DatabaseProperties databaseProperties;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(ymlFile.getAbsolutePath())));
            Yaml yaml = new Yaml();
            databaseProperties = yaml.loadAs(in, DatabaseProperties.class);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error: " + e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return databaseProperties;
    }

    public static void getDatabaseConnectionParameters(String file) {
        String fileSeparator = File.separator;
        File ymlFile = new File(ApplicationProperties.modulePath + fileSeparator + "config.yml");
        try (InputStream inputStream = new FileInputStream(ymlFile)) {
            Yaml yaml = new Yaml();
            Map<String, Object> rawData = yaml.load(inputStream);
            rawData = (Map<String, Object>) rawData.get("spring");
            final ObjectMapper mapper = new ObjectMapper();
            DataSource dataSource1 = mapper.convertValue(rawData.get("datasource"), org.nomisng.config.DataSource.class);
            dbUrl = dataSource1.getUrl();
            dbUser = dataSource1.getUsername();
            dbPass = dataSource1.getPassword();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public YmlFile() {
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof YmlFile)) return false;
        final YmlFile other = (YmlFile) o;
        if (!other.canEqual((Object) this)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof YmlFile;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int result = 1;
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "YmlFile()";
    }
    //</editor-fold>
}

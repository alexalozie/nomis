package org.nomisng.utility;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nomisng.config.ApplicationProperties;
import org.nomisng.util.FileCopyUtil;
import org.nomisng.web.chart.DashboardSummaryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DashboardSummaryResource.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UtilTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void givenEnrolmentSummary() throws Exception {
      mockMvc.perform(get("/api/dashboard/enrolment-summary/31"))
              .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void givenFileContent()
            throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("cbo.properties");
        String data  = FileCopyUtil.readFromInputStream(inputStream);
        File from = new File("src/main/resources/cbo.properties");
        File to = new File("src/main/resources/app.properties");
        Path moduleRuntimePath = Paths.get(ApplicationProperties.modulePath, "modules");
        File to2 = new File(moduleRuntimePath.resolve("config.properties").toAbsolutePath().toString());
        FileCopyUtil.copyFile(from, to2);

        assertThat(from).exists();
        assertThat(Files.readAllLines(to.toPath())
                .equals(Files.readAllLines(from.toPath())));
    }
}

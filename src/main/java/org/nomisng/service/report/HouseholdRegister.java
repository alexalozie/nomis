package org.nomisng.service.report;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.nomisng.config.ApplicationProperties;
import org.nomisng.domain.entity.Household;
import org.nomisng.repository.HouseholdRepository;
import org.nomisng.service.ExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseholdRegister {
    private final HouseholdRepository householdRepository;

    private final ExcelService excelService;
    private final ApplicationProperties applicationProperties;

    private final static String BASE_DIR = "/report/";
    private static final int UNARCHIVED = 0;

    public void getHouseholdReport(LocalDate reportingStartDate, LocalDate reportingEndDate) {
        List<Household> householdList = householdRepository.findAllByArchivedOrderByIdAsc(UNARCHIVED);

        String fileName = "household";
        File file = new File(applicationProperties.getTempDir() + BASE_DIR + fileName);
        //clean the base directory
        FileUtils.deleteQuietly(file);
        excelService.writeToExcelMultipleSheet(fileName, "Household", householdList);

    }

    public void downloadPrepFile(String file, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream baos = excelService.downloadFile(file);
        writeStream(baos, response);
    }

    private void writeStream(ByteArrayOutputStream baos, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Length", Integer.valueOf(baos.size()).toString());
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(baos.toByteArray());
        outputStream.close();
        response.flushBuffer();
    }

}

package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.service.report.AdminDataImportExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/api/data")
@RequiredArgsConstructor
public class AdminDataImportExportResource {
    private final AdminDataImportExportService dataImportExportService;

    @GetMapping("/export")
    @Async
    public ResponseEntity<String> runConvert(@RequestParam(required = false) List<String> tableNames) throws IOException {
        if (tableNames != null && !tableNames.isEmpty()) {
            dataImportExportService.exportDataToJson(tableNames);
            return ResponseEntity.status(HttpStatus.OK).body("Data extracted and exported successfully");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Table names cannot be empty");
    }

    @PostMapping("/import")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            if (!file.isEmpty()) {
                if (file.getContentType().equalsIgnoreCase("application/json")) {
                    dataImportExportService.importJsonData(file);
                } else if (file.getContentType().equalsIgnoreCase("application/x-zip-compressed")) {
                    dataImportExportService.importZipFile(file);
                }

                return ResponseEntity.status(HttpStatus.OK).body("Data imported  and record updated successfully");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not upload the file");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file, try again.");
    }

    @GetMapping("/available-files")
    public ResponseEntity<Set<String>> dirFileList() {
        return new ResponseEntity<>(dataImportExportService.listFiles(null), HttpStatus.OK);
    }

    @GetMapping("/download/{file}")
    public void downloadFile(@PathVariable String file, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream baos = dataImportExportService.downloadFile(file);
        writeStream(baos, response);
    }

    @GetMapping("/download/json/{file}")
    public ResponseEntity<byte[]> downloadJsonFile(@PathVariable String file) {
        ByteArrayOutputStream baos = dataImportExportService.downloadFile(file);
        byte[] byteData = baos.toByteArray();
        return exportJson(byteData, "form.json");
    }

    private ResponseEntity<byte[]> exportJson(byte[] jsonBody, String fileName) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentType(MediaType.APPLICATION_JSON) //MediaType.parseMediaType("application/vnd.ms-excel")
                .contentLength(jsonBody.length)
                .body(jsonBody);
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

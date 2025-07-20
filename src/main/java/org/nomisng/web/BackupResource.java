package org.nomisng.web;

import com.github.dockerjava.api.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.service.BackupService;
import org.nomisng.web.apierror.ApiError;
import org.nomisng.web.apierror.BadRequestAlertException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.service.ResponseMessage;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BackupResource {
    private final BackupService backupService;

    @PostMapping("/backup/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String message = "";
        try {

            backupService.upload(file.getInputStream());

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return new ResponseEntity<>(message, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/backup/restore/{fileName}")
    public ResponseEntity<String> restore(@PathVariable @Valid String fileName) {
        String message = "";
        try {

            if (!backupService.getFileExtension(fileName).equalsIgnoreCase("sql") || fileName == null) {
                throw new BadRequestException("Oops! Invalid request.");
            }
            backupService.restorePGSQL(fileName);
            message = "Database restored successfully.";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            message = "Oops! Invalid request.";
            return new ResponseEntity<>(message, HttpStatus.EXPECTATION_FAILED);
        }

    }

    @GetMapping("/backup/backup")
    public ResponseEntity<String> backup() {
        String message = "";
        try {
            backupService.backupPGSQL(false);
            message = "Database backup completed successfully.";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            message = "Oops! Invalid request.";
            return new ResponseEntity<>(message, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/backup/download")
    public void downloadBackup(HttpServletResponse response) throws IOException {
        ByteArrayOutputStream baos = backupService.downloadBackup();
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Length", Integer.valueOf(baos.size()).toString());
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(baos.toByteArray());
        outputStream.close();
        response.flushBuffer();
    }

    @GetMapping("/backup/download/{databaseName}")
    public void downloadBackup(HttpServletResponse response, @PathVariable String databaseName) throws IOException {
        String message;

        try {
            if (!backupService.getFileExtension(databaseName).equalsIgnoreCase("sql") || databaseName == null) {
                throw new BadRequestException("Invalid request. Try again");
            }

            ByteArrayOutputStream baos = backupService.downloadBackup(databaseName);
            if (baos.size() > 10000) {
                response.setHeader("Content-Type", "application/octet-stream");
                response.setHeader("Content-Length", Integer.valueOf(baos.size()).toString());
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(baos.toByteArray());
                outputStream.close();
                response.flushBuffer();
                System.out.println("Database download completed.");
            } else {
                message = "Database download failed. Invalid database backup";
                response.setHeader("Content-Type", "application/octet-stream");
                response.sendError(500, message);
                System.out.println("Database download failed.");
            }
        } catch (Exception e) {
            message = "Invalid request";
            response.setHeader("Content-Type", "application/octet-stream");
            response.sendError(500, message);
        }

    }

    @GetMapping("/backup/backup-available")
    public ResponseEntity<List<String>> backupAvailable() {
        return new ResponseEntity<>(backupService.backupAvailable(), HttpStatus.OK);
    }

}

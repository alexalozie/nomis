package org.nomisng.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.nomisng.domain.entity.FileData;
import org.nomisng.service.FileDataService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FileDataResource {
    private final FileDataService fileDataService;


    @PostMapping("/upload-file")
    public FileDataResource.Response uploadFile(@RequestParam("file") MultipartFile file) {
        FileData fileName = fileDataService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download-file/")
                .path(fileName.getFileName())
                .toUriString();

        return new FileDataResource.Response(fileName.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/upload-multiple-files")
    public List<Response> uploadMultiple(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/download-file/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        FileData fileData = fileDataService.getFile(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileData.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileData.getFileName() + "\"")
                .body(new ByteArrayResource(fileData.getData()));
    }

    @Data
    static class Response {
        private String fileName;
        private String fileDownloadUri;
        private String fileType;
        private long size;

        public Response(String fileName, String fileDownloadUri, String fileType, long size) {
            this.fileName = fileName;
            this.fileDownloadUri = fileDownloadUri;
            this.fileType = fileType;
            this.size = size;
        }
    }
}

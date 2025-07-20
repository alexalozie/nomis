package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.entity.FileData;
import org.nomisng.repository.FileDataRepository;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.apierror.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileDataService {

    private final FileDataRepository fileDataRepository;

    public FileData storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Invalid file name " + fileName);
            }

            FileData dbFile = new FileData(fileName, file.getContentType(), file.getBytes());

            return fileDataRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public FileData getFile(String fileId) {
        return fileDataRepository.findById(UUID.fromString(fileId))
                .orElseThrow(() -> new EntityNotFoundException(FileDataService.class, "File not found with id " + fileId));
    }
}

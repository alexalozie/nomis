package org.nomisng.web;

import io.github.jhipster.web.util.HeaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.nomisng.domain.dto.SchoolDTO;
import org.nomisng.repository.SchoolRepository;
import org.nomisng.service.SchoolService;
import org.nomisng.util.PaginationUtil;
import org.nomisng.util.ResponseUtil;
import org.nomisng.web.apierror.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SchoolResource {
    private static final String ENTITY_NAME = "school";
    private final Logger log = LoggerFactory.getLogger(SchoolResource.class);
    private final SchoolRepository schoolRepository;
    @Value("NOMIS")
    private String applicationName;
    private final SchoolService schoolService;

    public SchoolResource(SchoolRepository schoolRepository, SchoolService schoolService) {
        this.schoolRepository = schoolRepository;
        this.schoolService = schoolService;
    }


    @PostMapping("/schools")
    public ResponseEntity<SchoolDTO> createSchool(@RequestBody SchoolDTO schoolDTO) throws URISyntaxException {
        log.debug("REST request to save School : {}", schoolDTO);
        if (schoolDTO.getId() != null) {
            return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SchoolDTO result = schoolService.saveSchool(schoolDTO);
        return ResponseEntity.created(new URI("/api/schools/" + result.getId()))
                .body(result);
    }

    @PutMapping("/schools")
    public ResponseEntity<SchoolDTO> updateSchool(@PathVariable(value = "id", required = false) final Long id,
                                              @RequestBody SchoolDTO schoolDTO) throws URISyntaxException {
        log.debug("REST request to update School : {}", schoolDTO);
        if (schoolDTO.getId() == null) {
            return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!Objects.equals(id, schoolDTO.getId())) {
            return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!schoolRepository.existsById(id)) {
            return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SchoolDTO result = schoolService.saveSchool(schoolDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolDTO.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "schools/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchoolDTO> partialUpdateSchool(@PathVariable(value = "id", required = false) final Long id,
                                                     @RequestBody SchoolDTO schoolDTO) throws URISyntaxException {
        log.debug("REST request to partially update School : {}", schoolDTO);
        if (schoolDTO.getId() == null) {
            return new  ResponseEntity<SchoolDTO>(HttpStatus.BAD_REQUEST);
        }
        if (!Objects.equals(id, schoolDTO.getId())) {
            return new  ResponseEntity<SchoolDTO>(HttpStatus.BAD_REQUEST);
        }

        if (!schoolRepository.existsById(id)) {
            return new  ResponseEntity<SchoolDTO>(HttpStatus.BAD_REQUEST);
        }

        Optional<SchoolDTO> result = schoolService.partialUpdateSchool(schoolDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true,
                        ENTITY_NAME, schoolDTO.getId().toString()));
    }
    @GetMapping("/schools/{id}")
    public ResponseEntity<SchoolDTO> getSchool(@PathVariable Long id) {
        log.debug("REST request to get school : {}", id);
        Optional<SchoolDTO> schoolDTO = schoolService.findOneSchool(id);
        return ResponseUtil.wrapOrNotFound(schoolDTO);
    }

    @GetMapping("/schools")
    public ResponseEntity<List<SchoolDTO>> getSchools(Pageable pageable) {
        log.debug("REST request to get all school");
        Page<SchoolDTO> page = schoolService.findAllSchools(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @PostMapping("/schools/import")
    public ResponseEntity<String> importSchool(@RequestParam("file") @PathVariable MultipartFile file) throws IOException {
        String fileExtension = StringUtils.substring(file.getOriginalFilename(), file.getOriginalFilename().lastIndexOf("."));
        String excelType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if (!file.getContentType().equalsIgnoreCase(excelType)) {
            throw new BadRequestAlertException("Invalid file format", ENTITY_NAME, "fileinvalid");
        }

        FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
        schoolService.importSchoolData(fileInputStream, fileExtension);

        return ResponseEntity.ok().body("School data import completed successfully.");
    }

    @DeleteMapping("/schools/{schoolId}")
    public ResponseEntity<SchoolDTO> deleteCustomer(@PathVariable Long schoolId){
        Optional<SchoolDTO> schoolDTO = schoolService.findOneSchool(schoolId);

        if(schoolDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        schoolService.deleteSchool(schoolId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}

package org.nomisng.web;


import io.github.jhipster.web.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.DrugDTO;
import org.nomisng.repository.DrugRepository;
import org.nomisng.service.DrugService;
import org.nomisng.util.PaginationUtil;
import org.nomisng.util.ResponseUtil;
import org.nomisng.web.apierror.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class DrugResource {
    private static final String ENTITY_NAME = "drug";
    private final Logger log = LoggerFactory.getLogger(DrugResource.class);
    private final DrugRepository drugRepository;
    @Value("NOMIS")
    private String applicationName;
    private final DrugService drugService;

    @PostMapping("/drugs")
    public ResponseEntity<DrugDTO> createDrug(@RequestBody DrugDTO drugDTO) throws URISyntaxException {
        if (drugDTO.getId() != null) {
            throw new BadRequestAlertException("A new drug cannot already have an ID", ENTITY_NAME, "id exists");
        }
        DrugDTO result = drugService.saveDrug(drugDTO);
        return ResponseEntity
                .created(new URI("/api/drugs/" + result.getId()))
                .body(result);
    }

    @PutMapping("/drugs")
    public ResponseEntity<DrugDTO> updateDrug(@PathVariable(value = "id", required = false) final Long id,
                                           @RequestBody DrugDTO drugDTO) {
        log.debug("REST request to update Drug : {}", drugDTO);
        if (drugDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!Objects.equals(id, drugDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!drugRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DrugDTO result = drugService.saveDrug(drugDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, drugDTO.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DrugDTO> partialUpdateDrug(@PathVariable(value = "id", required = false) final Long id,
                                              @RequestBody DrugDTO drugDTO) throws URISyntaxException {
        log.debug("REST request to partially update Drug : {}", drugDTO);
        if (drugDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, drugDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!drugRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DrugDTO> result = drugService.partialUpdateDrug(drugDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true,
                        ENTITY_NAME, drugDTO.getId().toString()));
    }
    @GetMapping("/drugs/{id}")
    public ResponseEntity<DrugDTO> getDrug(@PathVariable Long id) {
        log.debug("REST request to get drug : {}", id);
        Optional<DrugDTO> drugDTO = drugService.findOneDrug(id);
        return ResponseUtil.wrapOrNotFound(drugDTO);
    }

    @GetMapping("/drugs")
    public ResponseEntity<List<DrugDTO>> getDrugs(Pageable pageable) {
        log.debug("REST request to get all drug");
        Page<DrugDTO> page = drugService.findAllDrug(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


}

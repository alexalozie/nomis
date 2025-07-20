package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.entity.Regimen;
import org.nomisng.repository.RegimenRepository;
import org.nomisng.util.PaginationUtil;
import org.nomisng.web.apierror.BadRequestAlertException;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class RegimenResource {
    private static final String ENTITY_NAME = "regimen";
    private final RegimenRepository regimenRepository;

    /**
     * POST  /regimens : Create a new regimen.
     *
     * @param regimen the regimen to create
     * @return the ResponseEntity with status 201 (Created) and with body the new regimen, or
     * with status 400 (Bad Request) if the regimen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regimens")
    public ResponseEntity<Regimen> createRegimen(@RequestBody Regimen regimen) throws URISyntaxException {
        log.debug("REST request to save regimen : {}", regimen);
        if (regimen.getId() != null) {
            throw new BadRequestAlertException("A new regimen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Regimen result = regimenRepository.save(regimen);
        return ResponseEntity.created(new URI("/api/regimens/" + result.getId())).body(result);
    }

    /**
     * PUT  /regimens : Updates an existing regimen.
     *
     * @param regimen the regimen to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated regimen,
     * or with status 400 (Bad Request) if the regimen is not valid,
     * or with status 500 (Internal Server Error) if the regimen couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regimens")
    public ResponseEntity<Regimen> updateRegimen(@RequestBody Regimen regimen) throws URISyntaxException {
        log.debug("REST request to update Regimen : {}", regimen);
        if (regimen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        return ResponseEntity.ok(regimenRepository.save(regimen));
    }

    /**
     * GET  /regimens/:id : get the "id" regimen.
     *
     * @param id the id of the regimen to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the regimen, or with status 404 (Not Found)
     */
    @GetMapping("/regimens/{id}")
    public ResponseEntity<Regimen> getRegimen(@PathVariable Long id) {
        log.debug("REST request to get regimen : {}", id);
        Regimen regimen = regimenRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Regimen.class, "Id", id + ""));
        return ResponseEntity.ok(regimen);
    }

    /**
     * GET  /regimens : get the all regimen.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the regimen, or with status 404 (Not Found)
     */
    @GetMapping("/regimens")
    public ResponseEntity<List<Regimen>> getRegimens(Pageable pageable) {
        log.debug("REST request to get all regimen");
        Page<Regimen> regimenPage = regimenRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), regimenPage);
        return new ResponseEntity<>(regimenPage.getContent(), headers, HttpStatus.OK);
    }

}

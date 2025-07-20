package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.DonorDTO;
import org.nomisng.domain.entity.Donor;
import org.nomisng.service.DonorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/donors")
@Slf4j
@RequiredArgsConstructor
public class DonorResource {
    private final DonorService donorService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<DonorDTO>> getAllDonors() {
        return ResponseEntity.ok(donorService.getAllDonors());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<DonorDTO> getDonor(@PathVariable Long id) {
        return ResponseEntity.ok(donorService.getDonor(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Donor> save(@Valid @RequestBody DonorDTO donorDTO) {
        return ResponseEntity.ok(donorService.saveDonor(donorDTO));
    }

    //@PreAuthorize("hasAnyRole('System Administrator', 'Administrator', 'Admin')")
    public ResponseEntity<Donor> update(@PathVariable Long id, @Valid @RequestBody DonorDTO donorDTO) {
        return ResponseEntity.ok(donorService.updateDonor(id, donorDTO));
    }

}

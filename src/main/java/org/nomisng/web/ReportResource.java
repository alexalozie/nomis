package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.ReportDetailDTO;
import org.nomisng.domain.dto.ReportInfoDTO;
import org.nomisng.domain.entity.ReportInfo;
import org.nomisng.service.birt.BirtReportService;
import org.nomisng.service.birt.OutputType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Controller
@RequiredArgsConstructor
public class ReportResource {
    private final BirtReportService reportService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<ReportInfo> save(@RequestBody ReportInfoDTO reportInfoDTO) {
        reportInfoDTO.setId(0L);
        return ResponseEntity.ok(reportService.save(reportInfoDTO));
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public void generateReport(@RequestBody ReportDetailDTO data, HttpServletResponse response, HttpServletRequest request) {
        String reportFormat = data.getReportFormat().toLowerCase().trim();
        OutputType format = OutputType.from(reportFormat);
        reportService.generateReport(data, format, data.getParameters(), response, request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<ReportInfo> update(@PathVariable Long id, @RequestBody ReportInfoDTO reportInfoDTO) {
        return ResponseEntity.ok(reportService.update(id, reportInfoDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.delete(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<ReportInfoDTO>> getAllReports() {
        return ResponseEntity.ok(reportService.getReports());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<ReportInfo> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReportId(id));
    }

}

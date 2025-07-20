package org.nomisng.web.chart;

import lombok.RequiredArgsConstructor;
import org.nomisng.service.chart.VisualizationChartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/visualization")
public class VisualizationResource {
    private final VisualizationChartService chartService;

    @GetMapping("/enrolment-stream")
    public ResponseEntity<Object> getServByEnrolmentStreamChart() {
        return ResponseEntity.ok(chartService.getServByEnrolmentStreamChart());
    }

    @GetMapping("/hiv-status")
    public ResponseEntity<Object> getVcHivStatusChart() {
        return ResponseEntity.ok(chartService.getVcHivStatusChart());
    }

    @GetMapping("/map-data")
    public ResponseEntity<Object> getMapData() {
        return ResponseEntity.ok(chartService.getMapData());
    }

    @GetMapping("/viral-load-eligibility")
    public ResponseEntity<Object> getVcViralLoadEligibilityChart() {
        return ResponseEntity.ok(chartService.getVcViralLoadCascadeChart());
    }

    @GetMapping("/serv-caregiver")
    public ResponseEntity<Object> getServComprehensiveAndCaregiverChart() {
        return ResponseEntity.ok(chartService.getServComprehensiveAndCaregiverChart());
    }

}

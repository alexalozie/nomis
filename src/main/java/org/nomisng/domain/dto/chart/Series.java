package org.nomisng.domain.dto.chart;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class Series {
    private String type;
    private String name;
    private double[] data;
    private Marker marker;
}

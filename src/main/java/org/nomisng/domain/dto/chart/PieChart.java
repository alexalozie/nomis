package org.nomisng.domain.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PieChart {
    private String name;
    private double y;
    private boolean sliced;
    private boolean selected;
}

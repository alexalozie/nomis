package org.nomisng.domain.dto.chart;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ColumnCombinationChart {
    private Title title;
    private XAxis xAxis;
    private YAxis yAxis;
    private Labels labels;
    private Series[] series;
}

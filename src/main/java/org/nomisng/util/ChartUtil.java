package org.nomisng.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.*;

@Component
@Data
public class ChartUtil {
    private Map<String, List> xAxis = new HashMap();
    private Map<String, Object> yAxis = new HashMap();
    private Map<String, Object> yAxisTitle = new HashMap();

    public List<String> setMonthCategories() {
        List<String> months = new ArrayList<>();
        for (int i = 5; i > -1; i--) {
            if (i == 0) {
                months.add(LocalDate.now().getMonth().toString());
                continue;
            }
            months.add(LocalDate.now().minusMonths(i).getMonth().toString());
        }
        return months;
    }

    public List<String> setGenderCategory() {
        return  Arrays.asList("Male", "Female");
    }

    public List<String> setAgeCategories() {
        return Arrays.asList("<1yr", "1-4yrs", "5-9yrs", "10-14yrs", "15-17yrs");
    }

    public List<String> setVCComprehensiveAndCaregiverCategories() {
        return Arrays.asList("OVC_Serv Comprehensive", "OVC_Serv Caregiver");
    }

    public List<String> setVLCategories() {
        return Arrays.asList("OVC Receiving ART", "Eligible to receive a VL test", "Documented VL result",
                "Documented virally suppressed");
    }
    public List<String> setHivStatusCategories() {
        return Arrays.asList("OVC_Serv <18y/o in the Comprehensive program", "HIV-negative status reported", "HIV-positive status reported",
                "HIV-unknown status reported", "Receiving ART", "HIV-test not reported");
    }
    public void setXAxis(List<String> categoryList) {
        xAxis.put("categories", categoryList);
    }

    public Map<String, List> getXAxis() {
        if (xAxis.isEmpty() || xAxis == null) {
            setXAxis(setMonthCategories());
        }
        return xAxis;
    }

    public void setYAxis() {
        yAxisTitle.put("text", "Values");
        yAxis.put("title", yAxisTitle);
    }

    public Map<String, Object> getYAxis() {
        if (yAxis.isEmpty() || yAxis == null) {
            setYAxis();
        }
        return yAxis;
    }

    public Map<String, Object> getMainMap(List<Object> data, String name, String stack, String type, String title) {
        Map<String, Object> mainMap = new HashMap<>();
        mainMap.put("data", data);
        if (name != null) {
            mainMap.put("data", data);
        }
        mainMap.put("name", name);
        if (stack != null) {
            mainMap.put("stack", stack);
        }
        if (type != null) {
            mainMap.put("type", type);
        }
        if (title != null) {
            mainMap.put("title", title);
        }
        return mainMap;
    }

    public Map<String, Object> buildMainMap(String type, String chartTitle, String subTitle, Map<String, List> xAxis, Map<String, Object> yAxis, List<Object> columnSeries, Map<String, Object> mapColumnSeries) {
        Map<String, Object> mainMap = new HashMap<>();
        mainMap.put("type", type);
        mainMap.put("text", chartTitle);
        mainMap.put("subTitle", subTitle);
        mainMap.put("xAxis", xAxis);
        mainMap.put("yAxis", yAxis);
        if (columnSeries != null && mapColumnSeries == null) {
            mainMap.put("series", columnSeries);
        } else if (mapColumnSeries != null && columnSeries == null) {
            mainMap.put("series", mapColumnSeries);
        }
        return mainMap;
    }

    private void clearChartList() {
/*        series.clear();
        data.clear();
        data.clear();*/
        xAxis.clear();
        yAxis.clear();
        yAxisTitle.clear();
        //columnSeries.clear();
    }

}

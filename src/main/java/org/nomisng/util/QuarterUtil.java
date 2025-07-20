package org.nomisng.util;

import org.nomisng.config.ContextProvider;
import org.nomisng.domain.dto.QuarterDto;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

public class QuarterUtil {

    private final JdbcTemplate jdbcTemplate = ContextProvider.getBean(JdbcTemplate.class);

    public QuarterDto getQuarter(LocalDate reportingPeriod){

        QuarterDto quarterDto = new QuarterDto();
        int rMonth = reportingPeriod.getMonthValue();
        int rYear = reportingPeriod.getYear();

        if(rMonth <= 3){
            quarterDto.setPreviousQuarterStartDate(LocalDate.of(rYear - 1, 10, 1));
            quarterDto.setPreviousQuarterEndDate(LocalDate.of(rYear - 1, 12, 31));
            quarterDto.setCurrentQuarterStartDate(LocalDate.of(rYear, 1, 1));
            quarterDto.setCurrentQuarterEndDate(LocalDate.of(rYear, 3, 31));

        } else if(rMonth <= 6){
            quarterDto.setPreviousQuarterStartDate(LocalDate.of(rYear, 1, 1));
            quarterDto.setPreviousQuarterEndDate(LocalDate.of(rYear, 3, 31));
            quarterDto.setCurrentQuarterStartDate(LocalDate.of(rYear, 4, 1));
            quarterDto.setCurrentQuarterEndDate(LocalDate.of(rYear, 6, 30));

        } else if(rMonth <= 9){
            quarterDto.setPreviousQuarterStartDate(LocalDate.of(rYear, 4, 1));
            quarterDto.setPreviousQuarterEndDate(LocalDate.of(rYear, 6, 30));
            quarterDto.setCurrentQuarterStartDate(LocalDate.of(rYear, 7, 1));
            quarterDto.setCurrentQuarterEndDate(LocalDate.of(rYear, 9, 31));

        } else if(rMonth <= 12){
            quarterDto.setPreviousQuarterStartDate(LocalDate.of(rYear, 7, 1));
            quarterDto.setPreviousQuarterEndDate(LocalDate.of(rYear, 9, 30));
            quarterDto.setCurrentQuarterStartDate(LocalDate.of(rYear, 10, 1));
            quarterDto.setCurrentQuarterEndDate(LocalDate.of(rYear, 12, 31));
        }

        return quarterDto;
    }


}

package org.nomisng.service.impl;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.nomisng.domain.dto.SchoolDTO;
import org.nomisng.domain.entity.ApplicationCodeset;
import org.nomisng.domain.entity.OrganisationUnit;
import org.nomisng.domain.entity.School;
import org.nomisng.domain.mapper.SchoolMapper;
import org.nomisng.repository.ApplicationCodesetRepository;
import org.nomisng.repository.OrganisationUnitRepository;
import org.nomisng.repository.SchoolRepository;
import org.nomisng.service.SchoolService;
import org.nomisng.service.vm.SchoolCsvMapper;
import org.nomisng.web.apierror.BadRequestAlertException;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {
    private final SchoolMapper schoolMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SchoolRepository schoolRepository;
    private final OrganisationUnitRepository organisationUnitRepository;
    private final Logger log = LoggerFactory.getLogger(SchoolServiceImpl.class);

    private final ApplicationCodesetRepository applicationCodesetRepository;

    private static final int UNARCHIVED = 0;

    @Override
    public Optional<SchoolDTO> findOneSchool(Long id) {
        return schoolRepository.findById(id).map(schoolMapper::toSchoolDTO);
    }

    @Override
    public SchoolDTO saveSchool(SchoolDTO schoolDTO) {
        School school = schoolMapper.toSchool(schoolDTO);
        school = schoolRepository.save(school);
        return schoolMapper.toSchoolDTO(school);
    }

    @Override
    public void deleteSchool(Long id) {
        schoolRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(School.class,"id", id + ""));
        schoolRepository.deleteById(id);
    }

    @Override
    public Page<SchoolDTO> findAllSchools(Pageable pageable) {
        return schoolRepository.findAll(pageable).map(schoolMapper::toSchoolDTO);
    }

    @Override
    public Optional<SchoolDTO> partialUpdateSchool(SchoolDTO schoolDTO) {
        return schoolRepository.findById(schoolDTO.getId()).map(existingSchool -> {
            schoolMapper.partialUpdate(existingSchool, schoolDTO);
            return existingSchool;
        }).map(schoolRepository::save).map(schoolMapper::toSchoolDTO);
    }

    @Override
    public Boolean checkIfSchoolExists(String name, Long stateId, Long lgaId) {
        Optional<School> school = schoolRepository.getByNameAndStateIdAndLgaId(name, stateId, lgaId);
        Boolean status = false;
        if (school.isPresent()) {
            status = true;
        }
        return status;
    }

    @Override
    public void importSchoolData(FileInputStream fileInputStream, String fileExtension) {
        String excelType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        try {
            if (fileExtension.equalsIgnoreCase(".csv")) {
                importCsvData(fileInputStream);
            } else if (fileExtension.equalsIgnoreCase(".xlsx")) {
                importExcelData(fileInputStream);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void importCsvData(FileInputStream fileInputStream) {
        try (Reader reader = new InputStreamReader(fileInputStream)) {
            ColumnPositionMappingStrategy<SchoolCsvMapper> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(SchoolCsvMapper.class);
            String[] fields = {"SN", "SCHOOL_NAME", "SCHOOL_TYPE", "STATE", "LGA", "SCHOOL_ADDRESS"};
            strategy.setColumnMapping(fields);
            CsvToBean<SchoolCsvMapper> csvToBean = new CsvToBeanBuilder<SchoolCsvMapper>(reader).withSkipLines(1).withMappingStrategy(strategy).withIgnoreLeadingWhiteSpace(true).build();
            List<SchoolCsvMapper> schoolCsvMapperList = csvToBean.parse();
            List<School> schoolList = new ArrayList<>();
            List<ApplicationCodeset> applicationCodesetList = applicationCodesetRepository.findAll();
            schoolCsvMapperList.forEach(schoolCsvMapper -> {
//                ApplicationCodeset codeset = applicationCodesetList.stream()
//                        .filter(d -> d.getCodesetGroup().equalsIgnoreCase("school_type") )
//                        .filter(d -> d.getDisplay().equalsIgnoreCase(schoolCsvMapper.getType()))
//                        .findFirst().orElse(null);
//                Long schTypeId = (codeset != null) ? codeset.getId() : 0L;
                School school = getSchoolData(schoolCsvMapper.getName(), schoolCsvMapper.getType(),
                        schoolCsvMapper.getState(), schoolCsvMapper.getLga(), schoolCsvMapper.getAddress());
                schoolList.add(school);
            });
            schoolRepository.saveAll(schoolList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void importExcelData(FileInputStream fileInputStream) {
        int colNumber = 0;
        int rowNumber = 0;
        int rowMax = 0;
        try (InputStream inputStream = fileInputStream) {
            List<School> schoolList = new ArrayList<>();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet("school");
            String schName;
            String schState;
            String schLga;
            String schAddress;
            List<ApplicationCodeset> applicationCodesetList = applicationCodesetRepository.findAll();
            for (Row row : sheet) {
               if (row.getRowNum() >=1 ) {
                   String schType = StringUtils.trimToEmpty(row.getCell(2).toString());
//                   ApplicationCodeset codeset = applicationCodesetList.stream()
//                           .filter(d -> d.getCodesetGroup().equalsIgnoreCase("school_type") )
//                           .filter(d -> d.getDisplay().equalsIgnoreCase(schType))
//                           .findFirst().orElse(null);
                   schName = StringUtils.trimToEmpty(row.getCell(1).toString());
                   schState = StringUtils.trimToEmpty(row.getCell(3).toString());
                   schLga = StringUtils.trimToEmpty(row.getCell(4).toString());
                   schAddress = StringUtils.trimToEmpty(row.getCell(5).toString());
                  // Long schTypeId = (codeset != null) ? codeset.getId() : 0L;
                   School school = getSchoolData(schName, schType, schState, schLga, schAddress);

                   schoolList.add(school);
               }
            }
            schoolRepository.saveAll(schoolList);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private School getSchoolData(String schName, String schType, String schState, String schLga, String schAddress) {
        Optional<OrganisationUnit> organisationUnit = organisationUnitRepository.findByNameAndArchived(schState, 0);
        School school = new School();
        school.setName(schName);
        if (schType != null) {
            school.setType(schType);
        }
        if (organisationUnit.isPresent()) {
            school.setStateId(organisationUnit.get().getId());
        }
        organisationUnit = organisationUnitRepository.findByNameAndArchived(schLga, 0);
        if (organisationUnit.isPresent()) {
            school.setLgaId(organisationUnit.get().getId());
        }
        school.setAddress(schAddress);
        school.setArchived(UNARCHIVED);
        return school;
    }

}

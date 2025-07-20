package org.nomisng.domain.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.nomisng.domain.dto.ReportInfoDTO;
import org.nomisng.domain.entity.ReportInfo;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportInfoMapper {
    ReportInfoDTO toReportInfoDTO(ReportInfo reportInfo);
    ReportInfo toReportInfo(ReportInfoDTO reportInfoDTO);


    @Named("mapWithoutTemplate")
    @Mapping(target = "resourceObject", ignore = true)
    ReportInfoDTO mapWithoutTemplate(ReportInfo reportInfo);

    @IterableMapping(qualifiedByName="mapWithoutTemplate")
    List<ReportInfoDTO> toReportInfoDTOs(List<ReportInfo> reportInfos);
}

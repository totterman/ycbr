package fi.smartbass.ycbr.inspection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = { DateTimeFormatter.class, OffsetDateTime.class })
public interface InspectionMapper {

    @Mapping(target = "timestamp", expression = "java(entity.getTimestamp().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "completed", expression = "java(null == entity.getCompleted() ? null :entity.getCompleted().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    InspectionDTO toDTO(InspectionEntity entity);

    @Mapping(target = "timestamp", expression = "java(OffsetDateTime.parse(dto.timestamp(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "completed", expression = "java(null == dto.completed() ? null : OffsetDateTime.parse(dto.completed(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    InspectionEntity toEntity(InspectionDTO dto);

    List<InspectionDTO> toDTOList(List<InspectionEntity> entities);
    List<InspectionEntity> toEntityList(List<InspectionDTO> dtos);

    Iterable<InspectionDTO> toDTOs(Iterable<InspectionEntity> entities);
    Iterable<InspectionEntity> toEntities(Iterable<InspectionDTO> dtos);
/*
    InspectionDataDto toDataDto(InspectionData data);
    HullDataDto toHullDataDto(HullData data);
    RigDataDto toRigDataDto(RigData data);
*/
    InspectionData toData(InspectionDataDto dto);
    HullData toHullData(HullDataDto dto);
    RigData toRigData(RigDataDto dto);
    EngineData toEngineData(EngineDataDto dto);
}

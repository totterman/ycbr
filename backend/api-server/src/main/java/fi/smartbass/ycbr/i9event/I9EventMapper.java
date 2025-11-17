package fi.smartbass.ycbr.i9event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = { DateTimeFormatter.class, OffsetDateTime.class })
public interface I9EventMapper {

    @Mapping(target = "day", expression = "java(i9Event.getStarts().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "starts", expression = "java(i9Event.getStarts().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "ends", expression = "java(i9Event.getEnds().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "boats", expression = "java(i9Event.getBoats().size())")
    @Mapping(target = "inspectors", expression = "java(i9Event.getInspectors().size())")
    I9EventDTO toDTO(I9Event i9Event);

    @Mapping(target = "starts", expression = "java(OffsetDateTime.parse(i9EventDTO.starts(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "ends", expression = "java(OffsetDateTime.parse(i9EventDTO.ends(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "boats", ignore = true)
    @Mapping(target = "inspectors", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    I9Event toEntity(I9EventDTO i9EventDTO);

    List<I9EventDTO> toDTOList(List<I9Event> I9Events);
    List<I9Event> toEntityList(List<I9EventDTO> dtos);

    Iterable<I9EventDTO> toDTOs(Iterable<I9Event> I9Events);
    Iterable<I9Event> toEntitys(Iterable<I9EventDTO> dtos);

}

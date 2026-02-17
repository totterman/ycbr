package fi.smartbass.ycbr.i9event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = { DateTimeFormatter.class, OffsetDateTime.class })
public interface I9EventMapper {

    @Mapping(target = "day", expression = "java(entity.getStarts().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "starts", expression = "java(entity.getStarts().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "ends", expression = "java(entity.getEnds().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
//    @Mapping(target = "bookings", expression = "java(entity.getBookings())")
    @Mapping(target = "boats", expression = "java(entity.getBoats().size())")
    @Mapping(target = "inspectors", expression = "java(entity.getInspectors().size())")
    I9EventDto toDTO(I9EventEntity entity);

    @Mapping(target = "starts", expression = "java(OffsetDateTime.parse(dto.starts(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "ends", expression = "java(OffsetDateTime.parse(dto.ends(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "boats", ignore = true)
    @Mapping(target = "inspectors", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    I9EventEntity toEntity(I9EventDto dto);

    @Mapping(target = "i9eventId", ignore = true)
    @Mapping(target = "starts", expression = "java(OffsetDateTime.parse(dto.starts(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "ends", expression = "java(OffsetDateTime.parse(dto.ends(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))")
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "boats", ignore = true)
    @Mapping(target = "inspectors", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    I9EventEntity toEntity(NewI9EventDto dto);

    List<I9EventDto> toDTOList(List<I9EventEntity> i9EventEntities);
    List<I9EventEntity> toEntityList(List<I9EventDto> dtos);

    Iterable<I9EventDto> toDTOs(Iterable<I9EventEntity> i9Events);
    Iterable<I9EventEntity> toEntities(Iterable<I9EventDto> dtos);

    BoatBookingDto toBoatBookingDto(BoatBooking boatBooking);
    InspectorRegistrationDto toInspectorRegistrationDTO(InspectorRegistration registration);
}

package fi.smartbass.ycbr.register;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BoatMapper {

    @Mappings({})
    BoatDto toDTO(BoatEntity entity);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "modifiedAt", ignore = true),
            @Mapping(target = "modifiedBy", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    BoatEntity toEntity(BoatDto dto);

    @Mappings({
            @Mapping(target = "boatId", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "modifiedAt", ignore = true),
            @Mapping(target = "modifiedBy", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    BoatEntity toEntity(NewBoatDto dto);

    List<BoatDto> toDTOList(List<BoatEntity> entities);
    List<BoatEntity> toEntityList(List<BoatDto> dtos);

    Iterable<BoatDto> toDTOs(Iterable<BoatEntity> entities);
    Iterable<BoatEntity> toEntities(Iterable<BoatDto> dtos);

}

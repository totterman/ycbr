package fi.smartbass.ycbr.register;

import org.mapstruct.*;

import java.util.List;
import java.util.Set;

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
            @Mapping(target = "sailnr", ignore = true),
            @Mapping(target = "hullnr", ignore = true),
            @Mapping(target = "material", ignore = true),
            @Mapping(target = "colour", ignore = true),
            @Mapping(target = "sailarea", ignore = true),
            @Mapping(target = "fuel", ignore = true),
            @Mapping(target = "water", ignore = true),
            @Mapping(target = "septi", ignore = true),
            @Mapping(target = "berths", ignore = true),
            @Mapping(target = "radio", ignore = true),
            @Mapping(target = "home", ignore = true),
            @Mapping(target = "winter", ignore = true),
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

    EngineEntity toEngineEntity(EngineDto dto);
    Set<EngineEntity> toEngineEntitys(Set<EngineDto> dtos);
    EngineDto toEngineDTO(EngineEntity entity);
    Set<EngineDto> toEngineDTOs(Set<EngineEntity> entities);
}

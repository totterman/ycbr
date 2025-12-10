package fi.smartbass.ycbr.register;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BoatMapper {

    @Mappings({})
    BoatDTO toDTO(BoatEntity entity);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "modifiedAt", ignore = true),
            @Mapping(target = "modifiedBy", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    BoatEntity toEntity(BoatDTO dto);

    List<BoatDTO> toDTOList(List<BoatEntity> entities);
    List<BoatEntity> toEntityList(List<BoatDTO> dtos);

    Iterable<BoatDTO> toDTOs(Iterable<BoatEntity> entities);
    Iterable<BoatEntity> toEntities(Iterable<BoatDTO> dtos);

}

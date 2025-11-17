package fi.smartbass.ycbr.register;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BoatMapper {

    @Mappings({})
    BoatDTO toDTO(Boat boat);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "modifiedAt", ignore = true),
            @Mapping(target = "modifiedBy", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    Boat toEntity(BoatDTO boatDTO);

    List<BoatDTO> toDTOList(List<Boat> boats);
    List<Boat> toEntityList(List<BoatDTO> dtos);

    Iterable<BoatDTO> toDTOs(Iterable<Boat> boats);
    Iterable<Boat> toEntitys(Iterable<BoatDTO> dtos);

}

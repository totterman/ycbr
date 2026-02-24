package fi.smartbass.ycbr.register;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BoatMapperTest {

    private BoatMapper mapper = Mappers.getMapper(BoatMapper.class);

    private final Set<EngineDto> engineDtos = Set.of(new EngineDto(0, "1995", "Yamaha", "ModelZ", "Serial123", 75.0));
    private final Set<EngineEntity> engineEntities = Set.of(new EngineEntity(0, "1995", "Yamaha", "ModelZ", "Serial123", 75.0));
    private final BoatDto dto1 = new BoatDto(UUID.randomUUID(), "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", engineDtos, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2");
    private final BoatDto dto2 = new BoatDto(UUID.randomUUID(), "club2", "cert2", "AnotherBoat", "S", "SailsRUs", "2019", "Reg5678", "S6554", "22", "G", "2006", "Navy Blue", 10.0, 3.5, 2.0, 17.2,6.8, 64.0, "S", engineDtos, 150.0, 250.0, 70.0, 8, "DK5678", "Hamburg", null, "Owner3", "Owner4");
    private final List<BoatDto> dtos = List.of(dto1, dto2);

    private final BoatEntity boat1 = new BoatEntity(UUID.randomUUID(), "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", engineEntities, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2", null, null, null, null, 0);
    private final BoatEntity boat2 = new BoatEntity(UUID.randomUUID(), "club2", "cert2", "AnotherBoat", "S", "SailsRUs", "2019", "Reg5678", "S6554", "22", "G", "2006", "Navy Blue", 10.0, 3.5, 2.0, 17.2,6.8, 64.0, "S", engineEntities, 150.0, 250.0, 70.0, 8, "DK5678", "Hamburg", null, "Owner3", "Owner4", null, null, null, null, 0);
    private final List<BoatEntity> boats = List.of(boat1, boat2);

    @Test
    @DisplayName("Maps DTO to entity and back")
    void dtoToEntityAndBack() {
        assertNotNull(mapper);

        BoatEntity entity = mapper.toEntity(dto1);
        assertEquals(entity.getBoatId(), dto1.boatId());
        assertEquals(entity.getName(), dto1.name());
        assertEquals(entity.getOwner(), dto1.owner());
        assertEquals(entity.getSign(), dto1.sign());
        assertEquals(entity.getMake(), dto1.make());
        assertEquals(entity.getModel(), dto1.model());

        BoatDto dto = mapper.toDTO(entity);
        assertEquals(entity.getBoatId(), dto.boatId());
        assertEquals(entity.getName(), dto.name());
        assertEquals(entity.getOwner(), dto.owner());
        assertEquals(entity.getSign(), dto.sign());
        assertEquals(entity.getMake(), dto.make());
        assertEquals(entity.getModel(), dto.model());
    }
}

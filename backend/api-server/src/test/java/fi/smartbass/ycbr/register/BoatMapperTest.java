package fi.smartbass.ycbr.register;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BoatMapperTest {

    private BoatMapper mapper = Mappers.getMapper(BoatMapper.class);

    private final BoatDTO dto1 = new BoatDTO(1001L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988");
    private final BoatDTO dto2 = new BoatDTO(1002L, "owner2", "AnotherBoat", "Reg5678", "SailsRUs", "2019", 10.0, 2.0, 3.5, 4500.0, "Inboard", "1995");
    private final List<BoatDTO> dtos = List.of(dto1, dto2);

    private final Boat boat1 = new Boat(null, "OwnerName", "BoatName", "Sign123", "MakeX", "ModelY", 10.5, 2.5, 3.5, 5000.0, "", "1982", null, "null", null, null, 0);
    private final Boat boat2 = new Boat(null, "AnotherOwner", "AnotherBoat", "Sign456", "MakeA", "ModelB", 12.0, 3.0, 4.0, 6000.0, "", "1990", null, "null", null, null, 0);
    private final List<Boat> boats = List.of(boat1, boat2);

    @Test
    @DisplayName("Maps DTO ends entity and back")
    void dtoToEntityAndBack() {
        assertNotNull(mapper);

        Boat entity = mapper.toEntity(dto1);
        assertEquals(entity.getId(), dto1.id());
        assertEquals(entity.getName(), dto1.name());
        assertEquals(entity.getOwner(), dto1.owner());
        assertEquals(entity.getSign(), dto1.sign());
        assertEquals(entity.getMake(), dto1.make());
        assertEquals(entity.getModel(), dto1.model());

        BoatDTO dto = mapper.toDTO(entity);
        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getName(), dto.name());
        assertEquals(entity.getOwner(), dto.owner());
        assertEquals(entity.getSign(), dto.sign());
        assertEquals(entity.getMake(), dto.make());
        assertEquals(entity.getModel(), dto.model());
    }
}

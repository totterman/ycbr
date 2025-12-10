package fi.smartbass.ycbr.inspection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class InspectionMapperTest {

    private final InspectionMapper mapper = Mappers.getMapper(InspectionMapper.class);
    private static InspectionDTO testDto;
    private static InspectionEntity testEntity;

    @BeforeAll
    static void setup() {
        Instant instantNow = Instant.now();
        OffsetDateTime offsetDateTimeNow = OffsetDateTime.now();
        testDto = new InspectionDTO(
                997L,
                offsetDateTimeNow.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                "Inspector Name",
                998L,
                999L,
                new InspectionDataDto(
                        new HullDataDto(false, false, false, true, false, false, false, false, false, false, 20),
                        new RigDataDto(false, false, false, false),
                        new EngineDataDto(false, true, false, false, false, false, false, false)
                ),
                null);
        testEntity = new InspectionEntity(
                997L,
                offsetDateTimeNow,
                "Inspector Name",
                998L,
                999L,
                new InspectionData(
                        new HullData(false, false, false, false, false, false, false, false, false, false, 30),
                        new RigData(true, false, true, false),
                        new EngineData(false, false, false, false, false, false, false, false)
                ),
                null,
                instantNow,
                "createdBy",
                instantNow,
                "modifiedBy",
                0
        );
    }

    @Test
    void toDTO() {
        assertNotNull(mapper);

        InspectionDTO dto = mapper.toDTO(testEntity);
        assertEquals(testEntity.getId(), dto.id());
        assertEquals(testEntity.getTimestamp(), OffsetDateTime.parse(dto.timestamp()));
        assertEquals(testEntity.getInspector(), dto.inspector());
        assertEquals(testEntity.getEvent(), dto.event());
        assertEquals(testEntity.getBoat(), dto.boat());
        assertNull(testEntity.getCompleted());

        assertEquals(testEntity.getInspection().getHullData().isHull(), dto.inspection().hullData().hull());
        assertEquals(testEntity.getInspection().getRigData().isRig(), dto.inspection().rigData().rig());
    }

    @Test
    void toEntity() {
        assertNotNull(mapper);
        InspectionEntity entity = mapper.toEntity(testDto);
        assertEquals(testDto.id(), entity.getId());
        assertEquals(testDto.timestamp(), entity.getTimestamp().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        assertEquals(testDto.inspector(), entity.getInspector());
        assertEquals(testDto.event(), entity.getEvent());
        assertEquals(testDto.boat(), entity.getBoat());
        assertNull(testDto.completed());
        assertEquals(testDto.inspection().hullData().throughulls(), entity.getInspection().getHullData().isThroughulls());
        assertEquals(testDto.inspection().rigData().reefing(), entity.getInspection().getRigData().isReefing());
    }

}
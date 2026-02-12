package fi.smartbass.ycbr.inspection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InspectionMapperTest {

    private final InspectionMapper mapper = Mappers.getMapper(InspectionMapper.class);
    private static InspectionDto testDto;
    private static InspectionEntity testEntity;

    @BeforeAll
    static void setup() {
        Instant instantNow = Instant.now();
        OffsetDateTime offsetDateTimeNow = OffsetDateTime.now();
        UUID inspectionId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();
        UUID boatId = UUID.randomUUID();
        RemarkDto remarkDto = new RemarkDto(0, "7.1", "Needs overhaul");
        Set<RemarkDto> remarkDtos = Set.of(remarkDto);
        Remark remark = new Remark(0, "7.1", "Needs overhaul");
        Set<Remark> remarks = Set.of(remark);

        testDto = new InspectionDto(
                inspectionId,
                offsetDateTimeNow.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                "Inspector Name",
                eventId,
                boatId,
                "2",
                new InspectionDataDto(
                        new HullDataDto(false, false, false, true, false, false, false, false, false, false, 20),
                        new RigDataDto(false, false, false, false),
                        new EngineDataDto(false, true, false, false, false, false, false, false, false),
                        new EquipmentDataDto(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
                        new NavigationDataDto(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
                        new SafetyDataDto(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
                ),
                null,
                remarkDtos);
        testEntity = new InspectionEntity(
                inspectionId,
                offsetDateTimeNow,
                "Inspector Name",
                eventId,
                boatId,
                "2",
                new InspectionData(
                        new HullData(false, false, false, false, false, false, false, false, false, false, 30),
                        new RigData(true, false, true, false),
                        new EngineData(false, false, false, false, false, false, false, false, false),
                        new EquipmentData(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
                        new NavigationData(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false),
                        new SafetyData(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)
                ),
                null,
                remarks,
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

        InspectionDto dto = mapper.toDTO(testEntity);
        assertEquals(testEntity.getInspectionId(), dto.inspectionId());
        assertEquals(testEntity.getTimestamp(), OffsetDateTime.parse(dto.timestamp()));
        assertEquals(testEntity.getInspectorName(), dto.inspectorName());
        assertEquals(testEntity.getEventId(), dto.eventId());
        assertEquals(testEntity.getBoatId(), dto.boatId());
        assertNull(testEntity.getCompleted());

        assertEquals(testEntity.getInspection().getHullData().isHull(), dto.inspection().hullData().hull());
        assertEquals(testEntity.getInspection().getRigData().isRig(), dto.inspection().rigData().rig());
        assertEquals(testEntity.getInspection().getSafetyData().getHand_extinguisher(), dto.inspection().safetyData().hand_extinguisher());
    }

    @Test
    void toEntity() {
        assertNotNull(mapper);
        InspectionEntity entity = mapper.toEntity(testDto);
        assertEquals(testDto.inspectionId(), entity.getInspectionId());
        assertEquals(testDto.timestamp(), entity.getTimestamp().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        assertEquals(testDto.inspectorName(), entity.getInspectorName());
        assertEquals(testDto.eventId(), entity.getEventId());
        assertEquals(testDto.boatId(), entity.getBoatId());
        assertNull(testDto.completed());
        assertEquals(testDto.inspection().hullData().throughulls(), entity.getInspection().getHullData().isThroughulls());
        assertEquals(testDto.inspection().rigData().reefing(), entity.getInspection().getRigData().isReefing());
    }

}
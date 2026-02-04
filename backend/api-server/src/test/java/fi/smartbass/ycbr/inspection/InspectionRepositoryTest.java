package fi.smartbass.ycbr.inspection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest(includeFilters = @ComponentScan.Filter(EnableJdbcAuditing.class))
@AutoConfigureTestDatabase
@ActiveProfiles("integration")
@Sql("/db.sql")
class InspectionRepositoryTest {

    @Autowired
    private InspectionRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionRepository.class);
    private final UUID eventId = UUID.randomUUID();
    private final UUID boatId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        HullData hullData = new HullData(false, false, false, false, false, false, false, false, false, false, 30);
        RigData rigData = new RigData(false, false, false, false);
        EngineData engineData = new EngineData(false, false, false, false, false, false, false, false);
        EquipmentData equipmentData = new EquipmentData(false, false, false, 0, false, false, false, false, false, false, false, false, false, false, false);
        MaritimeData maritimeData = new MaritimeData(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        SafetyData safetyData = new SafetyData(false, 0, 0, false, false, 0, false, 0, false, false, 0, false, false, false, false, false, false);
        InspectionData inspectionData = new InspectionData(hullData, rigData, engineData, equipmentData, maritimeData, safetyData);
        InspectionEntity entity = new InspectionEntity(null, OffsetDateTime.now(), "inspector1", eventId, boatId, InspectionClass.COASTAL, inspectionData, null, Instant.now(), "system", Instant.now(), "system", 0);
        repository.save(entity);
    }

    @Test
    @DisplayName("Find inspections by inspectorName name")
    void findByInspectorName() {
        Iterable<InspectionEntity> inspections = repository.findByInspectorName("inspector1");
        assertThat(inspections).isNotNull();
        assertThat(inspections.iterator().hasNext()).isTrue();
        InspectionEntity inspection = inspections.iterator().next();
        assertThat(inspection.getInspectorName()).isEqualTo("inspector1");
        long count = StreamSupport.stream(inspections.spliterator(), false).count();
        assertThat(count).isEqualTo(1);
    }

}
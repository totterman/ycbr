package fi.smartbass.ycbr.inspection;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InspectionServiceTest {

    @Spy
    InspectionMapper mapper = Mappers.getMapper(InspectionMapper.class);
    @Mock
    private InspectionRepository repository;
    @InjectMocks
    private InspectionService service;

    private final UUID inspectionId = UUID.randomUUID();
    private final UUID boatId = UUID.randomUUID();
    private final UUID eventId = UUID.randomUUID();

    private InspectionEntity newEntity;
    private InspectionEntity oldEntity;
    private List<InspectionEntity> entityList;
    private NewInspectionDto newInspectionDto;
    private final String inspectorName = "inspector1";
    private MyInspectionsDto myInspectionsDto;
    private List<MyInspectionsDto> myInspectionsDtoList;

    private @NotNull InspectionData getInspectionData() {
        HullData hullData = new HullData(false, false, false, false, false, false, false, false, false, false, 30);
        RigData rigData = new RigData(false, false, false, false);
        EngineData engineData = new EngineData(false, false, false, false, false, false, false, false, false);
        EquipmentData equipmentData = new EquipmentData(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        NavigationData navigationData = new NavigationData(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        SafetyData safetyData = new SafetyData(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        return new InspectionData(hullData, rigData, engineData, equipmentData, navigationData, safetyData);
    }

    private @NotNull InspectionDataDto getInspectionDataDto() {
        HullDataDto hullData = new HullDataDto(false, false, false, false, false, false, false, false, false, false, 30);
        RigDataDto rigData = new RigDataDto(false, false, false, false);
        EngineDataDto engineData = new EngineDataDto(false, false, false, false, false, false, false, false, false);
        EquipmentDataDto equipmentData = new EquipmentDataDto(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        NavigationDataDto navigationData = new NavigationDataDto(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        SafetyDataDto safetyData = new SafetyDataDto(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        return new InspectionDataDto(hullData, rigData, engineData, equipmentData, navigationData, safetyData);
    }

    @BeforeEach
    void setUp() {
        OffsetDateTime now = OffsetDateTime.now();
        Instant instantNow = Instant.now();
        newEntity = new InspectionEntity(null, now, inspectorName, eventId, boatId, InspectionClass.INSHORE, getInspectionData(), null, instantNow, "system", instantNow, "system", 0);
        oldEntity = new InspectionEntity(inspectionId, now, inspectorName, eventId, boatId, InspectionClass.INSHORE, getInspectionData(), null, instantNow, "system", instantNow, "system", 1);
        entityList = List.of(oldEntity);
        myInspectionsDto = new MyInspectionsDto(inspectionId, eventId, boatId, inspectorName, "Boat A", InspectionClass.UNDEFINED, "Place X", OffsetDateTime.now(), null);
        myInspectionsDtoList = List.of(myInspectionsDto);
        newInspectionDto = new NewInspectionDto(inspectorName, eventId, boatId, InspectionClass.UNDEFINED);
    }

    @Test
    @DisplayName("Read inspection by ID")
    void read() {
        when(repository.findById(inspectionId)).thenReturn(Optional.of(oldEntity));
        InspectionDto dto = service.read(inspectionId);
        assertThat(dto).isNotNull();
        assertThat(dto.inspectorName()).isEqualTo(oldEntity.getInspectorName());
    }

    @Test
    @DisplayName("Read throws if ID not found")
    void readThrows() {
        when(repository.findById(inspectionId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.read(inspectionId))
                .isInstanceOf(InspectionNotFoundException.class);
    }

    @Test
    @DisplayName("Fetch inspections by inspectorName name")
    void fetchByInspector() {
        when(repository.findByInspectorName(inspectorName)).thenReturn(entityList);
        Iterable<InspectionDto> dtos = service.fetchByInspector(inspectorName);
        assertThat(dtos).isNotNull();
        InspectionDto dto = dtos.iterator().next();
        assertThat(dto.inspectorName()).isEqualTo(inspectorName);
    }

    @Test
    @DisplayName("Fetch my inspections by inspectorName name")
    void fetchMyInspections() {
        when(repository.fetchMyInspections(inspectorName)).thenReturn(myInspectionsDtoList);
        Iterable<MyInspectionsDto> dtos = service.fetchMyInspections(inspectorName);
        assertThat(dtos).isNotNull();
        MyInspectionsDto dto = dtos.iterator().next();
        assertThat(dto.inspectorName()).isEqualTo(inspectorName);
    }

    @Test
    @DisplayName("Fetch all inspections")
    void fetchAllInspections() {
        when(repository.fetchAllInspections()).thenReturn(myInspectionsDtoList);
        Iterable<MyInspectionsDto> dtos = service.fetchAllInspections();
        assertThat(dtos).isNotNull();
        MyInspectionsDto dto = dtos.iterator().next();
        assertThat(dto.inspectorName()).isEqualTo(inspectorName);
    }

    @Test
    @DisplayName("Create new inspection")
    void create() {
        when(repository.save(any(InspectionEntity.class))).thenReturn(oldEntity);
        InspectionDto dto = service.create(newInspectionDto);
        assertThat(dto).isNotNull();
        assertThat(dto.inspectorName()).isEqualTo(newEntity.getInspectorName());
    }

    @Test
    @DisplayName("Update inspection")
    void update() {
        when(repository.save(any(InspectionEntity.class))).thenReturn(oldEntity);
        InspectionDto dto = mapper.toDTO(oldEntity);
        InspectionDto updatedDto = service.update(inspectionId, dto);
        assertThat(updatedDto).isNotNull();
        assertThat(updatedDto.inspectionId()).isEqualTo(inspectionId);
    }

    @Test
    @DisplayName("Update throws if ID mismatch")
    void updateThrows() {
        assertThatThrownBy(() -> {
            InspectionDto dto = mapper.toDTO(oldEntity);
            service.update(UUID.randomUUID(), dto);
        }).isInstanceOf(InspectionRequestMalformedException.class);
    }

}
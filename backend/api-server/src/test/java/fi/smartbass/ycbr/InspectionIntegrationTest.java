package fi.smartbass.ycbr;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.fasterxml.jackson.core.JsonProcessingException;
import fi.smartbass.ycbr.i9event.I9EventDto;
import fi.smartbass.ycbr.i9event.NewI9EventDto;
import fi.smartbass.ycbr.inspection.*;
import fi.smartbass.ycbr.register.BoatDto;
import fi.smartbass.ycbr.register.NewBoatDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.io.UnsupportedEncodingException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InspectionIntegrationTest extends BaseIntegrationTest {

    private final UUID inspectionId = UUID.randomUUID();
    private final UUID eventId = UUID.randomUUID();
    private final UUID boatId = UUID.randomUUID();
    private final String inspectorName = "Inspector Name";

    private final NewInspectionDto newInspectionDto = new NewInspectionDto(inspectorName, eventId, boatId, InspectionClass.PROTECTED_WATERS);
    private final InspectionDto dto = new InspectionDto(
            inspectionId,
            OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            inspectorName,
            eventId,
            boatId,
            InspectionClass.PROTECTED_WATERS,
            getInspectionDataDto(),
            null
    );
    private final List<InspectionDto> dtos = List.of(dto);
    private final MyInspectionsDto myInspectionsDto = new MyInspectionsDto(inspectionId, eventId, boatId, inspectorName, "Boat A", InspectionClass.INSHORE, "Place X", OffsetDateTime.now(), null);
    private final List<MyInspectionsDto> myInspectionsDtoList = List.of(myInspectionsDto);

    private InspectionDataDto getInspectionDataDto() {
        HullDataDto hullData = new HullDataDto(false, false, false, false, false, false, false, false, false, false, 30);
        RigDataDto rigData = new RigDataDto(false, false, false, false);
        EngineDataDto engineData = new EngineDataDto(false, false, false, false, false, false, false, false);
        EquipmentDataDto equipmentData = new EquipmentDataDto(false, false, false, 0, false, false, false, false, false, false, false, false, false, false, false);
        MaritimeDataDto maritimeData = new MaritimeDataDto(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        SafetyDataDto safetyData = new SafetyDataDto(false, 0, 0, false, false, 0, false, 0, false, false, 0, false, false, false, false, false, false);
        return new InspectionDataDto(hullData, rigData, engineData, equipmentData, maritimeData, safetyData);
    }

    @Test
    @DisplayName("Create and read inspections")
    @WithJwt("bengt.json")
    void testReadInspections() throws Exception {
        InspectionDto created = createOne(newInspectionDto);
        MvcTestResult getOne = mvc.get()
                .uri("/inspections/" + created.inspectionId())
                .exchange();
        assertThat(getOne)
                .hasStatus(HttpStatus.OK);
        InspectionDto inspection = om.readValue(getOne.getResponse().getContentAsString(), om.getTypeFactory().constructType(InspectionDto.class));
        System.out.println(" Inspections JSON: " + getOne.getResponse().getContentAsString());
        assertThat(inspection).isNotNull();
        assertThat(inspection.boatId()).isEqualByComparingTo(created.boatId());
        assertThat(inspection.eventId()).isEqualByComparingTo(created.eventId());
        assertThat(inspection.inspectorName()).isEqualTo(created.inspectorName());
    }

    @Test
    @DisplayName("Create and read inspections by inspectorName name")
    @WithJwt("bengt.json")
    void testReadByInspectorName() throws Exception {
        InspectionDto created = createOne(newInspectionDto);
        MvcTestResult getByName = mvc.get()
                .uri("/inspections/inspectorName?name=" + created.inspectorName())
                .exchange();
        assertThat(getByName)
                .hasStatus(HttpStatus.OK);
        List<InspectionDto> inspections = om.readValue(getByName.getResponse().getContentAsString(), om.getTypeFactory().constructCollectionType(List.class, InspectionDto.class));
        System.out.println(" Inspections JSON: " + getByName.getResponse().getContentAsString());
        assertThat(inspections).isNotNull();
        assertThat(inspections.getFirst().boatId()).isEqualByComparingTo(created.boatId());
        assertThat(inspections.getFirst().eventId()).isEqualByComparingTo(created.eventId());
        assertThat(inspections.getFirst().inspectorName()).isEqualTo(created.inspectorName());
    }

    @Test
    @DisplayName("Create and read my inspections")
    @WithJwt("kalle.json")
    void testMyInspections() throws Exception {
        BoatDto boatDto = createBoat();
        I9EventDto eventDto = createEvent();

        String anotherInspector = "Another Inspector";
        InspectionDto created = createOne(new NewInspectionDto(anotherInspector, eventDto.i9eventId(), boatDto.boatId(), InspectionClass.UNDEFINED));

        MvcTestResult getMyInspections = mvc.get()
                .uri("/inspections/my?name=" + created.inspectorName())
                .exchange();
        assertThat(getMyInspections)
                .hasStatus(HttpStatus.OK);
        List<MyInspectionsDto> inspections = om.readValue(getMyInspections.getResponse().getContentAsString(), om.getTypeFactory().constructCollectionType(List.class, MyInspectionsDto.class));
        System.out.println(" Inspections JSON: " + getMyInspections.getResponse().getContentAsString());
        assertThat(inspections).isNotNull();
        assertThat(inspections.size()).isEqualTo(1);
        MyInspectionsDto myDto = inspections.getFirst();
        assertThat(myDto.inspectorName()).isEqualTo(created.inspectorName());
        assertThat(myDto.boatName()).isEqualTo(boatDto.name());
        assertThat(myDto.place()).isEqualTo(eventDto.place());
    }

    @Test
    @DisplayName("Create and read all inspections")
    @WithJwt("kalle.json")
    void testAllInspections() throws Exception {
        BoatDto boatDto = createBoat();
        I9EventDto eventDto = createEvent();

        String anotherInspector = "Another Inspector";
        InspectionDto created = createOne(new NewInspectionDto(anotherInspector, eventDto.i9eventId(), boatDto.boatId(), InspectionClass.UNDEFINED));

        MvcTestResult getAllInspections = mvc.get()
                .uri("/inspections/all")
                .exchange();
        assertThat(getAllInspections)
                .hasStatus(HttpStatus.OK);
        List<MyInspectionsDto> inspections = om.readValue(getAllInspections.getResponse().getContentAsString(), om.getTypeFactory().constructCollectionType(List.class, MyInspectionsDto.class));
        System.out.println(" Inspections JSON: " + getAllInspections.getResponse().getContentAsString());
        assertThat(inspections).isNotNull();
        assertThat(inspections.size()).isEqualTo(1);
        MyInspectionsDto myDto = inspections.getFirst();
        assertThat(myDto.inspectorName()).isEqualTo(created.inspectorName());
        assertThat(myDto.boatName()).isEqualTo(boatDto.name());
        assertThat(myDto.place()).isEqualTo(eventDto.place());
    }


    @Test
    @DisplayName("Update an inspection")
    @WithJwt("bengt.json")
    void testUpdateInspection() throws Exception {
        InspectionDto created = createOne(newInspectionDto);
        InspectionDto updated = new InspectionDto(
                created.inspectionId(),
                created.timestamp(),
                "Updated Inspector",
                created.eventId(),
                created.boatId(),
                created.inspectionClass(),
                created.inspection(),
                created.completed()
        );
        String updatedInspectionJson = om.writeValueAsString(updated);
        MvcTestResult updateResult = mvc.put()
                .uri("/inspections/" + created.inspectionId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedInspectionJson)
                .exchange();
        assertThat(updateResult)
                .hasStatus(HttpStatus.OK);
        InspectionDto inspection = om.readValue(updateResult.getResponse().getContentAsString(), om.getTypeFactory().constructType(InspectionDto.class));
        System.out.println("Updated Inspection JSON: " + updateResult.getResponse().getContentAsString());
        assertThat(inspection).isNotNull();
        assertThat(inspection.inspectorName()).isEqualTo(updated.inspectorName());
    }

    @Test
    @DisplayName("Insert throws on invalid data")
    @WithJwt("bengt.json")
    void insertThrows() throws Exception {
        NewInspectionDto newDto = new NewInspectionDto("All_too_long_name_breaks_50_characters_limit_and_validation_fails",eventId, boatId, InspectionClass.PROTECTED_WATERS);
        String newJson = om.writeValueAsString(newDto);
        MvcTestResult addNew = mvc.post()
                .uri("/inspections")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newJson)
                .exchange();
        assertThat(addNew)
                .hasStatus(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private InspectionDto createOne(NewInspectionDto dto) throws JsonProcessingException, UnsupportedEncodingException {
        String newInspectionJson = om.writeValueAsString(dto);
        MvcTestResult addNew = mvc.post()
                .uri("/inspections")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newInspectionJson)
                .exchange();
        assertThat(addNew)
                .hasStatus(HttpStatus.CREATED);
        System.out.println("Created JSON: " + addNew.getResponse().getContentAsString());
        InspectionDto inspection = om.readValue(addNew.getResponse().getContentAsString(), om.getTypeFactory().constructType(InspectionDto.class));
        assertThat(inspection).isNotNull();
        assertThat(inspection.boatId()).isEqualByComparingTo(dto.boatId());
        assertThat(inspection.eventId()).isEqualByComparingTo(dto.eventId());
        return inspection;
    }

    private BoatDto createBoat() throws Exception {
        NewBoatDto newBoatDto = new NewBoatDto("owner11", "BoatName", "S", "Reg11234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988");
        String newBoatJson = om.writeValueAsString(newBoatDto);
        MvcTestResult addNew = mvc.post()
                .uri("/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newBoatJson)
                .exchange();
        assertThat(addNew)
                .hasStatus(HttpStatus.CREATED);
        System.out.println("Created Boat JSON: " + addNew.getResponse().getContentAsString());
        return om.readValue(addNew.getResponse().getContentAsString(), om.getTypeFactory().constructType(BoatDto.class));
    }

    private I9EventDto createEvent() throws Exception {
        NewI9EventDto newI9EventDto = new NewI9EventDto("Björkholmen", "2026-05-19T00:00:00.000+03:00", "2026-05-19T18:00:00.000+03:00", "2026-05-19T20:00:00.000+03:00");
        String newEventJson = om.writeValueAsString(newI9EventDto);
        MvcTestResult addNew = mvc.post()
                .uri("/i9events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newEventJson)
                .exchange();
        assertThat(addNew)
                .hasStatus(HttpStatus.CREATED);
        System.out.println("Created Event JSON: " + addNew.getResponse().getContentAsString());
        return om.readValue(addNew.getResponse().getContentAsString(), om.getTypeFactory().constructType(I9EventDto.class));
    }

}

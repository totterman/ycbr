package fi.smartbass.ycbr.inspection;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import com.c4_soft.springaddons.security.oauth2.test.webmvc.AutoConfigureAddonsWebmvcResourceServerSecurity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InspectionController.class)
@Import(InspectionControllerAdvice.class)
@AutoConfigureAddonsWebmvcResourceServerSecurity
class InspectionControllerTest {

    @MockitoBean(name = "eventService")
    private InspectionService service;

    @MockitoBean(name = "authentication")
    private Authentication authentication;

    @Autowired
    private MockMvc mockMvc;

    private final UUID inspectionId = UUID.randomUUID();
    private final UUID eventId = UUID.randomUUID();
    private final UUID boatId = UUID.randomUUID();
    private final String inspectorName = "Inspector Name";
    private final RemarkDto remark = new RemarkDto(0, "7.1", "Needs overhaul");
    private final Set<RemarkDto> remarks = Set.of(remark);

    private final InspectionDto dto = new InspectionDto(
            inspectionId,
            OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            inspectorName,
            eventId,
            boatId,
            "1",
            getInspectionDataDto(),
            null,
            remarks
    );
    private final List<InspectionDto> dtos = List.of(dto);
    private final MyInspectionsDto myInspectionsDto = new MyInspectionsDto(inspectionId, eventId, boatId, inspectorName, "Boat A", InspectionClass.INSHORE, "Place X", OffsetDateTime.now(), null);
    private final List<MyInspectionsDto> myInspectionsDtoList = List.of(myInspectionsDto);
    private final ObjectMapper om = new ObjectMapper();

    private InspectionDataDto getInspectionDataDto() {
        HullDataDto hullData = new HullDataDto(false, false, false, false, false, false, false, false, false, false, 30);
        RigDataDto rigData = new RigDataDto(false, false, false, false);
        EngineDataDto engineData = new EngineDataDto(false, false, false, false, false, false, false, false, false);
        EquipmentDataDto equipmentData = new EquipmentDataDto(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        NavigationDataDto maritimeData = new NavigationDataDto(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        SafetyDataDto safetyData = new SafetyDataDto(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        return new InspectionDataDto(hullData, rigData, engineData, equipmentData, maritimeData, safetyData);
    }

    @Test
    @DisplayName("GET /inspections/{id} returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void getInspection() throws Exception {
        when(service.read(inspectionId)).thenReturn(dto);
        mockMvc.perform(get("/inspections/" + inspectionId).principal(authentication))
                .andExpect(status().isOk());
        verify(service).read(inspectionId);
    }

    @Test
    @DisplayName("GET /inspections/{id} returns 404 Not Found")
    @WithMockAuthentication({ "guest" })
    void getInspectionThrows() throws Exception {
        when(service.read(inspectionId)).thenThrow(new InspectionNotFoundException(inspectionId));
        mockMvc.perform(get("/inspections/" + inspectionId).principal(authentication))
                .andExpect(status().isNotFound());
        verify(service).read(inspectionId);
    }

    @Test
    @DisplayName("GET /inspections/inspectorName?name={inspectorName} returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void getByInspector() throws Exception {
        when(service.fetchByInspector(inspectorName)).thenReturn(dtos);
        mockMvc.perform(get("/inspections/inspectorName?name=" + inspectorName)
                        .principal(authentication))
                .andExpect(status().isOk());
        verify(service).fetchByInspector(inspectorName);
    }

    @Test
    @DisplayName("POST /inspections returns 201 Created")
    @WithMockAuthentication({ "guest" })
    void postInspection() throws Exception {
        NewInspectionDto newInspectionDto = new NewInspectionDto(inspectorName, eventId, boatId, InspectionClass.COASTAL);
        String newInspectionJson = """
                {
                    "inspectorName": "%s",
                    "eventId": "%s",
                    "boatId": "%s",
                    "inspectionClass": "%s"
                }
                """.formatted(inspectorName, eventId, boatId, InspectionClass.COASTAL);
        when(service.create(newInspectionDto)).thenReturn(dto);
        mockMvc.perform(post("/inspections")
                        .principal(authentication)
                        .contentType("application/json")
                        .content(newInspectionJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PUT /inspections/{id} returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void putInspection() throws Exception {
        String inspectionJson = om.writeValueAsString(dto);
        when(service.update(inspectionId, dto)).thenReturn(dto);
        mockMvc.perform(put("/inspections/" + inspectionId)
                        .principal(authentication)
                        .contentType("application/json")
                        .content(inspectionJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /inspections/{id} returns 400 Bad Request")
    @WithMockAuthentication({ "guest" })
    void putInspectionThrows() throws Exception {
        UUID invalidInspectionId = UUID.randomUUID();
        String inspectionJson = om.writeValueAsString(dto);
        when(service.update(invalidInspectionId, dto)).thenThrow(new InspectionRequestMalformedException(invalidInspectionId, inspectionId));
        mockMvc.perform(put("/inspections/" + invalidInspectionId)
                        .principal(authentication)
                        .contentType("application/json")
                        .content(inspectionJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /inspections/my?name={inspectorName} returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void getMyInspections() throws Exception {
        when(service.fetchMyInspections(inspectorName)).thenReturn(myInspectionsDtoList);
        mockMvc.perform(get("/inspections/my?name=" + inspectorName)
                        .principal(authentication))
                .andExpect(status().isOk());
        verify(service).fetchMyInspections(inspectorName);
    }

    @Test
    @DisplayName("GET /inspections/all returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void getAllInspections() throws Exception {
        when(service.fetchAllInspections()).thenReturn(myInspectionsDtoList);
        mockMvc.perform(get("/inspections/all")
                        .principal(authentication))
                .andExpect(status().isOk());
        verify(service).fetchAllInspections();
    }
}
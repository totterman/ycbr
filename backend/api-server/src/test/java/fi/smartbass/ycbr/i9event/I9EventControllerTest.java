package fi.smartbass.ycbr.i9event;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import com.c4_soft.springaddons.security.oauth2.test.webmvc.AutoConfigureAddonsWebmvcResourceServerSecurity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(I9EventController.class)
@Import(I9EventControllerAdvice.class)
@AutoConfigureAddonsWebmvcResourceServerSecurity
class I9EventControllerTest {

    @MockitoBean(name = "eventService")
    private I9EventService eventService;

    @MockitoBean(name = "authentication")
    private Authentication authentication;

    @Autowired
    private MockMvc mockMvc;

    private final I9EventDTO dto1 = new I9EventDTO(1001L, "Björkholmen", "2024-07-15T00:00:00.000+02:00","2024-07-15T10:00:00.000+02:00","2024-07-15T16:00:00.000+02:00",2, 6);
    private final I9EventDTO dto2 = new I9EventDTO(1002L, "Blekholmen", "2024-08-20T00:00:00.000+02:00","2024-08-20T09:00:00.000+02:00","2024-08-20T15:00:00.000+02:00",3, 8);
    private final I9EventDTO dto3 = new I9EventDTO(1003L, "Barösund", "2024-09-10T00:00:00.000+02:00","2024-09-10T11:00:00.000+02:00","2024-09-10T17:00:00.000+02:00",1, 5);
    private final List<I9EventDTO> dtos = List.of(dto1, dto2, dto3);

    private final InspectorRegistrationDTO idto = new InspectorRegistrationDTO("Inspector Name", "Short Message");
    private final BoatBookingDTO bdto = new BoatBookingDTO("Some Boat", "Dock P1 42");

    @Test
    @DisplayName("GET /api/i9events returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void getAll() throws Exception {
        when(eventService.findAll()).thenReturn(dtos);
        mockMvc.perform(get("/i9events").principal(authentication))
                .andExpect(status().isOk());
        verify(eventService).findAll();
    }

    @Test
    @DisplayName("GET /api/i9events/1001 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void getOne() throws Exception {
        when(eventService.findById(1001L)).thenReturn(dto1);
        mockMvc.perform(get("/i9events/1001").principal(authentication))
                .andExpect(status().isOk());
        verify(eventService).findById(1001L);
    }

    @Test
    @DisplayName("POST /api/i9events/1 returns 201 Created")
    @WithMockAuthentication({ "guest" })
    void postGood() throws Exception {
        String jsonI9Event = "{\n" +
                "  \"id\": 16,\n" +
                "  \"place\": \"Blekholmen\",\n" +
                "  \"day\": \"2024-08-20\",\n" +
                "  \"starts\": \"2024-08-20T09:00:00+02:00\",\n" +
                "  \"ends\": \"2024-08-20T15:00:00.000+02:00\"\n" +
                "}";
        I9EventDTO dto = new I9EventDTO(16L, "Blekholmen", "2024-08-20", "2024-08-20T09:00:00+02:00", "2024-08-20T15:00:00.000+02:00", 0, 0);
        when(eventService.create(any(I9EventDTO.class))).thenReturn(dto);
        mockMvc.perform(post("/i9events")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonI9Event))
                .andExpect(status().isCreated());
        verify(eventService).create(any(I9EventDTO.class));
    }

    @Test
    @DisplayName("PUT /api/i9events/16 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void putGood() throws Exception {
        Long testId = 16L;
        String jsonI9Event = "{\n" +
                "  \"id\": 16,\n" +
                "  \"place\": \"Blekholmen\",\n" +
                "  \"day\": \"2024-08-20\",\n" +
                "  \"starts\": \"2024-08-20T09:00:00+02:00\",\n" +
                "  \"ends\": \"2024-08-20T15:00:00.000+02:00\"\n" +
                "}";
        I9EventDTO dto = new I9EventDTO(testId, "Blekholmen", "2024-08-20", "2024-08-20T09:00:00+02:00", "2024-08-20T15:00:00.000+02:00", 0, 0);
        when(eventService.upsert(anyLong(), any(I9EventDTO.class))).thenReturn(dto);
        mockMvc.perform(put("/i9events/" + testId)
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonI9Event))
                .andExpect(status().isOk());
        verify(eventService).upsert(anyLong(), any(I9EventDTO.class));
    }

    @Test
    @DisplayName("DELETE /api/i9events/1 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void deleteOk() throws Exception {
        doNothing().when(eventService).delete(1L);
        mockMvc.perform(delete("/i9events/1").principal(authentication))
                .andExpect(status().isOk());
        verify(eventService).delete(1L);    }

    @Test
    @DisplayName("GET /api/i9events/1001/inspectors returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void getInspectors() throws Exception {
        when(eventService.findInspectorsByEventId(1001L)).thenReturn(List.of(idto));
        mockMvc.perform(get("/i9events/1001/inspectors").principal(authentication))
                .andExpect(status().isOk());
        verify(eventService).findInspectorsByEventId(1001L);
    }

    @Test
    @DisplayName("POST /api/i9events/1001/inspectors returns 201 Created")
    @WithMockAuthentication({ "guest" })
    void addInspector() throws Exception {
        String json = "{ \"inspectorName\": \"Inspector Name\", \"message\": \"Short Message\" }";
        when(eventService.assignInspectorToEvent(1001L, idto)).thenReturn(dto1);
        mockMvc.perform(post("/i9events/1001/inspectors").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
        verify(eventService).assignInspectorToEvent(1001L, idto);
    }

    @Test
    @DisplayName("DELETE /api/i9events/1001/inspectors returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void removeInspector() throws Exception {
        String json = "{ \"inspectorName\": \"Inspector Name\", \"message\": \"Short message\" }";
        when(eventService.removeInspectorFromEvent(1001L, idto.inspectorName())).thenReturn(dto1);
        mockMvc.perform(delete("/i9events/1001/inspectors").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        verify(eventService).removeInspectorFromEvent(1001L, idto.inspectorName());
    }

    @Test
    @DisplayName("GET /api/i9events/1001/boats returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void getBoats() throws Exception {
        when(eventService.findBoatsByEventId(1001L)).thenReturn(List.of(bdto));
        mockMvc.perform(get("/i9events/1001/boats").principal(authentication))
                .andExpect(status().isOk());
        verify(eventService).findBoatsByEventId(1001L);
    }

    @Test
    @DisplayName("POST /api/i9events/1001/boats returns 201 Created")
    @WithMockAuthentication({ "guest" })
    void addBoat() throws Exception {
        String json = "{ \"boatName\": \"Some Boat\", \"message\": \"Dock P1 42\" }";
        when(eventService.assignBoatToEvent(1001L, bdto)).thenReturn(dto1);
        mockMvc.perform(post("/i9events/1001/boats").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
        verify(eventService).assignBoatToEvent(1001L, bdto);
    }

    @Test
    @DisplayName("DELETE /api/i9events/1001/boats returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void removeBoat() throws Exception {
        String json = "{ \"boatName\": \"Some Boat\", \"message\": \"Dock P1 42\" }";
        when(eventService.removeBoatFromEvent(1001L, bdto.boatName())).thenReturn(dto1);
        mockMvc.perform(delete("/i9events/1001/boats").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        verify(eventService).removeBoatFromEvent(1001L, bdto.boatName());
    }
}
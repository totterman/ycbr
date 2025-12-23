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
import java.util.UUID;

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

    private final UUID id1 = UUID.randomUUID();
    private final UUID id2 = UUID.randomUUID();
    private final UUID id3 = UUID.randomUUID();
    private final I9EventDto dto1 = new I9EventDto(id1, "Björkholmen", "2024-07-15T00:00:00.000+02:00","2024-07-15T10:00:00.000+02:00","2024-07-15T16:00:00.000+02:00",2, 6);
    private final I9EventDto dto2 = new I9EventDto(id2, "Blekholmen", "2024-08-20T00:00:00.000+02:00","2024-08-20T09:00:00.000+02:00","2024-08-20T15:00:00.000+02:00",3, 8);
    private final I9EventDto dto3 = new I9EventDto(id3, "Barösund", "2024-09-10T00:00:00.000+02:00","2024-09-10T11:00:00.000+02:00","2024-09-10T17:00:00.000+02:00",1, 5);
    private final List<I9EventDto> dtos = List.of(dto1, dto2, dto3);

    private final UUID boatId = UUID.randomUUID();
    private final InspectorRegistrationDto idto = new InspectorRegistrationDto("Inspector Name", "Short Message");
    private final BoatBookingDto bdto = new BoatBookingDto(boatId, "Dock P1 42", false);

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
    @DisplayName("GET /api/i9events/id1 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void getOne() throws Exception {
        when(eventService.findById(id1)).thenReturn(dto1);
        mockMvc.perform(get("/i9events/" + id1).principal(authentication))
                .andExpect(status().isOk());
        verify(eventService).findById(id1);
    }

    @Test
    @DisplayName("POST /api/i9events/ returns 201 Created")
    @WithMockAuthentication({ "guest" })
    void postGood() throws Exception {
        String jsonI9Event = """
                {
                  "place": "Blekholmen",
                  "day": "2024-08-20",
                  "starts": "2024-08-20T09:00:00+02:00",
                  "ends": "2024-08-20T15:00:00.000+02:00"
                }""";
//        I9EventDto dto = new I9EventDto(id2, "Blekholmen", "2024-08-20", "2024-08-20T09:00:00+02:00", "2024-08-20T15:00:00.000+02:00", 0, 0);
        when(eventService.create(any(NewI9EventDto.class))).thenReturn(dto2);
        mockMvc.perform(post("/i9events")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonI9Event))
                .andExpect(status().isCreated());
        verify(eventService).create(any(NewI9EventDto.class));
    }

    @Test
    @DisplayName("PUT /api/i9events/id3 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void putGood() throws Exception {
        String jsonI9Event = "{\n" +
                "  \"i9eventId\": \"" + id3 + "\",\n" +
                "  \"place\": \"Blekholmen\",\n" +
                "  \"day\": \"2024-08-20\",\n" +
                "  \"starts\": \"2024-08-20T09:00:00+02:00\",\n" +
                "  \"ends\": \"2024-08-20T15:00:00.000+02:00\"\n" +
                "}";
        I9EventDto dto = new I9EventDto(id3, "Blekholmen", "2024-08-20", "2024-08-20T09:00:00+02:00", "2024-08-20T15:00:00.000+02:00", 0, 0);
        when(eventService.upsert(id3, dto)).thenReturn(dto);
        mockMvc.perform(put("/i9events/" + id3)
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonI9Event))
                .andExpect(status().isOk());
        verify(eventService).upsert(id3, dto);
    }

    @Test
    @DisplayName("DELETE /api/i9events/id1 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void deleteOk() throws Exception {
        doNothing().when(eventService).delete(id1);
        mockMvc.perform(delete("/i9events/" + id1).principal(authentication))
                .andExpect(status().isOk());
        verify(eventService).delete(id1);    }

    @Test
    @DisplayName("GET /api/i9events/id1/inspectors returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void getInspectors() throws Exception {
        when(eventService.findInspectorsByEventId(id2)).thenReturn(List.of(idto));
        mockMvc.perform(get("/i9events/" + id2 + "/inspectors").principal(authentication))
                .andExpect(status().isOk());
        verify(eventService).findInspectorsByEventId(id2);
    }

    @Test
    @DisplayName("POST /api/i9events/id1/inspectors returns 201 Created")
    @WithMockAuthentication({ "guest" })
    void addInspector() throws Exception {
        String json = "{ \"inspectorName\": \"Inspector Name\", \"message\": \"Short Message\" }";
        when(eventService.assignInspectorToEvent(id1, idto)).thenReturn(dto1);
        mockMvc.perform(post("/i9events/" + id1 + "/inspectors").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
        verify(eventService).assignInspectorToEvent(id1, idto);
    }

    @Test
    @DisplayName("DELETE /api/i9events/id2/inspectors/name returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void removeInspector() throws Exception {
        String json = "{ \"inspectorName\": \"Inspector Name\", \"message\": \"Short message\" }";
        when(eventService.removeInspectorFromEvent(id2, idto.inspectorName())).thenReturn(dto1);
        mockMvc.perform(delete("/i9events/" + id2 + "/inspectors?name=" + idto.inspectorName()).principal(authentication))
                .andExpect(status().isOk());
        verify(eventService).removeInspectorFromEvent(id2, idto.inspectorName());
    }

    @Test
    @DisplayName("GET /api/i9events/id3/boats returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void getBoats() throws Exception {
        when(eventService.findBoatsByEventId(id3)).thenReturn(List.of(bdto));
        mockMvc.perform(get("/i9events/" + id3 + "/boats").principal(authentication))
                .andExpect(status().isOk());
        verify(eventService).findBoatsByEventId(id3);
    }

    @Test
    @DisplayName("POST /api/i9events/1001/boats returns 201 Created")
    @WithMockAuthentication({ "guest" })
    void addBoat() throws Exception {
        String json = "{ \"boatId\": \"" +  boatId + "\", \"message\": \"Dock P1 42\" }";
        when(eventService.assignBoatToEvent(id2, bdto)).thenReturn(dto1);
        mockMvc.perform(post("/i9events/" + id2 + "/boats").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
        verify(eventService).assignBoatToEvent(id2, bdto);
    }

    @Test
    @DisplayName("DELETE /api/i9events/1001/boats returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void removeBoat() throws Exception {
        String json = "{ \"boatId\": \"" + boatId + "\", \"message\": \"Dock P1 42\" }";
        when(eventService.removeBoatFromEvent(id3, boatId)).thenReturn(dto1);
        mockMvc.perform(delete("/i9events/" + id3 + "/boats").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        verify(eventService).removeBoatFromEvent(id3, boatId);
    }
}
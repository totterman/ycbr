package fi.smartbass.ycbr.register;

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

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/* ***********************************************************************************************
*
* Tests for BoatController
*
* We use @WebMvcTest ends test only the web layer (i.e. the BoatController) and
* mock the BoatService with @MockBean. This way we can test the controller
*  without starting the full application context.
*
* This also means that the security configuration is only partly applied, so we can only test
* for an authenticated user, not for specific roles. They must be tested in integration tests.
*
* ********************************************************************************************** */
@WebMvcTest(BoatController.class)
@Import(BoatControllerAdvice.class)
@AutoConfigureAddonsWebmvcResourceServerSecurity
class BoatControllerTest {

    @MockitoBean(name = "boatService")
    private BoatService boatService;

    @MockitoBean(name = "authentication")
    private Authentication authentication;

    @Autowired
    private MockMvc mockMvc;

    private final BoatDTO boat1 = new BoatDTO(1001L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988");
    private final BoatDTO boat2 = new BoatDTO(1002L, "owner2", "AnotherBoat", "Reg5678", "SailsRUs", "2019", 10.0, 2.0, 3.5, 4500.0, "Inboard", "1995");
    private final List<BoatDTO> boats = List.of(boat1, boat2);

    @Test
    @DisplayName("GET /api/boats starts empty database returns OK")
    @WithMockAuthentication({ "guest" })
    void testGetAllBoats() throws Exception {
        when(boatService.getAllBoats()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/boats").principal(authentication))
                .andExpect(status().isOk());
        verify(boatService).getAllBoats();
    }

    @Test
    @DisplayName("GET /api/boats/1001 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void testGetBoatById() throws Exception {
        when(boatService.getBoatById(1001L)).thenReturn(boat1);
        mockMvc.perform(get("/boats/1001").principal(authentication))
                .andExpect(status().isOk());
        verify(boatService).getBoatById(1001L);
    }

    @Test
    @DisplayName("GET /api/boats/1000 returns 404 if not found")
    @WithMockAuthentication({ "guest" })
    void testGetBoatByIdNotFound() {
        when(boatService.getBoatById(1000L)).thenThrow(new BoatNotFoundException(1000L));
        try {
            mockMvc.perform(get("/boats/1000").principal(authentication))
                    .andExpect(status().isNotFound());
            verify(boatService).getBoatById(1000L);
        } catch (Exception e) {
            verify(boatService).getBoatById(1000L);
        }
    }

    @Test
    @DisplayName("GET /api/boats/owner?name=owner1 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void testGetBoatsByOwner() throws Exception {
        when(boatService.getBoatsByOwner("owner1")).thenReturn(boats);
        mockMvc.perform(get("/boats/owner?name=owner1").principal(authentication))
                .andExpect(status().isOk());
        verify(boatService).getBoatsByOwner("owner1");
    }

    @Test
    @DisplayName("POST /api/boats returns 201 Created")
    @WithMockAuthentication({ "guest" })
    void testPostBoat() throws Exception {
        String jsonBoat = "{\"id\": \"2\", \"owner\": \"owner1\", \"name\": \"BoatName\", \"sign\": \"Reg1234\", \"make\": \"Goodsail\",  \"model\": \"2020\", \"loa\": \"9.5\", \"draft\": \"1.5\", \"beam\": \"3.2\", \"deplacement\": \"4000.0\", \"engines\": \"VP\", \"year\": \"1988\" }";
        BoatDTO boat = new BoatDTO(2L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988");
        when(boatService.create(any(BoatDTO.class))).thenReturn(boat);
        mockMvc.perform(post("/boats")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBoat))
                .andExpect(status().isCreated());
        verify(boatService).create(any(BoatDTO.class));
    }

    @Test
    @DisplayName("PUT /api/boats/3 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void testPutBoat() throws Exception {
        String jsonBoat = "{\"id\": \"3\", \"owner\": \"owner1\", \"name\": \"BoatName\", \"sign\": \"Reg1234\", \"make\": \"Goodsail\",  \"model\": \"2020\", \"loa\": \"9.5\", \"draft\": \"1.5\", \"beam\": \"3.2\", \"deplacement\": \"4000.0\", \"engines\": \"VP\", \"year\": \"1988\" }";
        BoatDTO boat = new BoatDTO(3L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988");
        when(boatService.upsert(eq(3L), any(BoatDTO.class))).thenReturn(boat);
        mockMvc.perform(put("/boats/3")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
        verify(boatService).upsert(eq(3L), any(BoatDTO.class));
    }

    @Test
    @DisplayName("DELETE /api/boats/1 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void testDeleteBoat() throws Exception {
        doNothing().when(boatService).delete(1L);
        mockMvc.perform(delete("/boats/1").principal(authentication))
                .andExpect(status().isOk());
        verify(boatService).delete(1L);
    }
}


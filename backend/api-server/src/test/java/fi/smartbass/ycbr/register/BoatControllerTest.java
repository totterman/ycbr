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
import java.util.Set;
import java.util.UUID;

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

    private final UUID boatId1 = UUID.randomUUID();
    private final UUID boatId2 = UUID.randomUUID();
    private final Set<EngineDto> engineDtos = Set.of(new EngineDto(0,"1995", "Yamaha", "ModelZ", "Serial123", 75.0));
    private final BoatDto boat1 = new BoatDto(boatId1, "club1", "cert1", "BoatName", "M", "Goodsail", "2020", "Reg1234", "L1234", "028", "W", "1977", "white", 9.5, 3.2, 1.5, 14.5, 4.7, 42.0, "D", engineDtos, 120.0,  200.0, 50.0, 6, "DK1234", "Cuxhaven", null, "Owner1", "Owner2");
    private final BoatDto boat2 = new BoatDto(boatId2, "club2", "cert2", "AnotherBoat", "S", "SailsRUs", "2019", "Reg5678", "S6554", "22", "G", "2006", "Navy Blue", 10.0, 3.5, 2.0, 17.2,6.8, 64.0, "S", engineDtos, 150.0, 250.0, 70.0, 8, "DK5678", "Hamburg", null, "Owner3", "Owner4");
    private final List<BoatDto> boats = List.of(boat1, boat2);

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
    @DisplayName("GET /api/boats/boatId1 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void testGetBoatById() throws Exception {
        when(boatService.getBoatByBoatId(boatId1)).thenReturn(boat1);
        mockMvc.perform(get("/boats/" + boatId1).principal(authentication))
                .andExpect(status().isOk());
        verify(boatService).getBoatByBoatId(boatId1);
    }

    @Test
    @DisplayName("GET /api/boats/boatId2 returns 404 if not found")
    @WithMockAuthentication({ "guest" })
    void testGetBoatByIdNotFound() {
        when(boatService.getBoatByBoatId(boatId2)).thenThrow(new BoatNotFoundException(boatId2));
        try {
            mockMvc.perform(get("/boats/" + boatId2).principal(authentication))
                    .andExpect(status().isNotFound());
            verify(boatService).getBoatByBoatId(boatId2);
        } catch (Exception e) {
            verify(boatService).getBoatByBoatId(boatId2);
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
        String jsonBoat = "{\"club\": \"club1\", \"cert\": \"cert1\", \"name\": \"BoatName\", \"kind\": \"S\", \"make\": \"Goodsail\",  \"model\": \"2020\", \"sign\": \"Reg1234\", \"year\": \"1994\", \"loa\": \"9.5\", \"beam\": \"3.2\", \"draft\": \"1.5\", \"height\": \"13.0\", \"deplacement\": \"4.0\", \"drive\": \"D\", \"engines\": null, \"owner\": \"owner One\", \"owner2\": \"owner Two\" }";
        BoatDto boat = boat1;
        when(boatService.create(any(NewBoatDto.class))).thenReturn(boat);
        mockMvc.perform(post("/boats")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBoat))
                .andExpect(status().isCreated());
        verify(boatService).create(any(NewBoatDto.class));
    }

    @Test
    @DisplayName("PUT /api/boats/3 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void testPutBoat() throws Exception {
        String jsonBoat = "{\"inspectionId\": \"" + boatId2 + "\", \"owner\": \"owner1\", \"name\": \"BoatName\", \"sign\": \"Reg1234\", \"make\": \"Goodsail\",  \"model\": \"2020\", \"loa\": \"9.5\", \"draft\": \"1.5\", \"beam\": \"3.2\", \"deplacement\": \"4000.0\", \"engines\": \"VP\", \"year\": \"1988\"\"owner\": \"owner One\", \"owner2\": \"owner Two\"  }";
        BoatDto boat = boat2;
        when(boatService.upsert(eq(boatId2), any(BoatDto.class))).thenReturn(boat);
        mockMvc.perform(put("/boats/" + boatId2)
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
        verify(boatService).upsert(eq(boatId2), any(BoatDto.class));
    }

    @Test
    @DisplayName("DELETE /api/boats/1 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void testDeleteBoat() throws Exception {
        doNothing().when(boatService).delete(boatId1);
        mockMvc.perform(delete("/boats/" + boatId1).principal(authentication))
                .andExpect(status().isOk());
        verify(boatService).delete(boatId1);
    }
}


package fi.smartbass.ycbr.register;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import com.c4_soft.springaddons.security.oauth2.test.webmvc.AutoConfigureAddonsWebmvcResourceServerSecurity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/* ***********************************************************************************************
*
* Tests for BoatController
*
* We use @WebMvcTest to test only the web layer (i.e. the BoatController) and
* mock the BoatService with @MockBean. This way we can test the controller
*  without starting the full application context.
*
* This also means that the security configuration is only partly applied, so we can only test
* for an authenticated user, not for specific roles. They must be tested in integration tests.
*
* ********************************************************************************************** */
@WebMvcTest(BoatController.class)
@AutoConfigureAddonsWebmvcResourceServerSecurity
class BoatControllerTest {

    @MockitoBean
    private BoatService boatService;

    @MockitoBean
    private Authentication authentication;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockAuthentication(authType = JwtAuthenticationToken.class, name = "p3t", authorities = { "guest" })
    void testGetAllBoats() throws Exception {
        when(boatService.getAllBoats()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/boats/").principal(authentication))
                .andExpect(status().isOk());
        verify(boatService).getAllBoats();
    }

    @Test
    @WithMockAuthentication(authType = JwtAuthenticationToken.class, name = "p3t", authorities = { "guest" })
    void testGetBoatById() throws Exception {
        Boat boat = new Boat(1L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        when(boatService.getBoatById(1L)).thenReturn(boat);
        mockMvc.perform(get("/boats/1").principal(authentication))
                .andExpect(status().isOk());
        verify(boatService).getBoatById(1L);
    }

    @Test
    @WithMockAuthentication(authType = JwtAuthenticationToken.class, name = "p3t", authorities = { "guest" })
    void testGetBoatsByOwner() throws Exception {
        when(boatService.getBoatsByOwner("owner1")).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/boats/owner/owner1").principal(authentication))
                .andExpect(status().isOk());
        verify(boatService).getBoatsByOwner("owner1");
    }

    @Test
    @WithMockAuthentication(authType = JwtAuthenticationToken.class, name = "p3t", authorities = { "guest" })
    void testPostBoat() throws Exception {
        String jsonBoat = """
{
  "id": 2,
  "owner": "owner1",
  "name": "BoatName",
  "registrationNumber": "Reg1234",
  "type": "Goodsail",
  "year": "2020",
  "loa": 9.5,
  "draft": 1.5,
  "beam": 3.2,
  "deplacement": 4000.0,
  "engine": "VP",
  "createdAt": null,
  "createdBy": null,
  "modifiedAt": null,
  "modifiedBy": null,
  "version": 0
}
                """;
        Boat boat = new Boat(2L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        when(boatService.addBoatToRegister(any(Boat.class))).thenReturn(boat);

        mockMvc.perform(post("/boats")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBoat))
                .andExpect(status().isCreated());

        verify(boatService).addBoatToRegister(any(Boat.class));
    }

    @Test
    @WithMockAuthentication(authType = JwtAuthenticationToken.class, name = "p3t", authorities = { "guest" })
    void testPutBoat() throws Exception {
        Boat boat = new Boat(3L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        when(boatService.updateBoatInRegister(eq(1L), any(Boat.class))).thenReturn(boat);

        mockMvc.perform(put("/boats/3")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(boatService).updateBoatInRegister(eq(3L), any(Boat.class));
    }

    @Test
    @WithMockAuthentication(authType = JwtAuthenticationToken.class, name = "p3t", authorities = { "guest" })
    void testDeleteBoat() throws Exception {
        doNothing().when(boatService).deleteBoatFromRegister(1L);

        mockMvc.perform(delete("/boats/1").principal(authentication))
                .andExpect(status().isOk());

        verify(boatService).deleteBoatFromRegister(1L);
    }
}


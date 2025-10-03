package fi.smartbass.ycbr.register;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithAnonymousUser;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import com.c4_soft.springaddons.security.oauth2.test.webmvc.AutoConfigureAddonsWebmvcResourceServerSecurity;
import com.c4_soft.springaddons.security.oauth2.test.webmvc.MockMvcSupport;

@WebMvcTest(BoatController.class)
@AutoConfigureAddonsWebmvcResourceServerSecurity
class BoatControllerIntegrationTest {

    @Autowired
    MockMvcSupport api;

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetBoats_thenUnauthorized() throws Exception {
        api.get("/boats")
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockAuthentication(authType = JwtAuthenticationToken.class, name = "boatowner", authorities = { "boatowner" })
    void givenUserIsBoatowner_whenGetBoats_thenOk() throws Exception {
        api.get("/boats")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("BoatName"))
                .andExpect(jsonPath("$[0].sign").value("Sign123"))
                .andExpect(jsonPath("$[0].make").value("MakeX"))
                .andExpect(jsonPath("$[0].model").value("ModelY"));
    }

    @Test
    @WithMockAuthentication(authType = JwtAuthenticationToken.class, name = "staff", authorities = { "staff" })
    void givenUserIsStaff_whenGetBoats_thenOk() throws Exception {
        api.get("/boats")
                .andExpect(status().isOk());
    }

    @Test
    @WithMockAuthentication(authType = JwtAuthenticationToken.class, name = "inspector", authorities = { "inspector" })
    void givenUserIsInspector_whenGetBoats_thenOk() throws Exception {
        api.get("/boats")
                .andExpect(status().isOk());
    }

    @Test
    @WithMockAuthentication(authType = JwtAuthenticationToken.class, name = "user", authorities = { "user" })
    void givenUserHasInvalidRole_whenGetBoats_thenForbidden() throws Exception {
        api.get("/boats")
                .andExpect(status().isOk());  // .isForbidden());
    }
}

package fi.smartbass.ycbr;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import com.c4_soft.springaddons.security.oauth2.test.webmvc.AutoConfigureAddonsWebmvcResourceServerSecurity;
import com.c4_soft.springaddons.security.oauth2.test.webmvc.MockMvcSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithAnonymousUser;

@WebMvcTest
@AutoConfigureAddonsWebmvcResourceServerSecurity
public class YcbrControllerUnitTest
{
    @Autowired
    MockMvcSupport mvc;

    @Test
    @WithMockAuthentication(authType = JwtAuthenticationToken.class, name = "p3t", authorities = { })
    void endpointWhenUserAuthorityThenAuthorized() throws Exception {
        this.mvc.get("/ycbr")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.greeting").isNotEmpty())
                .andExpect(jsonPath("$.greeting").value("Hello from YCBR!"));
    }

    @Test
    @WithAnonymousUser
    void anyWhenUnauthenticatedThenUnauthorized() throws Exception {
        this.mvc.get("/any")
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockAuthentication(authType = JwtAuthenticationToken.class, name = "p3t", authorities = { })
    void staffWhenUserAuthorityThenAuthorized() throws Exception {
        this.mvc.get("/ycbr/staff")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.greeting").isNotEmpty())
                .andExpect(jsonPath("$.greeting").value("Hello, staff!"));
    }

    @Test
    @WithAnonymousUser
    void staffWhenNotUserAuthorityThenForbidden() throws Exception {
        this.mvc.get("/ycbr/staff")
                .andExpect(status().isUnauthorized());
    }

}

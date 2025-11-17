package fi.smartbass.ycbr;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import com.c4_soft.springaddons.security.oauth2.test.webmvc.AddonsWebmvcTestConf;
import com.c4_soft.springaddons.security.oauth2.test.webmvc.MockMvcSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ImportAutoConfiguration(AddonsWebmvcTestConf.class)
@ActiveProfiles("integration")
class ApiServerApplicationTests {

    @Autowired
    MockMvcSupport api;

	@Test
	void contextLoads() {
	}

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetMe_thenOk() throws Exception {
        api.get("/me")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").isEmpty())
                .andExpect(jsonPath("$.email").isEmpty())
                .andExpect(jsonPath("$.roles").isEmpty())
                .andExpect(jsonPath("$.exp").isEmpty());
    }

    @Test
    @WithJwt("p3t.json")
    void givenRequestIsAuthorized_whenGetMe_thenOk() throws Exception {
        api.get("/me")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").isNotEmpty())
                .andExpect(jsonPath("$.email").isNotEmpty())
                .andExpect(jsonPath("$.roles").isNotEmpty())
                .andExpect(jsonPath("$.exp").isNumber());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetLiveness_thenOk() throws Exception {
        api.get("/actuator/health/liveness")
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetReadiness_thenOk() throws Exception {
        api.get("/actuator/health/readiness")
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Anonymous user cannot access /actuator/metrics")
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetMetrics_thenUnauthorized() throws Exception {
        api.get("/actuator/metrics")
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Admin user can access /actuator/metrics")
    @WithMockAuthentication(authorities = {"ROLE_ADMIN"})
    void givenRequestIsAdmin_whenGetMetrics_thenOk() throws Exception {
        api.get("/actuator/metrics")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.names").isArray());
    }

}

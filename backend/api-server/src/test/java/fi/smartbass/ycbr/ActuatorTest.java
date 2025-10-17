package fi.smartbass.ycbr;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ActuatorTest {

    private final WebTestClient api;

    @Autowired
    public ActuatorTest(WebTestClient api) {
        this.api = api;
    }

    @Test
    @DisplayName("Anonymous user cannot access /actuator/metrics")
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetMetrics_thenUnauthorized() throws Exception {
        api.get()
                .uri("/actuator/metrics")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @DisplayName("Admin user can access /actuator/metrics")
    @WithMockAuthentication(authorities = {"ROLE_ADMIN"})
    void givenRequestIsAdmin_whenGetMetrics_thenOk() throws Exception {
        api.get()
                .uri("/actuator/metrics")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.names").isArray();
    }

    @Test
    @DisplayName("Anonymous user can access /actuator/health/liveness")
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetLiveness_thenOk() throws Exception {
        api.get()
                .uri("/actuator/health/liveness")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("UP");
    }

    @Test
    @DisplayName("Anonymous user can access /actuator/health/readiness")
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetReadiness_thenOk() throws Exception {
        api.get()
                .uri("/actuator/health/readiness")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("UP");
    }
}
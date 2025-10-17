package fi.smartbass.ycbr;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class MeControllerTest {

    @Autowired
    private WebTestClient api;

    @Test
    @DisplayName("Anonymous user can access /me but gets empty info")
    void givenRequestIsAnonymous_whenGetMe_thenOk() throws Exception {
        api.get()
                .uri("/me")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.username").isEmpty()
                .jsonPath("$.email").isEmpty()
                .jsonPath("$.roles").isEmpty()
                .jsonPath("$.exp").isEmpty();
    }

    @Test
    @DisplayName("Authenticated user can access /me and gets own info")
    @WithJwt("p3t.json")
    void givenRequestIsAuthorized_whenGetMe_thenOk() throws Exception {
        api.get()
                .uri("/me")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.username").isEqualTo("p3t")
                .jsonPath("$.email").isEqualTo("petri@smartbass.fi")
                .jsonPath("$.roles").isNotEmpty()
                .jsonPath("$.exp").isNumber();
    }
}

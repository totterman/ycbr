package fi.smartbass.ycbr;

import com.c4_soft.springaddons.security.oauth2.test.webflux.AddonsWebfluxTestConf;
import com.c4_soft.springaddons.security.oauth2.test.webflux.WebTestClientSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.security.oauth2.client.autoconfigure.OAuth2ClientProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@EnableConfigurationProperties({OAuth2ClientProperties.class})
@AutoConfigureWebTestClient
@Import({TestSecurityConf.class, AddonsWebfluxTestConf.class})
class BffServerApplicationIntegrationTests {

    @Autowired
    private WebTestClientSupport api;

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetLoginOptions_thenOk() throws Exception {
        api.get("/login-options")
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$")
                .isArray()
                .jsonPath("$[0].label")
                .isEqualTo("ycbr")
                .jsonPath("$[0].loginUri")
                .isEqualTo("http://localhost:7080/bff/oauth2/authorization/ycbr")
                .jsonPath("$[0].isSameAuthority")
                .isEqualTo(true);
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetLiveness_thenOk() throws Exception {
        api.get("/actuator/health/liveness")
                .expectStatus()
                .isOk();
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetReadiness_thenOk() throws Exception {
        api.get("/actuator/health/readiness")
                .expectStatus()
                .isOk();
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetHealth_thenOk() throws Exception {
        api.get("/actuator/health")
                .expectStatus()
                .isOk();
    }
}

package fi.smartbass.ycbr;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.security.test.context.support.WithAnonymousUser;

import com.c4_soft.springaddons.security.oauth2.test.webflux.AutoConfigureAddonsWebfluxResourceServerSecurity;
import com.c4_soft.springaddons.security.oauth2.test.webflux.WebTestClientSupport;

@WebFluxTest
@AutoConfigureAddonsWebfluxResourceServerSecurity
@EnableConfigurationProperties({OAuth2ClientProperties.class})
class LoginOptionsControllerUnitTest {
    @Autowired
    WebTestClientSupport api;
    
    @Value("${hostname:localhost}")
    String hostname;

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
            .isEqualTo("http://%s:7080/bff/oauth2/authorization/ycbr".formatted(hostname))
            .jsonPath("$[0].isSameAuthority")
            .isEqualTo(true);
    }

}


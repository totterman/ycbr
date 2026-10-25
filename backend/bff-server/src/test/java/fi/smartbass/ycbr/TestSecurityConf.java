package fi.smartbass.ycbr;

import com.c4_soft.springaddons.security.oauth2.test.webflux.WebTestClientProperties;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import reactor.core.publisher.Mono;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
@ImportAutoConfiguration(classes = { WebTestClientProperties.class })
public class TestSecurityConf {
    @Bean
    InMemoryReactiveClientRegistrationRepository clientRegistrationRepository() {
        final var clientRegistrationRepository = mock(InMemoryReactiveClientRegistrationRepository.class);
        when(clientRegistrationRepository.iterator()).thenReturn(new ArrayList<ClientRegistration>().iterator());
        when(clientRegistrationRepository.spliterator()).thenReturn(new ArrayList<ClientRegistration>().spliterator());
        when(clientRegistrationRepository.findByRegistrationId(anyString()))
                .thenAnswer(
                        invocation -> Mono
                                .just(
                                        ClientRegistration
                                                .withRegistrationId(invocation.getArgument(0))
                                                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                                                .clientId(invocation.getArgument(0))
                                                .redirectUri("http://localhost:8080/oauth2/code/%s".formatted(invocation.getArgument(0).toString()))
                                                .authorizationUri("https://localhost:8443/auth")
                                                .tokenUri("https://localhost:8443/token")
                                                .build()));
        return clientRegistrationRepository;
    }

/*    @Bean
    WebTestClientSupport
    webTestClientSupport(WebTestClientProperties webTestClientProperties, WebTestClient webTestClient, SpringAddonsOidcProperties addonsProperties) {
        return new WebTestClientSupport(webTestClientProperties, webTestClient, addonsProperties);
    } */
}

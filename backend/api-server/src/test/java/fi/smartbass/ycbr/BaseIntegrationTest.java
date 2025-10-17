package fi.smartbass.ycbr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("integrationtest")
public abstract class BaseIntegrationTest {

    @Autowired
    public WebTestClient api;

/* ***********************************************************************************************
 *
 * PostgreSQL database container for integration tests
 * BE SURE VALUES ARE IN SYNC WITH src/test/resources/application-integrationtest.properties!!!
 *
 * ********************************************************************************************* */
    public static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:18-alpine")
                    .withDatabaseName("itdb")
                    .withInitScript("test-schema.sql")
                    .withUsername("test")
                    .withExposedPorts(5432)
                    .withPassword("test")
                    .withStartupTimeout(Duration.ofSeconds(30L));

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {

        postgres.start();

        registry.add("spring.r2dbc.url", () -> String.format(
                "r2dbc:postgresql://%s:%d/%s",
                postgres.getHost(),
                postgres.getMappedPort(5432),
                postgres.getDatabaseName()
        ));
        registry.add("spring.r2dbc.username", postgres::getUsername);
        registry.add("spring.r2dbc.password", postgres::getPassword);

        Runtime.getRuntime().addShutdownHook(new Thread(postgres::stop));
    }
}

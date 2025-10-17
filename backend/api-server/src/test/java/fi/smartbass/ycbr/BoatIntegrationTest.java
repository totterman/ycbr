package fi.smartbass.ycbr;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import fi.smartbass.ycbr.register.BoatDTO;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

public class BoatIntegrationTest extends BaseIntegrationTest {

    @BeforeAll
    static void setup() {
        System.out.println("Starting BoatIntegrationTest...");
   }

    @Test
    @Order(1)
    @DisplayName("Context loads")
    void contextLoads() {
        assert api != null;
        assert postgres != null;
    }

    @Test
    @Order(2)
    @DisplayName("PostgreSQL container is running")
    void testPostgreSQLModule() {
        assert postgres != null;
        assert postgres.isRunning();
        System.out.println("JDBC URL:          " + postgres.getJdbcUrl());
        System.out.println("Host:              " + postgres.getHost());
        System.out.println("Port:              " + postgres.getMappedPort(5432));
        System.out.println("Database:          " + postgres.getDatabaseName());
        System.out.println("Username:          " + postgres.getUsername());
        System.out.println("Password:          " + postgres.getPassword());
        System.out.println("Test query string: " + postgres.getTestQueryString());
    }


    @Test
    @Order(3)
    @DisplayName("Adding a boat and finding all boats works")
    @WithJwt("ronja.json")
    void insertBoat() {
        assert postgres.isRunning();
        BoatDTO boat = new BoatDTO(null, "owner11", "BoatName", "Reg11234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988");

        var before = api.get()
                .uri("/boats")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BoatDTO.class)
                .returnResult();

        int sizeBefore = before.getResponseBody().size();

        api.mutateWith(csrf())
                .post()
                .uri("/boats")
                .bodyValue(boat)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BoatDTO.class)
                .consumeWith(res -> {
                    BoatDTO createdBoat = res.getResponseBody();
                    assert createdBoat != null;
                    assert createdBoat.id() != null;
                    assert createdBoat.name().equals("BoatName");
                    assert createdBoat.owner().equals("owner11");
                });

        api.get()
                .uri("/boats")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BoatDTO.class)
                .hasSize(sizeBefore + 1)
                .consumeWith(res -> {
                    printResponseBody(res.getResponseBodyContent());
                    List<BoatDTO> boats = res.getResponseBody();
                    Assertions.assertNotNull(boats);
                    BoatDTO newBoat = boats.stream()
                            .filter(b -> b.name().equals(boat.name()) && b.owner().equals(boat.owner()))
                            .findFirst()
                            .orElse(null);
                    assert newBoat != null;
                    assert newBoat.id() != null;
                    assert newBoat.sign().equals(boat.sign());
                });
    }

    @Test
    @Order(4)
    @DisplayName("Finding boat by ID works")
    @WithMockAuthentication({ "boatowner" })
    void findById() {
        assert postgres.isRunning();
        api.get()
                .uri("/boats/1002")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BoatDTO.class)
                .consumeWith(res -> {
                    printResponseBody(res.getResponseBodyContent());
                    BoatDTO foundBoat = res.getResponseBody();
                    assert foundBoat != null;
                    assert foundBoat.id().equals(1002L);
                    assert foundBoat.name().equals("Ocean Explorer");
                    assert foundBoat.owner().equals("ronja");
                });
    }

    @Test
    @Order(5)
    @DisplayName("Finding boat by owner works")
    @WithMockAuthentication({ "boatowner" })
    void FindTwoByOwner() {
        assert postgres.isRunning();
        api.get()
                .uri(uriBuilder -> uriBuilder.path("/boats/owner").queryParam("name", "ronja").build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BoatDTO.class)
                .hasSize(2)
                .consumeWith(res -> {
                    BoatDTO secondBoat = res.getResponseBody().get(1);
                    assert secondBoat != null;
                    assert secondBoat.id() != null;
                });

    }

    @Test
    @Order(6)
    @DisplayName("Updating a boat works")
    @WithJwt("ronja.json")
    void updateBoat() {
        assert postgres.isRunning();

        var before = api.get()
                .uri("/boats/1002")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BoatDTO.class)
                .returnResult();
        BoatDTO boatBefore = before.getResponseBody();
        BoatDTO boatUpdated = new BoatDTO(boatBefore.id(), boatBefore.owner(), boatBefore.name() + "X", boatBefore.sign(), boatBefore.make(), boatBefore.model(), boatBefore.loa(), boatBefore.draft(), boatBefore.beam(), boatBefore.deplacement(), boatBefore.engines(), "2025");

        api.mutateWith(csrf())
                .put()
                .uri("/boats/" + boatUpdated.id())
                .bodyValue(boatUpdated)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BoatDTO.class)
                .consumeWith(res -> {
                    BoatDTO updatedBoat = res.getResponseBody();
                    assert updatedBoat != null;
                    assert updatedBoat.id() != null;
                });

        var after = api.get()
                .uri("/boats/1002")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BoatDTO.class)
                .returnResult();
        BoatDTO boatAfter = after.getResponseBody();
        assert boatAfter != null;
        assert boatAfter.year().equals(boatUpdated.year());
        assert boatBefore.id().equals(boatAfter.id());
        assert boatBefore.model().equals(boatAfter.model());
        assert !boatBefore.name().equals(boatAfter.name());
        assert !boatBefore.year().equals(boatAfter.year());
    }

    @Test
    @Order(6)
    @DisplayName("Updating new boat works")
    @WithJwt("ronja.json")
    void updateNewBoat() {
        assert postgres.isRunning();

        var before = api.get()
                .uri("/boats")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BoatDTO.class)
                .returnResult();
        int sizeBefore = before.getResponseBody().size();
        BoatDTO boat = new BoatDTO(112L, "owner112", "BoatName112", "Reg112112", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988");

        api.mutateWith(csrf())
                .put()
                .uri("/boats/" + boat.id())
                .bodyValue(boat)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BoatDTO.class)
                .consumeWith(res -> {
                    BoatDTO updatedBoat = res.getResponseBody();
                    assert updatedBoat != null;
                    assert updatedBoat.id() != null;
                    assert updatedBoat.owner().equals(boat.owner());
                    assert updatedBoat.name().equals(boat.name());
                    assert updatedBoat.sign().equals(boat.sign());
                });
    }

    @Test
    @Order(7)
    @DisplayName("Deleting an existing boat works")
    @WithMockAuthentication({ "boatowner" })
    void deleteExistingBoat() {
        assert postgres.isRunning();

        var before = api.get()
                .uri("/boats/1003")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BoatDTO.class)
                .returnResult();
        BoatDTO boatBefore = before.getResponseBody();

        api.mutateWith(csrf())
                .delete()
                .uri("/boats/" + boatBefore.id())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        api.get()
                .uri("/boats/" + boatBefore.id())
                .exchange()
                .expectStatus().isNotFound();
    }

    private void printResponseBody(byte[] responseBody) {
        if (responseBody != null) {
            System.out.println(new String(responseBody));
        } else {
            System.out.println("Response body is null");
        }
    }

}

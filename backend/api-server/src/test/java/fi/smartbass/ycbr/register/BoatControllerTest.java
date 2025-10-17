package fi.smartbass.ycbr.register;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import fi.smartbass.ycbr.register.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@WebFluxTest(controllers = BoatController.class)
public class BoatControllerTest {

    @Autowired
    private WebTestClient api;

    @MockitoBean
    private BoatService boatService;

    @MockitoBean
    private BoatRepository boatRepository;

    private final BoatDTO boat1 = new BoatDTO(1001L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988");
    private final BoatDTO boat2 = new BoatDTO(1002L, "owner2", "AnotherBoat", "Reg5678", "SailsRUs", "2019", 10.0, 2.0, 3.5, 4500.0, "Inboard", "1995");
    private final List<BoatDTO> boats = List.of(boat1, boat2);

    @Test
    @DisplayName("Unauthenticated returns 401")
    @WithAnonymousUser
    void testGetAllBoatsUnauthenticated() {
        given(boatService.getAllBoats()).willReturn(Flux.fromIterable(boats));
        api.get()
                .uri("/boats")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @DisplayName("GET /api/boats from empty database returns OK")
    @WithMockAuthentication({ "guest" })
    void testGetNoBoats() {
        given(boatService.getAllBoats()).willReturn(Flux.fromIterable(Collections.emptyList()));
        api.get()
                .uri("/boats")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BoatDTO.class)
                .hasSize(0);
    }

    @Test
    @DisplayName("GET /api/boats returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void testGetAllBoats() {
        given(boatService.getAllBoats()).willReturn(Flux.fromIterable(boats));
        api.get()
                .uri("/boats")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(boat1.id())
                .jsonPath("$[0].owner").isEqualTo(boat1.owner())
                .jsonPath("$[0].name").isEqualTo(boat1.name())
                .jsonPath("$[1].id").isEqualTo(boat2.id())
                .jsonPath("$[1].owner").isEqualTo(boat2.owner())
                .jsonPath("$[1].name").isEqualTo(boat2.name());
    }

    @Test
    @DisplayName("GET /api/boats/1 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void testGetBoatById() throws Exception {
        Long testId = boat1.id();
        given(boatService.getBoatById(testId)).willReturn(Mono.just(boat1));
        api.get()
                .uri("/boats/" + testId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(boat1.id())
                .jsonPath("$.owner").isEqualTo(boat1.owner())
                .jsonPath("$.name").isEqualTo(boat1.name());
    }

    @Test
    @DisplayName("GET /api/boats/1 returns 404 if not found")
    @WithMockAuthentication({ "guest" })
    void testGetBoatByIdNotFound() throws Exception {
        Long testId = boat1.id();
        given(boatService.getBoatById(testId)).willThrow(new BoatNotFoundException(testId));
        api.get()
                .uri("/boats/" + testId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("GET /api/boats/owner?name=owner1 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void testGetBoatsByOwner() {
        given(boatService.getBoatsByOwner("owner1")).willReturn(Flux.fromIterable(List.of(boat1)));
        api.get()
                .uri("/boats/owner?name=owner1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("POST /api/boats returns 201 Created")
    @WithMockAuthentication({ "guest" })
    void testPostBoat() {
        String jsonBoat = "{\"id\": \"2\", \"owner\": \"owner1\", \"name\": \"BoatName\", \"sign\": \"Reg1234\", \"make\": \"Goodsail\",  \"model\": \"2020\", \"loa\": \"9.5\", \"draft\": \"1.5\", \"beam\": \"3.2\", \"deplacement\": \"4000.0\", \"engines\": \"VP\", \"year\": \"1988\" }";
        BoatDTO boat = new BoatDTO(2L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988");
        given(boatService.addBoatToRegister(any(Boat.class))).willReturn(Mono.just(boat));

        api.mutateWith(csrf())
                .post()
                .uri("/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonBoat)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @DisplayName("PUT /api/boats/3 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void testPutBoat() {
        String jsonBoat = "{\"id\": \"3\", \"owner\": \"owner1\", \"name\": \"BoatName\", \"sign\": \"Reg1234\", \"make\": \"Goodsail\",  \"model\": \"2020\", \"loa\": \"9.5\", \"draft\": \"1.5\", \"beam\": \"3.2\", \"deplacement\": \"4000.0\", \"engines\": \"VP\", \"year\": \"1988\" }";
        BoatDTO boat = new BoatDTO(3L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988");
        given(boatService.updateBoatInRegister(eq(3L), any(Boat.class))).willReturn(Mono.just(boat));

        api.mutateWith(csrf())
                .put()
                .uri("/boats/3")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonBoat)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("DELETE /api/boats/1 returns 200 OK")
    @WithMockAuthentication({ "guest" })
    void testDeleteBoat() throws Exception {
        api.mutateWith(csrf())
                .delete()
                .uri("/boats/1")
                .exchange()
                .expectStatus().isOk();
    }

}

package fi.smartbass.ycbr.register;

import fi.smartbass.ycbr.register.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("repositorytest")
@ExtendWith(MockitoExtension.class)
@Import(BoatService.class)
@DataR2dbcTest
public class BoatServiceTest {

    @MockitoBean
    private BoatRepository boatRepository;

    @Autowired
    private BoatService boatService;

    private final Boat boat1 = new Boat(1001L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
    private final Boat boat2 = new Boat(1002L, "owner2", "AnotherBoat", "Reg5678", "SailsRUs", "2019", 10.0, 2.0, 3.5, 4500.0, "Inboard", "1995", null, "unitTest", null, "unitTest", 0);
    private final List<Boat> boats = List.of(boat1, boat2);

    @Test
    @DisplayName("getAllBoats returns all boats")
    void getAllBoats_returnsAllBoats() {
        when(boatRepository.findAll()).thenReturn(Flux.fromIterable(boats));
        Flux<BoatDTO> allBoats = boatService.getAllBoats();
        StepVerifier.create(allBoats)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    @DisplayName("getAllBoats returns no boats when empty")
    void getAllBoats_returnsNoBoatsWhenEmpty() {
        when(boatRepository.findAll()).thenReturn(Flux.fromIterable(Collections.emptyList()));
        StepVerifier.create(boatService.getAllBoats())
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("getBoatById returns boat when found")
    void getBoatById_returnsBoat_whenFound() {
        when(boatRepository.findById(1001L)).thenReturn(Mono.just(boat1));
        StepVerifier.create(boatService.getBoatById(1001L))
                .expectNextMatches(found -> found.id().equals(1001L) && found.owner().equals("owner1"))
                .verifyComplete();
    }

    @Test
    @DisplayName("getBoatById throws when not found")
    void getBoatById_throws_whenNotFound() {
        when(boatRepository.findById(1001L)).thenReturn(Mono.empty());
        StepVerifier.create(boatService.getBoatById(1001L))
                .expectError(BoatNotFoundException.class)
                .verify();
    }

    @Test
    @DisplayName("getBoatsByOwner returns boats for given owner")
    void getBoatsByOwner_returnsBoats() {
        when(boatRepository.findByOwner("owner1")).thenReturn(Flux.fromIterable(List.of(boat1)));
        StepVerifier.create(boatService.getBoatsByOwner("owner1"))
                .expectNextMatches(found -> found.owner().equals("owner1"))
                .verifyComplete();
    }

    @Test
    @DisplayName("addBoatToRegister saves boat when id is null")
    void addBoatToRegister_savesBoat_whenIdIsNull() {
        Boat boat = new Boat(null, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        when(boatRepository.save(boat)).thenReturn(Mono.just(boat));
        StepVerifier.create(boatService.addBoatToRegister(boat))
                .expectNextMatches(saved -> saved.owner().equals("owner") && saved.name().equals("name"))
                .verifyComplete();
    }

    @Test
    @DisplayName("addBoatToRegister throws when id exists")
    void addBoatToRegister_throws_whenIdExists() {
        Boat boat = new Boat(1L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        when(boatRepository.existsById(1L)).thenReturn(Mono.just(Boolean.TRUE));
        StepVerifier.create(boatService.addBoatToRegister(boat))
                .expectError(BoatAlreadyExistsException.class)
                .verify();
    }

    @Test
    @DisplayName("addBoatToRegister saves when id does not exist")
    void addBoatToRegister_saves_whenIdNotExists() {
        Boat boat = new Boat(1L, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", null, null, null, null, 0);
        when(boatRepository.existsById(1L)).thenReturn(Mono.just(Boolean.FALSE));
        when(boatRepository.save(boat)).thenReturn(Mono.just(boat));
        StepVerifier.create(boatService.addBoatToRegister(boat))
                .expectNextMatches(saved -> saved.owner().equals("owner") && saved.name().equals("name"))
                .verifyComplete();
    }

    @Test
    @DisplayName("deleteBoatFromRegister deletes when exists")
    void deleteBoatFromRegister_deletes_whenExists() {
        when(boatRepository.existsById(1L)).thenReturn(Mono.just(Boolean.TRUE));
        when(boatRepository.deleteById(1L)).thenReturn(Mono.empty());
        StepVerifier.create(boatService.deleteBoatFromRegister(1L)).verifyComplete();
    }

    @Test
    @DisplayName("deleteBoatFromRegister is ok when not exists")
    void deleteBoatFromRegister_ok_whenNotExists() {
        when(boatRepository.existsById(1L)).thenReturn(Mono.just(Boolean.FALSE));
        when(boatRepository.deleteById(1L)).thenReturn(Mono.empty());
        StepVerifier.create(boatService.deleteBoatFromRegister(1L)).verifyComplete();
    }

    @Test
    @DisplayName("updateBoatInRegister updates when exists")
    void updateBoatInRegister_updates_whenExists() {
        Instant createdAt = Instant.now();
        Long testId = 1L;
        Boat existing = new Boat(testId, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", createdAt, "createdBy", null, null, 0);
        Boat updated = new Boat(testId, "newOwner", "newName", "newSign", "newMake", "newModel", 2.0, 2.0, 2.0, 2.0, "newEngines", "newYear", null, null, null, null, 0);
        when(boatRepository.findById(testId)).thenReturn(Mono.just(existing));
        when(boatRepository.existsById(testId)).thenReturn(Mono.just(Boolean.TRUE));
        when(boatRepository.save(any(Boat.class))).thenReturn(Mono.just(updated));

        StepVerifier.create(boatService.getBoatById(testId))
                .expectNextMatches(result -> result.owner().equals("owner") && result.name().equals("name"))
                .verifyComplete();
        StepVerifier.create(boatService.updateBoatInRegister(testId, existing))
                .expectNextMatches(result -> result.owner().equals("newOwner") && result.name().equals("newName"))
                .verifyComplete();
    }

    @Test
    @DisplayName("updateBoatInRegister adds when not exists")
    void updateBoatInRegister_adds_whenNotExists() {
        Instant createdAt = Instant.now();
        Long testId = 1L;
        Boat existing = new Boat(testId, "owner", "name", "sign", "make", "model", 1.0, 1.0, 1.0, 1.0, "engines", "year", createdAt, "createdBy", null, null, 0);
        Boat updated = new Boat(testId, "newOwner", "newName", "newSign", "newMake", "newModel", 2.0, 2.0, 2.0, 2.0, "newEngines", "newYear", null, null, null, null, 0);
        when(boatRepository.existsById(testId)).thenReturn(Mono.just(Boolean.FALSE));
        when(boatRepository.save(any(Boat.class))).thenReturn(Mono.just(updated));
        when(boatRepository.findById(testId)).thenReturn(Mono.just(updated));

        StepVerifier.create(boatService.updateBoatInRegister(testId, existing))
                .expectNextMatches(result -> result.owner().equals("newOwner") && result.name().equals("newName"))
                .verifyComplete();
        StepVerifier.create(boatService.getBoatById(testId))
                .expectNextMatches(result -> result.owner().equals("newOwner") && result.name().equals("newName"))
                .verifyComplete();
    }
}

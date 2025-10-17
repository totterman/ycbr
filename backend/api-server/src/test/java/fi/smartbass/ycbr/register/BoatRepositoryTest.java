package fi.smartbass.ycbr.register;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.core.io.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.r2dbc.connection.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

@DataR2dbcTest
@ActiveProfiles("repositorytest")
public class BoatRepositoryTest {

    private final BoatRepository boatRepository;
    private final ConnectionFactory connectionFactory;

    @Autowired
    public BoatRepositoryTest(BoatRepository boatRepository, ConnectionFactory connectionFactory) {
        this.boatRepository = boatRepository;
        this.connectionFactory = connectionFactory;
    }

    private Boat savedBoat;

    private void executeScriptBlocking(final Resource sqlScript) {
        Mono.from(connectionFactory.create())
                .flatMap(connection -> ScriptUtils.executeSqlScript(connection, sqlScript))
                .block();
    }

    private void setUp() {
        Instant createdAt = Instant.now();
        savedBoat = new Boat(112L, "KnownOwner", "KnownBoat", "KnownSign112", "MakeX", "ModelY", 10.5, 2.5, 3.5, 5000.0, "", "1982", createdAt, "null", null, null, 0);
        Flux<Boat> deleteAndInsert = boatRepository.deleteAll()
                .thenMany(boatRepository.saveAll(
                        Flux.just(
                                new Boat(null, "OwnerName", "BoatName", "Sign123", "MakeX", "ModelY", 10.5, 2.5, 3.5, 5000.0, "", "1982", createdAt, "null", null, null, 0),
                                new Boat(null, "AnotherOwner", "AnotherBoat", "Sign456", "MakeA", "ModelB", 12.0, 3.0, 4.0, 6000.0, "", "1990", createdAt, "null", null, null, 0),
                                new Boat(null, "ThirdOwner", "ThirdBoat", "Sign789", "MakeC", "ModelD", 15.0, 4.0, 5.0, 7000.0, "", "1995", createdAt, "null", null, null, 0),
                                savedBoat
                        )));
        StepVerifier.create(deleteAndInsert)
                .expectNextCount(4L)
                .verifyComplete();
    }

    @BeforeEach
    public void rollOutTestData(@Value("classpath:/test-schema.sql") Resource script) {
        executeScriptBlocking(script);
        setUp();
    }

    @Test
    @DisplayName("Find by id")
    public void findById() {
        StepVerifier.create(boatRepository.findById(savedBoat.getId()))
                .expectNextMatches(found -> found.getSign().equals("KnownSign112") && found.getOwner().equals("KnownOwner"))
                .verifyComplete();
    }

    @Test
    @DisplayName("Not found by id")
    public void notFoundById() {
        StepVerifier.create(boatRepository.findById(999L))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("Save and find by id")
    public void saveAndFindById() {
        Boat newBoat = new Boat(null, "owner1", "NewBoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        StepVerifier.create(boatRepository.save(newBoat))
                .expectNextMatches(created -> created.getSign().equals(newBoat.getSign()) && created.getName().equals("NewBoatName") && created.getId() != null)
                .verifyComplete();
        StepVerifier.create(boatRepository.findById(newBoat.getId()))
                .expectNextMatches(found -> found.getSign().equals(newBoat.getSign()) && found.getOwner().equals("owner1"))
                .verifyComplete();
        StepVerifier.create(boatRepository.count())
                .expectNext(5L)
                .verifyComplete();
    }

    @Test
    @DisplayName("Find by owner")
    public void findByOwner() {
        StepVerifier.create(boatRepository.findByOwner("AnotherOwner"))
                .expectNextMatches(found -> found.getSign().equals("Sign456") && found.getName().equals("AnotherBoat"))
                .verifyComplete();
    }

    @Test
    @DisplayName("Not found by owner")
    public void notFoundByOwner() {
        StepVerifier.create(boatRepository.findByOwner("UnknownOwner"))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("Find by name")
    public void findByName() {
        StepVerifier.create(boatRepository.findByName("ThirdBoat"))
                .expectNextMatches(found -> found.getSign().equals("Sign789") && found.getOwner().equals("ThirdOwner"))
                .verifyComplete();
    }

    @Test
    @DisplayName("Not found by name")
    public void notFoundByName() {
        StepVerifier.create(boatRepository.findByName("UnknownBoat"))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("Delete by id")
    void deleteById() {
        StepVerifier.create(boatRepository.deleteById(savedBoat.getId()))
                .verifyComplete();
        StepVerifier.create(boatRepository.existsById(savedBoat.getId()))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    @DisplayName("Duplicate key error")
    void duplicateError() {
        Boat newBoat = new Boat(savedBoat.getId(), "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        StepVerifier.create(boatRepository.save(newBoat))
                .expectError(DuplicateKeyException.class)
                .verify();
    }

    @Test
    @DisplayName("Optimistic Lock Error")
    void optimisticLockError() {

        // Store the saved entity in two separate entity objects
        Boat boat1 = boatRepository.findById(savedBoat.getId()).block();
        Boat boat2 = boatRepository.findById(savedBoat.getId()).block();

        // Update the entity using the first entity object
        Assertions.assertNotNull(boat1);
        boat1.setName("n1");
        boatRepository.save(boat1).block();

        //  Update the entity using the second entity object.
        // This should fail since the second entity now holds a old version number, i.e. a Optimistic Lock Error
        Assertions.assertNotNull(boat2);
        StepVerifier.create(boatRepository.save(boat2))
                .expectError(OptimisticLockingFailureException.class)
                .verify();

        // Get the updated entity from the database and verify its new sate
        StepVerifier.create(boatRepository.findById(savedBoat.getId()))
                .expectNextMatches(found -> found.getVersion() == 2 && found.getName().equals("n1"))
                .verifyComplete();
    }
}

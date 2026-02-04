package fi.smartbass.ycbr.register;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest(includeFilters = @ComponentScan.Filter(EnableJdbcAuditing.class))
@AutoConfigureTestDatabase
@ActiveProfiles("integration")
@Sql("/db.sql")
class BoatRepositoryTest {

    @Autowired
    private BoatRepository boatRepository;

    private final UUID boatId1 = UUID.randomUUID();
    private final UUID boatId2 = UUID.randomUUID();
    private final UUID boatId3 = UUID.randomUUID();

    @Test
    @DisplayName("Save and find by inspectionId")
    void saveAndFindById() {
        BoatEntity boat = new BoatEntity(boatId1, "owner1", "BoatName", "M", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        BoatEntity saved = boatRepository.save(boat);

        Optional<BoatEntity> found = boatRepository.findByBoatId(saved.getBoatId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("BoatName");
    }

    @Test
    @DisplayName("Check auditing")
    void saveAndFindByIdNoId() {
        BoatEntity boat = new BoatEntity(null, "owner1", "BoatName", "M", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        BoatEntity saved = boatRepository.save(boat);
        assertThat(saved.getBoatId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getCreatedBy()).isNotNull();
        assertThat(saved.getModifiedAt()).isEqualTo(saved.getCreatedAt());
        assertThat(saved.getModifiedBy()).isEqualTo(saved.getCreatedBy());

        Optional<BoatEntity> cr = boatRepository.findByBoatId(saved.getBoatId());
        assertThat(cr).isPresent();

        BoatEntity created = cr.get();
        assertThat(created.getName()).isEqualTo("BoatName");

        BoatEntity modified = new BoatEntity(created.getBoatId(), created.getOwner(), "ModifiedBoatName", created.getKind(), created.getSign(), created.getMake(), created.getModel(), created.getLoa(), created.getDraft(), created.getBeam(), created.getDeplacement(), created.getEngines(), created.getYear(), created.getCreatedAt(), created.getCreatedBy(), null, null, created.getVersion());
        BoatEntity updated = boatRepository.save(modified);
        assertThat(updated.getVersion()).isGreaterThan(saved.getVersion());
        assertThat(updated.getModifiedAt()).isAfterOrEqualTo(saved.getModifiedAt());
    }

    @Test
    @DisplayName("Find by owner")
    void findByOwner() {
        BoatEntity boat = new BoatEntity(boatId2, "owner2", "BoatName", "S", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        boatRepository.save(boat);

        List<BoatEntity> boats = (List<BoatEntity>) boatRepository.findByOwner("owner2");
        assertThat(boats).extracting(BoatEntity::getOwner).containsOnly("owner2");
    }

    @Test
    @DisplayName("Exists by inspectionId")
    void existsById() {
        BoatEntity boat = new BoatEntity(boatId2, "owner3", "BoatName", "S", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        BoatEntity saved = boatRepository.save(boat);

        assertThat(boatRepository.existsById(saved.getBoatId())).isTrue();
        assertThat(boatRepository.existsById(boatId3)).isFalse();
    }

    @Test
    @DisplayName("Find by name")
    void findByName() {
        BoatEntity boat = new BoatEntity(boatId3, "owner4", "UniqueName", "M", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        boatRepository.save(boat);

        List<BoatEntity> boats = (List<BoatEntity>) boatRepository.findByName("UniqueName");
        assertThat(boats).hasSize(1);
        assertThat(boats.get(0).getName()).isEqualTo("UniqueName");
    }

    @Test
    @DisplayName("Delete by inspectionId")
    void deleteById() {
        BoatEntity boat = new BoatEntity(boatId2, "owner13", "BoatName", "S", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        BoatEntity saved = boatRepository.save(boat);

        boatRepository.deleteById(saved.getBoatId());
        assertThat(boatRepository.findById(saved.getBoatId())).isNotPresent();
    }
}

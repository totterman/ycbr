package fi.smartbass.ycbr.register;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest(includeFilters = @ComponentScan.Filter(EnableJdbcAuditing.class))
@AutoConfigureTestDatabase
@ActiveProfiles("integration")
@Sql("/db.sql")
class BoatRepositoryTest {

    @Autowired
    private BoatRepository boatRepository;

    @Test
    @DisplayName("Save and find by id")
    void saveAndFindById() {
        BoatEntity boat = new BoatEntity(1001L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        BoatEntity saved = boatRepository.save(boat);

        Optional<BoatEntity> found = boatRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("BoatName");
    }

    @Test
    @DisplayName("Check auditing")
    void saveAndFindByIdNoId() {
        BoatEntity boat = new BoatEntity(null, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, null, null, null, 0);
        BoatEntity saved = boatRepository.save(boat);
        assertThat(saved.getId()).isPositive();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getCreatedBy()).isNotNull();
        assertThat(saved.getModifiedAt()).isEqualTo(saved.getCreatedAt());
        assertThat(saved.getModifiedBy()).isEqualTo(saved.getCreatedBy());

        Optional<BoatEntity> cr = boatRepository.findById(saved.getId());
        assertThat(cr).isPresent();

        BoatEntity created = cr.get();
        assertThat(created.getName()).isEqualTo("BoatName");
        created.setName("NewBoatName");

        BoatEntity updated = boatRepository.save(created);
        assertThat(updated.getVersion()).isGreaterThan(saved.getVersion());
        assertThat(updated.getModifiedAt()).isAfterOrEqualTo(saved.getModifiedAt());
    }

    @Test
    @DisplayName("Find by owner")
    void findByOwner() {
        BoatEntity boat = new BoatEntity(1002L, "owner2", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        boatRepository.save(boat);

        List<BoatEntity> boats = (List<BoatEntity>) boatRepository.findByOwner("owner2");
        assertThat(boats).extracting(BoatEntity::getOwner).containsOnly("owner2");
    }

    @Test
    @DisplayName("Exists by id")
    void existsById() {
        BoatEntity boat = new BoatEntity(1003L, "owner3", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        BoatEntity saved = boatRepository.save(boat);

        assertThat(boatRepository.existsById(saved.getId())).isTrue();
        assertThat(boatRepository.existsById(99999L)).isFalse();
    }

    @Test
    @DisplayName("Find by name")
    void findByName() {
        BoatEntity boat = new BoatEntity(1004L, "owner4", "UniqueName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        boatRepository.save(boat);

        List<BoatEntity> boats = (List<BoatEntity>) boatRepository.findByName("UniqueName");
        assertThat(boats).hasSize(1);
        assertThat(boats.get(0).getName()).isEqualTo("UniqueName");
    }

    @Test
    @DisplayName("Delete by id")
    void deleteById() {
        BoatEntity boat = new BoatEntity(1013L, "owner13", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        BoatEntity saved = boatRepository.save(boat);

        boatRepository.deleteById(saved.getId());
        assertThat(boatRepository.findById(saved.getId())).isNotPresent();
    }
}

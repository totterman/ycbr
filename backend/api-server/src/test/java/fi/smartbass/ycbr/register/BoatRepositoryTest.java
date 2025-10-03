// backend/api-server/src/test/java/fi/smartbass/ycbr/register/BoatRepositoryTest.java
package fi.smartbass.ycbr.register;

import fi.smartbass.ycbr.config.DataConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase
@ActiveProfiles("integration")
class BoatRepositoryTest {

    @Autowired
    private BoatRepository boatRepository;

    @Test
    @DisplayName("Save and find by id")
    void saveAndFindById() {
        Boat boat = new Boat(1001L, "owner1", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        Boat saved = boatRepository.save(boat);

        Optional<Boat> found = boatRepository.findById(saved.id());
        assertThat(found).isPresent();
        assertThat(found.get().name()).isEqualTo("BoatName");
    }

    @Test
    @DisplayName("Find by owner")
    void findByOwner() {
        Boat boat = new Boat(1002L, "owner2", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        boatRepository.save(boat);

        List<Boat> boats = (List<Boat>) boatRepository.findByOwner("owner2");
        assertThat(boats).extracting(Boat::owner).containsOnly("owner2");
    }

    @Test
    @DisplayName("Exists by id")
    void existsById() {
        Boat boat = new Boat(1003L, "owner3", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        Boat saved = boatRepository.save(boat);

        assertThat(boatRepository.existsById(saved.id())).isTrue();
        assertThat(boatRepository.existsById(99999L)).isFalse();
    }

    @Test
    @DisplayName("Find by name")
    void findByName() {
        Boat boat = new Boat(1004L, "owner4", "UniqueName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        boatRepository.save(boat);

        List<Boat> boats = (List<Boat>) boatRepository.findByName("UniqueName");
        assertThat(boats).hasSize(1);
        assertThat(boats.get(0).name()).isEqualTo("UniqueName");
    }

    @Test
    @DisplayName("Delete by id")
    void deleteById() {
        Boat boat = new Boat(1013L, "owner13", "BoatName", "Reg1234", "Goodsail", "2020", 9.5, 1.5, 3.2, 4000.0, "VP", "1988", null, "unitTest", null, "unitTest", 0);
        Boat saved = boatRepository.save(boat);

        boatRepository.deleteById(saved.id());
        assertThat(boatRepository.findById(saved.id())).isNotPresent();
    }
}

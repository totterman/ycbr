package fi.smartbass.ycbr.register;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface BoatRepository extends CrudRepository<BoatEntity, UUID> {

    Iterable<BoatEntity> findByName(String name);
    Iterable<BoatEntity> findByOwner(String owner);

    Optional<BoatEntity> findByBoatId(UUID boatId);

    boolean existsByBoatId(UUID boatId);

    void deleteByBoatId(UUID boatId);
}

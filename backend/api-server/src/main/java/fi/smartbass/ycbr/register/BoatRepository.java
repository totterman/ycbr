package fi.smartbass.ycbr.register;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatRepository extends CrudRepository<BoatEntity, Long> {

    Iterable<BoatEntity> findByName(String name);
    Iterable<BoatEntity> findByOwner(String owner);

}

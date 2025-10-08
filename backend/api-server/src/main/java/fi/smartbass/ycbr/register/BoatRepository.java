package fi.smartbass.ycbr.register;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatRepository extends CrudRepository<Boat, Long> {

    Iterable<Boat> findByName(String name);
    Iterable<Boat> findByOwner(String owner);

}

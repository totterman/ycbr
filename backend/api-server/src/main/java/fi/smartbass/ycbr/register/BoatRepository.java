package fi.smartbass.ycbr.register;

import org.springframework.data.repository.CrudRepository;

public interface BoatRepository extends CrudRepository<Boat, Long> {

    Iterable<Boat> findByName(String name);
    Iterable<Boat> findByOwner(String owner);

}

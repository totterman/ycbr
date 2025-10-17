package fi.smartbass.ycbr.register;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BoatRepository extends R2dbcRepository<Boat, Long> {

    Flux<Boat> findByName(String name);
    Flux<Boat> findByOwner(String owner);

}

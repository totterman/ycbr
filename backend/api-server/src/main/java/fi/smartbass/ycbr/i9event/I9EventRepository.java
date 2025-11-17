package fi.smartbass.ycbr.i9event;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface I9EventRepository extends CrudRepository<I9Event, Long> {
    Iterable<I9Event> findByPlace(String place);
    Iterable<I9Event> findByStartsBetween(OffsetDateTime fromDate, OffsetDateTime toDate);
}

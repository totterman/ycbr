package fi.smartbass.ycbr.i9event;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Repository
public interface I9EventRepository extends CrudRepository<I9Event, Long> {
    @Transactional(readOnly = true)
    Iterable<I9Event> findByStartsBetween(OffsetDateTime fromDate, OffsetDateTime toDate);
}

package fi.smartbass.ycbr.i9event;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface I9EventRepository extends CrudRepository<I9EventEntity, UUID> {
    @Transactional(readOnly = true)
    Iterable<I9EventEntity> findByStartsBetween(OffsetDateTime fromDate, OffsetDateTime toDate);
}

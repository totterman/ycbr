package fi.smartbass.ycbr.i9event;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface I9EventRepository extends CrudRepository<I9EventEntity, UUID> {
    Iterable<I9EventEntity> findByStartsBetween(OffsetDateTime fromDate, OffsetDateTime toDate);

    @Query("""
            SELECT i9events
            FROM inspector_registrations
            WHERE inspector_name = :inspectorName
            """)
    Iterable<UUID> findByInspector(String inspectorName);

}

package fi.smartbass.ycbr.i9event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest(includeFilters = @ComponentScan.Filter(EnableJdbcAuditing.class))
@AutoConfigureTestDatabase
@ActiveProfiles("integration")
@Sql("/db.sql")
public class I9EventRepositoryTest {

    @Autowired
    private I9EventRepository eventRepository;

    private final UUID i9eventId = UUID.randomUUID();

    @Test
    @DisplayName("Save and find by inspectionId")
    void saveAndFindById() {
        I9EventEntity event = new I9EventEntity(null, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9EventEntity saved = eventRepository.save(event);
        Optional<I9EventEntity> found = eventRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getPlace()).isEqualTo("Björkholmen");
    }

    @Test
    @DisplayName("Check auditing")
    void saveAndFindByIdNoId() {
        I9EventEntity event = new I9EventEntity(null, "Blekholmen", OffsetDateTime.parse("2026-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2026-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9EventEntity saved = eventRepository.save(event);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getCreatedBy()).isNotNull();
        assertThat(saved.getModifiedAt()).isEqualTo(saved.getCreatedAt());
        assertThat(saved.getModifiedBy()).isEqualTo(saved.getCreatedBy());

        Optional<I9EventEntity> found = eventRepository.findById(saved.getId());
        assertThat(found).isPresent();

        I9EventEntity created = found.get();
        assertThat(created.getPlace()).isEqualTo(saved.getPlace());

        created.setPlace("Barösund");
        I9EventEntity updated = eventRepository.save(created);
        assertThat(updated.getVersion()).isGreaterThan(saved.getVersion());
        assertThat(updated.getModifiedAt()).isAfterOrEqualTo(saved.getModifiedAt());
    }

    @Test
    @DisplayName("Exists by inspectionId")
    void existsById() {
        I9EventEntity event = new I9EventEntity(null, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9EventEntity saved = eventRepository.save(event);

        assertThat(eventRepository.existsById(saved.getId())).isTrue();
        assertThat(eventRepository.existsById(i9eventId)).isFalse();
    }

    @Test
    @DisplayName("Save and find by start date")
    void saveAndFindByDateBetween() {
        I9EventEntity event1 = new I9EventEntity(null, "Björkholmen", OffsetDateTime.parse("2025-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2025-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9EventEntity event2 = new I9EventEntity(null, "Blekholmen", OffsetDateTime.parse("2026-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2026-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9EventEntity event3 = new I9EventEntity(null, "Barösund", OffsetDateTime.parse("2027-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2027-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        Iterable<I9EventEntity> saved = eventRepository.saveAll(List.of(event1, event2, event3));

        OffsetDateTime fromDate = OffsetDateTime.parse("2026-01-01T00:00:00.000+02:00");
        OffsetDateTime toDate = OffsetDateTime.parse("2026-12-31T23:59:59.999+02:00");
        List<I9EventEntity> found = (List<I9EventEntity>) eventRepository.findByStartsBetween(fromDate, toDate);

        assertThat(found.size()).isEqualTo(1);
        I9EventEntity match = found.get(0);
        assertThat(match.getPlace()).isEqualTo("Blekholmen");
    }

    @Test
    @DisplayName("Delete by inspectionId")
    void deleteById() {
        I9EventEntity event = new I9EventEntity(null, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9EventEntity saved = eventRepository.save(event);
        Optional<I9EventEntity> found = eventRepository.findById(saved.getId());
        assertThat(found).isPresent();

        eventRepository.deleteById(event.getId());
        Optional<I9EventEntity> deleted = eventRepository.findById(saved.getId());
        assertThat(deleted).isNotPresent();
    }

    @Test
    @DisplayName("Check inspector registrations")
    void testInspectorRegistrations() {
        I9EventEntity event = new I9EventEntity(null, "Gumbostrand", OffsetDateTime.parse("2026-05-15T10:00:00.000+02:00"), OffsetDateTime.parse("2026-05-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        event.addInspector("inspector1", "message1");
        I9EventEntity saved = eventRepository.save(event);
        assertThat(saved.getInspectors().size()).isEqualTo(event.getInspectors().size());
        assertThat(saved.getInspectors()).isEqualTo(event.getInspectors());
        saved.deleteInspector("inspector1");
        I9EventEntity deleted = eventRepository.save(saved);
        assertThat(deleted.getInspectors().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Check boatId bookings")
    void testBoatBookings() {
        UUID boatId = UUID.randomUUID();
        I9EventEntity event = new I9EventEntity(null, "Gumbostrand", OffsetDateTime.parse("2026-05-17T10:00:00.000+02:00"), OffsetDateTime.parse("2026-05-17T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        event.addBoat(boatId, "message2");
        I9EventEntity saved = eventRepository.save(event);
        assertThat(saved.getBoats().size()).isEqualTo(event.getBoats().size());
        assertThat(saved.getBoats()).isEqualTo(event.getBoats());
        saved.deleteBoat(boatId);
        I9EventEntity deleted = eventRepository.save(saved);
        assertThat(deleted.getBoats().size()).isEqualTo(0);
    }

}

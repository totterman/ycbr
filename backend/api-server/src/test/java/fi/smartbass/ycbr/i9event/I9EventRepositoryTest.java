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

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest(includeFilters = @ComponentScan.Filter(EnableJdbcAuditing.class))
@AutoConfigureTestDatabase
@ActiveProfiles("integration")
@Sql("/db.sql")
public class I9EventRepositoryTest {

    @Autowired
    private I9EventRepository eventRepository;

    @Test
    @DisplayName("Save and find by id")
    void saveAndFindById() {
        I9Event event = new I9Event(1001L, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9Event saved = eventRepository.save(event);
        Optional<I9Event> found = eventRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getPlace()).isEqualTo("Björkholmen");
    }

    @Test
    @DisplayName("Check auditing")
    void saveAndFindByIdNoId() {
        I9Event event = new I9Event(null, "Blekholmen", OffsetDateTime.parse("2026-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2026-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9Event saved = eventRepository.save(event);
        assertThat(saved.getId()).isPositive();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getCreatedBy()).isNotNull();
        assertThat(saved.getModifiedAt()).isEqualTo(saved.getCreatedAt());
        assertThat(saved.getModifiedBy()).isEqualTo(saved.getCreatedBy());

        Optional<I9Event> found = eventRepository.findById(saved.getId());
        assertThat(found).isPresent();

        I9Event created = found.get();
        assertThat(created.getPlace()).isEqualTo(saved.getPlace());

        created.setPlace("Barösund");
        I9Event updated = eventRepository.save(created);
        assertThat(updated.getVersion()).isGreaterThan(saved.getVersion());
        assertThat(updated.getModifiedAt()).isAfterOrEqualTo(saved.getModifiedAt());
    }

    @Test
    @DisplayName("Exists by id")
    void existsById() {
        I9Event event = new I9Event(1002L, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9Event saved = eventRepository.save(event);

        assertThat(eventRepository.existsById(saved.getId())).isTrue();
        assertThat(eventRepository.existsById(99999L)).isFalse();
    }

    @Test
    @DisplayName("Save and find by start date")
    void saveAndFindByDateBetween() {
        I9Event event1 = new I9Event(1003L, "Björkholmen", OffsetDateTime.parse("2025-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2025-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9Event event2 = new I9Event(1004L, "Björkholmen", OffsetDateTime.parse("2026-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2026-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9Event event3 = new I9Event(1005L, "Björkholmen", OffsetDateTime.parse("2027-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2027-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        Iterable<I9Event> saved = eventRepository.saveAll(List.of(event1, event2, event3));

        OffsetDateTime fromDate = OffsetDateTime.parse("2026-01-01T00:00:00.000+02:00");
        OffsetDateTime toDate = OffsetDateTime.parse("2026-12-31T23:59:59.999+02:00");
        List<I9Event> found = (List<I9Event>) eventRepository.findByStartsBetween(fromDate, toDate);

        assertThat(found.size()).isEqualTo(1);
        I9Event match = found.get(0);
        assertThat(match.getId()).isEqualTo(1004L);
    }

    @Test
    @DisplayName("Delete by id")
    void deleteById() {
        I9Event event = new I9Event(1013L, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9Event saved = eventRepository.save(event);
        Optional<I9Event> found = eventRepository.findById(saved.getId());
        assertThat(found).isPresent();

        eventRepository.deleteById(event.getId());
        Optional<I9Event> deleted = eventRepository.findById(saved.getId());
        assertThat(deleted).isNotPresent();
    }

    @Test
    @DisplayName("Check inspector registrations")
    void testInspectorRegistrations() {
        I9Event event = new I9Event(null, "Gumbostrand", OffsetDateTime.parse("2026-05-15T10:00:00.000+02:00"), OffsetDateTime.parse("2026-05-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        event.addInspector("inspector1", "message1");
        I9Event saved = eventRepository.save(event);
        assertThat(saved.getInspectors().size()).isEqualTo(event.getInspectors().size());
        assertThat(saved.getInspectors()).isEqualTo(event.getInspectors());
        saved.deleteInspector("inspector1");
        I9Event deleted = eventRepository.save(saved);
        assertThat(deleted.getInspectors().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Check boat bookings")
    void testBoatBookings() {
        I9Event event = new I9Event(null, "Gumbostrand", OffsetDateTime.parse("2026-05-17T10:00:00.000+02:00"), OffsetDateTime.parse("2026-05-17T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        event.addBoat(992L, "message2");
        I9Event saved = eventRepository.save(event);
        assertThat(saved.getBoats().size()).isEqualTo(event.getBoats().size());
        assertThat(saved.getBoats()).isEqualTo(event.getBoats());
        saved.deleteBoat(992L);
        I9Event deleted = eventRepository.save(saved);
        assertThat(deleted.getBoats().size()).isEqualTo(0);
    }

}

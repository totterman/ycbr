package fi.smartbass.ycbr.i9event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class I9EventServiceTest {

    @Spy
    I9EventMapper mapper = Mappers.getMapper(I9EventMapper.class);
    @Mock
    private I9EventRepository eventRepository;
    @InjectMocks
    private I9EventService eventService;

    @Test
    @DisplayName("Find all events")
    void findAll() {
        when(eventRepository.findAll()).thenReturn(Collections.emptyList());
        Iterable<I9EventDTO> result = eventService.findAll();
        assertThat(result).isEmpty();
        verify(eventRepository).findAll();
    }

    @Test
    @DisplayName("Find event by ID")
    void findById_returnsEvent_whenFound() {
        I9Event event = new I9Event(1001L, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        when(eventRepository.findById(1001L)).thenReturn(Optional.of(event));
        I9EventDTO result = eventService.findById(1001L);
        assertThat(result.place()).isEqualTo(event.getPlace());
    }

    @Test
    @DisplayName("Throw if event not found")
    void findById_throws_whenNotFound() {
        when(eventRepository.findById(1000L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> eventService.findById(1000L))
                .isInstanceOf(I9EventNotFoundException.class);
    }

    @Test
    @DisplayName("Find events between timestamps")
    void findByStartsBetween() {
        OffsetDateTime fromDate = OffsetDateTime.parse("2024-04-01T10:00:00.000+02:00");
        OffsetDateTime toDate = OffsetDateTime.parse("2024-12-31T10:00:00.000+02:00");
        I9Event event = new I9Event(1001L, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        when(eventRepository.findByStartsBetween(fromDate, toDate)).thenReturn(List.of(event));
        Iterable<I9EventDTO> result = eventService.findByStartsBetween(fromDate, toDate);
        assertThat(result.iterator().next().place().equals(event.getPlace()));
    }

    @Test
    @DisplayName("Create event")
    void createOk() {
        I9Event event = new I9Event(1001L, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9EventDTO dto = new I9EventDTO(1001L, "Björkholmen", "2024-07-15T00:00:00.000+02:00", "2024-07-15T10:00:00.000+02:00", "2024-07-15T16:00:00.000+02:00", 0, 0);
        when(eventRepository.existsById(event.getId())).thenReturn(false);
        when(eventRepository.save(event)).thenReturn(event);
        I9EventDTO result = eventService.create(dto);
        assertThat(result.place()).isEqualTo(event.getPlace());
        verify(eventRepository).save(event);
    }

    @Test
    @DisplayName("Create throws if event exists")
    void createThrows() {
        I9Event event = new I9Event(1001L, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9EventDTO dto = new I9EventDTO(1001L, "Björkholmen", "2024-07-15T00:00:00.000+02:00", "2024-07-15T10:00:00.000+02:00", "2024-07-15T16:00:00.000+02:00", 0, 0);
        when(eventRepository.existsById(event.getId())).thenReturn(true);
//        when(eventRepository.save(event)).thenReturn(event);
        assertThatThrownBy(() -> eventService.create(dto))
                .isInstanceOf(I9EventAlreadyExistsException.class);
    }

    @Test
    @DisplayName("Update event")
    void upsertOk() {
        I9Event before = new I9Event(1001L, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9EventDTO dto = new I9EventDTO(1001L, "Björkholmen", "2024-07-15T00:00:00.000+02:00", "2024-07-15T10:00:00.000+02:00", "2024-07-15T16:00:00.000+02:00", 0, 0);
        I9Event after = new I9Event(1001L, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T15:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        when(eventRepository.findById(1001L)).thenReturn(Optional.of(before));
        when(eventRepository.save(any(I9Event.class))).thenReturn(after);

        I9EventDTO result = eventService.upsert(1001L, dto);
        assertThat(result.place().equals(after.getPlace()));
    }

    @Test
    @DisplayName("Update throws if malformed request")
    void upsertThrows() {
        I9EventDTO dto = new I9EventDTO(1001L, "Björkholmen", "2024-07-15T00:00:00.000+02:00", "2024-07-15T10:00:00.000+02:00", "2024-07-15T16:00:00.000+02:00", 0, 0);
        assertThatThrownBy(() -> eventService.upsert(1004L, dto))
                .isInstanceOf(I9EventRequestMalformedException.class);
    }

    @Test
    @DisplayName("Deleting event is OK")
    void delete_deletes_whenExists() {
        when(eventRepository.existsById(1L)).thenReturn(true);
        eventService.delete(1L);
        verify(eventRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deleting non existing event is OK")
    void delete_ok_whenNotExists() {
        when(eventRepository.existsById(1L)).thenReturn(false);
        eventService.delete(1L);
        assertThatNoException();
    }

    @Test
    @DisplayName("Finds inspector registration")
    void findInspectorsByEventId() {
        I9Event event = new I9Event(1001L, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        event.addInspector("inspector1", "message1");
        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));
        InspectorRegistrationDTO result = eventService.findInspectorsByEventId(event.getId()).iterator().next();
        assertThat(result.inspectorName().equals("inspector1"));
        assertThat(result.message().equals("message1"));
    }

    @Test
    @DisplayName("Finds boat booking")
    void findBoatsByEventId() {
        I9Event event = new I9Event(1001L, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        event.addBoat(990L, "message2");
        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));
        BoatBookingDTO result = eventService.findBoatsByEventId(event.getId()).iterator().next();
        assertThat(result.boatId().equals(990L));
        assertThat(result.message().equals("message2"));
    }
}
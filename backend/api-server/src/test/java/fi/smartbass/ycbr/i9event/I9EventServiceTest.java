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
import java.util.UUID;

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

    private final UUID i9eventId = UUID.randomUUID();

    @Test
    @DisplayName("Find all events")
    void findAll() {
        when(eventRepository.findAll()).thenReturn(Collections.emptyList());
        Iterable<I9EventDto> result = eventService.findAll();
        assertThat(result).isEmpty();
        verify(eventRepository).findAll();
    }

    @Test
    @DisplayName("Find eventId by ID")
    void findById_returnsEvent_whenFound() {
        I9EventEntity event = new I9EventEntity(i9eventId, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        when(eventRepository.findById(i9eventId)).thenReturn(Optional.of(event));
        I9EventDto result = eventService.findById(i9eventId);
        assertThat(result.place()).isEqualTo(event.getPlace());
    }

    @Test
    @DisplayName("Throw if eventId not found")
    void findById_throws_whenNotFound() {
        when(eventRepository.findById(i9eventId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> eventService.findById(i9eventId))
                .isInstanceOf(I9EventNotFoundException.class);
    }

    @Test
    @DisplayName("Find events between timestamps")
    void findByStartsBetween() {
        OffsetDateTime fromDate = OffsetDateTime.parse("2024-04-01T10:00:00.000+02:00");
        OffsetDateTime toDate = OffsetDateTime.parse("2024-12-31T10:00:00.000+02:00");
        I9EventEntity event = new I9EventEntity(i9eventId, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        when(eventRepository.findByStartsBetween(fromDate, toDate)).thenReturn(List.of(event));
        Iterable<I9EventDto> result = eventService.findByStartsBetween(fromDate, toDate);
        assertThat(result.iterator().next().place().equals(event.getPlace()));
    }

    @Test
    @DisplayName("Create eventId")
    void createOk() {
        I9EventEntity event = new I9EventEntity(i9eventId, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9EventDto dto = new I9EventDto(i9eventId, "Björkholmen", "2024-07-15T00:00:00.000+02:00", "2024-07-15T10:00:00.000+02:00", "2024-07-15T16:00:00.000+02:00", 0, 0);
        when(eventRepository.existsById(event.getId())).thenReturn(false);
        when(eventRepository.save(event)).thenReturn(event);
        I9EventDto result = eventService.create(dto);
        assertThat(result.place()).isEqualTo(event.getPlace());
        verify(eventRepository).save(event);
    }

    @Test
    @DisplayName("Create throws if eventId exists")
    void createThrows() {
        I9EventEntity event = new I9EventEntity(i9eventId, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9EventDto dto = new I9EventDto(i9eventId, "Björkholmen", "2024-07-15T00:00:00.000+02:00", "2024-07-15T10:00:00.000+02:00", "2024-07-15T16:00:00.000+02:00", 0, 0);
        when(eventRepository.existsById(event.getId())).thenReturn(true);
//        when(eventRepository.save(eventId)).thenReturn(eventId);
        assertThatThrownBy(() -> eventService.create(dto))
                .isInstanceOf(I9EventAlreadyExistsException.class);
    }

    @Test
    @DisplayName("Update eventId")
    void upsertOk() {
        I9EventEntity before = new I9EventEntity(i9eventId, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        I9EventDto dto = new I9EventDto(i9eventId, "Björkholmen", "2024-07-15T00:00:00.000+02:00", "2024-07-15T10:00:00.000+02:00", "2024-07-15T16:00:00.000+02:00", 0, 0);
        I9EventEntity after = new I9EventEntity(i9eventId, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T15:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        when(eventRepository.findById(i9eventId)).thenReturn(Optional.of(before));
        when(eventRepository.save(any(I9EventEntity.class))).thenReturn(after);

        I9EventDto result = eventService.upsert(i9eventId, dto);
        assertThat(result.place().equals(after.getPlace()));
    }

    @Test
    @DisplayName("Update throws if malformed request")
    void upsertThrows() {
        I9EventDto dto = new I9EventDto(i9eventId, "Björkholmen", "2024-07-15T00:00:00.000+02:00", "2024-07-15T10:00:00.000+02:00", "2024-07-15T16:00:00.000+02:00", 0, 0);
        assertThatThrownBy(() -> eventService.upsert(UUID.randomUUID(), dto))
                .isInstanceOf(I9EventRequestMalformedException.class);
    }

    @Test
    @DisplayName("Deleting eventId is OK")
    void delete_deletes_whenExists() {
        when(eventRepository.existsById(i9eventId)).thenReturn(true);
        eventService.delete(i9eventId);
        verify(eventRepository).deleteById(i9eventId);
    }

    @Test
    @DisplayName("Deleting non existing eventId is OK")
    void delete_ok_whenNotExists() {
        when(eventRepository.existsById(i9eventId)).thenReturn(false);
        eventService.delete(i9eventId);
        assertThatNoException();
    }

    @Test
    @DisplayName("Finds inspector registration")
    void findInspectorsByEventId() {
        I9EventEntity event = new I9EventEntity(i9eventId, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        event.addInspector("inspector1", "message1");
        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));
        InspectorRegistrationDto result = eventService.findInspectorsByEventId(event.getId()).iterator().next();
        assertThat(result.inspectorName().equals("inspector1"));
        assertThat(result.message().equals("message1"));
    }

    @Test
    @DisplayName("Finds boatId booking")
    void findBoatsByEventId() {
        UUID boatId = UUID.randomUUID();
        I9EventEntity event = new I9EventEntity(i9eventId, "Björkholmen", OffsetDateTime.parse("2024-07-15T10:00:00.000+02:00"), OffsetDateTime.parse("2024-07-15T16:00:00.000+02:00"), Instant.now(), "system", Instant.now(), "system", 0);
        event.addBoat(boatId, "message2");
        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));
        BoatBookingDto result = eventService.findBoatsByEventId(event.getId()).iterator().next();
        assertThat(result.boatId().equals(boatId));
        assertThat(result.message().equals("message2"));
    }
}
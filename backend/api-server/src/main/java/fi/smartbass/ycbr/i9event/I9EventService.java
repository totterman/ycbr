package fi.smartbass.ycbr.i9event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class I9EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(I9EventService.class);
    private final I9EventRepository eventRepository;
    private final I9EventMapper mapper;

    public I9EventService(I9EventRepository eventRepository, I9EventMapper mapper) {
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public Iterable<I9EventDto> findAll() {
        return mapper.toDTOs(eventRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Iterable<I9EventComplete> findEverything() {
        return this.toComplete(eventRepository.findAll());
    }

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public I9EventDto findById(UUID id) {
        return mapper.toDTO(eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id)));
    }

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public Iterable<UUID> findByInspector(String inspectorName) {
        return eventRepository.findByInspector(inspectorName);
    }

    @Transactional(readOnly = true)
    public Iterable<I9EventDto> findByStartsBetween(OffsetDateTime fromDate, OffsetDateTime toDate) {
        return mapper.toDTOs(eventRepository.findByStartsBetween(fromDate, toDate));
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public I9EventDto create(NewI9EventDto dto) {
        return mapper.toDTO(eventRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public I9EventDto create(I9EventDto dto) {
        if (dto.i9eventId() != null && eventRepository.existsById(dto.i9eventId())) {
            LOGGER.warn("Inspection eventId with inspectionId {} already exists.", dto.i9eventId());
            throw new I9EventAlreadyExistsException(dto.i9eventId());
        }
        return mapper.toDTO(eventRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public I9EventDto upsert(UUID id, I9EventDto dto) {
        if (!id.equals(dto.i9eventId())) {
            LOGGER.warn("parameter {} and eventId {} are not equal", id, dto.i9eventId());
            throw new I9EventRequestMalformedException(id, dto.i9eventId());
        }
        return eventRepository.findById(id)
                .map(existingEvent -> {
                    I9EventEntity updatedEvent = mapper.toEntity(dto);
                    updatedEvent.setVersion(existingEvent.getVersion()); // Preserve the version for optimistic locking
                    LOGGER.info("upsert from {} to {}", existingEvent, updatedEvent);
                    return mapper.toDTO(eventRepository.save(updatedEvent));
                })
                .orElseGet(() -> create(dto));
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public void delete(UUID id) {
        if (!eventRepository.existsById(id)) {
            LOGGER.warn("BoatEntity with inspectionId {} not found for deletion.", id);
        }
        eventRepository.deleteById(id);
    }

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public Iterable<InspectorRegistrationDto> findInspectorsByEventId(UUID id) {
        I9EventEntity event = eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id));
        return event.getInspectors().stream().map(mapper::toInspectorRegistrationDTO).collect(Collectors.toSet());
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public I9EventDto assignInspectorToEvent(UUID id, InspectorRegistrationDto dto) {
        if (dto == null || dto.inspectorName() == null) throw new NullPointerException();
        I9EventEntity event = eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id));
        if (event.getInspectors().stream().noneMatch(b -> b.getInspectorName().equals(dto.inspectorName()))) {
            event.addInspector(dto.inspectorName(), dto.message());
            I9EventEntity newEvent = eventRepository.save(event);
            return mapper.toDTO(newEvent);
        }
        throw new InspectorExistsException(dto.inspectorName());
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public I9EventDto removeInspectorFromEvent(UUID id, String inspectorName) {
        I9EventEntity event = eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id));
        if (event.getInspectors().stream().noneMatch(b -> b.getInspectorName().equals(inspectorName))) {
            return mapper.toDTO(event);
        } else {
            event.deleteInspector(inspectorName);
            I9EventEntity newEvent = eventRepository.save(event);
            return mapper.toDTO(newEvent);
        }
    }

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public Iterable<BoatBookingDto> findBoatsByEventId(UUID id) {
        I9EventEntity event = eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id));
        return event.getBoats().stream().map(mapper::toBoatBookingDto).collect(Collectors.toSet());
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public I9EventDto assignBoatToEvent(UUID id, BoatBookingDto dto) {
        if (dto == null || dto.boatId() == null) throw new NullPointerException();
        I9EventEntity event = eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id));
        if (event.getBoats().stream().noneMatch(b -> b.getBoatId().equals(dto.boatId()))) {
            event.addBoat(dto.boatId(), dto.message());
            I9EventEntity newEvent = eventRepository.save(event);
            return mapper.toDTO(newEvent);
        }
        throw new BookingExistsException(dto.boatId());
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public I9EventDto removeBoatFromEvent(UUID id, UUID boatId) {
        I9EventEntity event = eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id));
        if (event.getBoats().stream().noneMatch(b -> b.getBoatId().equals(boatId))) {
            return mapper.toDTO(event);
        } else {
            event.deleteBoat(boatId);
            I9EventEntity newEvent = eventRepository.save(event);
            return mapper.toDTO(newEvent);
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
    public I9EventDto markBoatForInspector(UUID id, BoatBookingDto dto) {
        if (dto == null || dto.boatId() == null) throw new NullPointerException();
        I9EventEntity event = eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id));
//        if (eventId.getBoats().stream().noneMatch(b -> b.getBoatId().equals(dto.boatId()))) {
            event.markBoat(dto.boatId(), dto.message(), dto.taken());
            I9EventEntity newEvent = eventRepository.save(event);
            return mapper.toDTO(newEvent);
//        }
//        throw new BookingExistsException(dto.boatId());
    }

    private Iterable<I9EventComplete> toComplete(Iterable<I9EventEntity> events) {
        return StreamSupport.stream(events.spliterator(), false)
                .map(e ->
                new I9EventComplete(
                e.getI9eventId(),
                e.getPlace(),
                e.getStarts().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                e.getStarts().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                e.getEnds().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                e.getBoats().stream().map(mapper::toBoatBookingDto).collect(Collectors.toUnmodifiableSet()),
                e.getInspectors().stream().map(mapper::toInspectorRegistrationDTO).collect(Collectors.toUnmodifiableSet())
                )).collect(Collectors.toSet());
    }
}

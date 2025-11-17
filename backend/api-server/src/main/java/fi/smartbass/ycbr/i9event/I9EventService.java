package fi.smartbass.ycbr.i9event;

import fi.smartbass.ycbr.register.BoatRequestMalformedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class I9EventService {

    private final Log LOGGER = LogFactory.getLog(I9EventService.class);
    private final I9EventRepository eventRepository;
    private final I9EventMapper mapper;

    public I9EventService(I9EventRepository eventRepository, I9EventMapper mapper) {
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }

    public Iterable<I9EventDTO> findAll() {
        return mapper.toDTOs(eventRepository.findAll());
    }

    public I9EventDTO findById(Long id) {
        return mapper.toDTO(eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id)));
    }

    public Iterable<I9EventDTO> findByStartsBetween(OffsetDateTime fromDate, OffsetDateTime toDate) {
        return mapper.toDTOs(eventRepository.findByStartsBetween(fromDate, toDate));
    }

    public I9EventDTO create(I9EventDTO dto) {
        if (dto.id() != null && eventRepository.existsById(dto.id())) {
            LOGGER.warn("Inspection event with id " + dto.id() + " already exists.");
            throw new I9EventAlreadyExistsException(dto.id());
        }
        return mapper.toDTO(eventRepository.save(mapper.toEntity(dto)));
    }

    public I9EventDTO upsert(Long id, I9EventDTO dto) {
        if (!id.equals(dto.id())) {
            LOGGER.warn("parameter " + id + " and eventId " + dto.id() + " are not equal");
            throw new I9EventRequestMalformedException(id, dto.id());
        }
        return eventRepository.findById(id)
                .map(existingEvent -> {
                    I9Event updatedEvent = mapper.toEntity(dto);
                    updatedEvent.setId(existingEvent.getId());
                    updatedEvent.setCreatedAt(existingEvent.getCreatedAt()); // Preserve the original creation timestamp
                    updatedEvent.setCreatedBy(existingEvent.getCreatedBy()); // Preserve the original creator
                    updatedEvent.setVersion(existingEvent.getVersion()); // Preserve the version for optimistic locking
                    LOGGER.info("upsert from " + existingEvent + " to " + updatedEvent);
                    return mapper.toDTO(eventRepository.save(updatedEvent));
                })
                .orElseGet(() -> create(dto));
    }

    public void delete(Long id) {
        if (!eventRepository.existsById(id)) {
            LOGGER.warn("Boat with id " + id + " not found for deletion.");
        }
        eventRepository.deleteById(id);
    }

    public Iterable<InspectorRegistrationDTO> findInspectorsByEventId(Long id) {
        I9Event event = eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id));
        return event.getInspectors().stream().map(this::toInspectorRegistrationDTO).collect(Collectors.toSet());
    }

    public I9EventDTO assignInspectorToEvent(Long id, InspectorRegistrationDTO dto) {
        if (dto == null || dto.inspectorName() == null) throw new NullPointerException();
        I9Event event = eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id));
        if (event.getInspectors().stream().noneMatch(b -> b.getInspectorName().equals(dto.inspectorName()))) {
            event.addInspector(dto.inspectorName(), dto.message());
            I9Event newEvent = eventRepository.save(event);
            return mapper.toDTO(newEvent);
        }
        throw new InspectorExistsException(dto.inspectorName());
    }

    public I9EventDTO removeInspectorFromEvent(Long id, String inspectorName) {
        I9Event event = eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id));
        if (event.getInspectors().stream().noneMatch(b -> b.getInspectorName().equals(inspectorName))) {
            return mapper.toDTO(event);
        } else {
            event.deleteInspector(inspectorName);
            I9Event newEvent = eventRepository.save(event);
            return mapper.toDTO(newEvent);
        }
    }

    public Iterable<BoatBookingDTO> findBoatsByEventId(Long id) {
        I9Event event = eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id));
        return event.getBoats().stream().map(this::toBoatBookingDto).collect(Collectors.toSet());
    }

    public I9EventDTO assignBoatToEvent(Long id, BoatBookingDTO dto) {
        if (dto == null || dto.boatName() == null) throw new NullPointerException();
        I9Event event = eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id));
        if (event.getBoats().stream().noneMatch(b -> b.getBoatName().equals(dto.boatName()))) {
            event.addBoat(dto.boatName(), dto.message());
            I9Event newEvent = eventRepository.save(event);
            return mapper.toDTO(newEvent);
        }
        throw new BookingExistsException(dto.boatName());
    }

    public I9EventDTO removeBoatFromEvent(Long id, String boatName) {
        I9Event event = eventRepository.findById(id).orElseThrow(() -> new I9EventNotFoundException(id));
        if (event.getBoats().stream().noneMatch(b -> b.getBoatName().equals(boatName))) {
            return mapper.toDTO(event);
        } else {
            event.deleteBoat(boatName);
            I9Event newEvent = eventRepository.save(event);
            return mapper.toDTO(newEvent);
        }
    }

    private BoatBookingDTO toBoatBookingDto(BoatBooking boatBooking) {
        return new BoatBookingDTO(boatBooking.getBoatName(), boatBooking.getMessage());
    }

    private InspectorRegistrationDTO toInspectorRegistrationDTO(InspectorRegistration registration) {
        return new InspectorRegistrationDTO(registration.getInspectorName(), registration.getMessage());
    }
}

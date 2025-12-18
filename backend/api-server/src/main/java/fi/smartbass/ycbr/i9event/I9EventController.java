package fi.smartbass.ycbr.i9event;

import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/i9events")
public class I9EventController {

    private final Log LOGGER = LogFactory.getLog(I9EventController.class);
    private final I9EventService service;

    public I9EventController(I9EventService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public Iterable<I9EventDto> getAll(Authentication auth) {
        LOGGER.info("getAll() called: " + auth);
        return service.findAll();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('staff', 'inspector')")
    public Iterable<I9EventComplete> getEverything(Authentication auth) {
        LOGGER.info("getEverything() called: " + auth);
        return service.findEverything();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public I9EventDto getOne(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("getOne(" + id + ") called: " + auth);
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('staff')")
    public I9EventDto post(Authentication auth, @Valid @RequestBody NewI9EventDto dto) {
        LOGGER.info("post(" + dto +  ") called: " + auth);
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('staff')")
    public I9EventDto put(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody I9EventDto dto) {
        LOGGER.info("put(" + id +  ") called: " + auth);
        return service.upsert(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('staff')")
    public void delete(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("delete(" + id + ") called: " + auth);
        service.delete(id);
    }

    @GetMapping("/{id}/inspectors")
    @PreAuthorize("hasAnyAuthority('staff', 'inspector')")
    public Iterable<InspectorRegistrationDto> getInspectors(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("getInspectors(" + id + ") called: " + auth);
        return service.findInspectorsByEventId(id);
    }

    @PostMapping("/{id}/inspectors")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('staff', 'inspector')")
    public I9EventDto addInspector(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody InspectorRegistrationDto dto) {
        LOGGER.info("addInspector(" + id + ", " + dto.inspectorName() + ") called: " + auth);
        return service.assignInspectorToEvent(id, dto);
    }

    @DeleteMapping("/{id}/inspectors")
    @PreAuthorize("hasAnyAuthority('staff', 'inspector')")
    public I9EventDto removeInspector(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody InspectorRegistrationDto dto) {
        LOGGER.info("removeInspector(" + id + ", " + dto + ") called: " + auth);
        return service.removeInspectorFromEvent(id, dto.inspectorName());
    }

    @GetMapping("/{id}/boats")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public Iterable<BoatBookingDto> getBoats(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("getBoats(" + id + ") called: " + auth);
        return service.findBoatsByEventId(id);
    }

    @PostMapping("/{id}/boats")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public I9EventDto addBoat(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody BoatBookingDto boat) {
        LOGGER.info("addBoat(" + id + ", " + boat.boatId() + ") called: " + auth);
        return service.assignBoatToEvent(id, boat);
    }

    @PostMapping("/{id}/mark")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('inspector', 'staff')")
    public I9EventDto markBoat(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody BoatBookingDto boat) {
        LOGGER.info("markBoat(" + id + ", " + boat.boatId() + ") called: " + auth);
        return service.markBoatForInspector(id, boat);
    }

    @DeleteMapping("/{id}/boats")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public I9EventDto removeBoat(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody BoatBookingDto boat) {
        LOGGER.info("removeBoat(" + id + ", " + boat + ") called: " + auth);
        return service.removeBoatFromEvent(id, boat.boatId());
    }
}

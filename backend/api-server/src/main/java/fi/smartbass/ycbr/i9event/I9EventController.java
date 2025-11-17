package fi.smartbass.ycbr.i9event;

import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public Iterable<I9EventDTO> getAll(Authentication auth) {
        LOGGER.info("getAll() called: " + auth);
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public I9EventDTO getOne(Authentication auth, @PathVariable("id") Long id) {
        LOGGER.info("getOne(" + id + ") called: " + auth);
        return service.findById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('staff')")
    public I9EventDTO post(Authentication auth, @Valid @RequestBody I9EventDTO dto) {
        LOGGER.info("post(" + dto +  ") called: " + auth);
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('staff')")
    public I9EventDTO put(Authentication auth, @PathVariable("id") Long id, @Valid @RequestBody I9EventDTO dto) {
        LOGGER.info("put(" + id +  ") called: " + auth);
        return service.upsert(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('staff')")
    public void delete(Authentication auth, @PathVariable("id") Long id) {
        LOGGER.info("delete(" + id + ") called: " + auth);
        service.delete(id);
    }

    @GetMapping("/{id}/inspectors")
    @PreAuthorize("hasAnyAuthority('staff', 'inspector')")
    public Iterable<InspectorRegistrationDTO> getInspectors(Authentication auth, @PathVariable("id") Long id) {
        LOGGER.info("getInspectors(" + id + ") called: " + auth);
        return service.findInspectorsByEventId(id);
    }

    @PostMapping("/{id}/inspectors")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('staff', 'inspector')")
    public I9EventDTO addInspector(Authentication auth, @PathVariable("id") Long id, @Valid @RequestBody InspectorRegistrationDTO dto) {
        LOGGER.info("addInspector(" + id + ", " + dto.inspectorName() + ") called: " + auth);
        return service.assignInspectorToEvent(id, dto);
    }

    @DeleteMapping("/{id}/inspectors")
    @PreAuthorize("hasAnyAuthority('staff', 'inspector')")
    public I9EventDTO removeInspector(Authentication auth, @PathVariable("id") Long id, @Valid @RequestBody InspectorRegistrationDTO dto) {
        LOGGER.info("removeInspector(" + id + ", " + dto + ") called: " + auth);
        return service.removeInspectorFromEvent(id, dto.inspectorName());
    }

    @GetMapping("/{id}/boats")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public Iterable<BoatBookingDTO> getBoats(Authentication auth, @PathVariable("id") Long id) {
        LOGGER.info("getBoats(" + id + ") called: " + auth);
        return service.findBoatsByEventId(id);
    }

    @PostMapping("/{id}/boats")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public I9EventDTO addBoat(Authentication auth, @PathVariable("id") Long id, @Valid @RequestBody BoatBookingDTO boat) {
        LOGGER.info("addBoat(" + id + ", " + boat.boatName() + ") called: " + auth);
        return service.assignBoatToEvent(id, boat);
    }

    @DeleteMapping("/{id}/boats")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public I9EventDTO removeBoat(Authentication auth, @PathVariable("id") Long id, @Valid @RequestBody BoatBookingDTO boat) {
        LOGGER.info("removeBoat(" + id + ", " + boat + ") called: " + auth);
        return service.removeBoatFromEvent(id, boat.boatName());
    }

}

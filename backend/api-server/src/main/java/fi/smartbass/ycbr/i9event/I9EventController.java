package fi.smartbass.ycbr.i9event;

import fi.smartbass.ycbr.inspection.MyInspectionsDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/i9events")
public class I9EventController implements I9EventApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(I9EventController.class);
    private final I9EventService service;

    public I9EventController(I9EventService service) {
        this.service = service;
    }

    @Override
    public Iterable<I9EventDto> getAll(Authentication auth) {
        LOGGER.info("getAll() called: {}", auth);
        return service.findAll();
    }

    @Override
    public Iterable<I9EventComplete> getEverything(Authentication auth) {
        LOGGER.info("getEverything() called: {}", auth);
        return service.findEverything();
    }

    @Override
    public Iterable<UUID> getMyInspections(Authentication auth, @RequestParam("name") String inspectorName) {
        LOGGER.info("GET My Inspections for: {}", inspectorName);
        return service.findByInspector(inspectorName);
    }
    @Override
    public I9EventDto getOne(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("getOne({}) called: {}", id, auth);
        return service.findById(id);
    }

    @Override
    public I9EventDto post(Authentication auth, @Valid @RequestBody NewI9EventDto dto) {
        LOGGER.info("post({}) called: {}", dto, auth);
        return service.create(dto);
    }

    @Override
    public I9EventDto put(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody I9EventDto dto) {
        LOGGER.info("put({}) called: {}", id, auth);
        return service.upsert(id, dto);
    }

    @Override
    public void delete(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("delete({}) called: {}", id, auth);
        service.delete(id);
    }

    @Override
    public Iterable<InspectorRegistrationDto> getInspectors(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("getInspectors({}) called: {}", id, auth);
        return service.findInspectorsByEventId(id);
    }

    @Override
    public I9EventDto addInspector(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody InspectorRegistrationDto dto) {
        LOGGER.info("addInspector({}, {}) called: {}", id, dto.inspectorName(), auth);
        return service.assignInspectorToEvent(id, dto);
    }

    @Override
    public I9EventDto removeInspector(Authentication auth, @PathVariable("id") UUID id, @RequestParam("name") String inspectorName) {
        LOGGER.info("removeInspector({}, {}) called: {}", id, inspectorName, auth);
        return service.removeInspectorFromEvent(id, inspectorName);
    }

    @Override
    public Iterable<BoatBookingDto> getBoats(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("getBoats({}) called: {}", id, auth);
        return service.findBoatsByEventId(id);
    }

    @Override
    public I9EventDto addBoat(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody BoatBookingDto boat) {
        LOGGER.info("addBoat({}, {}) called: {}", id, boat.boatId(), auth);
        return service.assignBoatToEvent(id, boat);
    }

    @Override
    public I9EventDto markBoat(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody BoatBookingDto boat) {
        LOGGER.info("markBoat({}, {}) called: {}", id, boat.boatId(), auth);
        return service.markBoatForInspector(id, boat);
    }

    @Override
    public I9EventDto removeBoat(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody BoatBookingDto boat) {
        LOGGER.info("removeBoat({}, {}) called: {}", id, boat, auth);
        return service.removeBoatFromEvent(id, boat.boatId());
    }
}

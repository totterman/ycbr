package fi.smartbass.ycbr.register;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/boats")
public class BoatController implements BoatApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoatController.class);
    private final BoatService boatService;

    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    @Override
    public Iterable<BoatDto> get(Authentication auth) {
        LOGGER.info("get() called: {}", auth);
        return boatService.getAllBoats();
    }

    @Override
    public BoatDto getById(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("getById({}) called: {}", id, auth);
        return boatService.getBoatByBoatId(id);
    }

    @Override
    public Iterable<BoatDto> getByOwner(Authentication auth, @RequestParam("name") String owner) {
        LOGGER.info("getByOwner({}) called: {}", owner, auth);
        return boatService.getBoatsByOwner(owner);
    }

    @Override
    public BoatDto post(Authentication auth, @Valid @RequestBody NewBoatDto boat) {
        LOGGER.info("post() called: {}", auth);
        return boatService.create(boat);
    }

    @Override
    public BoatDto put(Authentication auth, @Valid @PathVariable("id") UUID id, @RequestBody BoatDto boat) {
        LOGGER.info("put({}) called: {}", id, auth);
        return boatService.upsert(id, boat);
    }

    @Override
    public void delete(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("delete() called: {} with params: {}", auth, id);
        boatService.delete(id);
    }
}

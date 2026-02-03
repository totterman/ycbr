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
public class BoatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoatController.class);
    private final BoatService boatService;

    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public Iterable<BoatDto> get(Authentication auth) {
        LOGGER.info("get() called: {}", auth);
        return boatService.getAllBoats();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public BoatDto getById(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("getById({}) called: {}", id, auth);
        return boatService.getBoatByBoatId(id);
    }

    @GetMapping("/owner")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public Iterable<BoatDto> getByOwner(Authentication auth, @RequestParam("name") String owner) {
        LOGGER.info("getByOwner({}) called: {}", owner, auth);
        return boatService.getBoatsByOwner(owner);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    @ResponseStatus(HttpStatus.CREATED)
    public BoatDto post(Authentication auth, @Valid @RequestBody NewBoatDto boat) {
        LOGGER.info("post() called: {}", auth);
        return boatService.create(boat);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public BoatDto put(Authentication auth, @Valid @PathVariable("id") UUID id, @RequestBody BoatDto boat) {
        LOGGER.info("put({}) called: {}", id, auth);
        return boatService.upsert(id, boat);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public void delete(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("delete() called: {} with params: {}", auth, id);
        boatService.delete(id);
    }

}

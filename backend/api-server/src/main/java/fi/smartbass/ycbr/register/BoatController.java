package fi.smartbass.ycbr.register;

import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boats")
public class BoatController {

    private final Log LOGGER = LogFactory.getLog(BoatController.class);
    private final BoatService boatService;

    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public Iterable<BoatDTO> get(Authentication auth) {
        LOGGER.info("get() called: " + auth);
        return boatService.getAllBoats();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public BoatDTO getById(Authentication auth, @PathVariable("id") Long id) {
        LOGGER.info("getById(" + id + ") called: " + auth);
        return boatService.getBoatById(id);
    }

    @GetMapping("/owner")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public Iterable<BoatDTO> getByOwner(Authentication auth, @RequestParam("name") String owner) {
        LOGGER.info("getByOwner(" + owner + ") called: " + auth);
        return boatService.getBoatsByOwner(owner);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    @ResponseStatus(HttpStatus.CREATED)
    public BoatDTO post(Authentication auth, @Valid @RequestBody BoatDTO boat) {
        LOGGER.info("post() called: " + auth);
        return boatService.create(boat);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public BoatDTO put(Authentication auth, @Valid @PathVariable("id") Long id, @RequestBody BoatDTO boat) {
        LOGGER.info("put(" + id + ") called: " + auth);
        return boatService.upsert(id, boat);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public void delete(Authentication auth, @PathVariable("id") Long id) {
        LOGGER.info("delete() called: " + auth + " with params: " + id);
        boatService.delete(id);
    }

}

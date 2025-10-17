package fi.smartbass.ycbr.register;

import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestController
public class BoatController {

    private final Log LOGGER = LogFactory.getLog(BoatController.class);
    private final BoatService boatService;

    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    @GetMapping("/boats")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public Flux<BoatDTO> get(Authentication auth) {
        LOGGER.info("get() called: " + auth);
        return boatService.getAllBoats();
    }

    @GetMapping("/boats/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public Mono<BoatDTO> getById(Authentication auth, @PathVariable("id") Long id) {
        LOGGER.info("getById(" + id + ") called: " + auth);
        return boatService.getBoatById(id);
    }

    @GetMapping("/boats/owner")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public Flux<BoatDTO> getByOwner(Authentication auth, @RequestParam("name") String owner) {
        LOGGER.info("getByOwner(" + owner + ") called: " + auth);
        return boatService.getBoatsByOwner(owner);
    }

    @PostMapping("/boats")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BoatDTO> post(Authentication auth, @Valid @RequestBody BoatDTO boat) {
        LOGGER.info("post() called: " + auth);
        Boat newBoat = new Boat();
        newBoat.setOwner(boat.owner());
        newBoat.setName(boat.name());
        newBoat.setSign(boat.sign());
        newBoat.setMake(boat.make());
        newBoat.setModel(boat.model());
        newBoat.setLoa(boat.loa());
        newBoat.setDraft(boat.draft());
        newBoat.setBeam(boat.beam());
        newBoat.setDeplacement(boat.deplacement());
        newBoat.setEngines(boat.engines());
        newBoat.setYear(boat.year());
        newBoat.setCreatedBy(auth.getName());
        newBoat.setCreatedAt(Instant.now());
        return boatService.addBoatToRegister(newBoat);
    }

    @PutMapping("/boats/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public Mono<BoatDTO> put(Authentication auth, @Valid @PathVariable("id") Long id, @RequestBody BoatDTO boat) {
        LOGGER.info("put(" + id + ") called: " + auth);
        Boat updatedBoat = new Boat();
        // updatedBoat.setId(id);
        updatedBoat.setOwner(boat.owner());
        updatedBoat.setName(boat.name());
        updatedBoat.setSign(boat.sign());
        updatedBoat.setMake(boat.make());
        updatedBoat.setModel(boat.model());
        updatedBoat.setLoa(boat.loa());
        updatedBoat.setDraft(boat.draft());
        updatedBoat.setBeam(boat.beam());
        updatedBoat.setDeplacement(boat.deplacement());
        updatedBoat.setEngines(boat.engines());
        updatedBoat.setYear(boat.year());
        updatedBoat.setCreatedBy(auth.getName());
        updatedBoat.setCreatedAt(Instant.now());
        updatedBoat.setModifiedBy(auth.getName());
        updatedBoat.setModifiedAt(Instant.now());
        return boatService.updateBoatInRegister(id, updatedBoat);
    }

    @DeleteMapping("/boats/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public Mono<Void> delete(Authentication auth, @PathVariable("id") Long id) {
        LOGGER.info("delete() called: " + auth + " with params: " + id);
        return boatService.deleteBoatFromRegister(id);
    }

}

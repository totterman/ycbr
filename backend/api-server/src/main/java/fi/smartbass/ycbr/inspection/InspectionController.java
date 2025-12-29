package fi.smartbass.ycbr.inspection;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/inspections")
public class InspectionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionController.class);
    private final InspectionService inspectionService;

    public InspectionController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    @GetMapping("/{id}")
    InspectionDto getInspection(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("GET inspection: {}", id);
        return inspectionService.read(id);
    }

    @GetMapping("/inspector")
    public Iterable<InspectionDto> getByInspector(Authentication auth, @RequestParam("name") String inspector) {
        LOGGER.info("GET inspections for: {}", inspector);
        return inspectionService.fetchByInspector(inspector);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    InspectionDto postInspection(Authentication auth, @Valid @RequestBody NewInspectionDto dto) {
        LOGGER.info("POST new inspection: eventId {}, boatId {}, inspector {}", dto.eventId(), dto.boatId(), dto.inspectorName());
        return inspectionService.create(dto);
    }

    @PutMapping("/{id}")
    InspectionDto putInspection(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody InspectionDto dto) {
        LOGGER.info("PUT inspection: inspectionId {}, boatId {}, inspector {}", dto.inspectionId(), dto.boatId(), dto.inspector());
        return inspectionService.update(id, dto);
    }

    @GetMapping("/my")
    public Iterable<MyInspectionsDto> getMyInspections(Authentication auth, @RequestParam("name") String inspectorName) {
        LOGGER.info("GET My Inspections for: {}", inspectorName);
        return inspectionService.fetchMyInspections(inspectorName);
    }

    @GetMapping("/all")
    public Iterable<MyInspectionsDto> getAllInspections(Authentication auth) {
        LOGGER.info("GET All Inspections");
        return inspectionService.fetchAllInspections();
    }

    @DeleteMapping("/{id}")
    void deleteInspection(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("DELETE inspection: {}", id);
        inspectionService.delete(id);
    }

}

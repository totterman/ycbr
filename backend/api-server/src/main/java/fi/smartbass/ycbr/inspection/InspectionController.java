package fi.smartbass.ycbr.inspection;

import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inspections")
public class InspectionController {

    private final Log LOGGER = LogFactory.getLog(InspectionController.class);
    private final InspectionService inspectionService;

    public InspectionController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    @GetMapping("/{id}")
    InspectionDTO getInspection(Authentication auth, @PathVariable("id") Long id) {
        LOGGER.info("GET inspection: " + id);
        return inspectionService.read(id);
    }

    @GetMapping("/inspector")
    public Iterable<InspectionDTO> getByInspector(Authentication auth, @RequestParam("name") String inspector) {
        LOGGER.info("GET inspections for: " + inspector);
        return inspectionService.fetchByInspector(inspector);
    }

        @PostMapping
    InspectionDTO postInspection(Authentication auth, @Valid @RequestBody NewInspectionDTO dto) {
        LOGGER.info("POST new inspection: event " + dto.eventId() + ", boat " + dto.boatId() + ", inspector " + dto.inspectorName());
        return inspectionService.create(dto);
    }

    @PutMapping("/{id}")
    InspectionDTO putInspection(Authentication auth, @PathVariable("id") Long id, @Valid @RequestBody InspectionDTO dto) {
        LOGGER.info("PUT inspection: id " + dto.id() + ", boat " + dto.boat() + ", inspector " + dto.inspector());
        return inspectionService.update(id, dto);
    }

}

package fi.smartbass.ycbr.inspection;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/inspections")
public class InspectionController implements InspectionApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(InspectionController.class);
    private final InspectionService inspectionService;

    public InspectionController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    @Override
    public InspectionDto getInspection(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("GET inspection: {}", id);
        return inspectionService.read(id);
    }

    @Override
    public Iterable<InspectionDto> getByInspector(Authentication auth, @RequestParam("name") String inspector) {
        LOGGER.info("GET inspections for: {}", inspector);
        return (inspectionService.fetchByInspector(inspector));
    }

    @Override
    public InspectionDto postInspection(Authentication auth, @Valid @RequestBody NewInspectionDto dto) {
        LOGGER.info("POST new inspection: eventId {}, boatId {}, inspectorName {}", dto.eventId(), dto.boatId(), dto.inspectorName());
        return inspectionService.create(dto);
    }

    @Override
    public InspectionDto putInspection(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody InspectionDto dto) {
        LOGGER.info("PUT inspection: inspectionId {}, boatId {}, inspectorName {}", dto.inspectionId(), dto.boatId(), dto.inspectorName());
        return inspectionService.update(id, dto);
    }

    @Override
    public Iterable<MyInspectionsDto> getMyInspections(Authentication auth, @RequestParam("name") String inspectorName) {
        LOGGER.info("GET My Inspections for: {}", inspectorName);
        return inspectionService.fetchMyInspections(inspectorName);
    }

    @Override
    public Iterable<MyInspectionsDto> getAllInspections(Authentication auth) {
        LOGGER.info("GET All Inspections");
        return inspectionService.fetchAllInspections();
    }

    @Override
    public void deleteInspection(Authentication auth, @PathVariable("id") UUID id) {
        LOGGER.info("DELETE inspection: {}", id);
        inspectionService.delete(id);
    }
}

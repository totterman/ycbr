package fi.smartbass.ycbr.inspection;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Inspection API", description = "API for managing boat inspections")
public interface InspectionApi {

    @Operation(summary = "Get inspection by ID", description = "Returns the inspection with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection found"),
            @ApiResponse(responseCode = "404", description = "Inspection not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inspector', 'staff')")
    public InspectionDto getInspection(Authentication auth, @PathVariable("id") UUID id);

    @Operation(summary = "Get inspections by inspector name", description = "Returns the inspections for the specified inspector name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection found"),
            @ApiResponse(responseCode = "404", description = "Inspection not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/inspectorName")
    @PreAuthorize("hasAnyAuthority('inspector', 'staff')")
    public Iterable<InspectionDto> getByInspector(Authentication auth, @RequestParam("name") String inspector);

    @Operation(summary = "Create new inspection", description = "Creates a new inspection with the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inspection created"),
            @ApiResponse(responseCode = "404", description = "Inspection exists"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('inspector', 'staff')")
    public InspectionDto postInspection(Authentication auth, @Valid @RequestBody NewInspectionDto dto);

    @Operation(summary = "update an inspection by ID", description = "Updates the inspection with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection found"),
            @ApiResponse(responseCode = "404", description = "Inspection not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inspector', 'staff')")
    public InspectionDto putInspection(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody InspectionDto dto);

    @Operation(summary = "Get inspections by inspector name", description = "Returns the inspections for the specified inspector name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection found"),
            @ApiResponse(responseCode = "404", description = "Inspection not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/my")
    @PreAuthorize("hasAnyAuthority('inspector', 'staff')")
    public Iterable<MyInspectionsDto> getMyInspections(Authentication auth, @RequestParam("name") String inspectorName);

    @Operation(summary = "Get all inspections", description = "Returns all inspections")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection found"),
            @ApiResponse(responseCode = "404", description = "Inspection not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('inspector', 'staff')")
    public Iterable<MyInspectionsDto> getAllInspections(Authentication auth);

    @Operation(summary = "Delete inspection by ID", description = "Deletes the inspection with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection deleted"),
            @ApiResponse(responseCode = "404", description = "Inspection not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inspector', 'staff')")
    public void deleteInspection(Authentication auth, @PathVariable("id") UUID id);
}

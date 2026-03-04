package fi.smartbass.ycbr.i9event;

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

@Tag(name = "Inspection Event API", description = "API for managing boat inspection events")
public interface I9EventApi {

    @Operation(summary = "Get all inspection events", description = "Returns all inspection events without details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection events found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public Iterable<I9EventDto> getAll(Authentication auth);

    @Operation(summary = "Get all inspection events", description = "Returns all inspection events with boat and inspector details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection events found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('staff', 'inspector')")
    public Iterable<I9EventComplete> getEverything(Authentication auth);

    @Operation(summary = "Get my inspection events", description = "Returns the inspection events for the specified inspector name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection events found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inspector not found")
    })
    @GetMapping("/my")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public Iterable<UUID> getMyInspections(Authentication auth, @RequestParam("name") String inspectorName);

    @Operation(summary = "Get one inspection event", description = "Returns inspection events with ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection events found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inspection not found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public I9EventDto getOne(Authentication auth, @PathVariable("id") UUID id);

    @Operation(summary = "Create new inspection event", description = "Creates a new inspection event with the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inspection event created"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('staff')")
    public I9EventDto post(Authentication auth, @Valid @RequestBody NewI9EventDto dto);

    @Operation(summary = "update an inspection event by ID", description = "Updates the inspection event with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection event found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inspection event not found"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('staff')")
    public I9EventDto put(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody I9EventDto dto);

    @Operation(summary = "Delete an inspection event by ID", description = "Deletes the inspection event with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspection event deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inspection event not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('staff')")
    public void delete(Authentication auth, @PathVariable("id") UUID id);

    @Operation(summary = "Get inspectors for an inspection event", description = "Returns the inspectors for the specified inspection event ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspectors found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inspection event not found")
    })
    @GetMapping("/{id}/inspectors")
    @PreAuthorize("hasAnyAuthority('staff', 'inspector')")
    public Iterable<InspectorRegistrationDto> getInspectors(Authentication auth, @PathVariable("id") UUID id);

    @Operation(summary = "Add an inspector to an inspection event", description = "Adds an inspector to the specified inspection event ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inspector added"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inspection event not found"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity")
    })
    @PostMapping("/{id}/inspectors")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('staff', 'inspector')")
    public I9EventDto addInspector(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody InspectorRegistrationDto dto);

    @Operation(summary = "Remove an inspector from an inspection event", description = "Removes an inspector from the specified inspection event ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inspector removed"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inspection event or inspector not found")
    })
    @DeleteMapping("/{id}/inspectors")
    @PreAuthorize("hasAnyAuthority('staff', 'inspector')")
    public I9EventDto removeInspector(Authentication auth, @PathVariable("id") UUID id, @RequestParam("name") String inspectorName);

    @Operation(summary = "Get boats for an inspection event", description = "Returns the boats for the specified inspection event ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boats found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inspection event not found")
    })
    @GetMapping("/{id}/boats")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public Iterable<BoatBookingDto> getBoats(Authentication auth, @PathVariable("id") UUID id);

    @Operation(summary = "Add a boat to an inspection event", description = "Adds a boat to the specified inspection event ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Boat added"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inspection event not found"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity")
    })
    @PostMapping("/{id}/boats")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public I9EventDto addBoat(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody BoatBookingDto boat);

    @Operation(summary = "Mark a boat for an inspection event", description = "Marks a boat for the specified inspection event ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Boat marked"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inspection event not found"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity")
    })
    @PostMapping("/{id}/mark")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('inspector', 'staff')")
    public I9EventDto markBoat(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody BoatBookingDto boat);

    @Operation(summary = "Remove a boat from an inspection event", description = "Removes a boat from the specified inspection event ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boat removed"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inspection event or boat not found")
    })
    @DeleteMapping("/{id}/boats")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public I9EventDto removeBoat(Authentication auth, @PathVariable("id") UUID id, @Valid @RequestBody BoatBookingDto boat);
}

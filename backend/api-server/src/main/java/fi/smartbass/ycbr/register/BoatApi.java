package fi.smartbass.ycbr.register;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Boat Register API", description = "API for managing boats in the register")
public interface BoatApi {

    @Operation(summary = "Get all boats", description = "Returns all boats in the register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boats found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public Iterable<BoatDto> get(Authentication auth);

    @Operation(summary = "Get boat by ID", description = "Returns the boat with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boat found"),
            @ApiResponse(responseCode = "404", description = "Boat not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public BoatDto getById(Authentication auth, @PathVariable("id") UUID id);

    @Operation(summary = "Get boats by owner name", description = "Returns the boats for the specified owner name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boats found"),
            @ApiResponse(responseCode = "404", description = "Boats not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/owner")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff', 'inspector')")
    public Iterable<BoatDto> getByOwner(Authentication auth, @RequestParam("name") String owner);

    @Operation(summary = "Create new boat", description = "Creates a new boat with the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Boat created"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity")
    })
    @PostMapping
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    @ResponseStatus(HttpStatus.CREATED)
    public BoatDto post(Authentication auth, @Valid @RequestBody NewBoatDto boat);

    @Operation(summary = "Update boat by ID", description = "Updates the boat with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boat updated"),
            @ApiResponse(responseCode = "404", description = "Boat not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public BoatDto put(Authentication auth, @Valid @PathVariable("id") UUID id, @RequestBody BoatDto boat);

    @Operation(summary = "Delete boat by ID", description = "Deletes the boat with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boat deleted"),
            @ApiResponse(responseCode = "404", description = "Boat not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('boatowner', 'staff')")
    public void delete(Authentication auth, @PathVariable("id") UUID id);
}

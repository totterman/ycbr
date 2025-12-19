package fi.smartbass.ycbr.inspection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record NewInspectionDto(
        @NotBlank(message = "Inspector must be defined")
        @Size(max = 50, message = "Inspector Name must be at most 50 characters")
        String inspectorName,

        @NotNull(message = "Inspection Event must be defined")
        UUID eventId,

        @NotNull(message = "Boat to Inspect must be defined")
        UUID boatId
) {}

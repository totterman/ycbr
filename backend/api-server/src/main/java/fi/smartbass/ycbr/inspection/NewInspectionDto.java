package fi.smartbass.ycbr.inspection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewInspectionDto(
        @NotBlank(message = "Inspector must be defined")
        String inspectorName,

        @NotNull(message = "Inspection Event must be defined")
        UUID eventId,

        @NotNull(message = "BoatEntity to Inspect must be defined")
        UUID boatId
) {}

package fi.smartbass.ycbr.inspection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewInspectionDTO(
        @NotBlank(message = "Inspector must be defined")
        String inspectorName,

        @NotNull(message = "Inspection Event must be defined")
        Long eventId,

        @NotNull(message = "BoatEntity to Inspect must be defined")
        Long boatId
) {}

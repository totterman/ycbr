package fi.smartbass.ycbr.inspection;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record InspectionDto(
        UUID inspectionId,
        String timestamp,
        @NotBlank(message = "Inspector must be defined")
        String inspector,
        @NotNull(message = "Inspection Event must be defined")
        UUID eventId,
        @NotNull(message = "BoatEntity to Inspect must be defined")
        UUID boatId,
        InspectionDataDto inspection,
        @Nullable
        String completed
) { }


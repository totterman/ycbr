package fi.smartbass.ycbr.inspection;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InspectionDTO(
        Long id,
        String timestamp,
        @NotBlank(message = "Inspector must be defined")
        String inspector,
        @NotNull(message = "Inspection Event must be defined")
        Long event,
        @NotNull(message = "BoatEntity to Inspect must be defined")
        Long boat,
        InspectionDataDto inspection,
        @Nullable
        String completed
) { }


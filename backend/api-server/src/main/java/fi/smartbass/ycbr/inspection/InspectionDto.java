package fi.smartbass.ycbr.inspection;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record InspectionDto(
        UUID inspectionId,

        String timestamp,

        @NotBlank(message = "Inspector must be defined")
        @Size(max = 50, message = "Inspector Name must be at most 50 characters")
        String inspectorName,

        @NotNull(message = "Inspection Event must be defined")
        UUID eventId,

        @NotNull(message = "BoatEntity to Inspect must be defined")
        UUID boatId,

        @NotNull(message = "Inspection class must be defined")
        @Size(max = 1, message = "Inspection Class must be just 1 character")
        @Pattern(regexp = "[01234]", message = "Inspection Class must be one of '0', '1', '2', '3' or '4'")
        String inspectionClass,

        @NotNull(message = "Inspection Type must be defined")
        @Size(max = 1, message = "Inspection Type must be just 1 character")
        @Pattern(regexp = "[AHBX]", message = "Inspection Type must be one of 'A', 'H', 'B' or 'X'")
        String inspectionType,

        InspectionDataDto inspection,

        @Nullable
        String completed,
        Set<RemarkDto> remarks
) { }


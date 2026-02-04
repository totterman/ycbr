package fi.smartbass.ycbr.inspection;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MyInspectionsDto(
        UUID inspectionId,
        UUID eventId,
        UUID boatId,
        @NotBlank(message = "Inspector Name must be defined")
        @Size(max = 50, message = "Inspector Name must be at most 50 characters")
        String inspectorName,
        @NotBlank(message = "Boat Name must be defined")
        @Size(max = 50, message = "Boat Name must be at most 50 characters")
        String boatName,
        @NotNull(message = "Inspection class must be defined")
        @Size(max = 1, message = "Inspection Class must be just 1 character")
        String inspectionClass,
        @NotBlank(message = "Inspection event Place must be defined")
        @Size(max = 50, message = "Inspection event Place must be at most 50 characters")
        String place,
        @NotBlank(message = "Inspection event Start time must be defined")
        OffsetDateTime day,
        @Nullable
        OffsetDateTime completed
) { }


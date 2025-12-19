package fi.smartbass.ycbr.i9event;

import jakarta.validation.constraints.Size;

public record InspectorRegistrationDto(
        @Size(max = 50, message = "Inspector Name must be at most 50 characters")
        String inspectorName,
        @Size(max = 50, message = "Registration Message must be at most 50 characters")
        String message) {}

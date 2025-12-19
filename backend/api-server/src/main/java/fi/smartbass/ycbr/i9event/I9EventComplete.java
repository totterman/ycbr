package fi.smartbass.ycbr.i9event;

import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Set;
import java.util.UUID;

public record I9EventComplete(
        UUID i9eventId,

        @Size(max = 50, message = "Inspection Place must be at most 50 characters")
        String place,

        @Size(max = 30, message = "Inspection Day must be at most 30 characters")
        String day,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @Size(max = 30, message = "Inspection Start Time must be at most 30 characters")
        String starts,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @Size(max = 30, message = "Inspection End Time must be at most 30 characters")
        String ends,

        Set<BoatBookingDto> boats,
        Set<InspectorRegistrationDto> inspectors) {}

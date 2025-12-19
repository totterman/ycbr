package fi.smartbass.ycbr.i9event;

import jakarta.validation.constraints.Size;

import java.util.UUID;

public record BoatBookingDto(
        UUID boatId,
        @Size(max = 50, message = "Booking Message must be at most 50 characters")
        String message,
        boolean taken
) {
}

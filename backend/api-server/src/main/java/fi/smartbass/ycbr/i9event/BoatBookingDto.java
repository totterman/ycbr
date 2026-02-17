package fi.smartbass.ycbr.i9event;

import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;

public record BoatBookingDto(
        UUID boatId,
        @Size(max = 50, message = "Booking Message must be at most 50 characters")
        String message,
        @Size(max = 1, message = "Inspection Type must be at just 1 character")
        String type,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @Size(max = 30, message = "Inspection Start Time must be at most 30 characters")
        String time,
        boolean taken
) {
}

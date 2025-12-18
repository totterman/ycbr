package fi.smartbass.ycbr.i9event;

import java.util.UUID;

public record BoatBookingDto(
        UUID boatId,
        String message,
        boolean taken
) {
}

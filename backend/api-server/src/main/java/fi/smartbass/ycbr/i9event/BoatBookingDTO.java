package fi.smartbass.ycbr.i9event;

public record BoatBookingDTO(
        Long boatId,
        String message,
        boolean taken
) {
}

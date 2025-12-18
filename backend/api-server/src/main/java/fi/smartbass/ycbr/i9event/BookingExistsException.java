package fi.smartbass.ycbr.i9event;

import java.util.UUID;

public class BookingExistsException extends RuntimeException {
    public BookingExistsException(Long boatId) {
        super("inspection booking for boatId with ID " + boatId + " already exists.");
    }

    public BookingExistsException(UUID boatId) {
        super("inspection booking for boatId with ID " + boatId + " already exists.");
    }

}

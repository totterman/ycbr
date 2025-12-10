package fi.smartbass.ycbr.i9event;

public class BookingExistsException extends RuntimeException {
    public BookingExistsException(Long boatId) {
        super("inspection booking for boat with ID " + boatId + " already exists.");
    }
}

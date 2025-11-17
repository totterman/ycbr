package fi.smartbass.ycbr.i9event;

public class BookingExistsException extends RuntimeException {
    public BookingExistsException(String boat) {
        super("inspection booking for boat " + boat + " already exists.");
    }
}

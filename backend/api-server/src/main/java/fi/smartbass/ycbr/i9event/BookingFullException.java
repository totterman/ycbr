package fi.smartbass.ycbr.i9event;

import java.util.UUID;

public class BookingFullException extends RuntimeException {
    public BookingFullException(String time) {
        super("No booking availability at " + time);
    }
}

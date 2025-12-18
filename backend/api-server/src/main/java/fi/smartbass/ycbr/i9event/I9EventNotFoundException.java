package fi.smartbass.ycbr.i9event;

import java.util.UUID;

public class I9EventNotFoundException extends RuntimeException {
    public I9EventNotFoundException(Long id) {
        super("Inspection eventId with ID " + id + " not found.");
    }
    public I9EventNotFoundException(UUID id) {
        super("Inspection eventId with ID " + id + " not found.");
    }
}

package fi.smartbass.ycbr.i9event;

import java.util.UUID;

public class I9EventAlreadyExistsException extends RuntimeException {
    public I9EventAlreadyExistsException(Long id) {
        super("inspection eventId with ID " + id + " already exists.");
    }
    public I9EventAlreadyExistsException(UUID id) {
        super("inspection eventId with ID " + id + " already exists.");
    }
}

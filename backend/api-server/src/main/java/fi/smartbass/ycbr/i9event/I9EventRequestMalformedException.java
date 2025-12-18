package fi.smartbass.ycbr.i9event;

import java.util.UUID;

public class I9EventRequestMalformedException extends RuntimeException {
    public I9EventRequestMalformedException(Long parameter, Long i9eId) {
        super("Inspection eventId request malformed: parameter " + parameter + " and ID " + i9eId + " are not equal.");
    }
    public I9EventRequestMalformedException(UUID parameter, UUID i9eId) {
        super("Inspection eventId request malformed: parameter " + parameter + " and ID " + i9eId + " are not equal.");
    }
}

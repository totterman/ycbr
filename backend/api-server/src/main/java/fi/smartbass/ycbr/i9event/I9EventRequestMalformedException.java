package fi.smartbass.ycbr.i9event;

public class I9EventRequestMalformedException extends RuntimeException {
    public I9EventRequestMalformedException(Long parameter, Long i9eId) {
        super("Inspection event request malformed: parameter " + parameter + " and ID " + i9eId + " are not equal.");
    }
}

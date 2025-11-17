package fi.smartbass.ycbr.i9event;

public class I9EventNotFoundException extends RuntimeException {
    public I9EventNotFoundException(Long id) {
        super("Inspection event with ID " + id + " not found.");
    }
}

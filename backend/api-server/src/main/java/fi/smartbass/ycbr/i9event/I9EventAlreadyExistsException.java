package fi.smartbass.ycbr.i9event;

public class I9EventAlreadyExistsException extends RuntimeException {
    public I9EventAlreadyExistsException(Long id) {
        super("inspection event with ID " + id + " already exists.");
    }
}

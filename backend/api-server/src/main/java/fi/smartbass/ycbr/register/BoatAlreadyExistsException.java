package fi.smartbass.ycbr.register;

public class BoatAlreadyExistsException extends RuntimeException {
    public BoatAlreadyExistsException(Long id) {
        super("BoatEntity with ID " + id + " already exists.");
    }
}

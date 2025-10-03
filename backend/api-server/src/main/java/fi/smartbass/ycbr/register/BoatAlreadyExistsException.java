package fi.smartbass.ycbr.register;

public class BoatAlreadyExistsException extends RuntimeException {
    public BoatAlreadyExistsException(Long id) {
        super("Boat with ID " + id + " already exists.");
    }
}

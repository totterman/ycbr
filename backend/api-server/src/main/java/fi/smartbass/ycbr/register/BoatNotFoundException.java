package fi.smartbass.ycbr.register;

public class BoatNotFoundException extends RuntimeException {
    public BoatNotFoundException(Long id) {
        super("Boat with ID " + id + " not found.");
    }
}

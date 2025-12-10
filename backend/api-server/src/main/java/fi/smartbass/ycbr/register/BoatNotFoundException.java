package fi.smartbass.ycbr.register;

public class BoatNotFoundException extends RuntimeException {
    public BoatNotFoundException(Long id) {
        super("BoatEntity with ID " + id + " not found.");
    }
}

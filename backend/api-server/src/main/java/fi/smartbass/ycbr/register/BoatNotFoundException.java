package fi.smartbass.ycbr.register;

import java.util.UUID;

public class BoatNotFoundException extends RuntimeException {
    public BoatNotFoundException(Long id) {
        super("BoatEntity with ID " + id + " not found.");
    }
    public BoatNotFoundException(UUID boatId) {
        super("BoatEntity with ID " + boatId + " not found.");
    }

}

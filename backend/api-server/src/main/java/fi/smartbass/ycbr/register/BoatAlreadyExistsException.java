package fi.smartbass.ycbr.register;

import java.util.UUID;

public class BoatAlreadyExistsException extends RuntimeException {
    public BoatAlreadyExistsException(Long id) {
        super("BoatEntity with ID " + id + " already exists.");
    }
    public BoatAlreadyExistsException(UUID boatId) {
        super("BoatEntity with ID " + boatId + " already exists.");
    }
}

package fi.smartbass.ycbr.register;

import java.util.UUID;

public class BoatRequestMalformedException extends RuntimeException {
    public BoatRequestMalformedException(Long parameter, Long boatId) {
        super("BoatEntity request malformed: parameter " + parameter + " and ID " + boatId + " are not equal.");
    }

    public BoatRequestMalformedException(UUID parameter, UUID boatId) {
        super("BoatEntity request malformed: parameter " + parameter + " and ID " + boatId + " are not equal.");
    }

}

package fi.smartbass.ycbr.register;

public class BoatRequestMalformedException extends RuntimeException {
    public BoatRequestMalformedException(Long parameter, Long boatId) {
        super("BoatEntity request malformed: parameter " + parameter + " and ID " + boatId + " are not equal.");
    }
}

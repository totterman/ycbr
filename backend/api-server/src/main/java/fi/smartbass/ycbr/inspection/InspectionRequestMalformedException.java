package fi.smartbass.ycbr.inspection;

public class InspectionRequestMalformedException extends RuntimeException {
    public InspectionRequestMalformedException(Long parameter, Long id) {
        super("Inspection request malformed: parameter " + parameter + " and ID " + id + " are not equal.");
    }
}

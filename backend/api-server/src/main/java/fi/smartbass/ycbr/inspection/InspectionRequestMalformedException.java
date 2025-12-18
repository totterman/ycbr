package fi.smartbass.ycbr.inspection;

import java.util.UUID;

public class InspectionRequestMalformedException extends RuntimeException {
    public InspectionRequestMalformedException(Long parameter, Long id) {
        super("Inspection request malformed: parameter " + parameter + " and ID " + id + " are not equal.");
    }
    public InspectionRequestMalformedException(UUID parameter, UUID id) {
        super("Inspection request malformed: parameter " + parameter + " and ID " + id + " are not equal.");
    }
}

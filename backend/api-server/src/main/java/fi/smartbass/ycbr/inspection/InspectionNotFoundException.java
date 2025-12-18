package fi.smartbass.ycbr.inspection;

import java.util.UUID;

public class InspectionNotFoundException extends RuntimeException {
    public InspectionNotFoundException(Long id) {
        super("Inspection with ID " + id + " not found.");
    }
    public InspectionNotFoundException(UUID id) {
        super("Inspection with ID " + id + " not found.");
    }
}

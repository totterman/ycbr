package fi.smartbass.ycbr.inspection;

public class InspectionNotFoundException extends RuntimeException {
    public InspectionNotFoundException(Long id) {
        super("Inspection with ID " + id + " not found.");
    }
}

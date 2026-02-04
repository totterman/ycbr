package fi.smartbass.ycbr.i9event;

public class InspectorExistsException extends RuntimeException {
    public InspectorExistsException(String inspector) {
        super("inspectorName " + inspector + " already registered.");
    }
}

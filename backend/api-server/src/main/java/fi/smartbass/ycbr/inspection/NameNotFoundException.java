package fi.smartbass.ycbr.inspection;

public class NameNotFoundException extends RuntimeException {
    public NameNotFoundException(String name) {
        super("User with name " + name + " not found.");
    }
}

package fi.smartbass.ycbr.i9event;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(name = "inspector_registrations")
public class InspectorRegistration {

    @NotBlank
    private final String inspectorName;

    private final String message;

    public InspectorRegistration(String inspectorName, String message) {
        this.inspectorName = inspectorName;
        this.message = message;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InspectorRegistration that = (InspectorRegistration) o;
        return Objects.equals(inspectorName, that.inspectorName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(inspectorName);
    }

    @Override
    public String toString() {
        return "InspectorRegistration{" +
                "inspectorName='" + inspectorName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

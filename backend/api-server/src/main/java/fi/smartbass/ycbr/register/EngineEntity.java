package fi.smartbass.ycbr.register;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table(name = "engines")
public class EngineEntity {
    @NotNull
    @PositiveOrZero(message = "Engine Position must be zero or greater")
    private int pos;

    @Size(min = 4, max = 4, message = "Engine Built Year must be 4 characters")
    private final String year;

    @NotNull
    @Size(max = 50, message = "Engine Make must be at most 50 characters")
    private final String make;

    @Size(max = 50, message = "Engine Model must be at most 50 characters")
    private final String model;

    @Size(max = 20, message = "Engine Serial Number must be at most 20 characters")
    private final String serial;

    @NotNull
    @Positive(message = "Engine Power (kW) must be greater than zero")
    private Double power;

    public EngineEntity(int pos, String year, String make, String model, String serial, Double power) {
        this.pos = pos;
        this.year = year;
        this.make = make;
        this.model = model;
        this.serial = serial;
        this.power = power;
    }

    public @PositiveOrZero(message = "Engine Position must be zero or greater") int getPos() { return pos; }

    public @Size(min = 4, max = 4, message = "Engine Built Year must be 4 characters") String getYear() {
        return year;
    }

    public @Size(max = 50, message = "Engine Make must be at most 50 characters") String getMake() {
        return make;
    }

    public @Size(max = 50, message = "Engine Model must be at most 50 characters") String getModel() {
        return model;
    }

    public @Size(max = 20, message = "Engine Serial Number must be at most 20 characters") String getSerial() {
        return serial;
    }

    public @Positive(message = "Engine Power (kW) must be greater than zero") Double getPower() {
        return power;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EngineEntity that = (EngineEntity) o;
        return pos == that.pos && Objects.equals(make, that.make) && Objects.equals(power, that.power);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, make, power);
    }
}

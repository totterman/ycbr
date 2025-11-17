package fi.smartbass.ycbr.i9event;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("boat_bookings")
public class BoatBooking {

    @NotBlank
    private final String boatName;

    private final String message;

    public BoatBooking(String boatName, String message) {
        this.boatName = boatName;
        this.message = message;
    }

    public String getBoatName() {
        return boatName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BoatBooking that = (BoatBooking) o;
        return Objects.equals(boatName, that.boatName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(boatName);
    }
}

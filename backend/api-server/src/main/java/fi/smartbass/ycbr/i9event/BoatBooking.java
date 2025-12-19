package fi.smartbass.ycbr.i9event;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;

@Table("boat_bookings")
public class BoatBooking {

    @NotBlank
    private final UUID boatId;

    private final String message;
    private final boolean taken;

    public BoatBooking(UUID boatId, String message, boolean taken) {
        this.boatId = boatId;
        this.message = message;
        this.taken = taken;
    }

    public UUID getBoatId() {
        return boatId;
    }
    public String getMessage() {
        return message;
    }
    public boolean isTaken() { return taken; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BoatBooking that = (BoatBooking) o;
        return Objects.equals(boatId, that.boatId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(boatId);
    }

    @Override
    public String toString() {
        return "BoatBooking{" +
                "boatId=" + boatId +
                ", message='" + message + '\'' +
                ", taken=" + taken +
                '}';
    }
}

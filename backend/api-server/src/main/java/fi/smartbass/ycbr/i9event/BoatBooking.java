package fi.smartbass.ycbr.i9event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table("boat_bookings")
public class BoatBooking {

    @NotBlank
    private final UUID boatId;

    @Size(max = 50, message = "Booking Message must be at most 50 characters")
    private final String message;
    @Size(max = 1, message = "Inspection Type must be at just 1 character")
    @Pattern(regexp = "[AHBX]", message = "Inspection Type must be one of 'A', 'H', 'B', or 'X'")
    private final String type;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Size(max = 30, message = "Inspection Start Time must be at most 30 characters")
    private final String time;
    private final boolean taken;

    public BoatBooking(UUID boatId, String message, String type, String time, boolean taken) {
        this.boatId = boatId;
        this.message = message;
        this.type = type;
        this.time = time;
        this.taken = taken;
    }

    public UUID getBoatId() {
        return boatId;
    }
    public String getMessage() {
        return message;
    }
    public String getType() { return type; }
    public String getTime() { return time; }
    public boolean isTaken() { return taken; }

    public List<String> getBookings() {
        switch (type) {
            case "Y" -> {
                return List.of(time);
            }
            case "H" -> {
                return List.of(time, nextOne());
            }
            case "B" -> {
                return List.of(time, nextOne());
            }
            default -> throw new IllegalArgumentException("Unknown inspection type: " + type);
        }
    }

    private String nextOne() {
        return OffsetDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME).plusMinutes(20).format(DateTimeFormatter.ISO_DATE_TIME);
    }

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
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", taken=" + taken +
                '}';
    }
}

package fi.smartbass.ycbr.i9event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Table(name = "i9events")
public class I9EventEntity {
    @Id
    private final UUID i9eventId;

    @NotBlank
    private final String place;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull
    private final OffsetDateTime starts;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull
    private final OffsetDateTime ends;

    private Set<BoatBooking> boats;
    private Set<InspectorRegistration> inspectors;

    @CreatedDate
    @InsertOnlyProperty
    private final Instant createdAt;

    @CreatedBy
    @InsertOnlyProperty
    private final String createdBy;

    @LastModifiedDate
    private final Instant modifiedAt;

    @LastModifiedBy
    private final String modifiedBy;

    @Version
    private int version;

    public I9EventEntity(UUID i9eventId, String place, OffsetDateTime starts, OffsetDateTime ends, Set<BoatBooking> boats, Set<InspectorRegistration> inspectors, Instant createdAt, String createdBy, Instant modifiedAt, String modifiedBy, int version) {
        this.i9eventId = i9eventId;
        this.place = place;
        this.starts = starts;
        this.ends = ends;
        this.boats = boats == null ? ConcurrentHashMap.newKeySet() : boats;
        this.inspectors = inspectors == null ? ConcurrentHashMap.newKeySet() : inspectors;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
        this.version = version;
    }

    public void addBoat(UUID boatId, String message, String type, String time) {
        boats.add(createBoatBooking(boatId, message, type, time));
    }

    public void deleteBoat(UUID boatId) {
        boats.removeIf(b -> b.getBoatId().equals(boatId));
    }

    public void markBoat(UUID boatId, String message, String type, String time, boolean taken) {
        BoatBooking booking = boats.stream().filter(b -> b.getBoatId().equals(boatId)).findFirst().orElse(null);
        if (booking == null) {
            boats.add(markBoatBooking(boatId, message, type, time, taken));
        } else {
            /*
            * Since BoatBooking is immutable, we need to remove the old booking and add a new one with the updated 'taken' status.
            * This is necessary because we cannot modify the existing BoatBooking instance directly.
            * Furthermore, we do not want to delete existing Inspection Type and Time.
            */
            boats.remove(booking);
            boats.add(markBoatBooking(boatId, booking.getMessage(), booking.getType(), booking.getTime(), taken));
        }
    }

    private BoatBooking createBoatBooking(UUID boatId, String message, String type, String time) {
        return new BoatBooking(boatId, message, type, time, false);
    }

    private BoatBooking markBoatBooking(UUID boatId, String message, String type, String time, boolean taken) {
        return new BoatBooking(boatId, message, type, time, taken);
    }

    public void addInspector(String inspectorName, String message) {
        inspectors.add(createRegistration(inspectorName, message));
    }

    public void deleteInspector(String inspectorName) {
        inspectors.removeIf(r -> r.getInspectorName().equals(inspectorName));
    }

    private InspectorRegistration createRegistration(String inspectorName, String message) {
        return new InspectorRegistration(inspectorName, message);
    }

   /*
    * BUSINESS LOGIC HERE
    * Returns a list of all boat bookings for this event. This is used to return the bookings in the DTO.
    * Since the bookings are stored in a Set of BoatBooking objects, we need to flatten the list of bookings for each boat into a single list of strings.
    */
    public List<String> getBookings() {
        return boats.stream().flatMap(bb -> bb.getBookings().stream()).toList();
    }

    public UUID getI9eventId() {
        return i9eventId;
    }

    public @NotBlank String getPlace() {
        return place;
    }

    public @NotNull OffsetDateTime getStarts() {
        return starts;
    }

    public @NotNull OffsetDateTime getEnds() {
        return ends;
    }

    public Set<BoatBooking> getBoats() {
        return boats;
    }

    public void setBoats(Set<BoatBooking> boats) {
        this.boats = boats;
    }

    public Set<InspectorRegistration> getInspectors() {
        return inspectors;
    }

    public void setInspectors(Set<InspectorRegistration> inspectors) {
        this.inspectors = inspectors;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        I9EventEntity i9EventEntity = (I9EventEntity) o;
        return Objects.equals(place, i9EventEntity.place) && Objects.equals(starts, i9EventEntity.starts) && Objects.equals(ends, i9EventEntity.ends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(place, starts, ends);
    }

    @Override
    public String toString() {
        return "I9EventEntity{" +
                "i9eventId=" + i9eventId +
                ", place='" + place + '\'' +
                ", starts=" + starts +
                ", ends=" + ends +
                ", boats=" + boats +
                ", inspectors=" + inspectors +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedAt=" + modifiedAt +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", version=" + version +
                '}';
    }
}

package fi.smartbass.ycbr.i9event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.OffsetDateTime;
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

    public void addBoat(UUID boatId, String message) {
        boats.add(createBoatBooking(boatId, message));
    }

    public void deleteBoat(UUID boatId) {
        boats.removeIf(b -> b.getBoatId().equals(boatId));
    }

    public void markBoat(UUID boatId, String message, boolean taken) {
        deleteBoat(boatId);
        boats.add(markBoatBooking(boatId, message, taken));
    }

    private BoatBooking createBoatBooking(UUID boatId, String message) {
        return new BoatBooking(boatId, message, false);
    }

    private BoatBooking markBoatBooking(UUID boatId, String message, boolean taken) {
        return new BoatBooking(boatId, message, taken);
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

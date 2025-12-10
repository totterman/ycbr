package fi.smartbass.ycbr.i9event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Table(name = "i9events")
public class I9Event {
    @Id
    private Long id;

    @NotBlank
    private String place;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull
    private OffsetDateTime starts;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull
    private OffsetDateTime ends;

    private Set<BoatBooking> boats;
    private Set<InspectorRegistration> inspectors;

    @CreatedDate
    @InsertOnlyProperty
    private Instant createdAt;

    @CreatedBy
    @InsertOnlyProperty
    private String createdBy;

    @LastModifiedDate
    private Instant modifiedAt;

    @LastModifiedBy
    private String modifiedBy;

    @Version
    private int version;

    public void addBoat(Long boatId, String message) {
        boats.add(createBoatBooking(boatId, message));
    }

    public void deleteBoat(Long boatId) {
        boats.removeIf(b -> b.getBoatId().equals(boatId));
    }

    public void markBoat(Long boatId, String message) {
        deleteBoat(boatId);
        boats.add(markBoatBooking(boatId, message));
    }

    private BoatBooking createBoatBooking(Long boatId, String message) {
        return new BoatBooking(boatId, message, false);
    }

    private BoatBooking markBoatBooking(Long boatId, String message) {
        return new BoatBooking(boatId, message, true);
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

    public I9Event(Long id, String place, OffsetDateTime starts, OffsetDateTime ends, Instant createdAt, String createdBy, Instant modifiedAt, String modifiedBy, int version) {
        this.id = id;
        this.place = place;
        this.starts = starts;
        this.ends = ends;
        this.boats = ConcurrentHashMap.newKeySet();
        this.inspectors = ConcurrentHashMap.newKeySet();
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getPlace() {
        return place;
    }

    public void setPlace(@NotBlank String place) {
        this.place = place;
    }

    public @NotNull OffsetDateTime getStarts() {
        return starts;
    }

    public void setStarts(@NotNull OffsetDateTime starts) {
        this.starts = starts;
    }

    public @NotNull OffsetDateTime getEnds() {
        return ends;
    }

    public void setEnds(@NotNull OffsetDateTime ends) {
        this.ends = ends;
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

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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
        I9Event i9Event = (I9Event) o;
        return Objects.equals(place, i9Event.place) && Objects.equals(starts, i9Event.starts) && Objects.equals(ends, i9Event.ends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(place, starts, ends);
    }
}

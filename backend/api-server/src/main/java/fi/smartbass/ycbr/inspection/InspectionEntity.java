package fi.smartbass.ycbr.inspection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Table(name = "inspections")
public class InspectionEntity {
    @Id
    private UUID inspectionId;

    private OffsetDateTime timestamp;

    @NotBlank(message = "Inspector must be defined")
    private String inspector;

    @NotNull(message = "Inspection Event must be defined")
    private UUID eventId;

    @NotNull(message = "BoatEntity to Inspect must be defined")
    private UUID boatId;

    private InspectionData inspection;
    private OffsetDateTime completed;

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

    public InspectionEntity(UUID inspectionId, OffsetDateTime timestamp, String inspector, UUID eventId, UUID boatId, InspectionData inspection, OffsetDateTime completed, Instant createdAt, String createdBy, Instant modifiedAt, String modifiedBy, int version) {
        this.inspectionId = inspectionId;
        this.timestamp = timestamp;
        this.inspector = inspector;
        this.eventId = eventId;
        this.boatId = boatId;
        this.inspection = inspection;
        this.completed = completed;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
        this.version = version;
    }

    public UUID getInspectionId() {
        return inspectionId;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public @NotBlank(message = "Inspector must be defined") String getInspector() {
        return inspector;
    }

    public @NotNull(message = "Inspection Event must be defined") UUID getEventId() {
        return eventId;
    }

    public @NotNull(message = "BoatEntity to Inspect must be defined") UUID getBoatId() {
        return boatId;
    }

    public InspectionData getInspection() {
        return inspection;
    }

    public OffsetDateTime getCompleted() {
        return completed;
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
    public String toString() {
        return "InspectionEntity{" +
                "inspectionId=" + inspectionId +
                ", timestamp=" + timestamp +
                ", inspector='" + inspector + '\'' +
                ", eventId=" + eventId +
                ", boatId=" + boatId +
                ", inspection=" + inspection +
                ", completed=" + completed +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedAt=" + modifiedAt +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", version=" + version +
                '}';
    }
}

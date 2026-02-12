package fi.smartbass.ycbr.inspection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Table(name = "inspections")
public class InspectionEntity {
    @Id
    private UUID inspectionId;

    private OffsetDateTime timestamp;

    @NotBlank(message = "Inspector must be defined")
    private String inspectorName;

    @NotNull(message = "Inspection Event must be defined")
    private UUID eventId;

    @NotNull(message = "BoatEntity to Inspect must be defined")
    private UUID boatId;

    @Size(max = 1, message = "Inspection Class must be just 1 character")
    private String inspectionClass;

    private InspectionData inspection;
    private OffsetDateTime completed;
    private Set<Remark> remarks;

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

    public InspectionEntity(UUID inspectionId, OffsetDateTime timestamp, String inspectorName, UUID eventId, UUID boatId, String inspectionClass, InspectionData inspection, OffsetDateTime completed, Set<Remark> remarks, Instant createdAt, String createdBy, Instant modifiedAt, String modifiedBy, int version) {
        this.inspectionId = inspectionId;
        this.timestamp = timestamp;
        this.inspectorName = inspectorName;
        this.eventId = eventId;
        this.boatId = boatId;
        this.inspectionClass = inspectionClass;
        this.inspection = inspection;
        this.completed = completed;
        this.remarks = remarks == null ? ConcurrentHashMap.newKeySet() : remarks;
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

    public @NotBlank(message = "Inspector must be defined") String getInspectorName() {
        return inspectorName;
    }

    public @NotNull(message = "Inspection Event must be defined") UUID getEventId() {
        return eventId;
    }

    public @NotNull(message = "BoatEntity to Inspect must be defined") UUID getBoatId() {
        return boatId;
    }

    public String getInspectionClass() {
        return inspectionClass;
    }

    public InspectionData getInspection() {
        return inspection;
    }

    public OffsetDateTime getCompleted() {
        return completed;
    }

    public Set<Remark> getRemarks() { return remarks; }

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
                ", inspectorName='" + inspectorName + '\'' +
                ", eventId=" + eventId +
                ", boatId=" + boatId +
                ", inspectionClass=" + inspectionClass +
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

package fi.smartbass.ycbr.inspection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotNull(message = "Inspection class must be defined")
    @Size(max = 1, message = "Inspection Class must be just 1 character")
    @Pattern(regexp = "[01234]", message = "Inspection Class must be one of '0', '1', '2', '3' or '4'")
    private String inspectionClass;

    @NotNull(message = "Inspection Type must be defined")
    @Size(max = 1, message = "Inspection Type must be just 1 character")
    @Pattern(regexp = "[AHBX]", message = "Inspection Type must be one of 'A', 'H', 'B' or 'X'")
    private String inspectionType;


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

    public InspectionEntity(UUID inspectionId, OffsetDateTime timestamp, String inspectorName, UUID eventId, UUID boatId, String inspectionClass, String inspectionType, InspectionData inspection, OffsetDateTime completed, Set<Remark> remarks, Instant createdAt, String createdBy, Instant modifiedAt, String modifiedBy, int version) {
        this.inspectionId = inspectionId;
        this.timestamp = timestamp;
        this.inspectorName = inspectorName;
        this.eventId = eventId;
        this.boatId = boatId;
        this.inspectionClass = inspectionClass;
        this.inspectionType = inspectionType;
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

    public @NotNull(message = "Inspection Type must be defined") @Size(max = 1, message = "Inspection Type must be just 1 character") @Pattern(regexp = "A|H|B", message = "Inspection Type must be one of 'A', 'H', or 'B'") String getInspectionType() {
        return inspectionType;
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
                ", inspectionType=" + inspectionType +
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

package fi.smartbass.ycbr.inspection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.OffsetDateTime;

@Table(name = "inspections")
public class InspectionEntity {
    @Id
    private Long id;

    private OffsetDateTime timestamp;

    @NotBlank(message = "Inspector must be defined")
    private String inspector;

    @NotNull(message = "Inspection Event must be defined")
    private Long event;

    @NotNull(message = "BoatEntity to Inspect must be defined")
    private Long boat;

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

    public InspectionEntity(Long id, OffsetDateTime timestamp, String inspector, Long event, Long boat, InspectionData inspection, OffsetDateTime completed, Instant createdAt, String createdBy, Instant modifiedAt, String modifiedBy, int version) {
        this.id = id;
        this.timestamp = timestamp;
        this.inspector = inspector;
        this.event = event;
        this.boat = boat;
        this.inspection = inspection;
        this.completed = completed;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public @NotBlank(message = "Inspector must be defined") String getInspector() {
        return inspector;
    }

    public @NotNull(message = "Inspection Event must be defined") Long getEvent() {
        return event;
    }

    public @NotNull(message = "BoatEntity to Inspect must be defined") Long getBoat() {
        return boat;
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
                "id=" + id +
                ", timestamp=" + timestamp +
                ", inspector='" + inspector + '\'' +
                ", event=" + event +
                ", boat=" + boat +
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

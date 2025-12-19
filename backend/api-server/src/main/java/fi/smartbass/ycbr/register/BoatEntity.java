package fi.smartbass.ycbr.register;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Table(name = "boats")
public class BoatEntity {

    @Id
    private UUID boatId;

    @NotBlank(message = "BoatEntity owner must be defined")
    @Size(min = 3, max = 50, message = "Boat owner length must be at most 50 characters")
    private String owner;

    @NotBlank(message = "BoatEntity name must be defined")
    @Size(min = 3, max = 50, message = "Boat name length must be at most 50 characters")
    private String name;

    @Size(max = 50, message = "Boat Sign length must be at most 50 characters")
    private String sign;

    @Size(max = 50, message = "Boat Make length must be at most 50 characters")
    private String make;

    @Size(max = 50, message = "Boat Model length must be at most 50 characters")
    private String model;

    @NotNull(message = "BoatEntity length must be defined")
    @Positive(message = "BoatEntity length must be greater than zero")
    private Double loa;

    @Positive(message = "BoatEntity draft must be greater than zero")
    private Double draft;

    @Positive(message = "BoatEntity beam must be greater than zero")
    private Double beam;

    @Positive(message = "BoatEntity deplacement must be greater than zero")
    private Double deplacement;

    @Size(max = 50, message = "Boat Engine length must be at most 50 characters")
    private String engines;

    @Size(max = 4, message = "Boat Year must be at most 4 characters")
    private String year;

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

    public BoatEntity() {
    }

    public BoatEntity(UUID boatId, String owner, String name, String sign, String make, String model, Double loa, Double draft, Double beam, Double deplacement, String engines, String year, Instant createdAt, String createdBy, Instant modifiedAt, String modifiedBy, int version) {
        this.boatId = boatId;
        this.owner = owner;
        this.name = name;
        this.sign = sign;
        this.make = make;
        this.model = model;
        this.loa = loa;
        this.draft = draft;
        this.beam = beam;
        this.deplacement = deplacement;
        this.engines = engines;
        this.year = year;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
        this.version = version;
    }

    public UUID getBoatId() {
        return boatId;
    }

    public void setBoatId(UUID boatId) {
        this.boatId = boatId;
    }

    public @NotBlank(message = "BoatEntity owner must be defined") @Size(min = 3, max = 50, message = "Boat owner length must be at most 50 characters") String getOwner() {
        return owner;
    }

    public void setOwner(@NotBlank(message = "BoatEntity owner must be defined") @Size(min = 3, max = 50, message = "Boat owner length must be at most 50 characters") String owner) {
        this.owner = owner;
    }

    public @NotBlank(message = "BoatEntity name must be defined") @Size(min = 3, max = 50, message = "Boat name length must be at most 50 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "BoatEntity name must be defined") @Size(min = 3, max = 50, message = "Boat name length must be at most 50 characters") String name) {
        this.name = name;
    }

    public @Size(max = 50, message = "Boat Sign length must be at most 50 characters") String getSign() {
        return sign;
    }

    public void setSign(@Size(max = 50, message = "Boat Sign length must be at most 50 characters") String sign) {
        this.sign = sign;
    }

    public @Size(max = 50, message = "Boat Make length must be at most 50 characters") String getMake() {
        return make;
    }

    public void setMake(@Size(max = 50, message = "Boat Make length must be at most 50 characters") String make) {
        this.make = make;
    }

    public @Size(max = 50, message = "Boat Model length must be at most 50 characters") String getModel() {
        return model;
    }

    public void setModel(@Size(max = 50, message = "Boat Model length must be at most 50 characters") String model) {
        this.model = model;
    }

    public @NotNull(message = "BoatEntity length must be defined") @Positive(message = "BoatEntity length must be greater than zero") Double getLoa() {
        return loa;
    }

    public void setLoa(@NotNull(message = "BoatEntity length must be defined") @Positive(message = "BoatEntity length must be greater than zero") Double loa) {
        this.loa = loa;
    }

    public @Positive(message = "BoatEntity draft must be greater than zero") Double getDraft() {
        return draft;
    }

    public void setDraft(@Positive(message = "BoatEntity draft must be greater than zero") Double draft) {
        this.draft = draft;
    }

    public @Positive(message = "BoatEntity beam must be greater than zero") Double getBeam() {
        return beam;
    }

    public void setBeam(@Positive(message = "BoatEntity beam must be greater than zero") Double beam) {
        this.beam = beam;
    }

    public @Positive(message = "BoatEntity deplacement must be greater than zero") Double getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(@Positive(message = "BoatEntity deplacement must be greater than zero") Double deplacement) {
        this.deplacement = deplacement;
    }

    public @Size(max = 50, message = "Boat Engine length must be at most 50 characters") String getEngines() {
        return engines;
    }

    public void setEngines(@Size(max = 50, message = "Boat Engine length must be at most 50 characters") String engines) {
        this.engines = engines;
    }

    public @Size(max = 4, message = "Boat Year must be at most 4 characters") String getYear() {
        return year;
    }

    public void setYear(@Size(max = 4, message = "Boat Year must be at most 4 characters") String year) {
        this.year = year;
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
        BoatEntity boat = (BoatEntity) o;
        return Objects.equals(boatId, boat.boatId) && Objects.equals(owner, boat.owner) && Objects.equals(name, boat.name) && Objects.equals(sign, boat.sign) && Objects.equals(make, boat.make) && Objects.equals(model, boat.model) && Objects.equals(loa, boat.loa) && Objects.equals(year, boat.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boatId, owner, name, sign, make, model, loa, year);
    }

}
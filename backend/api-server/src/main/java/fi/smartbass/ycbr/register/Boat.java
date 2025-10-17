package fi.smartbass.ycbr.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Objects;

@Table(name = "boats")
public class Boat {

    @Id
    private Long id;

    @NotBlank(message = "Boat owner must be defined")
    private String owner;

    @NotBlank(message = "Boat name must be defined")
    private String name;

    private String sign;
    private String make;
    private String model;

    @NotNull(message = "Boat length must be defined")
    @Positive(message = "Boat length must be greater than zero")
    private Double loa;

    @Positive(message = "Boat draft must be greater than zero")
    private Double draft;

    @Positive(message = "Boat beam must be greater than zero")
    private Double beam;

    @Positive(message = "Boat deplacement must be greater than zero")
    private Double deplacement;

    private String engines;
    private String year;

    @CreatedDate
    private Instant createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private Instant modifiedAt;

    @LastModifiedBy
    private String modifiedBy;

    @Version
    private int version;

    public Boat() {
    }

    public Boat(Long id, String owner, String name, String sign, String make, String model, Double loa, Double draft, Double beam, Double deplacement, String engines, String year, Instant createdAt, String createdBy, Instant modifiedAt, String modifiedBy, int version) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Boat owner must be defined") String getOwner() {
        return owner;
    }

    public void setOwner(@NotBlank(message = "Boat owner must be defined") String owner) {
        this.owner = owner;
    }

    public @NotBlank(message = "Boat name must be defined") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Boat name must be defined") String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public @NotNull(message = "Boat length must be defined") @Positive(message = "Boat length must be greater than zero") Double getLoa() {
        return loa;
    }

    public void setLoa(@NotNull(message = "Boat length must be defined") @Positive(message = "Boat length must be greater than zero") Double loa) {
        this.loa = loa;
    }

    public @Positive(message = "Boat draft must be greater than zero") Double getDraft() {
        return draft;
    }

    public void setDraft(@Positive(message = "Boat draft must be greater than zero") Double draft) {
        this.draft = draft;
    }

    public @Positive(message = "Boat beam must be greater than zero") Double getBeam() {
        return beam;
    }

    public void setBeam(@Positive(message = "Boat beam must be greater than zero") Double beam) {
        this.beam = beam;
    }

    public @Positive(message = "Boat deplacement must be greater than zero") Double getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(@Positive(message = "Boat deplacement must be greater than zero") Double deplacement) {
        this.deplacement = deplacement;
    }

    public String getEngines() {
        return engines;
    }

    public void setEngines(String engines) {
        this.engines = engines;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
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
        Boat boat = (Boat) o;
        return Objects.equals(id, boat.id) && Objects.equals(owner, boat.owner) && Objects.equals(name, boat.name) && Objects.equals(sign, boat.sign) && Objects.equals(make, boat.make) && Objects.equals(model, boat.model) && Objects.equals(loa, boat.loa) && Objects.equals(year, boat.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, name, sign, make, model, loa, year);
    }

}

package fi.smartbass.ycbr.register;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Table(name = "boats")
public class BoatEntity {

    @Id
    private UUID boatId;

    @NotBlank(message = "Boat club must be defined")
    @Size(min = 1, max = 20, message = "Boat Club must be at most 20 characters")
    private String club;

    @NotBlank(message = "Boat certificate must be defined")
    @Size(min = 1, max = 20, message = "Boat Certificate Number must be at most 20 characters")
    private String cert;

    @NotBlank(message = "Boat name must be defined")
    @Size(min = 1, max = 50, message = "Boat Name must be at most 50 characters")
    private String name;

    @Size(min = 1, max = 1, message = "Boat Kind is coded 1 character Motorboat/Sailboat/Other")
    private String kind;

    @Size(max = 50, message = "Boat Make length must be at most 50 characters")
    private String make;

    @Size(max = 50, message = "Boat Model length must be at most 50 characters")
    private String model;

    @Size(max = 20, message = "Boat Sign length must be at most 20 characters")
    private String sign;

    @Size(max = 20, message = "Sail Number length must be at most 20 characters")
    private String sailnr;

    @Size(max = 20, message = "Hull Number length must be at most 20 characters")
    private String hullnr;

    @Size(min = 1, max = 1, message = "Hull Material is coded 1 character Grp/Alu/Steel/Wood/Other")
    private String material;

    @Size(min = 4, max = 4, message = "Hull Build Year must be 4 characters")
    private String year;

    @Size(max = 20, message = "Hull Colour must be at most 20 characters")
    private String colour;

    @Positive(message = "Length Overall must be greater than zero")
    private Double loa;

    @Positive(message = "Beam must be greater than zero")
    private Double beam;

    @Positive(message = "Draft must be greater than zero")
    private Double draft;

    @Positive(message = "Mast height must be greater than zero")
    private Double height;

    @Positive(message = "Deplacement must be greater than zero")
    private Double deplacement;

    @Positive(message = "Sail Area must be greater than zero")
    private Double sailarea;

    @Size(min = 1, max = 1, message = "Boat Engine Drive type coded 1 character Direct/Outboard/sterNdrive/Saildrive/Electric/Other")
    private String drive;

    private Set<EngineEntity> engines;

    @Positive(message = "Fuel Tank Volume must be greater than zero")
    private Double fuel;

    @Positive(message = "Fresh Water Tandk Volume must be greater than zero")
    private Double water;

    @Positive(message = "Septic Tank Volume must be greater than zero")
    private Double septi;

    @Positive(message = "Berths number must be greater than zero")
    private int berths;

    @Size(max = 10, message = "Radio Callsign must be at most 10 characters")
    private String radio;

    @Size(max = 50, message = "Home Port must be at most 50 characters")
    private String home;

    @Size(max = 50, message = "Winter Port must be at most 50 characters")
    private String winter;

    @NotBlank(message = "Boat owner must be defined")
    @Size(min = 3, max = 50, message = "Boat Owner must be at most 50 characters")
    private String owner;

    @Nullable
    @Size(min = 3, max = 50, message = "Boat Second Owner must be at most 50 characters")
    private String owner2;

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


/* ***************************************************************************
 *
 * Java Bean boilerplate code (constructors, getters, setters, equals, hashcode)
 *
 * ************************************************************************* */

    public BoatEntity(UUID boatId, String club, String cert, String name, String kind, String make, String model, String sign, String sailnr, String hullnr, String material, String year, String colour, Double loa, Double beam, Double draft, Double height, Double deplacement, Double sailarea, String drive, Set<EngineEntity> engines, Double fuel, Double water, Double septi, int berths, String radio, String home, String winter, String owner, @Nullable String owner2, Instant createdAt, String createdBy, Instant modifiedAt, String modifiedBy, int version) {
        this.boatId = boatId;
        this.club = club;
        this.cert = cert;
        this.name = name;
        this.kind = kind;
        this.make = make;
        this.model = model;
        this.sign = sign;
        this.sailnr = sailnr;
        this.hullnr = hullnr;
        this.material = material;
        this.year = year;
        this.colour = colour;
        this.loa = loa;
        this.beam = beam;
        this.draft = draft;
        this.height = height;
        this.deplacement = deplacement;
        this.sailarea = sailarea;
        this.drive = drive;
        this.engines = engines == null ? ConcurrentHashMap.newKeySet() : engines;
        this.fuel = fuel;
        this.water = water;
        this.septi = septi;
        this.berths = berths;
        this.radio = radio;
        this.home = home;
        this.winter = winter;
        this.owner = owner;
        this.owner2 = owner2;
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

    public @NotBlank(message = "Boat club must be defined") @Size(min = 1, max = 20, message = "Boat Club must be at most 20 characters") String getClub() {
        return club;
    }

    public void setClub(@NotBlank(message = "Boat club must be defined") @Size(min = 1, max = 20, message = "Boat Club must be at most 20 characters") String club) {
        this.club = club;
    }

    public @NotBlank(message = "Boat certificate must be defined") @Size(min = 1, max = 20, message = "Boat Certificate Number must be at most 20 characters") String getCert() {
        return cert;
    }

    public void setCert(@NotBlank(message = "Boat certificate must be defined") @Size(min = 1, max = 20, message = "Boat Certificate Number must be at most 20 characters") String cert) {
        this.cert = cert;
    }

    public @NotBlank(message = "Boat name must be defined") @Size(min = 1, max = 50, message = "Boat Name must be at most 50 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Boat name must be defined") @Size(min = 1, max = 50, message = "Boat Name must be at most 50 characters") String name) {
        this.name = name;
    }

    public @Size(min = 1, max = 1, message = "Boat Kind is coded 1 character Motorboat/Sailboat/Other") String getKind() {
        return kind;
    }

    public void setKind(@Size(min = 1, max = 1, message = "Boat Kind is coded 1 character Motorboat/Sailboat/Other") String kind) {
        this.kind = kind;
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

    public @Size(max = 20, message = "Boat Sign length must be at most 20 characters") String getSign() {
        return sign;
    }

    public void setSign(@Size(max = 20, message = "Boat Sign length must be at most 20 characters") String sign) {
        this.sign = sign;
    }

    public @Size(max = 20, message = "Sail Number length must be at most 20 characters") String getSailnr() {
        return sailnr;
    }

    public void setSailnr(@Size(max = 20, message = "Sail Number length must be at most 20 characters") String sailnr) {
        this.sailnr = sailnr;
    }

    public @Size(max = 20, message = "Hull Number length must be at most 20 characters") String getHullnr() {
        return hullnr;
    }

    public void setHullnr(@Size(max = 20, message = "Hull Number length must be at most 20 characters") String hullnr) {
        this.hullnr = hullnr;
    }

    public @Size(min = 1, max = 1, message = "Hull Material is coded 1 character Grp/Alu/Steel/Wood/Other") String getMaterial() {
        return material;
    }

    public void setMaterial(@Size(min = 1, max = 1, message = "Hull Material is coded 1 character Grp/Alu/Steel/Wood/Other") String material) {
        this.material = material;
    }

    public @Size(min = 4, max = 4, message = "Hull Build Year must be 4 characters") String getYear() {
        return year;
    }

    public void setYear(@Size(min = 4, max = 4, message = "Hull Build Year must be 4 characters") String year) {
        this.year = year;
    }

    public @Size(max = 20, message = "Hull Colour must be at most 20 characters") String getColour() {
        return colour;
    }

    public void setColour(@Size(max = 20, message = "Hull Colour must be at most 20 characters") String colour) {
        this.colour = colour;
    }

    public @Positive(message = "Length Overall must be greater than zero") Double getLoa() {
        return loa;
    }

    public void setLoa(@Positive(message = "Length Overall must be greater than zero") Double loa) {
        this.loa = loa;
    }

    public @Positive(message = "Beam must be greater than zero") Double getBeam() {
        return beam;
    }

    public void setBeam(@Positive(message = "Beam must be greater than zero") Double beam) {
        this.beam = beam;
    }

    public @Positive(message = "Draft must be greater than zero") Double getDraft() {
        return draft;
    }

    public void setDraft(@Positive(message = "Draft must be greater than zero") Double draft) {
        this.draft = draft;
    }

    public @Positive(message = "Mast height must be greater than zero") Double getHeight() {
        return height;
    }

    public void setHeight(@Positive(message = "Mast height must be greater than zero") Double height) {
        this.height = height;
    }

    public @Positive(message = "Deplacement must be greater than zero") Double getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(@Positive(message = "Deplacement must be greater than zero") Double deplacement) {
        this.deplacement = deplacement;
    }

    public @Positive(message = "Sail Area must be greater than zero") Double getSailarea() {
        return sailarea;
    }

    public void setSailarea(@Positive(message = "Sail Area must be greater than zero") Double sailarea) {
        this.sailarea = sailarea;
    }

    public @Size(min = 1, max = 1, message = "Boat Engine Drive type coded 1 character Direct/Outboard/sterNdrive/Saildrive/Electric/Other") String getDrive() {
        return drive;
    }

    public void setDrive(@Size(min = 1, max = 1, message = "Boat Engine Drive type coded 1 character Direct/Outboard/sterNdrive/Saildrive/Electric/Other") String drive) {
        this.drive = drive;
    }

    public Set<EngineEntity> getEngines() {
        return engines;
    }

    public void setEngines(Set<EngineEntity> engines) {
        this.engines = engines;
    }

    public @Positive(message = "Fuel Tank Volume must be greater than zero") Double getFuel() {
        return fuel;
    }

    public void setFuel(@Positive(message = "Fuel Tank Volume must be greater than zero") Double fuel) {
        this.fuel = fuel;
    }

    public @Positive(message = "Fresh Water Tandk Volume must be greater than zero") Double getWater() {
        return water;
    }

    public void setWater(@Positive(message = "Fresh Water Tandk Volume must be greater than zero") Double water) {
        this.water = water;
    }

    public @Positive(message = "Septic Tank Volume must be greater than zero") Double getSepti() {
        return septi;
    }

    public void setSepti(@Positive(message = "Septic Tank Volume must be greater than zero") Double septi) {
        this.septi = septi;
    }

    public @Positive(message = "Berths number must be greater than zero") int getBerths() {
        return berths;
    }

    public void setBerths(@Positive(message = "Berths number must be greater than zero") int berths) {
        this.berths = berths;
    }

    public @Size(max = 10, message = "Radio Callsign must be at most 10 characters") String getRadio() {
        return radio;
    }

    public void setRadio(@Size(max = 10, message = "Radio Callsign must be at most 10 characters") String radio) {
        this.radio = radio;
    }

    public @Size(max = 50, message = "Home Port must be at most 50 characters") String getHome() {
        return home;
    }

    public void setHome(@Size(max = 50, message = "Home Port must be at most 50 characters") String home) {
        this.home = home;
    }

    public @Size(max = 50, message = "Winter Port must be at most 50 characters") String getWinter() {
        return winter;
    }

    public void setWinter(@Size(max = 50, message = "Winter Port must be at most 50 characters") String winter) {
        this.winter = winter;
    }

    public @NotBlank(message = "Boat owner must be defined") @Size(min = 3, max = 50, message = "Boat Owner must be at most 50 characters") String getOwner() {
        return owner;
    }

    public void setOwner(@NotBlank(message = "Boat owner must be defined") @Size(min = 3, max = 50, message = "Boat Owner must be at most 50 characters") String owner) {
        this.owner = owner;
    }

    @Nullable
    public @Size(min = 3, max = 50, message = "Boat Second Owner must be at most 50 characters") String getOwner2() {
        return owner2;
    }

    public void setOwner2(@Nullable @Size(min = 3, max = 50, message = "Boat Second Owner must be at most 50 characters") String owner2) {
        this.owner2 = owner2;
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
        BoatEntity that = (BoatEntity) o;
        return Objects.equals(boatId, that.boatId) && Objects.equals(name, that.name) && Objects.equals(make, that.make) && Objects.equals(model, that.model) && Objects.equals(sign, that.sign) && Objects.equals(year, that.year) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boatId, name, make, model, sign, year, owner);
    }
}
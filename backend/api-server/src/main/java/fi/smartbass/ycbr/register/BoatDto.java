package fi.smartbass.ycbr.register;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record BoatDto(
        UUID boatId,

        @NotBlank(message = "Boat club must be defined")
        @Size(min = 1, max = 20, message = "Boat Club must be at most 20 characters")
        String club,

        @NotBlank(message = "Boat certificate must be defined")
        @Size(min = 1, max = 20, message = "Boat Certificate Number must be at most 20 characters")
        String cert,

        @NotBlank(message = "Boat name must be defined")
        @Size(min = 1, max = 50, message = "Boat Name must be at most 50 characters")
        String name,

        @Size(min = 1, max = 1, message = "Boat Kind is coded 1 character Motorboat/Sailboat/Other")
        String kind,

        @Size(max = 50, message = "Boat Make length must be at most 50 characters")
        String make,

        @Size(max = 50, message = "Boat Model length must be at most 50 characters")
        String model,

        @Size(max = 20, message = "Boat Sign length must be at most 20 characters")
        String sign,

        @Size(max = 20, message = "Sail Number length must be at most 20 characters")
        String sailnr,

        @Size(max = 20, message = "Hull Number length must be at most 20 characters")
        String hullnr,

        @Size(min = 1, max = 1, message = "Hull Material is coded 1 character Grp/Alu/Steel/Wood/Other")
        String material,

        @Size(min = 4, max = 4, message = "Hull Build Year must be 4 characters")
        String year,

        @Size(max = 20, message = "Hull Colour must be at most 20 characters")
        String colour,

        @Positive(message = "Length Overall must be greater than zero")
        Double loa,

        @Positive(message = "Beam must be greater than zero")
        Double beam,

        @Positive(message = "Draft must be greater than zero")
        Double draft,

        @Positive(message = "Mast height must be greater than zero")
        Double height,

        @Positive(message = "Deplacement must be greater than zero")
        Double deplacement,

        @Positive(message = "Sail Area must be greater than zero")
        Double sailarea,

        @Size(min = 1, max = 1, message = "Boat Engine Drive type coded 1 character Direct/Outboard/sterNdrive/Saildrive/Electric/Other")
        String drive,

        Set<EngineDto> engines,

        @Positive(message = "Fuel Tank Volume must be greater than zero")
        Double fuel,

        @Positive(message = "Fresh Water Tandk Volume must be greater than zero")
        Double water,

        @Positive(message = "Septic Tank Volume must be greater than zero")
        Double septi,

        @Positive(message = "Berths number must be greater than zero")
        int berths,

        @Size(max = 10, message = "Radio Callsign must be at most 10 characters")
        String radio,

        @Size(max = 50, message = "Home Port must be at most 50 characters")
        String home,

        @Size(max = 50, message = "Winter Port must be at most 50 characters")
        String winter,

        @NotBlank(message = "Boat owner must be defined")
        @Size(min = 3, max = 50, message = "Boat Owner must be at most 50 characters")
        String owner,

        @Nullable
        @Size(min = 3, max = 50, message = "Boat Second Owner must be at most 50 characters")
        String owner2
        ) {}

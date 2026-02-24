package fi.smartbass.ycbr.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

public record NewBoatDto(
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

        @Size(min = 4, max = 4, message = "Hull Build Year must be 4 characters")
        String year,

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

        @Size(min = 1, max = 1, message = "Boat Engine Drive type coded 1 character Direct/Outboard/sterNdrive/Saildrive/Electric/Other")
        String drive,

        Set<EngineDto> engines,

        @NotBlank(message = "Boat Owner must be defined")
        @Size(min = 3, max = 50, message = "Boat Owner must be at most 50 characters")
        String owner,

        @Size(min = 3, max = 50, message = "Boat Second Owner must be at most 50 characters")
        String owner2
        ) {}

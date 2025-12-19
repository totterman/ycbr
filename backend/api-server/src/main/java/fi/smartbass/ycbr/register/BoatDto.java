package fi.smartbass.ycbr.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record BoatDto(
        UUID boatId,

        @NotBlank(message = "BoatEntity owner must be defined")
        @Size(min = 3, max = 50, message = "Boat owner length must be at most 50 characters")
        String owner,

        @NotBlank(message = "BoatEntity name must be defined")
        @Size(min = 3, max = 50, message = "Boat name length must be at most 50 characters")
        String name,

        @Size(max = 50, message = "Boat Sign length must be at most 50 characters")
        String sign,

        @Size(max = 50, message = "Boat Make length must be at most 50 characters")
        String make,

        @Size(max = 50, message = "Boat Model length must be at most 50 characters")
        String model,

        @NotNull(message = "BoatEntity length must be defined")
        @Positive(message = "BoatEntity length must be greater than zero")
        Double loa,

        @Positive(message = "BoatEntity draft must be greater than zero")
        Double draft,

        @Positive(message = "BoatEntity beam must be greater than zero")
        Double beam,

        @Positive(message = "BoatEntity deplacement must be greater than zero")
        Double deplacement,

        @Size(max = 50, message = "Boat Engine length must be at most 50 characters")
        String engines,

        @Size(max = 4, message = "Boat Year must be at most 4 characters")
        String year) {
}

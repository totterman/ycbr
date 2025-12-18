package fi.smartbass.ycbr.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NewBoatDto(
        @NotBlank(message = "BoatEntity owner must be defined")
        String owner,

        @NotBlank(message = "BoatEntity name must be defined")
        String name,

        String sign,
        String make,
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

        String engines,
        String year
) {
}

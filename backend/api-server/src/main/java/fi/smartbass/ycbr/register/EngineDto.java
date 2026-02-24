package fi.smartbass.ycbr.register;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record EngineDto(

        @PositiveOrZero(message = "Engine Position must be zero or greater")
        int pos,

        @Size(min = 4, max = 4, message = "Engine Built Year must be 4 characters")
        String year,

        @Size(max = 50, message = "Engine Make must be at most 50 characters")
        String make,

        @Size(max = 50, message = "Engine Model must be at most 50 characters")
        String model,

        @Size(max = 20, message = "Engine Serial Number must be at most 20 characters")
        String serial,

        @Positive(message = "Engine Power (kW) must be greater than zero")
        Double power

) {
}

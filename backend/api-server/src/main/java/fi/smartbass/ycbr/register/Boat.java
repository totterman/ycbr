package fi.smartbass.ycbr.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.*;

import java.time.Instant;

public record Boat(
        @Id Long id,
        @NotBlank(message = "Boat owner must be defined") String owner,
        @NotBlank(message = "Boat name must be defined") String name,
        String sign,
        String make,
        String model,
        @NotNull(message = "Boat length must be defined")
        @Positive(message = "Boat length must be greater than zero") Double loa,
        @Positive(message = "Boat draft must be greater than zero") Double draft,
        @Positive(message = "Boat beam must be greater than zero") Double beam,
        @Positive(message = "Boat deplacement must be greater than zero") Double deplacement,
        String engines,
        String year,
        @CreatedDate Instant createdAt,
        @CreatedBy String createdBy,
        @LastModifiedDate Instant modifiedAt,
        @LastModifiedBy String modifiedBy,
        @Version int version
) {
    public static Boat of(Long id, String owner, String name, String sign, String make, String model, Double loa, Double draft, Double beam, Double deplacement, String engines, String year) {
        return new Boat(id, owner, name, sign, make, model, loa, draft, beam, deplacement, engines, year, null, null, null, null, 0);
    }
}

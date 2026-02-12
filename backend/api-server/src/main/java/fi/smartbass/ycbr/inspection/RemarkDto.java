package fi.smartbass.ycbr.inspection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record RemarkDto(
        @PositiveOrZero
        int id,

        @NotBlank(message = "Remark Item must be defined")
        @Size(max = 5, message = "Remark Item must be max 5 characters")
        String item,

        @Size(max = 255, message = "Remark Text must be max 255 characters")
        String text) {}

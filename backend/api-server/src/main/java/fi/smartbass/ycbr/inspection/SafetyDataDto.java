package fi.smartbass.ycbr.inspection;

import jakarta.validation.constraints.PositiveOrZero;

public record SafetyDataDto(
        boolean buoyancy,
        @PositiveOrZero
        int harness,
        @PositiveOrZero
        int lifebuoy,
        boolean signals_a,
        boolean signals_b,
        @PositiveOrZero
        int fixed_handpump,
        boolean electric_pump,
        @PositiveOrZero
        int hand_extinguisher,
        boolean fire_blanket,
        boolean plugs,
        @PositiveOrZero
        int flashlight,
        boolean firstaid,
        boolean spare_steering,
        boolean emergency_tools,
        boolean reserves,
        boolean liferaft,
        boolean detector) {
}

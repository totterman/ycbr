package fi.smartbass.ycbr.inspection;

import jakarta.validation.constraints.PositiveOrZero;

public record SafetyDataDto(
        boolean buoyancy,
        boolean harness,
        boolean lifebuoy,
        boolean signals_a,
        boolean signals_b,
        boolean fixed_handpump,
        boolean electric_pump,
        boolean hand_extinguisher,
        boolean fire_blanket,
        boolean plugs,
        boolean flashlight,
        boolean firstaid,
        boolean spare_steering,
        boolean emergency_tools,
        boolean reserves,
        boolean liferaft,
        boolean detector) {
}

package fi.smartbass.ycbr.inspection;

public record SafetyDataDto(boolean buoyancy, int harness, int lifebuoy, boolean signals_a, boolean signals_b, int fixed_handpump, boolean electric_pump, int hand_extinguisher, boolean fire_blanket, boolean plugs, int flashlight, boolean firstaid, boolean spare_steering, boolean emergency_tools, boolean reserves, boolean liferaft, boolean detector) {
}

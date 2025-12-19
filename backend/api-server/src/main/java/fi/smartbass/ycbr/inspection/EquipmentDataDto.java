package fi.smartbass.ycbr.inspection;

import jakarta.validation.constraints.PositiveOrZero;

public record EquipmentDataDto(
        boolean markings,
        boolean anchors,
        boolean sea_anchor,
        @PositiveOrZero
        int lines,
        boolean tools,
        boolean paddel,
        boolean hook,
        boolean resque_line,
        boolean fenders,
        boolean ladders,
        boolean defroster,
        boolean toilet,
        boolean gas_system,
        boolean stove,
        boolean flag) {
}

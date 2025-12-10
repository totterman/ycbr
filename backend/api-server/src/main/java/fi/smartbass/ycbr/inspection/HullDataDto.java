package fi.smartbass.ycbr.inspection;

public record HullDataDto(boolean hull, boolean openings,
                          boolean material,
                          boolean keel_rudder,
                          boolean steering,
                          boolean drive_shaft_prop,
                          boolean throughulls,
                          boolean fall_protection,
                          boolean heavy_objects,
                          boolean fresh_water, int lowest_leak) {
}

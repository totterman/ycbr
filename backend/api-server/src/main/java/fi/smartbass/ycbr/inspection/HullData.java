package fi.smartbass.ycbr.inspection;

import org.springframework.data.relational.core.mapping.Table;

@Table(name = "hull_data")
public class HullData {
    private final boolean hull;
    private final boolean openings;
    private final boolean material;
    private final boolean keel_rudder;
    private final boolean steering;
    private final boolean drive_shaft_prop;
    private final boolean throughulls;
    private final boolean fall_protection;
    private final boolean heavy_objects;
    private final boolean fresh_water;
    private final int lowest_leak;

    public HullData(boolean hull, boolean openings, boolean material, boolean keel_rudder, boolean steering, boolean drive_shaft_prop, boolean throughulls, boolean fall_protection, boolean heavy_objects, boolean fresh_water, int lowest_leak) {
        this.hull = hull;
        this.openings = openings;
        this.material = material;
        this.keel_rudder = keel_rudder;
        this.steering = steering;
        this.drive_shaft_prop = drive_shaft_prop;
        this.throughulls = throughulls;
        this.fall_protection = fall_protection;
        this.heavy_objects = heavy_objects;
        this.fresh_water = fresh_water;
        this.lowest_leak = lowest_leak;
    }

    public boolean isHull() {
        return hull;
    }

    public boolean isOpenings() {
        return openings;
    }

    public boolean isMaterial() {
        return material;
    }

    public boolean isKeel_rudder() {
        return keel_rudder;
    }

    public boolean isSteering() {
        return steering;
    }

    public boolean isDrive_shaft_prop() {
        return drive_shaft_prop;
    }

    public boolean isThroughulls() {
        return throughulls;
    }

    public boolean isFall_protection() {
        return fall_protection;
    }

    public boolean isHeavy_objects() {
        return heavy_objects;
    }

    public boolean isFresh_water() {
        return fresh_water;
    }

    public int getLowest_leak() {
        return lowest_leak;
    }

    @Override
    public String toString() {
        return "HullData{" +
                "hull=" + hull +
                ", openings=" + openings +
                ", material=" + material +
                ", keel_rudder=" + keel_rudder +
                ", steering=" + steering +
                ", drive_shaft_prop=" + drive_shaft_prop +
                ", throughulls=" + throughulls +
                ", fall_protection=" + fall_protection +
                ", heavy_objects=" + heavy_objects +
                ", fresh_water=" + fresh_water +
                ", lowest_leak=" + lowest_leak +
                '}';
    }
}

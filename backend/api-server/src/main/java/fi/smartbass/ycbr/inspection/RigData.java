package fi.smartbass.ycbr.inspection;

import org.springframework.data.relational.core.mapping.Table;

@Table(name = "rig_data")
public class RigData {
    private final boolean rig;
    private final boolean sails;
    private final boolean storm_sails;
    private final boolean reefing;

    public RigData(boolean rig, boolean sails, boolean storm_sails, boolean reefing) {
        this.rig = rig;
        this.sails = sails;
        this.storm_sails = storm_sails;
        this.reefing = reefing;
    }

    public boolean isRig() {
        return rig;
    }

    public boolean isSails() {
        return sails;
    }

    public boolean isStorm_sails() {
        return storm_sails;
    }

    public boolean isReefing() {
        return reefing;
    }

    @Override
    public String toString() {
        return "RigData{" +
                "rig=" + rig +
                ", sails=" + sails +
                ", storm_sails=" + storm_sails +
                ", reefing=" + reefing +
                '}';
    }
}

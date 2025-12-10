package fi.smartbass.ycbr.inspection;

import org.springframework.data.relational.core.mapping.Table;

@Table(name = "inspection_data")
public class InspectionData {
    private final HullData hullData;
    private final RigData rigData;
    private final EngineData engineData;

    public InspectionData(HullData hullData, RigData rigData, EngineData engineData) {
        this.hullData = hullData;
        this.rigData = rigData;
        this.engineData = engineData;
    }

    public HullData getHullData() {
        return hullData;
    }

    public RigData getRigData() {
        return rigData;
    }

    public EngineData getEngineData() {
        return engineData;
    }

    @Override
    public String toString() {
        return "InspectionData{" +
                "hullData=" + hullData +
                ", rigData=" + rigData +
                ", engineData=" + engineData +
                '}';
    }
}

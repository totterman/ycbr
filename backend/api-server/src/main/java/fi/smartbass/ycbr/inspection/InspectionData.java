package fi.smartbass.ycbr.inspection;

import org.springframework.data.relational.core.mapping.Table;

@Table(name = "inspection_data")
public class InspectionData {
    private final HullData hullData;
    private final RigData rigData;
    private final EngineData engineData;
    private final EquipmentData equipmentData;
    private final NavigationData navigationData;
    private final SafetyData safetyData;

    public InspectionData(HullData hullData, RigData rigData, EngineData engineData, EquipmentData equipmentData, NavigationData navigationData, SafetyData safetyData) {
        this.hullData = hullData;
        this.rigData = rigData;
        this.engineData = engineData;
        this.equipmentData = equipmentData;
        this.navigationData = navigationData;
        this.safetyData = safetyData;
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

    public EquipmentData getEquipmentData() {
        return equipmentData;
    }

    public NavigationData getNavigationData() {
        return navigationData;
    }

    public SafetyData getSafetyData() {
        return safetyData;
    }

    @Override
    public String toString() {
        return "InspectionData{" +
                "hullData=" + hullData +
                ", rigData=" + rigData +
                ", engineData=" + engineData +
                ", equipmentData=" + equipmentData +
                ", navigationData=" + navigationData +
                ", safetyData=" + safetyData +
                '}';
    }
}

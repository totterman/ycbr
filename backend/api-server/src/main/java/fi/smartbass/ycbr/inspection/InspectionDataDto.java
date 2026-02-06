package fi.smartbass.ycbr.inspection;

public record InspectionDataDto(
        HullDataDto hullData,
        RigDataDto rigData,
        EngineDataDto engineData,
        EquipmentDataDto equipmentData,
        NavigationDataDto navigationData,
        SafetyDataDto safetyData) {}

package fi.smartbass.ycbr.inspection;

public record EngineDataDto(boolean installation,
        boolean controls,
        boolean fuel_system,
        boolean cooling,
        boolean strainer,
        boolean separate_batteries,
        boolean shore_power,
        boolean aggregate) {
}

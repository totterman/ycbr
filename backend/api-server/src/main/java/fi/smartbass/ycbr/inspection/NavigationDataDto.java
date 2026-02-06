package fi.smartbass.ycbr.inspection;

public record NavigationDataDto(
        boolean lights,
        boolean dayshapes,
        boolean horn,
        boolean reflector,
        boolean compass,
        boolean bearing,
        boolean log,
        boolean charts,
        boolean radio,
        boolean satnav,
        boolean radar,
        boolean spotlight,
        boolean vhf,
        boolean hand_vhf,
        boolean documents) {
}

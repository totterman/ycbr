package fi.smartbass.ycbr.inspection;

import org.springframework.data.relational.core.mapping.Table;

@Table(name = "navigation_data")
public class NavigationData {
    public final boolean lights;
    public final boolean dayshapes;
    public final boolean horn;
    public final boolean reflector;
    public final boolean compass;
    public final boolean bearing;
    public final boolean log;
    public final boolean charts;
    public final boolean radio;
    public final boolean satnav;
    public final boolean radar;
    public final boolean spotlight;
    public final boolean vhf;
    public final boolean hand_vhf;
    public final boolean documents;

    public NavigationData(boolean lights, boolean dayshapes, boolean horn, boolean reflector, boolean compass, boolean bearing, boolean log, boolean charts, boolean radio, boolean satnav, boolean radar, boolean spotlight, boolean vhf, boolean hand_vhf, boolean documents) {
        this.lights = lights;
        this.dayshapes = dayshapes;
        this.horn = horn;
        this.reflector = reflector;
        this.compass = compass;
        this.bearing = bearing;
        this.log = log;
        this.charts = charts;
        this.radio = radio;
        this.satnav = satnav;
        this.radar = radar;
        this.spotlight = spotlight;
        this.vhf = vhf;
        this.hand_vhf = hand_vhf;
        this.documents = documents;
    }

    public boolean isLights() {
        return lights;
    }

    public boolean isDayshapes() {
        return dayshapes;
    }

    public boolean isHorn() {
        return horn;
    }

    public boolean isReflector() {
        return reflector;
    }

    public boolean isCompass() {
        return compass;
    }

    public boolean isBearing() {
        return bearing;
    }

    public boolean isLog() {
        return log;
    }

    public boolean isCharts() {
        return charts;
    }

    public boolean isRadio() {
        return radio;
    }

    public boolean isSatnav() {
        return satnav;
    }

    public boolean isRadar() {
        return radar;
    }

    public boolean isSpotlight() {
        return spotlight;
    }

    public boolean isVhf() {
        return vhf;
    }

    public boolean isHand_vhf() {
        return hand_vhf;
    }

    public boolean isDocuments() {
        return documents;
    }

    @Override
    public String toString() {
        return "NavigationData{" +
                "lights=" + lights +
                ", dayshapes=" + dayshapes +
                ", horn=" + horn +
                ", reflector=" + reflector +
                ", compass=" + compass +
                ", bearing=" + bearing +
                ", log=" + log +
                ", charts=" + charts +
                ", radio=" + radio +
                ", satnav=" + satnav +
                ", radar=" + radar +
                ", spotlight=" + spotlight +
                ", vhf=" + vhf +
                ", hand_vhf=" + hand_vhf +
                ", documents=" + documents +
                '}';
    }
}

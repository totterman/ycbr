package fi.smartbass.ycbr.inspection;

import org.springframework.data.relational.core.mapping.Table;

@Table(name = "maritime_data")
public class MaritimeData {
    public boolean lights;
    public boolean dayshapes;
    public boolean horn;
    public boolean reflector;
    public boolean compass;
    public boolean bearing;
    public boolean log;
    public boolean charts;
    public boolean radio;
    public boolean satnav;
    public boolean radar;
    public boolean spotlight;
    public boolean vhf;
    public boolean hand_vhf;
    public boolean documents;

    public MaritimeData(boolean lights, boolean dayshapes, boolean horn, boolean reflector, boolean compass, boolean bearing, boolean log, boolean charts, boolean radio, boolean satnav, boolean radar, boolean spotlight, boolean vhf, boolean hand_vhf, boolean documents) {
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
        return "MaritimeData{" +
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

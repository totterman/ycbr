package fi.smartbass.ycbr.inspection;

import org.springframework.data.relational.core.mapping.Table;

@Table(name = "engine_data")
public class EngineData {
    private final boolean installation;
    private final boolean controls;
    private final boolean fuel_system;
    private final boolean cooling;
    private final boolean strainer;
    private final boolean electrical;
    private final boolean separate_batteries;
    private final boolean shore_power;
    private final boolean aggregate;
    private final boolean reserve;

    public EngineData(boolean installation, boolean controls, boolean fuel_system, boolean cooling, boolean strainer, boolean electrical, boolean separate_batteries, boolean shore_power, boolean aggregate, boolean reserve) {
        this.installation = installation;
        this.controls = controls;
        this.fuel_system = fuel_system;
        this.cooling = cooling;
        this.strainer = strainer;
        this.electrical = electrical;
        this.separate_batteries = separate_batteries;
        this.shore_power = shore_power;
        this.aggregate = aggregate;
        this.reserve = reserve;
    }

    public boolean isInstallation() {
        return installation;
    }

    public boolean isControls() {
        return controls;
    }

    public boolean isFuel_system() {
        return fuel_system;
    }

    public boolean isCooling() {
        return cooling;
    }

    public boolean isStrainer() {
        return strainer;
    }

    public boolean isElectrical() { return electrical; }

    public boolean isSeparate_batteries() {
        return separate_batteries;
    }

    public boolean isShore_power() {
        return shore_power;
    }

    public boolean isAggregate() {
        return aggregate;
    }

    public boolean isReserve() { return reserve; }

    @Override
    public String toString() {
        return "EngineData{" +
                "installation=" + installation +
                ", controls=" + controls +
                ", fuel_system=" + fuel_system +
                ", cooling=" + cooling +
                ", strainer=" + strainer +
                ", electrical=" + electrical +
                ", separate_batteries=" + separate_batteries +
                ", shore_power=" + shore_power +
                ", aggregate=" + aggregate +
                ", reserve=" + reserve +
                '}';
    }
}

package fi.smartbass.ycbr.inspection;

import org.springframework.data.relational.core.mapping.Table;

@Table(name = "safety_data")
public class SafetyData {
    public final boolean buoyancy;
    public final boolean harness;
    public final boolean lifebuoy;
    public final boolean signals_a;
    public final boolean signals_b;
    public final boolean fixed_handpump;
    public final boolean electric_pump;
    public final boolean hand_extinguisher;
    public final boolean fire_blanket;
    public final boolean plugs;
    public final boolean flashlight;
    public final boolean firstaid;
    public final boolean spare_steering;
    public final boolean emergency_tools;
    public final boolean reserves;
    public final boolean liferaft;
    public final boolean detector;

    public SafetyData(boolean buoyancy, boolean harness, boolean lifebuoy, boolean signals_a, boolean signals_b, boolean fixed_handpump, boolean electric_pump, boolean hand_extinguisher, boolean fire_blanket, boolean plugs, boolean flashlight, boolean firstaid, boolean spare_steering, boolean emergency_tools, boolean reserves, boolean liferaft, boolean detector) {
        this.buoyancy = buoyancy;
        this.harness = harness;
        this.lifebuoy = lifebuoy;
        this.signals_a = signals_a;
        this.signals_b = signals_b;
        this.fixed_handpump = fixed_handpump;
        this.electric_pump = electric_pump;
        this.hand_extinguisher = hand_extinguisher;
        this.fire_blanket = fire_blanket;
        this.plugs = plugs;
        this.flashlight = flashlight;
        this.firstaid = firstaid;
        this.spare_steering = spare_steering;
        this.emergency_tools = emergency_tools;
        this.reserves = reserves;
        this.liferaft = liferaft;
        this.detector = detector;
    }

    public boolean isBuoyancy() {
        return buoyancy;
    }

    public boolean getHarness() {
        return harness;
    }

    public boolean getLifebuoy() {
        return lifebuoy;
    }

    public boolean isSignals_a() {
        return signals_a;
    }

    public boolean isSignals_b() {
        return signals_b;
    }

    public boolean getFixed_handpump() {
        return fixed_handpump;
    }

    public boolean isElectric_pump() {
        return electric_pump;
    }

    public boolean getHand_extinguisher() {
        return hand_extinguisher;
    }

    public boolean isFire_blanket() {
        return fire_blanket;
    }

    public boolean isPlugs() {
        return plugs;
    }

    public boolean getFlashlight() {
        return flashlight;
    }

    public boolean isFirstaid() {
        return firstaid;
    }

    public boolean isSpare_steering() {
        return spare_steering;
    }

    public boolean isEmergency_tools() {
        return emergency_tools;
    }

    public boolean isReserves() {
        return reserves;
    }

    public boolean isLiferaft() {
        return liferaft;
    }

    public boolean isDetector() {
        return detector;
    }

    @Override
    public String toString() {
        return "SafetyData{" +
                "buoyancy=" + buoyancy +
                ", harness=" + harness +
                ", lifebuoy=" + lifebuoy +
                ", signals_a=" + signals_a +
                ", signals_b=" + signals_b +
                ", fixed_handpump=" + fixed_handpump +
                ", electric_pump=" + electric_pump +
                ", hand_extinguisher=" + hand_extinguisher +
                ", fire_blanket=" + fire_blanket +
                ", plugs=" + plugs +
                ", flashlight=" + flashlight +
                ", firstaid=" + firstaid +
                ", spare_steering=" + spare_steering +
                ", emergency_tools=" + emergency_tools +
                ", reserves=" + reserves +
                ", liferaft=" + liferaft +
                ", detector=" + detector +
                '}';
    }
}

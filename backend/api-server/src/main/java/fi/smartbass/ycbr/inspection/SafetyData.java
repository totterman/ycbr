package fi.smartbass.ycbr.inspection;

import org.springframework.data.relational.core.mapping.Table;

@Table(name = "safety_data")
public class SafetyData {
    public boolean buoyancy;
    public int harness;
    public int lifebuoy;
    public boolean signals_a;
    public boolean signals_b;
    public int fixed_handpump;
    public boolean electric_pump;
    public int hand_extinguisher;
    public boolean fire_blanket;
    public boolean plugs;
    public int flashlight;
    public boolean firstaid;
    public boolean spare_steering;
    public boolean emergency_tools;
    public boolean reserves;
    public boolean liferaft;
    public boolean detector;

    public SafetyData(boolean buoyancy, int harness, int lifebuoy, boolean signals_a, boolean signals_b, int fixed_handpump, boolean electric_pump, int hand_extinguisher, boolean fire_blanket, boolean plugs, int flashlight, boolean firstaid, boolean spare_steering, boolean emergency_tools, boolean reserves, boolean liferaft, boolean detector) {
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

    public int getHarness() {
        return harness;
    }

    public int getLifebuoy() {
        return lifebuoy;
    }

    public boolean isSignals_a() {
        return signals_a;
    }

    public boolean isSignals_b() {
        return signals_b;
    }

    public int getFixed_handpump() {
        return fixed_handpump;
    }

    public boolean isElectric_pump() {
        return electric_pump;
    }

    public int getHand_extinguisher() {
        return hand_extinguisher;
    }

    public boolean isFire_blanket() {
        return fire_blanket;
    }

    public boolean isPlugs() {
        return plugs;
    }

    public int getFlashlight() {
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

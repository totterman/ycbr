package fi.smartbass.ycbr.inspection;

public class EquipmentData {
    public final boolean markings;
    public final boolean anchors;
    public final boolean sea_anchor;
    public final int lines;
    public final boolean tools;
    public final boolean paddel;
    public final boolean hook;
    public final boolean resque_line;
    public final boolean fenders;
    public final boolean ladders;
    public final boolean defroster;
    public final boolean toilet;
    public final boolean gas_system;
    public final boolean stove;
    public final boolean flag;

    public EquipmentData(boolean markings, boolean anchors, boolean sea_anchor, int lines, boolean tools, boolean paddel, boolean hook, boolean resque_line, boolean fenders, boolean ladders, boolean defroster, boolean toilet, boolean gas_system, boolean stove, boolean flag) {
        this.markings = markings;
        this.anchors = anchors;
        this.sea_anchor = sea_anchor;
        this.lines = lines;
        this.tools = tools;
        this.paddel = paddel;
        this.hook = hook;
        this.resque_line = resque_line;
        this.fenders = fenders;
        this.ladders = ladders;
        this.defroster = defroster;
        this.toilet = toilet;
        this.gas_system = gas_system;
        this.stove = stove;
        this.flag = flag;
    }

    public boolean isMarkings() {
        return markings;
    }

    public boolean isAnchors() {
        return anchors;
    }

    public boolean isSea_anchor() {
        return sea_anchor;
    }

    public int getLines() {
        return lines;
    }

    public boolean isTools() {
        return tools;
    }

    public boolean isPaddel() {
        return paddel;
    }

    public boolean isHook() {
        return hook;
    }

    public boolean isResque_line() {
        return resque_line;
    }

    public boolean isFenders() {
        return fenders;
    }

    public boolean isLadders() {
        return ladders;
    }

    public boolean isDefroster() {
        return defroster;
    }

    public boolean isToilet() {
        return toilet;
    }

    public boolean isGas_system() {
        return gas_system;
    }

    public boolean isStove() {
        return stove;
    }

    public boolean isFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return "EquipmentData{" +
                "markings=" + markings +
                ", anchors=" + anchors +
                ", sea_anchor=" + sea_anchor +
                ", lines=" + lines +
                ", tools=" + tools +
                ", paddel=" + paddel +
                ", hook=" + hook +
                ", resque_line=" + resque_line +
                ", fenders=" + fenders +
                ", ladders=" + ladders +
                ", defroster=" + defroster +
                ", toilet=" + toilet +
                ", gas_system=" + gas_system +
                ", stove=" + stove +
                ", flag=" + flag +
                '}';
    }
}

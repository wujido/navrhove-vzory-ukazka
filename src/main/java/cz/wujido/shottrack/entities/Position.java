package cz.wujido.shottrack.entities;

import java.io.Serializable;

public enum Position implements Serializable {
    PRONE(45),
    STANDING(115);

    public final int diameter;

    Position(int diameter) {
        this.diameter = diameter;
    }
}

package cz.wujido.shottrack.entities;

import java.io.Serializable;

public class Shot implements Serializable {
    private final int sectors = 12;
    private final int anglePerSector = 360 / sectors;
    private final int sectorOnXAxis = 3;
    private final int sector;
    private final boolean isLucky;
    private final boolean isHit;
    private final boolean isCenter;


    /**
     * Default constructor
     *
     * @param distance       Distance from center of target
     * @param angle          Angle of polar coordinates (counter clockwise from horizontal axis)
     * @param isHit          Indicates if target was hit
     * @param targetDiameter Diameter of target
     */
    public Shot(final int distance, final int angle, boolean isHit, int targetDiameter) {
        int radius = targetDiameter / 2;

        var sector = Math.floorMod(Math.round((float) sectorOnXAxis - ((float) angle / anglePerSector)), sectors);
        if (sector == 0) sector = sectors;
        this.sector = sector;

        isLucky = isHit && (distance >= radius - (radius * 0.2));

        this.isHit = isHit;
        isCenter = distance <= targetDiameter * 0.2;
    }

    /**
     * Return sector of target hit by the bullet
     * Sector represent even range around 'clock hours'
     *
     * @return Sector in range 1-12
     */
    public int getSector() {
        return sector;
    }

    /**
     * Indicates if shot was outside of target but still hit
     *
     * @return indicator
     */
    public boolean isLucky() {
        return isLucky;
    }

    /**
     * Indicates if shot was hit
     *
     * @return indicator
     */
    public boolean isHit() {
        return isHit;
    }

    /**
     * Indicates if shot was close to center
     *
     * @return indicator
     */
    public boolean isCenter() {
        return isCenter;
    }
}

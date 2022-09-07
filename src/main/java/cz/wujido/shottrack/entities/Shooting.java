package cz.wujido.shottrack.entities;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Shooting implements Serializable {

    private Position position;
    private int missCount;
    private int luckyShotCount;
    private int centerShotCount;
    private LocalDateTime startTime;
    private ArrayList<LocalDateTime> shotTimes;
    private ArrayList<Shot> shots;

    private Shooting() {
    }

    private Shooting(Position position) {
        this.position = position;
        shotTimes = new ArrayList<>();
        shots = new ArrayList<>();
    }

    /**
     * Factory for creating prone shooting
     *
     * @return new instance of Shooting object, configured as prone position
     */
    public static Shooting CreateProneShooting() {
        return new Shooting(Position.PRONE);
    }


    /**
     * Factory for creating standing shooting
     *
     * @return new instance of Shooting object, configured as standing position
     */
    public static Shooting CreateStandingShooting() {
        return new Shooting(Position.STANDING);
    }

    /**
     * Add shot to shooting object
     *
     * @param shot instance of shot
     */
    public void addShot(Shot shot) {
        addShot(shot, null);
    }

    /**
     * Add shot to shooting object and set time
     *
     * @param shot     instance of shot
     * @param shotTime time of added shot
     */
    public void addShot(Shot shot, LocalDateTime shotTime) {
        if (!shot.isHit()) missCount++;
        if (shot.isLucky()) luckyShotCount++;
        if (shot.isCenter()) centerShotCount++;
        shotTimes.add(shotTime);
        shots.add(shot);
    }

    /**
     * Set start time of shooting
     *
     * @param startTime starting time
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Return time when shooting starts
     *
     * @return starting time
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Return count of shots inside shooting
     *
     * @return count of shots
     */
    public int getShotCount() {
        return shotTimes.size();
    }

    /**
     * Return count of center shots inside shooting
     *
     * @return count of center shots
     */
    public int getCenterShotCount() {
        return centerShotCount;
    }

    /**
     * Return count of lucky shots inside shooting
     *
     * @return count of lucky shots
     */
    public int getLuckyShotCount() {
        return luckyShotCount;
    }

    /**
     * Return count of missed shots inside shooting
     *
     * @return count of missed shots
     */
    public int getMissCount() {
        return missCount;
    }

    /**
     * Return position of shooting
     *
     * @return position of shooting
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Return duration from start to `ith` shot
     *
     * @param i index (from 1) of shot
     * @return duration from start
     */
    public Duration getDurationToShot(int i) {
        if (i > shotTimes.size() || startTime == null) return Duration.ZERO;
        return Duration.between(startTime, shotTimes.get(i - 1));
    }

    /**
     * Return duration between two shots
     *
     * @param from index of first shot
     * @param to   index of second shot
     * @return duration between shots
     */
    public Duration getDurationBetweenShots(int from, int to) {
        if (from >= to)
            throw new IllegalArgumentException("From must be lower than to");

        LocalDateTime firstShotTime = from == 0 ? startTime : shotTimes.get(from - 1);
        LocalDateTime secondShotTime = shotTimes.get(to - 1);

        if (firstShotTime == null || secondShotTime == null) return Duration.ZERO;
        return Duration.between(firstShotTime, secondShotTime);
    }

    /**
     * Return average time span between shots
     *
     * @return average time span
     */
    public Duration getAverageShotSpan() {
        if (shotTimes.size() < 2) return Duration.ZERO;
        var duration = java.time.Duration.ofMillis(0);

        for (int i = 1; i < shotTimes.size(); i++) {
            var timeSpan = getDurationBetweenShots(i, i + 1);
            duration = duration.plus(timeSpan);
        }

        return Duration.ofNanos(duration.toNanos() / (shotTimes.size() - 1));
    }

    public ArrayList<Shot> getShots() {
        return shots;
    }
}

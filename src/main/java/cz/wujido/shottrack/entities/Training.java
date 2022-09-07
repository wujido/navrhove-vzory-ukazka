package cz.wujido.shottrack.entities;

import org.javatuples.Pair;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Training implements Serializable {
    private int shootingCount;
    private int proneShootingCount;
    private int standingShootingCount;
    private int totalShotCount;
    private int proneShotCount;
    private int standingShotCount;
    private int totalMissCount;
    private int proneMissCount;
    private int standingMissCount;
    private final ArrayList<Pair<LocalDateTime, Shooting>> shootings = new ArrayList<>();

    public void addShooting(Shooting shooting) {
        addShooting(shooting, LocalDateTime.now());
    }

    public void addShooting(Shooting shooting, LocalDateTime dateTime) {
        if (shooting.getPosition() == Position.PRONE) incrementProneCounters(shooting);
        else incrementStandingCounters(shooting);

        incrementTotalCounters(shooting);
        shootings.add(new Pair<>(dateTime, shooting));
    }

    private void incrementTotalCounters(Shooting shooting) {
        shootingCount++;
        totalShotCount += shooting.getShotCount();
        totalMissCount += shooting.getMissCount();
    }

    private void incrementProneCounters(Shooting shooting) {
        proneShootingCount++;
        proneShotCount += shooting.getShotCount();
        proneMissCount += shooting.getMissCount();
    }

    private void incrementStandingCounters(Shooting shooting) {
        standingShootingCount++;
        standingShotCount += shooting.getShotCount();
        standingMissCount += shooting.getMissCount();
    }


    public int getShootingCount() {
        return shootingCount;
    }

    public int getProneShootingCount() {
        return proneShootingCount;
    }

    public int getStandingShootingCount() {
        return standingShootingCount;
    }

    public int getTotalShotCount() {
        return totalShotCount;
    }

    public int getProneShotCount() {
        return proneShotCount;
    }

    public int getStandingShotCount() {
        return standingShotCount;
    }

    public int getTotalMissCount() {
        return totalMissCount;
    }

    public int getProneMissCount() {
        return proneMissCount;
    }

    public int getStandingMissCount() {
        return standingMissCount;
    }

    public ArrayList<Pair<LocalDateTime, Shooting>> getShootings() {
        return shootings;
    }
}

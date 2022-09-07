package cz.wujido.shottrack.entities;

import org.javatuples.Pair;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ShootingRepository implements Serializable {
    private final Training soloShootings = new Training();
    private final Training allShootings = new Training();
    private final ArrayList<Pair<LocalDateTime, Training>> trainings = new ArrayList<>();


    public void addShooting(Shooting shooting) {
        soloShootings.addShooting(shooting);
        allShootings.addShooting(shooting);
    }

    public void addTraining(Training training) {
        trainings.add(new Pair<>(LocalDateTime.now(), training));
        for (Pair<LocalDateTime, Shooting> shooting : training.getShootings()) {
            allShootings.addShooting(shooting.getValue1(), shooting.getValue0());
        }
    }


    public Training getSoloShootings() {
        return soloShootings;
    }

    public Training getAllShootings() {
        return allShootings;
    }

    public ArrayList<Pair<LocalDateTime, Training>> getTrainings() {
        return trainings;
    }
}

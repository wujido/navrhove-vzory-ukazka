package cz.wujido.shottrack;

import cz.wujido.shottrack.entities.*;

import java.io.Serializable;

public class ShotTrackModel implements Serializable {
    private transient Notification notification;
    private transient boolean hasNotification;

    public boolean hasNotification() {
        return hasNotification;
    }

    public Notification getNotification() {
        hasNotification = false;
        return notification;
    }

    public void setNotification(String content, Notification.Type type) {
        hasNotification = true;
        this.notification = new Notification(content, type);
    }

    private Shooting newShooting;
    private final String shootingWasNotInitializedExceptionMessage = "New shooting must be initialized before access (call `initializeNewShooting`)";

    public void initializeNewShooting(Position position) {
        newShooting = switch (position) {
            case PRONE -> Shooting.CreateProneShooting();
            case STANDING -> Shooting.CreateStandingShooting();
        };
    }

    public Shooting getNewShooting() {
        if (newShooting == null)
            throw new IllegalStateException(shootingWasNotInitializedExceptionMessage);

        return newShooting;
    }

    public void saveNewShooting() {
        if (newShooting == null)
            throw new IllegalStateException(shootingWasNotInitializedExceptionMessage);

        if (isNewTrainingInitialized())
            newTraining.addShooting(newShooting);
        else repository.addShooting(newShooting);

        discardNewShooting();
    }

    public void discardNewShooting() {
        newShooting = null;
    }

    private Training newTraining;
    private final String trainingWasNotInitializedExceptionMessage = "New training must be initialized before access (call `initializeNewTraining`";

    public void initializeNewTraining() {
        newTraining = new Training();
    }

    public Training getNewTraining() {
        if (newTraining == null)
            throw new IllegalStateException(trainingWasNotInitializedExceptionMessage);

        return newTraining;
    }

    public boolean isNewTrainingInitialized() {
        return newTraining != null;
    }

    public void saveNewTraining() {
        if (newTraining == null)
            throw new IllegalStateException(trainingWasNotInitializedExceptionMessage);

        repository.addTraining(newTraining);
        discardNewTraining();
    }

    public void discardNewTraining() {
        newTraining = null;
    }


    private final ShootingRepository repository = new ShootingRepository();

    public ShootingRepository getShootingRepository() {
        return repository;
    }
}

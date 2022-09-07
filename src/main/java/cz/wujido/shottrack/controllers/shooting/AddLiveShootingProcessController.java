package cz.wujido.shottrack.controllers.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.entities.Notification;
import cz.wujido.shottrack.entities.Shooting;
import cz.wujido.shottrack.entities.Shot;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.shooting.AddLiveShootingProcessView;
import cz.wujido.shottrack.views.shooting.AddLiveShotView;
import org.javatuples.Pair;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AddLiveShootingProcessController extends Controller {
    private final ArrayList<Pair<LocalDateTime, Boolean>> shots = new ArrayList<>();
    private Phase actualPhase = Phase.TIMES;
    private ResultPhase actualResultPhase = ResultPhase.DISTANCE;
    private int distance;
    private int resultIndex = 0;

    public AddLiveShootingProcessController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
        model.getNewShooting().setStartTime(LocalDateTime.now());
    }

    @Override
    protected View initializeView() {
        return new AddLiveShootingProcessView(output, model, 1);
    }

    @Override
    protected void executeAction(String action) {
        switch (actualPhase) {
            case TIMES -> executeTimes(action);
            case RESULTS -> executeResults(action);
        }
    }

    private void executeTimes(String action) {
        if ("f".equals(action)) {
            if (shots.size() == 0) {
                setNotification("Zaznamenejte alespoň jeden výstřel", Notification.Type.ERR);
            }
            actualPhase = Phase.RESULTS;
            setResultScreen();
            return;
        }

        switch (action) {
            case "1" -> addShooting(true);
            case "2" -> addShooting(false);
            default -> invalidAction(action);
        }
    }

    private void addShooting(boolean hit) {
        shots.add(new Pair<>(LocalDateTime.now(), hit));
        setView(new AddLiveShootingProcessView(output, model, shots.size() + 1));
    }

    private void executeResults(String action) {
        switch (actualResultPhase) {
            case DISTANCE -> executeDistance(action);
            case ANGLE -> executeAngle(action);
        }

    }

    private void setResultScreen() {
        var view = new AddLiveShotView(output, model, resultIndex + 1);

        if (actualResultPhase == ResultPhase.ANGLE) {
            view.displayAnswer(String.valueOf(distance));
            view.displayAngleQuestion();
        }

        setView(view);
    }

    private void executeDistance(String action) {
        int answer;
        String errMessage = "Zadaná hodnot musí být nezáporné číslo";
        try {
            answer = Integer.parseInt(action);
        } catch (NumberFormatException e) {
            setNotification(errMessage, Notification.Type.ERR);
            return;
        }

        if (answer < 0) {
            setNotification(errMessage, Notification.Type.ERR);
            return;
        }

        distance = answer;
        actualResultPhase = ResultPhase.ANGLE;
        setResultScreen();
    }


    private void executeAngle(String action) {
        int answer;
        String errMessage = "Zadaná hodnota musí být číslo z intervalu 0 - 360";
        try {
            answer = Integer.parseInt(action);
        } catch (NumberFormatException e) {
            setNotification(errMessage, Notification.Type.ERR);
            return;
        }

        if (answer < 0 || answer > 360) {
            setNotification(errMessage, Notification.Type.ERR);
            return;
        }

        Shooting shooting = model.getNewShooting();
        Pair<LocalDateTime, Boolean> shot = shots.get(resultIndex++);
        var newShot = new Shot(distance, answer, shot.getValue1(), shooting.getPosition().diameter);
        shooting.addShot(newShot, shot.getValue0());

        if (resultIndex == shots.size()) {
            saveShooting();
            saveModel();
            setBackRoute();
            return;
        }

        actualResultPhase = ResultPhase.DISTANCE;
        setResultScreen();
    }

    private void saveShooting() {
        model.saveNewShooting();
        setNotification("Položka byla úspěšně uložena", Notification.Type.SUCCESS, true);
    }

    private void setBackRoute() {
        if (model.isNewTrainingInitialized())
            setRoute("shooting/training");
        else
            setRoute("shooting/single");
    }

    private enum Phase {
        TIMES,
        RESULTS
    }

    private enum ResultPhase {
        DISTANCE,
        ANGLE
    }
}

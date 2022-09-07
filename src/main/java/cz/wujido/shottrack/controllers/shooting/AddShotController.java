package cz.wujido.shottrack.controllers.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.entities.Notification;
import cz.wujido.shottrack.entities.Shooting;
import cz.wujido.shottrack.entities.Shot;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.shooting.AddShotView;

import java.io.InputStream;
import java.io.PrintStream;

public class AddShotController extends Controller {
    private Phase actualPhase = Phase.HIT;
    private boolean isHit;
    private int distance;

    public AddShotController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    @Override
    protected View initializeView() {
        return new AddShotView(output, model);
    }

    @Override
    protected void executeAction(String action) {
        switch (actualPhase) {
            case HIT -> executeHit(action);
            case DISTANCE -> executeDistance(action);
            case ANGLE -> executeAngle(action);
        }
    }


    private void executeHit(String action) {
        switch (action) {
            case "y" -> isHit = true;
            case "n" -> isHit = false;
            default -> {
                invalidAction(action);
                return;
            }
        }

        actualPhase = Phase.DISTANCE;
        setView(getNextView());
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
        actualPhase = Phase.ANGLE;
        setView(getNextView());
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
        var newShot = new Shot(distance, answer, isHit, shooting.getPosition().diameter);
        shooting.addShot(newShot);
        setRoute("shooting/add/old");
    }

    private View getNextView() {
        var newView = new AddShotView(output, model);

        if (actualPhase == Phase.HIT) return newView;

        newView.displayAnswer(isHit ? "y" : "n");
        newView.displayDistanceQuestion();

        if (actualPhase == Phase.DISTANCE) return newView;
        newView.displayAnswer(String.valueOf(distance));
        newView.displayAngleQuestion();

        return newView;
    }

    private enum Phase {
        HIT,
        DISTANCE,
        ANGLE
    }
}

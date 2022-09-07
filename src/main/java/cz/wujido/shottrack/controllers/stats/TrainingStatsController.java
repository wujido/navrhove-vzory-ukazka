package cz.wujido.shottrack.controllers.stats;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.entities.Notification;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.stats.TrainingsLobbyView;

import java.io.InputStream;
import java.io.PrintStream;

public class TrainingStatsController extends Controller {
    public TrainingStatsController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    @Override
    protected View initializeView() {
        return new TrainingsLobbyView(output, model);
    }

    @Override
    protected void executeAction(String action) {
        if ("b".equals(action)) {
            setRoute("stats");
            return;
        }

        try {
            var index = Integer.parseInt(action);
            var trainings = model.getShootingRepository().getTrainings();

            if (index > trainings.size() || index <= 0) {
                setNotification("Zadané číslo musí být mezi 1 - " + trainings.size(), Notification.Type.ERR);
                return;
            }

            setRoute("stats/training/detail", trainings.size() - index);
            return;
        } catch (NumberFormatException ignored) {
        }

        invalidAction(action);
    }
}

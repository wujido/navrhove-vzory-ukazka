package cz.wujido.shottrack.controllers.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.entities.Notification;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.shooting.PositionSelectionView;
import cz.wujido.shottrack.views.shooting.TrainingShootingView;

import java.io.InputStream;
import java.io.PrintStream;

public class TrainingShootingController extends ShootingModeController {
    public TrainingShootingController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    @Override
    protected View initializeView() {
        return new TrainingShootingView(output, model);
    }

    @Override
    protected void executeAction(String action) {
        if (nextRoute != null) {
            executePositionChoose(action);
            return;
        }

        switch (action) {
            case "1" -> nextRoute = "shooting/add/old";
            case "2" -> nextRoute = "shooting/add/live";
            case "3" -> setRoute("shooting/training/list");
            case "4" -> {
                if (model.getNewTraining().getShootingCount() == 0) {
                    setNotification("Před uložením přidejte alespoň jednu položku", Notification.Type.ERR);
                    return;
                }
                model.saveNewTraining();
                saveModel();
                setNotification("Trénink byl úspěšně uložen", Notification.Type.SUCCESS, true);
                setRoute("home");
            }
            case "q" -> {
                if (model.getNewTraining().getShootingCount() > 0)
                    confirmAction("Opravdu chcete zahodit aktuální trénink?", "qsuc");
                else setRoute("home");
            }
            case "qsuc" -> {
                model.discardNewTraining();
                setRoute("home");
            }
            default -> invalidAction(action);
        }

        // Continue only if one of `add` options was selected
        if (nextRoute == null) return;

        setView(new PositionSelectionView(output, model));
    }
}

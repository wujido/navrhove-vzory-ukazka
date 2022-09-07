package cz.wujido.shottrack.controllers.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.entities.Notification;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.shooting.AddOldShootingView;

import java.io.InputStream;
import java.io.PrintStream;

public class AddOldShootingController extends Controller {
    public AddOldShootingController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    @Override
    protected View initializeView() {
        return new AddOldShootingView(output, model);
    }

    @Override
    protected void executeAction(String action) {
        switch (action) {
            case "1" -> setRoute("shooting/add/shot");
            case "2" -> setRoute("shooting/view/created");
            case "3" -> {
                if (model.getNewShooting().getShotCount() == 0) {
                    setNotification("Před uložením přidejte alespoň jednu ránu.", Notification.Type.ERR);
                    return;
                }

                setBackRoute();
                model.saveNewShooting();
                saveModel();
                setNotification("Položka byla úspěšně uložena", Notification.Type.SUCCESS, true);
            }
            case "q" -> {
                if (model.getNewShooting().getShotCount() > 0)
                    confirmAction("Opravdu chce zahodit aktuální položku?", "qsuc");
                else setBackRoute();
            }
            case "qsuc" -> {
                setBackRoute();
                model.discardNewShooting();
            }
            default -> invalidAction(action);
        }
    }

    private void setBackRoute() {
        if (model.isNewTrainingInitialized())
            setRoute("shooting/training");
        else
            setRoute("shooting/single");
    }
}

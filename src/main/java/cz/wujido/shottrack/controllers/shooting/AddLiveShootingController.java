package cz.wujido.shottrack.controllers.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.shooting.AddLiveShootingView;

import java.io.InputStream;
import java.io.PrintStream;

public class AddLiveShootingController extends Controller {
    public AddLiveShootingController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    @Override
    protected View initializeView() {
        return new AddLiveShootingView(output, model);
    }

    @Override
    protected void executeAction(String action) {
        switch (action) {
            case "1" -> setRoute("shooting/add/live/process");
            case "b" -> setBackRoute();
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

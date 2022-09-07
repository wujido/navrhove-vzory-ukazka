package cz.wujido.shottrack.controllers.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.shooting.ShootingLobbyView;

import java.io.InputStream;
import java.io.PrintStream;

public class ShootingLobbyController extends Controller {
    public ShootingLobbyController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    public View initializeView() {
        return new ShootingLobbyView(output, model);
    }

    @Override
    protected void executeAction(String action) {

        switch (action) {
            case "1" -> setRoute("shooting/single");
            case "2" -> {
                model.initializeNewTraining();
                setRoute("shooting/training");
            }
            default -> invalidAction(action);
        }
    }

}
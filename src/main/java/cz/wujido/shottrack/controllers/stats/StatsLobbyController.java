package cz.wujido.shottrack.controllers.stats;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.stats.StatsLobbyView;

import java.io.InputStream;
import java.io.PrintStream;

public class StatsLobbyController extends Controller {
    public StatsLobbyController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    @Override
    protected View initializeView() {
        return new StatsLobbyView(output, model);
    }

    @Override
    protected void executeAction(String action) {
        switch (action) {
            case "1" -> setRoute("stats/all");
            case "2" -> setRoute("stats/training");
            case "3" -> setRoute("stats/solo");
            case "m" -> setRoute("home");
            default -> invalidAction(action);
        }
    }
}

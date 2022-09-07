package cz.wujido.shottrack.controllers;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.HomeView;
import cz.wujido.shottrack.views.View;

import java.io.InputStream;
import java.io.PrintStream;

public class HomeController extends Controller {

    public HomeController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    public View initializeView() {
        return new HomeView(output, model);
    }

    @Override
    protected void executeAction(String action) {
        switch (action) {
            case "1" -> setRoute("shooting");
            case "2" -> setRoute("stats");
            case "q" -> {
                saveModel();
                die();
            }
            default -> invalidAction(action);
        }
    }
}

package cz.wujido.shottrack.controllers.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.shooting.PositionSelectionView;
import cz.wujido.shottrack.views.shooting.SingleShootingView;

import java.io.InputStream;
import java.io.PrintStream;

public class SingleShootingController extends ShootingModeController {
    public SingleShootingController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    @Override
    protected View initializeView() {
        return new SingleShootingView(output, model);
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
            case "m" -> setRoute("home");
            default -> invalidAction(action);
        }

        // Continue only if one of `add` options was selected
        if (nextRoute == null) return;

        setView(new PositionSelectionView(output, model));
    }
}

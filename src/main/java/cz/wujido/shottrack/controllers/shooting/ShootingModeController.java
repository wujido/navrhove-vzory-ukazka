package cz.wujido.shottrack.controllers.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.entities.Position;

import java.io.InputStream;
import java.io.PrintStream;

public abstract class ShootingModeController extends Controller {
    protected String nextRoute;

    public ShootingModeController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    protected final void executePositionChoose(String action) {
        Position position = null;
        switch (action) {
            case "1" -> position = Position.PRONE;
            case "2" -> position = Position.STANDING;
            default -> invalidAction(action);
        }

        // Continue only if correct position was selected
        if (position == null) return;

        model.initializeNewShooting(position);
        setRoute(nextRoute);
    }
}

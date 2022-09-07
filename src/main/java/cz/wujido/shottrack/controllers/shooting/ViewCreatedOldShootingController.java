package cz.wujido.shottrack.controllers.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.views.ShowShootingView;
import cz.wujido.shottrack.views.View;

import java.io.InputStream;
import java.io.PrintStream;

public class ViewCreatedOldShootingController extends Controller {
    public ViewCreatedOldShootingController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    @Override
    protected View initializeView() {
        return new ShowShootingView(output, model, model.getNewShooting());
    }

    @Override
    protected void executeAction(String action) {
        if ("b".equals(action)) {
            setRoute("shooting/add/old");
            return;
        }

        invalidAction(action);
    }
}

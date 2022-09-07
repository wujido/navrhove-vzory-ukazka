package cz.wujido.shottrack.controllers.stats;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.stats.AllStatsView;

import java.io.InputStream;
import java.io.PrintStream;

public class AllStatsController extends Controller {
    public AllStatsController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    @Override
    protected View initializeView() {
        return new AllStatsView(output, model, model.getShootingRepository().getAllShootings());
    }

    @Override
    protected void executeAction(String action) {
        if ("b".equals(action)) {
            setRoute("stats");
            return;
        }

        invalidAction(action);
    }
}

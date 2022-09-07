package cz.wujido.shottrack.controllers.stats;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.views.ShowShootingView;
import cz.wujido.shottrack.views.View;

import java.io.InputStream;
import java.io.PrintStream;

public class ShootingDetailController extends Controller {
    private int shootingIndex;

    public ShootingDetailController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    @Override
    protected void bindParams(int[] params) {
        shootingIndex = params[0];
    }

    @Override
    protected View initializeView() {
        var shooting = model.getShootingRepository().getSoloShootings().getShootings().get(shootingIndex);
        return new ShowShootingView(output, model, shooting.getValue1());
    }

    @Override
    protected void executeAction(String action) {
        if ("b".equals(action)) {
            setRoute("stats/solo");
            return;
        }

        invalidAction(action);
    }
}

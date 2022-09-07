package cz.wujido.shottrack.controllers.stats;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.entities.Notification;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.stats.ShootingLobbyView;

import java.io.InputStream;
import java.io.PrintStream;

public class ShootingStatsController extends Controller {
    public ShootingStatsController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    @Override
    protected View initializeView() {
        return new ShootingLobbyView(output, model);
    }

    @Override
    protected void executeAction(String action) {
        if ("b".equals(action)) {
            setRoute("stats");
            return;
        }

        try {
            var index = Integer.parseInt(action);
            var shootings = model.getShootingRepository().getSoloShootings().getShootings();

            if (index > shootings.size() || index <= 0) {
                setNotification("Zadané číslo musí být mezi 1 - " + shootings.size(), Notification.Type.ERR);
                return;
            }

            setRoute("stats/solo/detail", shootings.size() - 1);
            return;
        } catch (NumberFormatException ignored) {
        }

        invalidAction(action);
    }
}

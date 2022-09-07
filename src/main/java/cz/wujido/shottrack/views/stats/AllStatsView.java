package cz.wujido.shottrack.views.stats;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.entities.Training;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.appComponents.TrainingStats;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Space;
import cz.wujido.shottrack.views.components.Text;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;

public class AllStatsView extends View {
    public AllStatsView(PrintStream output, ShotTrackModel model, Training training) {
        super(output, model);

        registerComponent(new Title("Dlouhodobá statistika"));

        if (training.getShootingCount() == 0) {
            var t = new Text();
            t.addLine("Zatím jste neuložily žádnou položku");
            registerComponent(t);
            registerMenu();
            return;
        }

        registerComponent(new Space());

        registerAppComponent(new TrainingStats(training));

        registerMenu();
    }

    private void registerMenu() {
        registerComponent(new Space());
        var menu = new Menu();
        menu.addOption("b", "Zpět");
        registerComponent(menu);
    }
}

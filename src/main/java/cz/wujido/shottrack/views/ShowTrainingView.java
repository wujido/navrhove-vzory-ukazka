package cz.wujido.shottrack.views;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.entities.Training;
import cz.wujido.shottrack.views.appComponents.TrainingStats;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Space;
import cz.wujido.shottrack.views.components.Text;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;

public class ShowTrainingView extends View {
    public ShowTrainingView(PrintStream output, ShotTrackModel model, Training training) {
        super(output, model);

        registerComponent(new Title("Detail tréninku"));

        if (training.getShootingCount() == 0) {
            var t = new Text();
            t.addLine("Tréninik  neobsahuje žádné položky");
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

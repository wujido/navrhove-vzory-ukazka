package cz.wujido.shottrack.views.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;

public class TrainingShootingView extends View {
    public TrainingShootingView(PrintStream output, ShotTrackModel model) {
        super(output, model);

        var title = new Title("Zaznamenávání tréninku");
        registerComponent(title);

        var menu = new Menu();
        menu.addOption("Zadat položku zpětně");
        menu.addOption("Zaznamenat položku živě");
        menu.addOption("Prohlédnout položky (" + model.getNewTraining().getShootingCount() + ")");
        menu.addOption("Uložit trénink");
        menu.addOption("q", "Zahodit trénink");
        registerComponent(menu);
    }
}

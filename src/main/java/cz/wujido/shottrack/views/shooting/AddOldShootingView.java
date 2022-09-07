package cz.wujido.shottrack.views.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;

public class AddOldShootingView extends View {
    public AddOldShootingView(PrintStream output, ShotTrackModel model) {
        super(output, model);

        var title = new Title("Zpětně uložit položku");
        registerComponent(title);

        var menu = new Menu();
        menu.addOption("Přidat ránu");
        menu.addOption("Zobrazit rány (" + model.getNewShooting().getShotCount() + ")");
        menu.addOption("Uložit položku");
        menu.addOption("q", "Zahodit položku");
        registerComponent(menu);
    }
}

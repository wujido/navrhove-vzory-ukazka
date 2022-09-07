package cz.wujido.shottrack.views.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;

public class SingleShootingView extends View {
    public SingleShootingView(PrintStream output, ShotTrackModel model) {
        super(output, model);

        var title = new Title("Zaznamenat samostatnou položku");
        registerComponent(title);

        var menu = new Menu();
        menu.addOption("Zadat položku zpětně");
        menu.addOption("Zaznamenat položku živě");
        menu.addOption("m", "Zpět do menu");
        registerComponent(menu);
    }
}

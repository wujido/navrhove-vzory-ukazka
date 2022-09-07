package cz.wujido.shottrack.views;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;

public class HomeView extends View {
    public HomeView(PrintStream output, ShotTrackModel model) {
        super(output, model);

        var title = new Title("Vítejte v aplikaci Shottrack!");
        registerComponent(title);

        var menu = new Menu();
        menu.addOption("Zaznamenat střelbu");
        menu.addOption("Prohlížet statistiky");
        menu.addOption("q", "Zavřít program");
        registerComponent(menu);
    }
}

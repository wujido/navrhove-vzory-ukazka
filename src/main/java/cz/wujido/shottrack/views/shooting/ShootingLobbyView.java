package cz.wujido.shottrack.views.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;

public class ShootingLobbyView extends View {
    public ShootingLobbyView(PrintStream output, ShotTrackModel model) {
        super(output, model);

        var title = new Title("Co chcete zaznamenat?");
        registerComponent(title);

        var menu = new Menu();
        menu.addOption("Samostatnou položku");
        menu.addOption("Nový trénink");
        registerComponent(menu);
    }
}

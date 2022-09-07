package cz.wujido.shottrack.views.stats;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ShootingLobbyView extends View {

    public ShootingLobbyView(PrintStream output, ShotTrackModel model) {
        super(output, model);

        registerComponent(new Title("Samostatné položky"));

        var menu = new Menu();
        var shootings = model.getShootingRepository().getSoloShootings().getShootings();

        for (int i = shootings.size() - 1; i >= 0; i--) {
            menu.addOption(shootings.get(i).getValue0().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT)));
        }

        menu.addOption("b", "Zpět");

        registerComponent(menu);
    }
}

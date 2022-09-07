package cz.wujido.shottrack.views.stats;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;

public class StatsLobbyView extends View {
    public StatsLobbyView(PrintStream output, ShotTrackModel model) {
        super(output, model);

        registerComponent(new Title("Prohlížení statistik"));

        var menu = new Menu();
        menu.addOption("Dlouhodobá statistika");
        menu.addOption("Jednotlivé tréninky");
        menu.addOption("Samostatné položky");
        menu.addOption("m", "Zpět do menu");
        registerComponent(menu);
    }
}

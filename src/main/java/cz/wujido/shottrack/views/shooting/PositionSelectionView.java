package cz.wujido.shottrack.views.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;

public class PositionSelectionView extends View {
    public PositionSelectionView(PrintStream output, ShotTrackModel model) {
        super(output, model);

        var title = new Title("Jaká je poloha střelce?");
        registerComponent(title);

        var menu = new Menu();
        menu.addOption("V leže");
        menu.addOption("Ve stoje");
        registerComponent(menu);
    }
}

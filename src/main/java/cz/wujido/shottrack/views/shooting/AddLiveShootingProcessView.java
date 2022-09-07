package cz.wujido.shottrack.views.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Text;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;

public class AddLiveShootingProcessView extends View {
    public AddLiveShootingProcessView(PrintStream output, ShotTrackModel model, int order) {
        super(output, model);

        registerComponent(new Title("Zaznamenávání střelby (rána " + order + ".)"));
        var info = new Text();
        info.addLine("Výsledek zadávejte v moment výstřelu");
        info.addLine("Bude zaznamenán čas výstřelu");
        info.addLine("V moment kdy střelec opustí stav zadejte [f]");
        registerComponent(info);

        var menu = new Menu();
        menu.addOption("Zásah");
        menu.addOption("Netrefa");
        registerComponent(menu);
    }
}

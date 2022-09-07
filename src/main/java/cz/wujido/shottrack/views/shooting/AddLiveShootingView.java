package cz.wujido.shottrack.views.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Space;
import cz.wujido.shottrack.views.components.Text;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;

public class AddLiveShootingView extends View {
    public AddLiveShootingView(PrintStream output, ShotTrackModel model) {
        super(output, model);

        registerComponent(new Title("Zaznamenat položku živě"));
        var info = new Text();
        info.addLine("Po zahájení střelby budete zadávat výsledky jednotlivých ran");
        info.addLine("Výsledek zadávejte v moment výstřelu");
        info.addLine("Tím dojde k zaznamenání času výstřelu");
        info.addLine("V moment kdy střelec opustí stav zadejte [f]");
        registerComponent(info);

        registerComponent(new Space());

        var menu = new Menu();
        menu.addOption("Zahájit střelbu");
        menu.addOption("b", "Zpět");
        registerComponent(menu);
    }
}

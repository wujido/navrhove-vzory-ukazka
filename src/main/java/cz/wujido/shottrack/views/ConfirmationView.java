package cz.wujido.shottrack.views;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Text;

import java.io.PrintStream;

public class ConfirmationView extends View {
    public ConfirmationView(PrintStream output, ShotTrackModel model, String question) {
        super(output, model);

        var q = new Text();
        q.addLine(question);
        registerComponent(q);

        var menu = new Menu();
        menu.addOption("y", "Ano");
        menu.addOption("n", "Ne");
        registerComponent(menu);
    }
}

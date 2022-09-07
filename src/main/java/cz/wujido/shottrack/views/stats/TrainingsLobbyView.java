package cz.wujido.shottrack.views.stats;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.components.Menu;
import cz.wujido.shottrack.views.components.Text;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TrainingsLobbyView extends View {

    public TrainingsLobbyView(PrintStream output, ShotTrackModel model) {
        super(output, model);

        registerComponent(new Title("Jednotlivé tréninky"));

        var menu = new Menu();
        var trainings = model.getShootingRepository().getTrainings();

        if (trainings.size() == 0) {
            var t = new Text();
            t.addLine("Zatím jste nepřidali žádné tréninky");
            registerComponent(t);
        }

        for (int i = trainings.size() - 1; i >= 0; i--) {
            menu.addOption(trainings.get(i).getValue0().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT)));
        }

        menu.addOption("b", "Zpět");

        registerComponent(menu);
    }
}

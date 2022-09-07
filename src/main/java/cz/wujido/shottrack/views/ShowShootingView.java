package cz.wujido.shottrack.views;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.entities.Position;
import cz.wujido.shottrack.entities.Shooting;
import cz.wujido.shottrack.entities.Shot;
import cz.wujido.shottrack.views.components.*;

import java.io.PrintStream;

public class ShowShootingView extends View {
    public ShowShootingView(PrintStream output, ShotTrackModel model, Shooting shooting) {
        super(output, model);

        registerComponent(new Title("Detail položky"));

        var position = new Text();
        position.addLine("Poloha: " + (shooting.getPosition() == Position.PRONE ? "v leže" : "ve stoje"));
        registerComponent(position);

        registerComponent(new Space());


        var shots = shooting.getShots();
        if (shots.size() > 0) {

            var stats = new Table();
            stats.setColumns("Rány", "Minuté rány", "Kalibry", "Centry", "Čas první rány", "Průměrny rozestup mezi ranami");
            stats.addRow(
                    shooting.getShotCount(),
                    shooting.getMissCount(),
                    shooting.getLuckyShotCount(),
                    shooting.getCenterShotCount(),
                    shooting.getDurationToShot(1).getSeconds() + "s",
                    shooting.getAverageShotSpan().getSeconds() + "s"
            );
            registerComponent(stats);

            registerComponent(new Space());

            registerComponent(new Title("Jednotlivé rány"));
            var shotsTable = new Table();
            shotsTable.setColumns("Rána", "Zásah", "Poloha", "Centr", "Kalibr");

            var i = 1;
            for (Shot shot : shots) {
                shotsTable.addRow(
                        i,
                        shot.isHit() ? "X" : "",
                        "na " + shot.getSector() + "h",
                        shot.isCenter() ? "X" : "",
                        shot.isLucky() ? "X" : ""
                );
                i++;
            }

            registerComponent(shotsTable);

        } else {
            var text = new Text();
            text.addLine("Nejsou k dispozici žádné rány.");
            registerComponent(text);
        }

        registerComponent(new Space());

        var menu = new Menu();
        menu.addOption("b", "Zpět");
        registerComponent(menu);
    }
}

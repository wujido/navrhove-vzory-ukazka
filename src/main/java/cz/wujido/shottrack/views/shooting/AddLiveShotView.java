package cz.wujido.shottrack.views.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.entities.Position;
import cz.wujido.shottrack.views.View;
import cz.wujido.shottrack.views.components.Text;
import cz.wujido.shottrack.views.components.Title;

import java.io.PrintStream;

public class AddLiveShotView extends View {
    public AddLiveShotView(PrintStream output, ShotTrackModel model, int order) {
        super(output, model);

        registerComponent(new Title("Zadejte výsledk rány " + order + "."));

        var question = new Text();
        question.addLine("Jaká byla vzdálenost rány od středu? (v mm)");

        var shooting = model.getNewShooting();
        var position = shooting.getPosition();
        var positionText = position == Position.PRONE ? "v leže" : "ve stoje";

        question.addLine("Poloměr terče pro střelbu " + positionText + " je " + position.diameter / 2 + "mm");
        registerComponent(question);
    }

    public void displayAnswer(String answer) {
        var text = new Text();
        text.addLine("\033[36m" + answer + "\033[0m");
        registerComponent(text);
    }

    public void displayAngleQuestion() {
        var question = new Text();
        question.addLine("V jakém úhlu se rána nacházela? (0 - 360)");
        registerComponent(question);
    }
}

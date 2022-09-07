package cz.wujido.shottrack.controllers.stats;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.views.ShowTrainingView;
import cz.wujido.shottrack.views.View;

import java.io.InputStream;
import java.io.PrintStream;

public class TrainingDetailController extends Controller {
    private int trainingIndex;

    public TrainingDetailController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    @Override
    protected void bindParams(int[] params) {
        trainingIndex = params[0];
    }

    @Override
    protected View initializeView() {
        var training = model.getShootingRepository().getTrainings().get(trainingIndex);
        return new ShowTrainingView(output, model, training.getValue1());
    }

    @Override
    protected void executeAction(String action) {

        if ("b".equals(action)) {
            setRoute("stats/training");
            return;
        }

        invalidAction(action);

    }
}

package cz.wujido.shottrack.controllers.shooting;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.controllers.Controller;
import cz.wujido.shottrack.views.ShowTrainingView;
import cz.wujido.shottrack.views.View;

import java.io.InputStream;
import java.io.PrintStream;

public class ViewCreatedTrainingController extends Controller {
    public ViewCreatedTrainingController(InputStream input, PrintStream output, ShotTrackModel model) {
        super(input, output, model);
    }

    @Override
    protected View initializeView() {
        return new ShowTrainingView(output, model, model.getNewTraining());
    }

    @Override
    protected void executeAction(String action) {
        if ("b".equals(action)) {
            setRoute("shooting/training");
            return;
        }

        invalidAction(action);

    }
}

package cz.wujido.shottrack.views.appComponents;

import cz.wujido.shottrack.entities.Training;
import cz.wujido.shottrack.views.components.Space;
import cz.wujido.shottrack.views.components.Table;
import cz.wujido.shottrack.views.components.Title;

public class TrainingStats extends AppComponent {
    public TrainingStats(Training training) {

        var totalStats = new Table();
        totalStats.setColumns("Počet položek", "Počet ran", "Netrefy", "úspěšnost");
        totalStats.addRow(
                training.getShootingCount(),
                training.getTotalShotCount(),
                training.getTotalMissCount(),
                formatSuccessRate(countSuccessRate(training.getTotalShotCount(), training.getTotalMissCount()))
        );

        registerComponent(totalStats);

        if (training.getProneShootingCount() > 0) {
            registerComponent(new Space());
            registerComponent(new Title("Ležka"));

            var proneStats = new Table();
            proneStats.setColumns("Počet položek", "Počet ran", "Netrefy", "úspěšnost");
            proneStats.addRow(
                    training.getProneShootingCount(),
                    training.getProneShotCount(),
                    training.getProneMissCount(),
                    formatSuccessRate(countSuccessRate(training.getProneShotCount(), training.getProneMissCount()))
            );
            registerComponent(proneStats);
        }

        if (training.getStandingShootingCount() > 0) {
            registerComponent(new Space());
            registerComponent(new Title("Stojka"));

            var proneStats = new Table();
            proneStats.setColumns("Počet položek", "Počet ran", "Netrefy", "úspěšnost");
            proneStats.addRow(
                    training.getStandingShootingCount(),
                    training.getStandingShotCount(),
                    training.getStandingMissCount(),
                    formatSuccessRate(countSuccessRate(training.getStandingShotCount(), training.getStandingMissCount()))
            );
            registerComponent(proneStats);
        }
    }

    private float countSuccessRate(int total, int miss) {
        return (1 - ((float) miss / total)) * 100;
    }

    private String formatSuccessRate(float rate) {
        return String.format("%.2f%%", rate);
    }
}

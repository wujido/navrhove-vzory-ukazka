import cz.wujido.shottrack.entities.Shooting;
import cz.wujido.shottrack.entities.ShootingRepository;
import cz.wujido.shottrack.entities.Training;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ShootingRepositoryTest {
    private ShootingRepository rep = new ShootingRepository();

    @Test
    @DisplayName("Add shooting adds shooting to solo shootings")
    void addShootingAddsShootingToSoloShootings() {
        Assertions.assertEquals(0, rep.getSoloShootings().getShootingCount());
        rep.addShooting(Shooting.CreateStandingShooting());
        Assertions.assertEquals(1, rep.getSoloShootings().getShootingCount());
    }

    @Test
    @DisplayName("Add shooting adds shooting to all shootings")
    void addShootingAddsShootingToAllShootings() {
        Assertions.assertEquals(0, rep.getAllShootings().getShootingCount());
        rep.addShooting(Shooting.CreateStandingShooting());
        Assertions.assertEquals(1, rep.getAllShootings().getShootingCount());
    }

    @Test
    @DisplayName("Add training adds training to trainings")
    void addTrainingAddsTrainingToTrainings() {
        Assertions.assertEquals(0, rep.getTrainings().size());
        rep.addTraining(new Training());
        Assertions.assertEquals(1, rep.getTrainings().size());
    }

    @Test
    @DisplayName("Add training add shootings to all shootings")
    void addTrainingAddShootingsToAllShootings() {
        Assertions.assertEquals(0, rep.getAllShootings().getShootingCount());

        var tr = new Training();
        tr.addShooting(Shooting.CreateStandingShooting());
        tr.addShooting(Shooting.CreateProneShooting());
        rep.addTraining(tr);

        Assertions.assertEquals(2, rep.getAllShootings().getShootingCount());
    }
}

import cz.wujido.shottrack.entities.Shooting;
import cz.wujido.shottrack.entities.Shot;
import cz.wujido.shottrack.entities.Training;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TrainingTest {
    private final Training training = new Training();

    @Test
    @DisplayName("Add shooting, count should increment")
    void addShootingCountShouldIncrement() {
        Assertions.assertEquals(0, training.getShootingCount());
        training.addShooting(Shooting.CreateProneShooting());
        Assertions.assertEquals(1, training.getShootingCount());
        training.addShooting(Shooting.CreateStandingShooting());
        Assertions.assertEquals(2, training.getShootingCount());
    }

    @Test
    @DisplayName("Add shooting, prone count should increment if added shooting is prone")
    void addShootingProneCountShouldIncrementIfAddedShootingIsProne() {
        Assertions.assertEquals(0, training.getProneShootingCount());
        training.addShooting(Shooting.CreateProneShooting());
        training.addShooting(Shooting.CreateStandingShooting());
        Assertions.assertEquals(1, training.getProneShootingCount());
    }


    @Test
    @DisplayName("Add shooting, standing count should increment if added shooting is standing")
    void addShootingStandingCountShouldIncrementIfAddedShootingIsStanding() {
        Assertions.assertEquals(0, training.getStandingShootingCount());
        training.addShooting(Shooting.CreateProneShooting());
        training.addShooting(Shooting.CreateStandingShooting());
        Assertions.assertEquals(1, training.getStandingShootingCount());
    }

    @Test
    @DisplayName("Add shooting, should correctly increment total shot count")
    void addShootingShouldCorrectlyIncrementTotalShotCount() {
        Assertions.assertEquals(0, training.getTotalShotCount());
        var s1 = Shooting.CreateProneShooting();
        s1.addShot(new Shot(0, 0, true, 10));
        training.addShooting(s1);

        Assertions.assertEquals(1, training.getTotalShotCount());

        var s2 = Shooting.CreateProneShooting();
        s2.addShot(new Shot(0, 0, true, 10));
        s2.addShot(new Shot(0, 0, true, 10));
        training.addShooting(s2);

        Assertions.assertEquals(3, training.getTotalShotCount());
    }

    @Test
    @DisplayName("Add shooting, should correctly increment prone shot count")
    void addShootingShouldCorrectlyIncrementProneShotCount() {
        Assertions.assertEquals(0, training.getProneShotCount());
        var s1 = Shooting.CreateProneShooting();
        s1.addShot(new Shot(0, 0, true, 10));
        training.addShooting(s1);

        var s2 = Shooting.CreateStandingShooting();
        s2.addShot(new Shot(0, 0, true, 10));
        training.addShooting(s2);

        Assertions.assertEquals(1, training.getProneShotCount());
    }


    @Test
    @DisplayName("Add shooting, should correctly increment standing shot count")
    void addShootingShouldCorrectlyIncrementStandingShotCount() {
        Assertions.assertEquals(0, training.getStandingShotCount());
        var s1 = Shooting.CreateProneShooting();
        s1.addShot(new Shot(0, 0, true, 10));
        training.addShooting(s1);

        var s2 = Shooting.CreateStandingShooting();
        s2.addShot(new Shot(0, 0, true, 10));
        training.addShooting(s2);

        Assertions.assertEquals(1, training.getStandingShotCount());
    }


    @Test
    @DisplayName("Add shooting, should correctly increment total miss count")
    void addShootingShouldCorrectlyIncrementTotalMissCount() {
        Assertions.assertEquals(0, training.getTotalMissCount());
        var s1 = Shooting.CreateProneShooting();
        s1.addShot(new Shot(0, 0, true, 10));
        s1.addShot(new Shot(0, 0, false, 10));
        s1.addShot(new Shot(0, 0, false, 10));
        training.addShooting(s1);
        training.addShooting(s1);

        Assertions.assertEquals(4, training.getTotalMissCount());
    }

    @Test
    @DisplayName("Add shooting, should correctly increment prone miss count")
    void addShootingShouldCorrectlyIncrementProneMissCount() {
        Assertions.assertEquals(0, training.getProneMissCount());
        var s1 = Shooting.CreateProneShooting();
        s1.addShot(new Shot(0, 0, false, 10));
        s1.addShot(new Shot(0, 0, true, 10));
        training.addShooting(s1);

        var s2 = Shooting.CreateStandingShooting();
        s2.addShot(new Shot(0, 0, false, 10));
        s2.addShot(new Shot(0, 0, true, 10));
        training.addShooting(s2);

        Assertions.assertEquals(1, training.getProneMissCount());
    }

    @Test
    @DisplayName("Add shooting, should correctly increment standing miss count")
    void addShootingShouldCorrectlyIncrementStandingMissCount() {

        Assertions.assertEquals(0, training.getStandingMissCount());
        var s1 = Shooting.CreateProneShooting();
        s1.addShot(new Shot(0, 0, false, 10));
        s1.addShot(new Shot(0, 0, true, 10));
        training.addShooting(s1);

        var s2 = Shooting.CreateStandingShooting();
        s2.addShot(new Shot(0, 0, false, 10));
        s2.addShot(new Shot(0, 0, true, 10));
        training.addShooting(s2);

        Assertions.assertEquals(1, training.getStandingMissCount());
    }
}

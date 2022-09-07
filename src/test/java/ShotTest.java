import cz.wujido.shottrack.entities.Shot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ShotTest {
    @ParameterizedTest
    @DisplayName("Shot should calculate sector (1-12) based on coordinates")
    @CsvSource({"0,3", "350, 3", "15,3", "16,2", "50,1", "123, 11"})
    void shotShouldCalculateSectorBasedOnCoordinates(int angle, int expectedSector) {
        var shot = new Shot(10, angle, true, 450);
        Assertions.assertEquals(expectedSector, shot.getSector());
    }

    @Test
    @DisplayName("Hit outside of target should be lucky shot")
    void hitOutsideOfTargetShouldBeLuckyShot() {
        var shot = new Shot(226, 0, true, 450);
        Assertions.assertTrue(shot.isLucky());
    }


    @Test
    @DisplayName("Hit more than 10% inside of target should not be lucky shot")
    void hitInsideOfTargetShouldNotBeLuckyShot() {
        var shot = new Shot(180, 0, true, 450);
        Assertions.assertTrue(shot.isLucky());

        shot = new Shot(179, 0, true, 450);
        Assertions.assertFalse(shot.isLucky());
    }

    @Test
    @DisplayName("Miss should not be lucky shot")
    void missShouldNotBeLuckyShot() {
        var shot = new Shot(500, 0, false, 450);
        Assertions.assertFalse(shot.isLucky());
    }

    @Test
    @DisplayName("Is hit should reflect value from constructor")
    void isHitShouldReflectValueFromConstructor() {
        var shot = new Shot(0, 0, true, 450);
        Assertions.assertTrue(shot.isHit());

        shot = new Shot(0, 0, false, 450);
        Assertions.assertFalse(shot.isHit());
    }

    @Test
    @DisplayName("Hit close to center should be center")
    void hitCloseToCenterShouldBeCenter() {
        var shot = new Shot(0, 0, true, 450);
        Assertions.assertTrue(shot.isCenter());
    }

    @Test
    @DisplayName("Hit more than 20% off center should not be center")
    void hitMoreThan20PercentOffCenterShouldNotBeCenter() {
        var shot = new Shot(9, 0, true, 45);
        Assertions.assertTrue(shot.isCenter());

        shot = new Shot(10, 0, true, 45);
        Assertions.assertFalse(shot.isCenter());
    }
}

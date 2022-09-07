import cz.wujido.shottrack.entities.Position;
import cz.wujido.shottrack.entities.Shooting;
import cz.wujido.shottrack.entities.Shot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShootingTest {
    @Test
    @DisplayName("Create prone shooting should have set correct position")
    void createProneShootingShouldHaveSetCorrectPosition() {
        var shooting = getShooting();
        assertEquals(Position.PRONE, shooting.getPosition());
    }

    @Test
    @DisplayName("Create standing shooting should have set correct position")
    void createStandingShootingShouldHaveSetCorrectPosition() {
        var shooting = Shooting.CreateStandingShooting();
        assertEquals(Position.STANDING, shooting.getPosition());
    }

    @Test
    @DisplayName("Miss count should increment if added shot is miss")
    void missCountShouldIncrementIfAddedShotIsMiss() {
        var shooting = getShooting();
        assertEquals(0, shooting.getMissCount());
        shooting.addShot(new Shot(0, 0, false, 0));
        assertEquals(1, shooting.getMissCount());
    }

    @Test
    @DisplayName("Miss count should stay the same if added shot is hit")
    void missCountShouldStayTheSameIfAddedShotIsHit() {
        var shooting = getShooting();
        assertEquals(0, shooting.getMissCount());
        shooting.addShot(new Shot(0, 0, true, 0));
        assertEquals(0, shooting.getMissCount());
    }

    @Test
    @DisplayName("Lucky shot count should increment if added shot is lucky shot")
    void luckyShotCountShouldIncrementIfAddedShotIsLuckyShot() {
        var shooting = getShooting();
        assertEquals(0, shooting.getLuckyShotCount());
        shooting.addShot(new Shot(1, 0, true, 0));
        assertEquals(1, shooting.getLuckyShotCount());
    }

    @Test
    @DisplayName("Lucky shot count should stay the same if added shot is not lucky")
    void luckyShotCountShouldStayTheSameIfAddedShotIsNotLucky() {
        var shooting = getShooting();
        assertEquals(0, shooting.getLuckyShotCount());
        shooting.addShot(new Shot(0, 0, true, 1000));
        assertEquals(0, shooting.getLuckyShotCount());
    }

    @Test
    @DisplayName("Center shot count should increment if added shot is lucky shot")
    void centerShotCountShouldIncrementIfAddedShotIsCenterShot() {
        var shooting = getShooting();
        assertEquals(0, shooting.getCenterShotCount());
        shooting.addShot(new Shot(0, 0, true, 10));
        assertEquals(1, shooting.getCenterShotCount());
    }

    @Test
    @DisplayName("Center shot count should stay the same if added shot is not center")
    void centerShotCountShouldStayTheSameIfAddedShotIsNotCenter() {
        var shooting = getShooting();
        assertEquals(0, shooting.getCenterShotCount());
        shooting.addShot(new Shot(10, 0, true, 10));
        assertEquals(0, shooting.getCenterShotCount());
    }

    @Test
    @DisplayName("Shot count should increment when shot is added")
    void shotCountShouldIncrementWhenShotIsAdded() {
        var shooting = getShooting();
        assertEquals(0, shooting.getShotCount());
        shooting.addShot(new Shot(0, 0, true, 0));
        assertEquals(1, shooting.getShotCount());
    }

    @Test
    @DisplayName("Start time should be same as set")
    void startTimeShouldBeSameAsSet() {
        var shooting = getShooting();
        LocalDateTime startTime = LocalDateTime.of(2021, 1, 1, 10, 0, 0);
        shooting.setStartTime(startTime);
        assertEquals(startTime, shooting.getStartTime());
    }

    @Test
    @DisplayName("Get duration to shot should return zero duration if index of shot is higher than count of shots")
    void getDurationToShotShouldReturnZeroDurationIfIndexOfShotIsHigherThanCountOfShots() {
        var shooting = getShooting();

        Assertions.assertEquals(Duration.ZERO, shooting.getDurationToShot(1));
    }

    @Test
    @DisplayName("Get duration to shot return zero duration if start wasn't set and try to get first shot")
    void getDurationToShotReturnZeroDurationIfStartWasNotSetAndTryToGetFirstShot() {
        var shooting = getShooting();
        shooting.addShot(new Shot(0, 0, true, Position.PRONE.diameter));

        Assertions.assertEquals(Duration.ZERO, shooting.getDurationToShot(1));
    }

    @Test
    @DisplayName("Get duration to shot should should be correct")
    void getDurationToShotShouldBeCorrect() {
        var shooting = getShooting();

        LocalDateTime startTime = LocalDateTime.of(2021, 1, 1, 10, 0, 0);
        shooting.setStartTime(startTime);

        LocalDateTime shotTime = LocalDateTime.of(2021, 1, 1, 10, 0, 14);
        shooting.addShot(new Shot(0, 0, true, 0), shotTime);

        assertEquals(Duration.between(startTime, shotTime), shooting.getDurationToShot(1));

    }

    @Test
    @DisplayName("Get duration between shots should be correct")
    void getDurationBetweenShotsShouldBeCorrect() {
        var shooting = getShooting();

        LocalDateTime startTime = LocalDateTime.of(2021, 1, 1, 10, 0, 0);
        shooting.setStartTime(startTime);

        LocalDateTime shotTime = LocalDateTime.of(2021, 1, 1, 10, 0, 14);
        shooting.addShot(new Shot(0, 0, true, 0), shotTime);

        LocalDateTime secondShotTime = LocalDateTime.of(2021, 1, 1, 10, 0, 16);
        shooting.addShot(new Shot(0, 0, true, 0), secondShotTime);

        assertEquals(Duration.between(shotTime, secondShotTime), shooting.getDurationBetweenShots(1, 2));
    }

    @Test
    @DisplayName("Get duration between shots should return zero duration if any of shots does not have assigned time")
    void getDurationBetweenShotsShouldReturnZeroDurationIfAnyOfShotsDoesNotHaveAssignedTime() {
        var shooting = getShooting();
        var commonShot = new Shot(0, 0, true, 10);

        var time = LocalDateTime.now();

        shooting.setStartTime(time);

        shooting.addShot(commonShot);
        shooting.addShot(commonShot, time.plusSeconds(10));


        Assertions.assertEquals(Duration.ZERO, shooting.getDurationBetweenShots(0, 1));
        Assertions.assertEquals(Duration.ZERO, shooting.getDurationBetweenShots(1, 2));
        Assertions.assertEquals(Duration.ofSeconds(10), shooting.getDurationBetweenShots(0, 2));
    }

    @Test
    @DisplayName("Get duration between shots should throw exception if from is gt or eq to to")
    void getDurationBetweenShotsShouldThrowExceptionIfFromIsGtOrEqToTo() {
        var shooting = getShooting();

        Assertions.assertThrows(IllegalArgumentException.class, () -> shooting.getDurationBetweenShots(1, 1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> shooting.getDurationBetweenShots(2, 1));
    }

    @Test
    @DisplayName("Get average shot span should be correct")
    void getAverageShotSpanShouldBeCorrect() {
        var shooting = getShooting();
        var commonShot = new Shot(0, 0, true, 10);

        LocalDateTime firstShotTime = LocalDateTime.of(2021, 1, 1, 10, 0, 0);
        shooting.addShot(commonShot, firstShotTime);

        var secondShotTime = firstShotTime.plusSeconds(1);
        shooting.addShot(commonShot, secondShotTime);

        LocalDateTime thirdShotTime = secondShotTime.plusSeconds(3);
        shooting.addShot(commonShot, thirdShotTime);

        assertEquals(Duration.ofMillis(2000), shooting.getAverageShotSpan());
    }

    @Test
    @DisplayName("Get average shot span should return zero duration if only one shot was added")
    void getAverageShotSpanShouldReturnZeroDurationIfOnlyOneShotWasAdded() {
        var shooting = getShooting();
        var commonShot = new Shot(0, 0, true, 10);
        shooting.addShot(commonShot);

        Assertions.assertEquals(Duration.ZERO, shooting.getAverageShotSpan());
    }

    private Shooting getShooting() {
        return Shooting.CreateProneShooting();
    }
}

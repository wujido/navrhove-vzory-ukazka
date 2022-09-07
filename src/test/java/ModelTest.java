import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.entities.Notification;
import cz.wujido.shottrack.entities.Position;
import cz.wujido.shottrack.entities.Shooting;
import cz.wujido.shottrack.entities.Training;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ModelTest {
    private final ShotTrackModel model = new ShotTrackModel();

    @Test
    @DisplayName("Has notification in initially false")
    void hasNotificationInInitiallyFalse() {
        Assertions.assertFalse(model.hasNotification());
    }

    @Test
    @DisplayName("Set notification - has notification is true")
    void setNotificationHasNotificationIsTrue() {
        model.setNotification("content", Notification.Type.ERR);
        Assertions.assertTrue(model.hasNotification());
    }

    @Test
    @DisplayName("Get notification - has notification is false")
    void getNotificationHasNotificationIsFalse() {
        model.setNotification("content", Notification.Type.ERR);
        model.getNotification();
        Assertions.assertFalse(model.hasNotification());
    }

    @Test
    @DisplayName("Get notification - get notification with correct values")
    void getNotificationGetNotificationWithCorrectValues() {
        var content = "content";
        var type = Notification.Type.ERR;
        model.setNotification(content, type);

        var res = model.getNotification();
        Assertions.assertEquals(content, res.content);
        Assertions.assertEquals(type, res.type);
    }

    @Test
    @DisplayName("GetNewShooting throws IllegalStateException before new shooting was initialized")
    void getNewShootingThrowsIllegalStateExceptionBeforeNewShootingWasInitialized() {
        Assertions.assertThrows(IllegalStateException.class, model::getNewShooting);
        model.initializeNewShooting(Position.PRONE);
        Assertions.assertInstanceOf(Shooting.class, model.getNewShooting());
        Assertions.assertEquals(Position.PRONE, model.getNewShooting().getPosition());
    }

    @Test
    @DisplayName("Save new shooting throws IllegalStateException before new shooting was initialized")
    void saveNewShootingThrowsIllegalStateExceptionBeforeNewShootingWasInitialized() {
        Assertions.assertThrows(IllegalStateException.class, model::saveNewShooting);
        model.initializeNewShooting(Position.PRONE);
        Assertions.assertDoesNotThrow(model::saveNewShooting);
    }

    @Test
    @DisplayName("Save new shooting should add shooting to new training if initialized")
    void saveNewShootingShouldAddShootingToNewTrainingIfInitialized() {
        model.initializeNewShooting(Position.PRONE);
        model.initializeNewTraining();
        model.saveNewShooting();

        Assertions.assertEquals(1, model.getNewTraining().getShootingCount());
    }

    @Test
    @DisplayName("Save new shooting should make new shooting not initialized")
    void saveNewShootingShouldMakeNewShootingNotInitialized() {
        model.initializeNewShooting(Position.PRONE);
        model.saveNewShooting();

        Assertions.assertThrows(IllegalStateException.class, model::getNewShooting);
    }

    @Test
    @DisplayName("Discard new shooting should make new shooting not initialized")
    void discardNewShootingShouldMakeNewShootingNotInitialized() {
        model.initializeNewShooting(Position.PRONE);
        model.discardNewShooting();

        Assertions.assertThrows(IllegalStateException.class, model::getNewShooting);

    }

    @Test
    @DisplayName("Get new training throws IllegalStateException before new training was initialized")
    void getNewTrainingThrowsIllegalStateExceptionBeforeNewTrainingWasInitialized() {
        Assertions.assertThrows(IllegalStateException.class, model::getNewTraining);
        model.initializeNewTraining();
        Assertions.assertInstanceOf(Training.class, model.getNewTraining());
    }

    @Test
    @DisplayName("Is new training initialized test")
    void isNewTrainingInitializedTest() {
        Assertions.assertFalse(model.isNewTrainingInitialized());
        model.initializeNewTraining();
        Assertions.assertTrue(model.isNewTrainingInitialized());
    }
}

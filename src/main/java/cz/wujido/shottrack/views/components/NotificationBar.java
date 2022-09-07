package cz.wujido.shottrack.views.components;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.displayEngine.Screen;

public class NotificationBar implements Component {
    private final ShotTrackModel model;

    public NotificationBar(ShotTrackModel model) {
        this.model = model;
    }

    @Override
    public Screen getScreen() {

        var screen = new Screen();
        String esc = "\033[";

        if (!model.hasNotification()) {
            screen.append(esc + "1A");
            return screen;
        }

        var notification = model.getNotification();

        screen.append(esc + notification.type.ansiColor);
        screen.append(notification.content);
        screen.append(esc + "0m");

        return screen;
    }

}

package cz.wujido.shottrack.views;

import cz.wujido.shottrack.ShotTrackModel;
import cz.wujido.shottrack.displayEngine.Frame;
import cz.wujido.shottrack.views.appComponents.AppComponent;
import cz.wujido.shottrack.views.components.Component;
import cz.wujido.shottrack.views.components.NotificationBar;
import org.javatuples.Pair;

import java.io.PrintStream;
import java.util.ArrayList;

public abstract class View {
    private final ArrayList<Pair<String, Component>> components = new ArrayList<>();
    protected final PrintStream output;
    protected final Frame frame;
    protected final ShotTrackModel model;

    public View(PrintStream output, ShotTrackModel model) {
        this.output = output;
        frame = new Frame(output, 0);
        this.model = model;
    }

    public final void display() {
        frame.deactivateAllScreen();
        for (var component : components) {
            frame.registerScreen(component.getValue0(), component.getValue1().getScreen());
            frame.activateLastRegisteredScreen();
        }

        frame.registerScreen("notificationBar", new NotificationBar(model).getScreen());
        frame.activateLastRegisteredScreen();

        frame.display();
    }

    public final void hide() {
        frame.deactivateAllScreen();
        frame.display();
    }

    protected void registerComponent(Component component) {
        components.add(new Pair<>(String.valueOf(System.identityHashCode(component)), component));
    }

    protected void registerAppComponent(AppComponent appComponent) {
        for (Component component : appComponent.getComponents()) {
            registerComponent(component);
        }
    }
}

package cz.wujido.shottrack.views.appComponents;

import cz.wujido.shottrack.views.components.Component;

import java.util.ArrayList;

public abstract class AppComponent {
    private final ArrayList<Component> components = new ArrayList<>();

    public ArrayList<Component> getComponents() {
        return components;
    }

    protected void registerComponent(Component component) {
        components.add(component);
    }
}

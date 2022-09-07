package cz.wujido.shottrack.views.components;

import cz.wujido.shottrack.displayEngine.Screen;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu implements Component {
    private final HashMap<String, String> options = new HashMap<>();
    private final ArrayList<String> keys = new ArrayList<>();

    public void addOption(String key, String title) {
        options.put(key, title);
        keys.add(key);
    }

    public void addOption(String title) {
        addOption(String.valueOf(options.size() + 1), title);
    }

    public HashMap<String, String> getOptions() {
        return options;
    }

    public Screen getScreen() {
        var s = new Screen();
        s.appendLn("vyberte akci:");

        for (var key : keys) {
            String line = "[" + key + "] " + options.get(key);
            s.appendLn(line);
        }

        return s;
    }
}

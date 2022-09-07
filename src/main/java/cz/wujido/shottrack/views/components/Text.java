package cz.wujido.shottrack.views.components;

import cz.wujido.shottrack.displayEngine.Screen;

import java.util.ArrayList;

public class Text implements Component {
    private ArrayList<String> lines = new ArrayList<>();

    public void addLine(String line) {
        lines.add(line);
    }

    @Override
    public Screen getScreen() {
        var s = new Screen();

        for (String line : lines) {
            s.appendLn(line);
        }

        return s;
    }
}

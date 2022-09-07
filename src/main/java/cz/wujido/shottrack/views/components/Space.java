package cz.wujido.shottrack.views.components;

import cz.wujido.shottrack.displayEngine.Screen;

public class Space implements Component {
    private int lines;

    public Space() {
        this(1);
    }

    public Space(int lines) {
        this.lines = lines;
    }

    @Override
    public Screen getScreen() {
        var s = new Screen();
        for (int i = 0; i < lines; i++) {
            s.appendLn("");
        }
        return s;
    }
}

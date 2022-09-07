package cz.wujido.shottrack.views.components;

import cz.wujido.shottrack.displayEngine.Screen;

public class Title implements Component {
    private String title = "";

    public Title(String title) {
        setTitle(title);
    }

    @Override
    public Screen getScreen() {
        var s = new Screen();
        s.appendLn(title);
        return s;
    }

    public void setTitle(String title) {
        this.title = "\033[1;4m" + title + "\033[0m";
    }
}

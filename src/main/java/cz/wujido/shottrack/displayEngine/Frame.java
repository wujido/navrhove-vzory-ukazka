package cz.wujido.shottrack.displayEngine;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Frame {
    private final HashMap<String, Screen> screens = new HashMap<>();
    private final ArrayList<String> activeScreens = new ArrayList<>();
    private final PrintStream writer;
    private final int topLine;
    private int lineCount;
    private String lastKey;

    public Frame(PrintStream writer, int topLine) {
        this.writer = writer;
        this.topLine = topLine;
    }

    public int getIndexOfLastActiveScreen() {
        int size = activeScreens.size();
        return size > 0 ? size - 1 : 0;
    }

    /**
     * @return line count of all active screens
     */
    public int getLineCount() {
        return lineCount;
    }

    /**
     * Register screen to the Frame
     *
     * @param key    unique identifier of the screen
     * @param screen screen to be registered
     */
    public void registerScreen(String key, Screen screen) {
        screen.freeze();
        lastKey = key;
        screens.put(key, screen);
    }

    /**
     * Mark screen as active.
     *
     * @param key key of the registered screen
     */
    public void activateScreen(String key) {
        lineCount += screens.get(key).Lines;
        activeScreens.add(key);
    }

    /**
     * Mark previously registered screen as active
     */
    public void activateLastRegisteredScreen() {
        activateScreen(lastKey);
    }

    /**
     * Mark all screens as active
     *
     * @param keys set of keys of registered screens
     */
    public void activateScreen(String... keys) {
        for (String key : keys) {
            activateScreen(key);
        }
    }

    /**
     * Mark screen as inactive
     *
     * @param key key of registered screen
     */
    public void deactivateScreen(String key) {
        lineCount -= screens.get(key).Lines;
        activeScreens.remove(key);
    }

    /**
     * Mark last active screen as inactive
     */
    public void deactivateLastScreen() {
        int indexOfLastActiveScreen = getIndexOfLastActiveScreen();
        lineCount -= screens.get(activeScreens.get(indexOfLastActiveScreen)).Lines;
        activeScreens.remove(indexOfLastActiveScreen);
    }

    /**
     * Mark all active screens as inactive
     */
    public void deactivateAllScreen() {
        activeScreens.clear();
    }

    /**
     * Write all active screens to `PrintStream` provided in constructor
     */
    public void display() {
        var esc = "\033[";
        var clearToBottom = esc + "J";
        var clearScreen = esc + "2J";
        var moveToTop = esc + topLine + "H";

        if (topLine == 0) writer.print(clearScreen);

        writer.print(moveToTop + clearToBottom);

        for (var key : activeScreens) {
            var screen = screens.get(key);
            writer.print(screen);
        }
    }

}

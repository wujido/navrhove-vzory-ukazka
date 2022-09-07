package cz.wujido.shottrack.displayEngine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Screen {
    public int Lines = 0;
    private final StringBuilder content = new StringBuilder();
    private boolean isFrozen;
    private boolean didAppendLine;

    public void append(String str) {
        if (isFrozen) throw new UnsupportedOperationException("Screen is frozen and can't be appended any more");
        if (Lines == 0) Lines++;
        Lines += countLines(str);
        content.append(str);
        didAppendLine = false;
    }

    public void appendLn(String str) {
        append(str + System.lineSeparator());
        didAppendLine = true;
    }

    public void freeze() {
        if (!didAppendLine) {
            appendLn("");
        }
        isFrozen = true;
    }

    private final Pattern newLinePattern = Pattern.compile("\r\n|\r|\n");

    private int countLines(String str) {
        int lines = 0;
        Matcher m = newLinePattern.matcher(str);
        while (m.find()) {
            lines++;
        }
        return lines;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}

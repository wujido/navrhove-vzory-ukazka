package cz.wujido.shottrack.views.components;

import cz.wujido.shottrack.displayEngine.Screen;

import java.util.ArrayList;
import java.util.Arrays;

public class Table implements Component {
    private String[] columns;
    private int[] columnWidths;
    private final ArrayList<String[]> rows = new ArrayList<>();

    public void setColumns(String... columns) {
        this.columns = columns;

        columnWidths = new int[columns.length];

        updateColumnWidths(columns);
    }


    public void addRow(Object... items) {
        if (columns == null)
            throw new IllegalStateException("Columns have to be set first (Call `setColumns`)");

        if (items.length > columns.length)
            throw new IllegalArgumentException("Row can't have more items than number of columns of the table");

        String[] stringItems = Arrays.stream(items).map(Object::toString).toArray(String[]::new);

        if (stringItems.length < columns.length) {
            var allItems = new String[columns.length];
            System.arraycopy(stringItems, 0, allItems, 0, stringItems.length);
            stringItems = allItems;
        }

        rows.add(stringItems);
        updateColumnWidths(stringItems);
    }


    @Override
    public Screen getScreen() {
        var s = new Screen();
        for (int i = 0; i < columns.length; i++) {
            s.append(tableCell(columns[i], i));
        }
        s.appendLn("|");

        for (int w : columnWidths) {
            for (int i = 0; i < w + 3; i++) {
                s.append("-");
            }
        }
        s.appendLn("-");

        for (String[] row : rows) {
            for (int i = 0; i < columns.length; i++) {
                s.append(tableCell(row[i], i));
            }
            s.appendLn("|");
        }

        return s;
    }

    private void updateColumnWidths(String[] items) {
        for (int i = 0; i < items.length; i++) {
            var original = columnWidths[i];
            var current = items[i].length();
            if (original < current)
                columnWidths[i] = current;
        }
    }

    private String rightPad(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text);
    }

    private String tableCell(String item, int column) {
        return "| " + rightPad(item, columnWidths[column]) + " ";
    }
}

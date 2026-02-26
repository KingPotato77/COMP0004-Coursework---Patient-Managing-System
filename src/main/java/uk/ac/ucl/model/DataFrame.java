package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.Objects;

public class DataFrame {
    private final ArrayList<Column> columns;

    public DataFrame() {
        columns = new ArrayList<Column>();
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    // change to List<String> ???
    public ArrayList<String> getColumnNames() {
        ArrayList<String> names = new ArrayList<>();

        for (Column column : columns) {
            names.add(column.getName());
        }
        return names;
    }

    public int getRowCount() {
        if (columns.isEmpty()){
            return 0;
        }
        return columns.getFirst().getSize();
    }

    public String getValue(String columnName, int row) {
        for (Column column : columns) {
            if (Objects.equals(column.getName(), columnName)) {
                return column.getRowValue(row);
            }
        }
        return null;
    }

    public void putValue(String columnName, int row, String value) {
        for (Column column : columns) {
            if (Objects.equals(column.getName(), columnName)) {
                column.setRowValue(value, row);
            }
        }
    }

    public void addValue(String columnName, String value) {
        for (Column column: columns) {
            if (Objects.equals(column.getName(), columnName)) {
                column.addRowValue(value);
            }
        }
    }
}

// maybe make findColumn() to not repeat the for loop in every method

package uk.ac.ucl.model;

import java.util.ArrayList;

public class Column {
    private final String name;
    private final ArrayList<String> rows;

    public Column(String name) {
        this.name = name;
        rows = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.rows.size();
    }

    public String getRowValue(int idx) {
        return rows.get(idx);
    }

    public void setRowValue(String value, int idx) {
        rows.set(idx, value);
    }

    public void addRowValue(String value) {
        rows.add(value);
    }

    public void removeRow(int idx) {
        rows.remove(idx);
    }
}

package util;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;

public class ResultSetTableModel extends AbstractTableModel {
    private ArrayList<String> columnNames = new ArrayList<>();
    private ArrayList<Object[]> rows = new ArrayList<>();

    public ResultSetTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int cols = md.getColumnCount();
        for (int i = 1; i <= cols; i++) columnNames.add(md.getColumnName(i));
        while (rs.next()) {
            Object[] row = new Object[cols];
            for (int i = 0; i < cols; i++) row[i] = rs.getObject(i+1);
            rows.add(row);
        }
    }

    @Override public int getRowCount() { return rows.size(); }
    @Override public int getColumnCount() { return columnNames.size(); }
    @Override public String getColumnName(int col) { return columnNames.get(col); }
    @Override public Object getValueAt(int row, int col) { return rows.get(row)[col]; }
}

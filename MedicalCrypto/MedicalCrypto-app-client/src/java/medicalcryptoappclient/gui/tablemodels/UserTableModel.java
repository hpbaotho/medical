/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalcryptoappclient.gui.tablemodels;

import entities.medical.dto.PersonsDTO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Piotrek
 */
public class UserTableModel extends AbstractTableModel {

    private List<Object[]> rows = new ArrayList<Object[]>();
    private String[] columns = {"LP", "PESEL", "NAZWISKO IMIĘ"};

    public UserTableModel(List<PersonsDTO> tuplets) {
        this.setContent(tuplets);
    }

    public void setContent(List<PersonsDTO> tuplets) {
        rows.clear();
        for (int i = 0; i < tuplets.size(); i++) {
            PersonsDTO personsDTO = tuplets.get(i);
            Object[] record = new Object[3];
            record[0] = new Boolean(false);
            record[1] = personsDTO.getFlippedPesel();
            record[2] = personsDTO;
            rows.add(record);
        }
        fireTableRowsInserted(0, rows.size() - 1);
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColumnCount() {
        return columns.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex > getRowCount() || rowIndex < 0) {
            return null;
        }
        if (columnIndex < -1 || columnIndex > getColumnCount()) {
            return null;
        } else if (columnIndex == -1) {
            return rows.get(rowIndex);
        } else {
            return rows.get(rowIndex)[columnIndex];
        }
    }

    @Override
    public String getColumnName(int col) {
        return columns[col].toString();
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        if (col == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        rows.get(row)[col] = value;
        fireTableCellUpdated(row, col);
    }
}

package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;



public abstract class AbstractTableFrame extends AbstractTableModel{

	protected String[] columnNames;
	protected List<?> items;
	
	public AbstractTableFrame(List<?> items,String[] columnNames) {
		this.items = items;
		this.columnNames = columnNames;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return items.size();
	}
	
}

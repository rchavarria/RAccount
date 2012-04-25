package es.rchavarria.raccount.frontend.gui.models;

import java.sql.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.rchavarria.raccount.model.Movement;

public class MovementListTableModel extends DefaultTableModel {
    
	private static final long serialVersionUID = -3063700095010151104L;
	
	private static final String[] COLUMNS = new String[] { "Date", "Description", "Amount", "Balance" };
	private static final Class<?>[] COLUMNS_CLASS = new Class[] { Date.class, String.class, Double.class, Double.class };

	private List<Movement> movements;

	public MovementListTableModel(final List<Movement> movements) {
		this.movements = movements;
	}

	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}

	@Override
	public String getColumnName(final int column) {
		return COLUMNS[column];
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		return COLUMNS_CLASS[columnIndex];
	}

	@Override
	public int getRowCount() {
		return this.movements != null ? this.movements.size() : 0;
	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return false;
	}

	@Override
	public Object getValueAt(final int row, final int column) {
		Movement mov = this.movements.get(row);
		switch (column) {
		case 0:
			return mov.getMovementDate();
		case 1:
			return mov.getDescription();
		case 2:
			return mov.getAmount();
		case 3:
			return mov.getFinalBalance();
		default:
			return "-";
		}
	}
}

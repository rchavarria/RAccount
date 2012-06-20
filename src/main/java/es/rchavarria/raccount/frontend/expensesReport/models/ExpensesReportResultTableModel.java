package es.rchavarria.raccount.frontend.expensesReport.models;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.rchavarria.raccount.model.ExpensesByConcept;

public class ExpensesReportResultTableModel extends DefaultTableModel {
    
	private static final long serialVersionUID = -3063700095010151104L;
	
	private static final String[] COLUMNS = new String[] { "Concepto", "Gastos" };
	private static final Class<?>[] COLUMNS_CLASS = new Class[] { String.class, Double.class };

	private List<ExpensesByConcept> expenses;

	public ExpensesReportResultTableModel(List<ExpensesByConcept> expenses) {
		this.expenses = expenses;
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
		return this.expenses != null ? this.expenses.size() : 0;
	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return false;
	}

	@Override
	public Object getValueAt(final int row, final int column) {
		ExpensesByConcept expense = this.expenses.get(row);
		switch (column) {
		case 0:
			return expense.concept.getName();
		case 1:
			return expense.expenses;
		default:
			throw new IllegalArgumentException("Column #" + column + " doesn't exist");
		}
	}
}

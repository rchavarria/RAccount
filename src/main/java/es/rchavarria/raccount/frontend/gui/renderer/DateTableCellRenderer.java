package es.rchavarria.raccount.frontend.gui.renderer;

import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class DateTableCellRenderer implements TableCellRenderer {

    private SimpleDateFormat sdf;
	private JLabel lbl;

	public DateTableCellRenderer() {
		this.sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.lbl = new JLabel();
		this.lbl.setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {

		String txt = value != null ? this.sdf.format(value) : "";
		this.lbl.setText(txt);
		return this.lbl;
	}

}

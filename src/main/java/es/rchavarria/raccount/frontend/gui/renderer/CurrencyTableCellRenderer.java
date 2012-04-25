package es.rchavarria.raccount.frontend.gui.renderer;

import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CurrencyTableCellRenderer implements TableCellRenderer {

    private JLabel lbl;
	private NumberFormat nf;

	public CurrencyTableCellRenderer() {
		this.nf = NumberFormat.getCurrencyInstance();
		this.lbl = new JLabel();
		this.lbl.setOpaque(true);
		this.lbl.setHorizontalAlignment(JLabel.RIGHT);
	}

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {

		String txt = value != null ? this.nf.format(value) : "";
		this.lbl.setText(txt);
		calculateBackgroundColor(txt);

		return this.lbl;
	}

	private void calculateBackgroundColor(final String txt) {
		float n = 0;
		try {
			n = this.nf.parse(txt).floatValue();
		} catch (ParseException e) {
		}

		Color clr = Color.WHITE;
		if (n < 0) {
			clr = Color.PINK;
		}
		this.lbl.setBackground(clr);
	}

}

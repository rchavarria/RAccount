package es.rchavarria.raccount.frontend.gui.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import es.rchavarria.raccount.model.Concept;

public class ConceptJListRenderer implements ListCellRenderer {
    private JLabel lbl;

    public ConceptJListRenderer() {
        lbl = new JLabel();
        lbl.setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(final JList list, final Object value, final int index,
            final boolean isSelected, final boolean cellHasFocus) {

        String txt = null;
        if (value != null) {
            Concept c = (Concept) value;
            txt = c.getIdConcept() + ": " + c.getName();
        } else {
            txt = "";
        }
        lbl.setText(txt);

        Color background = isSelected ? Color.LIGHT_GRAY : Color.WHITE;
        lbl.setBackground(background);

        return lbl;
    }
}

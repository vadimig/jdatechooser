/*
 * CaptionCellRenderer.java
 *
 * Created on 5 Ќо€брь 2006 г., 9:16
 *
 */

package datechooser.beans.customizer.render;

import datechooser.beans.customizer.*;
import java.awt.*;
import java.beans.*;
import javax.swing.*;

/**
 * Renderer for cells with property name.<br>
 * –исовальщик дл€ €чеек с названи€ми свойств.
 * @author Androsov Vadim
 * @since 1.0
 */
public class CaptionCellRenderer extends CellRenderer {
    
    private JLabel renderer;
    
    public CaptionCellRenderer(PropertyDescriptorsHolder holder) {
        super(holder);
        renderer = new JLabel();
        renderer.setOpaque(true);
    }
    
    protected Component getRenderer(
            PropertyDescriptor propertyDescriptor, PropertyEditorSupport propertyEditorSupport, 
            JTable table, boolean isSelected, boolean hasFocus) {
        String name = propertyDescriptor.getDisplayName();
        if (name == null) name = propertyDescriptor.getName();
        renderer.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        renderer.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
        renderer.setFont(table.getFont());
        renderer.setText(name);
        return renderer;
    }
}

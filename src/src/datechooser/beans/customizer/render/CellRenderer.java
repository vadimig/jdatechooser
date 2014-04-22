/*
 * CellRenderer.java
 *
 * Created on 5 Ќо€брь 2006 г., 8:45
 *
 */

package datechooser.beans.customizer.render;

import datechooser.beans.customizer.*;
import java.awt.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

/**
 * Renderer for all cells (properties and names).<br>
 * –исовальщик всех €чеек со свойствами (как названий, так и значений)
 * @author Androsov Vadim
 * @see CaptionCellRenderer
 * @see ValueCellRenderer
 * @since 1.0
 */
public abstract class CellRenderer implements TableCellRenderer {
    
    private PropertyDescriptorsHolder holder;
    
    public CellRenderer(PropertyDescriptorsHolder holder) {
        this.holder = holder;
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        String property = (String) value;
        return getRenderer(holder.getPropertydescriptor(property), 
                holder.getPropertyEditor(property), table, isSelected, hasFocus);
    }

    protected abstract Component getRenderer(
            PropertyDescriptor propertyDescriptor, PropertyEditorSupport propertyEditorSupport, 
            JTable table, boolean isSelected, boolean hasFocus);
}

/*
 * PropertyModel.java
 *
 * Created on 4 Ноябрь 2006 г., 16:22
 *
 */

package datechooser.beans.customizer;

import datechooser.beans.customizer.render.CaptionCellRenderer;
import datechooser.beans.customizer.render.ValueCellRenderer;
import javax.swing.table.DefaultTableModel;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * Customizer use table for properties visualization. This is its model.<br>
 * Настройщик использует таблицу для отображения свойств. Это ее модель.
 * Физически во всех ячейках хранятся имена соответсвующих свойств.
 * @author Androsov Vadim
 * @since 1.0
 */
public class PropertyTableModel extends DefaultTableModel {
    
    private PropertyDescriptorsHolder holder;
    
    
    public PropertyTableModel(PropertyDescriptorsHolder holder) {
        super(holder.getPropertyCount(), 2);
        this.holder = holder;
    }

    public Object getValueAt(int row, int column) {
        return holder.getPropertyDescriptors()[row].getName();
    }

    public boolean isCellEditable(int row, int column) {
        return column == 1;
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return CaptionCellRenderer.class;
            case 1: return ValueCellRenderer.class;
            default: return CaptionCellRenderer.class;
        }
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:  return getEditorLocaleString("property");
            case 1:  return getEditorLocaleString("value");
            default: return "?? (" + column + ')';
        }
    }
}

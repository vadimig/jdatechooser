package datechooser.beans;

import datechooser.model.multiple.PeriodSet;
import java.awt.*;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 * Table cell renderer based on datechooser bean.
 * @author Androsov Vadim
 * @since 1.1
 */
class BeanTableCellRenderer implements TableCellRenderer, BeanTableCell {
    
    private boolean useCalendarForSingleDate;
    private DateChooserVisual bean;
    private boolean commitLocked;
    private Border selectBorder;
    private Border focusBorder;
    private Border emptyBorder;
    
    public BeanTableCellRenderer(DateChooserVisual bean) {
        setUseCalendarForSingleDate(true);
        this.bean = bean;
        selectBorder = BorderFactory.createLineBorder(Color.BLUE, 1);
        focusBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        emptyBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        bean.setBorder(emptyBorder);
        if (isSelected) {
            bean.setBorder(selectBorder);
        }
        if (hasFocus) {
            bean.setBorder(focusBorder);
        }
        if (value instanceof Calendar) {
            Calendar cval = (Calendar) value;
            bean.setSelectedDate(cval);
        }    else if (value instanceof PeriodSet) {
            PeriodSet pval = (PeriodSet) value;
            bean.setSelection(pval);
        }
        return bean;
    }
    
    public boolean isUseCalendarForSingleDate() {
        return useCalendarForSingleDate;
    }
    
    public void setUseCalendarForSingleDate(boolean useCalendarForSingleDate) {
        this.useCalendarForSingleDate = useCalendarForSingleDate;
    }    
}
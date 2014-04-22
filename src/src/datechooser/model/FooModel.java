/*
 * FooModel.java
 *
 * Created on 7 Август 2006 г., 12:09
 *
 */

package datechooser.model;

import datechooser.model.multiple.PeriodSet;
import java.awt.Point;
import java.util.*;

/**
 * Foo model. No selection available.<br>
 * Модель пустышка. Не позволяет делать выбора вообще.
 * @author Androsov Vadim
 * @since 1.0
 */
public class FooModel extends AbstractDateChooseModel {
    
    private static final int CAPTION = 5;
    
    private int selRow = -1;
    private int selColumn = -1;
    
    public FooModel() {
        super(new GregorianCalendar(1982, 7, 26), DaysGrid.getRowsCount(), 
                DaysGrid.getColsCount());
        setConstraints(new GregorianCalendar(1982, 7, 10), null);
    }

    public boolean isSelected(Calendar aDate) {
        int day = aDate.get(Calendar.DAY_OF_MONTH); 
        if ((day >= 15) && (day <= 18)) {
            return true;
        } else {
            return false;
        }
    }

    public Calendar getSelectedDate() {
        return null;
    }

    protected void applySelection() {
    }

    public boolean isCursor(int row, int column) {
        if (isSomeSelected()) {
            return (row == selRow) && (column == selColumn);
        } else {
            return (row - column) == 1;
        }
    }

    public boolean select(int row, int column) {
        selRow = row;
        selColumn = column;
        firePropertyChange("selected", null, null);
        fireCursorMove();
        return true;
    }

    public boolean isSomeSelected() {
        return (selRow >=0) && (selColumn >= 0);
    }
    
    public CellState getSelectedCellState() {
        if (!isSomeSelected()) {
            return CellState.NORMAL;
        }
        return getCellState(selRow, selColumn);
    }
    
    public void setTypeSelected(int typeIndex) {
        if (typeIndex >= CAPTION) {
            select(-1, -1);
        } else {
            select(1 + typeIndex, typeIndex);
        } 
        
    }

    protected void selectColumn(int column) {
        
    }

    public void applySelectNothing() {
    }

    protected void refreshIncompatibility() {
    }

    public boolean isNothingSelected() {
        return false;
    }

    public void setNothingSelected(boolean nothingSelected) {
    }

}

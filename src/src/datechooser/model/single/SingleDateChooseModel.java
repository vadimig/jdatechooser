/*
 * DefaultSingleDateChooseModel.java
 *
 * Created on 21 Май 2006 г., 15:40
 *
 */

package datechooser.model.single;

import datechooser.model.*;
import java.util.Calendar;

/**
 * Model lets select only one date.
 * Not used in beans, but you may use it manually.<br>
 * Модель, позволяющая выбирать только одну дату.
 * В компонентах не используется. Можно подключить только вручную.
 * @see datechooser.model.DateChoose
 * @author Vadik
 */
public class SingleDateChooseModel
        extends AbstractDateChooseModel
        implements SingleDateChoose {
    
    private Calendar selected;
    private boolean nothingSelected;
    
    public SingleDateChooseModel(Calendar current) {
        super(current, DaysGrid.getRowsCount(), DaysGrid.getColsCount());
        selected = (Calendar) getCurrent().clone();
        setNothingSelected(false);
    }
    
    public boolean isSelected(Calendar date) {
        if (isNothingSelected()) return false;
        if (DateUtils.equals(date, selected)) {
            return true;
        }
        return false;
    }
    
    public Calendar getSelectedDate() {
        if (isNothingSelected()) return null;
        return getCurrent();
    }
    
    protected void applySelection() {
        if (isDateForbidden(getCurrent())) {
            return;
        }
        selected.setTime(getCurrent().getTime());
        setNothingSelected(false);
        firePropertyChange("selected", null, selected);
        fireSelectionChange();
        commit();
    }
    
    protected void selectColumn(int column) {
    }
    
    public void applySelectNothing() {
        boolean wasNothing = isNothingSelected();
        setNothingSelected(true);
        firePropertyChange("nothing", wasNothing, true);
        fireSelectionChange();
        commit();
    }
    
    public boolean isNothingSelected() {
        return nothingSelected;
    }
    
    public void setNothingSelected(boolean nothingSelected) {
        if ((!isNothingAllowed()) && nothingSelected) return;
        this.nothingSelected = nothingSelected;
    }
    
    protected void refreshIncompatibility() {
    }
    
}

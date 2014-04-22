/*
 * DefaultMultipleDateChooseModel.java
 *
 * Created on 3 Июль 2006 г., 11:12
 *
 */

package datechooser.model.multiple;

import datechooser.beans.editor.descriptor.DescriptionManager;
import datechooser.model.*;
import datechooser.model.exeptions.IncompatibleDataExeption;
import static datechooser.beans.locale.LocaleUtils.getErrorsLocaleString;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import javax.swing.JButton;

/**
 * Multy selection model. Used in all beans.<br>
 * Модель с возможность множественного выбора.
 * Используется во всех компонентах.
 * @author Androsov Vadim
 * @see datechooser.model.DateChoose
 * @see datechooser.model.multiple.MultyDateChoose
 * @since 1.0
 */

public class MultyDateChooseModel
        extends AbstractDateChooseModel
        implements MultyDateChoose {
    
    private PeriodSet selection;
    private PeriodSet defaultPeriods;
    private MultySelectModes mode;
    private Calendar previous;
    private MultyModelBehavior behavior;
    
    private boolean add;
    private boolean periodSelectionStarted;
    
    public MultyDateChooseModel(Calendar current) {
        super(current, DaysGrid.getRowsCount(), DaysGrid.getColsCount());
        setAdd(false);
        setBehavior(MultyModelBehavior.SELECT_ALL);
        setPeriodSelectionStarted(false);
        
        selection = new PeriodSet();
        defaultPeriods = new PeriodSet();
        defaultPeriods.add(super.getDefaultDate());
        
        selection.add(new Period(getCurrent()));
        
        previous = (Calendar) current.clone();
        mode = MultySelectModes.SINGLE;
    }
    
    public boolean isSelected(Calendar aDate) {
        return selection.contains(aDate);
    }
    
    /**
     * Last selected date.<br> 
     * Возвращает последнюю выбранную дату (для этой модели вернет то,
     * что скорее всего ожидалость, только если выбрана одна дата)
     */
    public Calendar getSelectedDate() {
        if (isNothingSelected()) return null;
        return getCurrent();
    }
    
    public void reset() {
        selection.clear();
    }
    
    public Iterable<Calendar> getSelectedDates() {
        return selection.getDates();
    }
    
    public Iterable<Period> getSelectedPeriods() {
        return selection.getPeriods();
    }
    
    public PeriodSet getSelectedPeriodSet() {
        return selection;
    }
    
    public void setSelectedPeriods(Iterable<Period> newSelection) {
        reset();
        if (newSelection != null) {
            selection.set(newSelection);
            select(selection.getFirstDate());
        }
        firePropertyChange("selected", null, null);
    }
    
    public void setSelectedPeriods(PeriodSet newSelection) {
        reset();
        if (newSelection != null) {
            selection.set(newSelection);
            select(selection.getFirstDate());
        }
        firePropertyChange("selected", null, null);
    }
    
    /**
     * Last selected period.<br> 
     * Возвращаем последний выбранный период.
     * Думается, это наиболее привлекательный метод работы с компонентом.
     */
    public Period getSelectedPeriod() {
        return selection.getLastAddedPeriod();
    }
    
    /**
     * Добавляет период в список выбранных, есле его не получится
     * объединить с существующий (т.е. если он не имеет точек пересечения
     * с текущими)
     */
    private void addPeriod(Period aPeriod) {
        if (isPeriodForbidden(aPeriod)) {
            return;
        }
        selection.add(aPeriod);
    }
    
    public void setMode(MultySelectModes mode, boolean add) {
        switch(getBehavior()) {
            case SELECT_SINGLE:
                this.mode = MultySelectModes.SINGLE;
                setAdd(false);
                return;
            case SELECT_PERIOD:
                this.mode = mode;
                setAdd(false);
                return;
            case SELECT_ALL:
                this.mode = mode;
                setAdd(add);
        }
    }
    
    
    public boolean isAdd() {
        return add;
    }
    
    public void setAdd(boolean add) {
        this.add = add;
    }
    
    public boolean isPeriodSelectionStarted() {
        return periodSelectionStarted;
    }
    
    public void setPeriodSelectionStarted(boolean periodSelectionStarted) {
        this.periodSelectionStarted = periodSelectionStarted;
    }
    
   protected void applySelection() {
        if (isDateForbidden(getCurrent())) {
            return;
        }
        if ((!isAdd()) && (!isPeriodSelectionStarted())) {
            reset();
       }
        switch (mode) {
            case SINGLE:
                addPeriod(new Period(getCurrent()));
                break;
            case PERIOD:
                setPeriodSelectionStarted(true);
                addPeriod(new Period(previous, getCurrent()));
                break;
        }
        previous.setTime(getCurrent().getTime());
        firePropertyChange("selected", null, null);
        fireSelectionChange();
        if (getBehavior().equals(MultyModelBehavior.SELECT_SINGLE)) {
            commit();
        }
    }
    
   public void setBehavior(MultyModelBehavior behavior) {
        this.behavior = behavior;
        if (behavior == MultyModelBehavior.SELECT_SINGLE) {
            if (!getSelection().isSingleDate()) {
                selectOneDate(getSelection().getFirstDate());
                firePropertyChange("behavior", null, null);
            }
        }
    }
    
    public MultyModelBehavior getBehavior() {
        return behavior;
    }
    
    public PeriodSet getDefaultPeriodSet() {
        return defaultPeriods;
    }
    
    public void setDefaultPeriodSet(PeriodSet periodSet) throws IncompatibleDataExeption {
        if (isPeriodSetForbidden(periodSet)) {
            throw new IncompatibleDataExeption(getErrorsLocaleString("Periods_forbidden"));
        }
        defaultPeriods.set(periodSet);
        setSelectedPeriods(defaultPeriods);
        firePropertyChange("defaultDates", null, null);
    }
    
    protected boolean isPeriodSetForbidden(PeriodSet periods) {
        for (Period period : periods.getPeriods()) {
            if (isPeriodForbidden(period)) return true;
        }
        return false;
    }
    
    public Calendar getDefaultDate() {
        return defaultPeriods.getCount() > 0 ? defaultPeriods.getFirstDate() : null;
    }
    
    public Iterable<Period> getDefaultPeriods() {
        return defaultPeriods.getPeriods();
    }
    
    public void setDefaultPeriods(Iterable<Period> newPeriods) throws IncompatibleDataExeption {
        defaultPeriods.clear();
        if (newPeriods != null) {
            defaultPeriods.set(newPeriods);
            Calendar defDate = defaultPeriods.getFirstDate();
            if (defDate != null) {
                setDefaultDate(defDate);
                select(defDate);
            }
            if (getBehavior() == MultyModelBehavior.SELECT_SINGLE) {
                selectOneDate(defDate);
            } else {
                setSelectedPeriods(newPeriods);
            }
        }
        firePropertyChange("defaultDates", null, null);
    }
    
    private void selectOneDate(Calendar aDate) {
        reset();
        addPeriod(new Period(aDate));
        firePropertyChange("selected", null, null);
    }
    
    public CellState getCellState(int row, int column) {
        CellState cellState = super.getCellState(row, column);
        if (cellState == CellState.NORMAL) {
            if (defaultPeriods != null) {
                if (defaultPeriods.contains(getCellDate(row, column))) {
                    return cellState.NOW;
                }
            }
        }
        return cellState;
    }
    
    protected void selectColumn(int column) {
    }
    
    public MultySelectModes getMode() {
        return mode;
    }
    
    protected PeriodSet getSelection() {
        return selection;
    }
    
    public void applySelectNothing() {
        reset();
        firePropertyChange("selected", null, null);
        fireSelectionChange();
        commit();
    }
    
    protected boolean isForbiddenDefault(PeriodSet forbiddenPeriods) {
        return getDefaultPeriodSet().intersects(forbiddenPeriods);
    }
    
    public boolean isNothingSelected() {
        if (selection == null) return true;
        return selection.isEmpty();
    }
    
    public void setNothingSelected(boolean nothingSelected) {
        if ((!isNothingAllowed()) && nothingSelected) return;
        if (nothingSelected) reset();
    }
    
    private boolean isTouchesSomeSelected(Period period) {
        return selection.near(period);
    }
}

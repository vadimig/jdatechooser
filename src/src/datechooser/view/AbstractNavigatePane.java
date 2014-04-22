/*
 * YearMonthPane.java
 *
 * Created on 26 Июль 2006 г., 21:00
 *
 */

package datechooser.view;

import datechooser.model.DateChoose;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Abstract navigate panel.
 * Placed over days selection grid and lets select year, month quickly and
 * make null selection.<br>
 * Обобщенная навигационная панель.
 * Находится над сеткой с днями и позволяет быстро перемещаться по месяцам и
 * годам, также позволяет сделать пустой выбор.
 * @author Androsov Vadim
 * @since 1.0
 */
public abstract class AbstractNavigatePane extends JPanel implements PropertyChangeListener {
    
    protected boolean editedManually;
    private DateChoose model;
    private Calendar curDate;
    protected String[] monthsList;
    private boolean localeChanged;
    private boolean nothingSelectEnabled;
    
    public AbstractNavigatePane() {
        setFont(new Font("Serif", Font.PLAIN, 12));
        editedManually = false;
        nothingSelectEnabled = true;
        OnShowChange listener = new OnShowChange();
        setLocaleChanged(true);
    }
    /**
     * Sets null selection allowed.<br> 
     * С помощью этого метода панель уведомляется, о разрешении пустого выбора.
     * Дело в том что пустой выбор можно сделать только с ее помощью и
     * при запрете этой операции нужно сделать недоступными соответсвтующие элементы управления.
     * @since 1.0
     */
    public abstract void applyNothingSelectEnabled(boolean enabled);
    /**
     * Updates month select component.<br>
     * Уведомление обновить состояние элемента для выбора месяца в соответствии с моделью.
     * @since 1.0
     */
    public abstract void updateMonthControl();
    /**
     * Get selected month.<br>
     * Должна возвращать выбранный месяц.
     * @since 1.0
     */
    public abstract int getMonth();
    /**
     * Get selected year.<br>
     * Должна возвращать выбранный год.
     * @since 1.0
     */
    public abstract int getYear();
    /**
     * Set month.<br>
     * Установить месяц.
     * @since 1.0
     */
    public abstract void setMonth(int aMonth);
    /**
     * Set year.<br>
     * Установить год.
     * @since 1.0
     */
    public abstract void setYear(int aYear);
    
    protected void initMonthList() {
        if (!isLocaleChanged()) return;
        editedManually = false;
        DateFormatSymbols dateSymbols = new DateFormatSymbols(getLocale());
        monthsList = dateSymbols.getMonths();
        updateMonthControl();
        setLocaleChanged(false);
        editedManually = true;
    }
    
    protected void fireMonthYearChanged() {
        try {
            editedManually = false;
            getModel().showMonthYear(
                    getMonth(),
                    getYear());
        } finally {
            refresh();
            editedManually = true;
        }
    }
    
    /**
     * Обновляет интерфейс в соответствии с текущей датой.
     * @since 1.0
     */
    protected void refresh() {
        curDate = getModel().getVisibleDate();
        setMonth(curDate.get(Calendar.MONTH));
        setYear(curDate.get(Calendar.YEAR));
    }
    
    /**
     * Обновляет интерфейс в соответствии с текущей датой в
     * автоматическом режиме (не ввод с клавиатуры).
     * @since 1.0
     */
    protected void autoRefresh() {
        editedManually = false;
        refresh();
        editedManually = true;
    }
    
    /**
     * Обновляет интерфейс в соответствии с текущей датой в
     * ручном режиме (ввод с клавиатуры).
     * @since 1.0
     */
    protected void manualRefresh() {
        editedManually = true;
        refresh();
    }
    
    protected void someChanged() {
        if (!editedManually) return;
        fireMonthYearChanged();
    }
    
    protected class OnShowChange
            implements ChangeListener, ActionListener {
        
        public void actionPerformed(ActionEvent e) {
            someChanged();
        }
        
        public void stateChanged(ChangeEvent e) {
            someChanged();
        }
    }
    
    protected DateChoose getModel() {
        return model;
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        autoRefresh();
    }
    
    public void setModel(DateChoose model) {
        if (getModel() == model) {
            return;
        }
        if (getModel() != null) {
            getModel().removePropertyChangeListener(this);
        }
        this.model = model;
        model.addPropertyChangeListener(this);
        setEnabled(getModel().isEnabled());
        autoRefresh();
    }
    
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }
    
    public void setLocale(Locale l) {
        if (getLocale().equals(l)) return;
        super.setLocale(l);
        setLocaleChanged(true);
        initMonthList();
    }
    
    private boolean isLocaleChanged() {
        return localeChanged;
    }
    
    private void setLocaleChanged(boolean localeChanged) {
        this.localeChanged = localeChanged;
    }
    
    public boolean isNothingSelectEnabled() {
        return nothingSelectEnabled;
    }
    
    public void setNothingSelectEnabled(boolean nothingSelectEnabled) {
        this.nothingSelectEnabled = nothingSelectEnabled;
        applyNothingSelectEnabled(nothingSelectEnabled);
    }
}


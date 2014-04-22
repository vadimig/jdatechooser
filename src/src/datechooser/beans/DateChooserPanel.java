/*
 * DateChooser.java
 *
 * Created on 15 Июль 2006 г., 11:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datechooser.beans;

import datechooser.controller.EventHandlerMultiply;
import datechooser.events.CommitListener;
import datechooser.events.CursorMoveListener;
import datechooser.events.SelectionChangedListener;
import datechooser.model.DateChoose;
import datechooser.model.exeptions.IncompatibleDataExeption;
import datechooser.model.multiple.MultyDateChooseModel;
import datechooser.model.multiple.MultyModelBehavior;
import datechooser.model.multiple.Period;
import datechooser.model.multiple.PeriodSet;
import datechooser.view.BackRenderer;
import datechooser.view.CalendarPane;
import datechooser.view.WeekDaysStyle;
import datechooser.view.appearance.*;
import java.awt.*;
import java.beans.*;
import java.io.Serializable;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Bean: date selection panel.<br>
 * Компонент "Панель для выбра дат"
 * @author Androsov Vadim
 * @since 1.0
 */
public class DateChooserPanel extends DateChooserVisual implements PropertyChangeListener {
    
    public static final long serialVersionUID = -267882659809359160L;
    
    /**
     * Panel property's name prefix.<br>
     * Приставка к названиям всех свойтсв панели.
     * @since 1.0
     */
    public static final String PANEL_PREFIX = PREFIX + "panel_";
    /**
     * Property name. <br>
     * Название свойства.
     * @see DateChooserPanel#setBorder(Border)
     * @since 1.0
     */
    public static final String PROPERTY_BORDER = PANEL_PREFIX + "border";
    
    private MultyDateChooseModel model;
    private CalendarPane calendarPane;
    
    public DateChooserPanel() {
        calendarPane = new CalendarPane();
        model = new MultyDateChooseModel(new GregorianCalendar());
        calendarPane.initialize(model, new EventHandlerMultiply());
        calendarPane.setPreferredSize(new Dimension(250, 180));
        calendarPane.addPropertyChangeListener(this);
        setLayout(new BorderLayout());
        add(calendarPane, BorderLayout.CENTER);
    }
    
    public boolean isAutoScroll() {
        return model.isAutoScroll();
    }

    public void setLocale(Locale locale) {
        super.setLocale(locale);
        calendarPane.setLocale(locale);
    }
    
    
    public boolean isDateChooserPanelProperty(String name) {
        if (name == null) return false;
        return name.startsWith(PREFIX);
    }
    
    public void setAutoScroll(boolean autoScroll) {
        boolean oldAutoScroll = isAutoScroll();
        model.setAutoScroll(autoScroll);
        firePropertyChange(PROPERTY_AUTOSCROLL, isAutoScroll(), autoScroll);
    }
    
    public boolean  isShowOneMonth() {
        return !model.isShowNeighbourMonth();
    }
    
    public void setShowOneMonth(boolean showOneMonth) {
        boolean oldShowOneMonth = isShowOneMonth();
        model.setShowNeighbourMonth(!showOneMonth);
        firePropertyChange(PROPERTY_ONE_MONTH, oldShowOneMonth, showOneMonth);
    }
    
    public boolean isEnabled() {
        return model.isEnabled();
    }
    
    public void setEnabled(boolean enabled) {
        boolean oldEnabled = isEnabled();
        model.setEnabled(enabled);
        firePropertyChange(PROPERTY_ENABLED, oldEnabled, enabled);
    }
    
    /**
     * Celendar panel border.<br>
     * Граница панели.
     * @since 1.0
     */
    public void setBorder(Border border) {
        Border oldBorder = getBorder();
        super.setBorder(border);
        firePropertyChange(PROPERTY_BORDER, oldBorder, getBorder());
    }
    
    public MultyModelBehavior getBehavior() {
        return model.getBehavior();
    }
    
    public void setBehavior(MultyModelBehavior behavior) {
        MultyModelBehavior oldBehavior = getBehavior();
        model.setBehavior(behavior);
        firePropertyChange(PROPERTY_BEHAVIOR, oldBehavior, behavior);
    }
    
    public AppearancesList getCurrentView() {
        return calendarPane.getAppearancesList();
    }
    
    public void setCurrentView(AppearancesList aList) {
        //Событие "изменение свойства" генерируется в методе предка.
        calendarPane.setAppearancesList(aList);
    }
    
    public PeriodSet getDefaultPeriods() {
        return model.getDefaultPeriodSet();
    }
    
    public void setDefaultPeriods(PeriodSet periods) throws IncompatibleDataExeption {
        PeriodSet oldDefault = getDefaultPeriods();
        model.setDefaultPeriodSet(periods);
        firePropertyChange(PROPERTY_DEFAULT_DATES, oldDefault, periods);
    }
    
    public Iterable<Period> getSelection() {
        return model.getSelectedPeriods();
    }
    
    public PeriodSet getSelectedPeriodSet() {
        return model.getSelectedPeriodSet();
    }
    
    public Calendar getSelectedDate() {
        return model.getSelectedDate();
    }
    
    public void setSelectedDate(Calendar aDate) {
        if (aDate == null) {
            model.selectNothing();
        } else {
            model.setSelectedDate(aDate);
        }
    }
    
    public void setSelection(Iterable<Period> periods) {
        if (periods == null) {
            model.selectNothing();
        } else {
            model.setSelectedPeriods(periods);
        }
    }
    
    public void setSelection(PeriodSet periods) {
        if (periods == null) {
            model.selectNothing();
        } else {
            model.setSelectedPeriods(periods);
        }
    }
    
    public PeriodSet getForbiddenPeriods() {
        return model.getForbiddenSet();
    }
    
    public void setForbiddenPeriods(PeriodSet periods) throws IncompatibleDataExeption {
        PeriodSet oldForbid = getForbiddenPeriods();
        model.setForbiddenSet(periods);
        firePropertyChange(PROPERTY_FORBID_DATES, oldForbid, periods);
    }
    
    public void setForbidden(Iterable<Period> forbiddenPeriods) {
        model.setForbidden(forbiddenPeriods);
    }
    
    public Calendar getMaxDate() {
        return model.getMaxConstraint();
    }
    
    public void setMaxDate(Calendar aDate) {
        Calendar oldMax = getMaxDate();
        model.setMaxConstraint(aDate);
        firePropertyChange(PROPERTY_MAX_DATE, oldMax, aDate);
    }
    
    public Calendar getMinDate() {
        return model.getMinConstraint();
    }
    
    public void setMinDate(Calendar aDate) {
        Calendar oldMin = getMinDate();
        model.setMinConstraint(aDate);
        firePropertyChange(PROPERTY_MIN_DATE, oldMin, aDate);
    }
    
    public void addCommitListener(CommitListener listener) {
        model.addCommitListener(listener);
    }
    
    public void removeCommitListener(CommitListener listener) {
        model.removeCommitListener(listener);
    }
    
    public void addSelectionChangedListener(SelectionChangedListener listener) {
        model.addSelectionChangedListener(listener);
    }
    
    public void removeSelectionChangedListener(SelectionChangedListener listener) {
        model.removeSelectionChangedListener(listener);
    }
    
    public void commit() {
        model.commit();
    }
    
    public boolean isLocked() {
        return model.isLocked();
    }
    
    public void setLocked(boolean lock) {
        boolean oldLock = isLocked();
        model.setLocked(lock);
        firePropertyChange(PROPERTY_LOCKED, oldLock, lock);
    }
    
    public void addCursorMoveListener(CursorMoveListener listener) {
        model.addCursorMoveListener(listener);
    }
    
    public void removeCursorMoveListener(CursorMoveListener listener) {
        model.removeCursorMoveListener(listener);
    }
    
    public Calendar getCurrent() {
        return model.getCurrent();
    }
    
    public boolean setCurrent(Calendar aDate) {
        Calendar oldDate = getCurrent();
        if (aDate == null) {
            model.selectNothing();
            return true;
        }
        boolean result = model.select(aDate);
        firePropertyChange(PROPERTY_CURRENT, oldDate, aDate);
        return result;
    }
    
    public void setNothingAllowed(boolean allow) {
        boolean oldAllow = isNothingAllowed();
        model.setNothingAllowed(allow);
        firePropertyChange(PROPERTY_NOTHING_ALLOWED, oldAllow, allow);
    }
    
    public boolean isNothingAllowed() {
        return model.isNothingAllowed();
    }
    
    public Color getCalendarBackground() {
        return calendarPane.getGridBackground();
    }
    
    public void setCalendarBackground(Color backColor) {
        Color was = getCalendarBackground();
        calendarPane.setGridBackground(backColor);
        firePropertyChange(PROPERTY_BACK_COLOR, was, backColor);
    }
    
    public Dimension getCalendarPreferredSize() {
        return getPreferredSize();
    }
    
    public void setCalendarPreferredSize(Dimension dim) {
        Dimension oldSize = getCalendarPreferredSize();
        setPreferredSize(dim);
        firePropertyChange(PROPERTY_CALENDAR_SIZE, oldSize, getCalendarPreferredSize());
    }

    public WeekDaysStyle getWeekStyle() {
        return calendarPane.getWeekStyle();
    }

    public void setWeekStyle(WeekDaysStyle weekStyle) {
        calendarPane.setWeekStyle(weekStyle);
    }

    public Font getNavigateFont() {
        return calendarPane.getNavigateFont();
    }

    public void setNavigateFont(Font font) {
        calendarPane.setNavigateFont(font);
    }

    public int getCurrentNavigateIndex() {
        return calendarPane.getCurrentNavigateIndex();
    }

    public void setCurrentNavigateIndex(int currentNavigateIndex) {
        calendarPane.setCurrentNavigateIndex(currentNavigateIndex);
    }

    public DateChoose getModel() {
        return model;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }

    public AppearancesList getAppearancesList() {
        return calendarPane.getAppearancesList();
    }
}


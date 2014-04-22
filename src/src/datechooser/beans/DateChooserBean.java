package datechooser.beans;

import datechooser.events.CommitListener;
import datechooser.events.CursorMoveListener;
import datechooser.events.SelectionChangedListener;
import datechooser.model.exeptions.IncompatibleDataExeption;
import datechooser.model.multiple.MultyModelBehavior;
import datechooser.model.multiple.Period;
import datechooser.model.multiple.PeriodSet;
import datechooser.view.WeekDaysStyle;
import datechooser.view.appearance.AppearancesList;
import java.awt.*;
import java.util.*;

/**
 * Common interface for all datechooser components.
 * @author Androsov Vadim
 * @since 1.0
 */
public interface DateChooserBean {
    
    
    /**
     * Property's name prefix.
     * @since 1.0
     */
    public static final String PREFIX = "dch_";
    
    /**
     * Property name.
     * @see DateChooserBean#isAutoScroll()
     * @since 1.0
     */
    public static final String PROPERTY_AUTOSCROLL = PREFIX + "autoScroll";
    
    /**
     * Property name.
     * @see DateChooserBean#getBehavior()
     * @since 1.0
     */
    public static final String PROPERTY_BEHAVIOR = PREFIX + "behavior";
    
    /**
     * Property name.
     * @see DateChooserBean#getCurrent()
     * @since 1.0
     */
    public static final String PROPERTY_CURRENT = PREFIX + "current";
    
    /**
     * Property name.
     * @see DateChooserBean#getDefaultPeriods()
     * @since 1.0
     */
    public static final String PROPERTY_DEFAULT_DATES = PREFIX + "defaultDates";
    
    /**
     * Property name.
     * @see DateChooserBean#isEnabled()
     * @since 1.0
     */
    public static final String PROPERTY_ENABLED = PREFIX + "enabled";
    
    /**
     * Property name.
     * @see DateChooserBean#getForbiddenPeriods()
     * @since 1.0
     */
    public static final String PROPERTY_FORBID_DATES = PREFIX + "forbidDates";
    
    /**
     * Property name.
     * @see DateChooserBean#isLocked()
     * @since 1.0
     */
    public static final String PROPERTY_LOCKED = PREFIX + "locked";
    
    /**
     * Property name.
     * @see DateChooserBean#getMaxDate()
     * @since 1.0
     */
    public static final String PROPERTY_MAX_DATE = PREFIX + "maxDate";
    
    /**
     * Property name.
     * @see DateChooserBean#getMinDate()
     * @since 1.0
     */
    public static final String PROPERTY_MIN_DATE = PREFIX + "minDate";
    
    /**
     * Property name.
     * @see DateChooserBean#isNothingAllowed()
     * @since 1.0
     */
    public static final String PROPERTY_NOTHING_ALLOWED = PREFIX + "nothingAllowed";
    
    /**
     * Property name.
     * @see DateChooserBean#isShowOneMonth()
     * @since 1.0
     */
    public static final String PROPERTY_ONE_MONTH = PREFIX + "oneMonth";
    
    /**
     * Property name.
     * @see DateChooserBean#getNavigateFont()
     * @since 1.0
     */
    public static final String PROPERTY_NAVIG_FONT = PREFIX + "navFont";
    
    /**
     * Property name.
     * @see DateChooserBean#getCurrentView()
     * @since 1.0
     */
    public static final String PROPERTY_VIEW = PREFIX + "view";
    
    /**
     * Property name.
     * @see DateChooserBean#getLocale()
     * @since 1.0
     */
    public static final String PROPERTY_LOCALE = PREFIX + "locale";
    
    /**
     * Property name.
     * @see DateChooserBean#getCurrentNavigateIndex()
     * @since 1.0
     */
    public static final String PROPERTY_NAVIG_PANE = PREFIX + "navigPane";
    
    /**
     * Property name.
     * @see DateChooserBean#getWeekStyle()
     * @since 1.0
     */
    public static final String PROPERTY_WEEK_STYLE = PREFIX + "weekDayStyle";
    
    /**
     * Property name.
     * @see DateChooserBean#getCalendarBackground()
     * @since 1.0
     */
    public static final String PROPERTY_BACK_COLOR = PREFIX + "backgroundColor";
    
    /**
     * Property name.
     * @see DateChooserBean#getCalendarPreferredSize()
     * @since 1.0
     */
    public static final String PROPERTY_CALENDAR_SIZE = PREFIX + "calSize";
    
    /**
     * Selection model: one date, single period, multy.
     * @see datechooser.model.multiple.MultyModelBehavior
     * @since 1.0
     */
    MultyModelBehavior getBehavior();
    
    /**
     * @see DateChooserBean#getBehavior()
     * @since 1.0
     */
    void setBehavior(MultyModelBehavior behavior);
    
    /**
     * Current calendar appearance.
     * @see datechooser.view.appearance.AppearancesList
     * @since 1.0
     */
    AppearancesList getCurrentView();
    
    /**
     * @see DateChooserBean#getCurrentView()
     * @since 1.0
     */
    void setCurrentView(AppearancesList aList);
    
    /**
     * Default date(s).
     * @see datechooser.model.multiple.PeriodSet
     * @see DateChooserBean#setDefaultPeriods(PeriodSet)
     * @since 1.0
     */
    PeriodSet getDefaultPeriods();
    
    /**
     * Set default period(s). You can not set forbidden dates as default ones,
     * but dates out of min/max values are acceptable.
     * @see DateChooserBean#getDefaultPeriods()
     * @see datechooser.model.multiple.PeriodSet
     * @see datechooser.model.exeptions.IncompatibleDataExeption
     * @throws datechooser.model.exeptions.IncompatibleDataExeption When default period includes forbidden dates.
     * @since 1.0
     */
    void setDefaultPeriods(PeriodSet periods) throws IncompatibleDataExeption;
    
    /**
     * Forbidden date(s).
     * @see datechooser.model.multiple.PeriodSet
     * @see DateChooserBean#setForbiddenPeriods(PeriodSet)
     * @since 1.0
     */
    PeriodSet getForbiddenPeriods();
    
    /**
     * Sets forbiddent date(s). You can not forbid default date(s).
     * @see DateChooserBean#getForbiddenPeriods()
     * @see datechooser.model.multiple.PeriodSet
     * @see datechooser.model.exeptions.IncompatibleDataExeption
     * @throws datechooser.model.exeptions.IncompatibleDataExeption If you are trying to forbid default date.<br>
     * @since 1.0
     */
    void setForbiddenPeriods(PeriodSet periods) throws IncompatibleDataExeption;

    /**
     * @see DateChooserBean#setForbiddenPeriods(PeriodSet)
     * @see datechooser.model.multiple.Period
     * @since 1.0
     */
    void setForbidden(Iterable<Period> forbiddenPeriods) throws IncompatibleDataExeption;
    
    /**
     * Maximal date user can select.
     * @see DateChooserBean#setMaxDate(Calendar)
     * @since 1.0
     */
    Calendar getMaxDate();
    
    /**
     * @see DateChooserBean#getMaxDate()
     * @since 1.0
     */
    void setMaxDate(Calendar aDate);
    
    /**
     * Minimal date user can select.
     * @see DateChooserBean#setMinDate(Calendar)
     * @since 1.0
     */
    Calendar getMinDate();
    
    /**
     * @see DateChooserBean#getMinDate()
     * @since 1.0
     */
    void setMinDate(Calendar aDate);
    
    /**
     * Selected date.
     * @return Selected date.
     * First date if some dates or period(s) selected
     * @since 1.0
     */
    Calendar getSelectedDate();
    
    /**
     * @see DateChooserBean#getSelectedDate()
     * @since 1.0
     */
    void setSelectedDate(Calendar aDate);
    
    /**
     * All selected dates.<br>
     * Множество выбранных дат.
     * @see datechooser.model.multiple.PeriodSet
     * @since 1.0
     */
    PeriodSet getSelectedPeriodSet();
    
    /**
     * @see DateChooserBean#getSelectedPeriodSet()
     * @since 1.0
     */
    void setSelection(PeriodSet periods);
    
    /**
     * All selected dates.<br> 
     * Множество выбранных дат.
     * @see datechooser.model.multiple.Period
     * @since 1.0
     */    
    Iterable<Period> getSelection();
    
    /**
     * @see DateChooserBean#getSelection()
     * @since 1.0
     */
    void setSelection(Iterable<Period> periods);
    
    /**
     * If true component automatically scrolls when date from the next month selected,
     * otherwise user can not select next month's date.<br>
     * Если данное свойство истинно, календарь автоматически прокручивается
     * при выборе даты из сосещнего месяца, иначе выбор дня из соседнего месяца
     * не допускается.
     * @since 1.0
     */
    boolean isAutoScroll();
   
    /**
     * @see DateChooserBean#isAutoScroll()
     * @since 1.0
     */
    void setAutoScroll(boolean autoScroll);
     
    /**
     * Is calendar enabled (allows date selection).
     * @since 1.0
     */
    boolean isEnabled();
   
    /**
     * @see DateChooserBean#isEnabled()
     * @since 1.0
     */
    void setEnabled(boolean enabled);
    
    /**
     * If true - you can use calendar only in readonly mode, you can move cursor,
     * scroll month and year, but can not select anything.
     * @since 1.0
     */
    boolean isLocked();
    
    /**
     * @see DateChooserBean#isLocked()
     * @since 1.0
     */
     void setLocked(boolean lock);
    
     /**
     * If true - days of next month are visible. Does not influence scroll property.
     * На прокрутку это свойство никак не влияет.
     * @since 1.0
     */
    boolean isShowOneMonth();
    
    /**
     * @see DateChooserBean#isShowOneMonth()
     * @since 1.0
     */
    void setShowOneMonth(boolean showOneMonth);
     
    /**
     * Weeddays output style: one letter, short, full.
     * @see datechooser.view.WeekDaysStyle
     * @since 1.0
     */
    public WeekDaysStyle getWeekStyle();
    
    /**
     * @see DateChooserBean#getWeekStyle()
     * @since 1.0
     */
    public void setWeekStyle(WeekDaysStyle weekStyle);
  
    /**
     * Navigate panel font.
     * @see DateChooserBean#setCurrentNavigateIndex(int)
     * @since 1.0
     */
    Font getNavigateFont();
    
    /**
     * 
     * @see DateChooserBean#getNavigateFont()
     * @since 1.0
     */
    void setNavigateFont(Font font);
    
    /**
     * Localization.
      * @since 1.0
     */
    Locale getLocale();
    
    /**
     * 
     * @since 1.0
     * @see DateChooserBean#getLocale()
     */
    void setLocale(Locale locale);
    
    /**
     * Navigation panels:<br>
     * 1) ComboBox for month selection, textfield for year,<br>
     * 2) Use only buttons. <br>
     * @since 1.0
     */
    int getCurrentNavigateIndex();
    
    /**
     * @see DateChooserBean#getCurrentNavigateIndex()
     * @since 1.0
     */
    void setCurrentNavigateIndex(int currentNavigateIndex);
    
    /**
     * Background color for calendar panel. Visible only if some of day cells 
     * are transparent.
     * выбора дня.
     * @since 1.0
     */
    Color getCalendarBackground();
    
    /**
     * @see DateChooserBean#getCalendarBackground()
     * @since 1.0
     */
    void setCalendarBackground(Color backColor);
    
    /**
     * Calendar panel preferred size.
     * @since 1.0
     */
    public Dimension getCalendarPreferredSize();
    
    /**
     * @see DateChooserBean#getCalendarPreferredSize()
     * @since 1.0
     */
    public void setCalendarPreferredSize(Dimension dim);
    
    /**
     * Current date (date under cursor).
     * @since 1.0
     */
    Calendar getCurrent();
    
    /**
     * @see DateChooserBean#getCurrent()
     * @since 1.0
     */
    boolean setCurrent(Calendar aDate);
    
    /**
     * @see DateChooserBean#isNothingAllowed()
     * @since 1.0
     */
    void setNothingAllowed(boolean allow);
    
    /**
     * Allows empty selection.
     * @since 1.0
     */
    boolean isNothingAllowed();
    
    /**
     * Gets skins list.
     * @since 1.1
     * @return Appearances list.
     */
    AppearancesList getAppearancesList();

    /**
     * Commits selection.
     * @since 1.0
     */
    void commit();
    
    /**
     * Adds listener for commit event.
     * @see datechooser.events.CommitListener
     * @see datechooser.events.CommitEvent
     * @since 1.0
     */
    void addCommitListener(CommitListener listener);
    
    /**
     * Removes listener for commit event.
     * @see datechooser.events.CommitListener
     * @see datechooser.events.CommitEvent
     * @since 1.0
     */
    void removeCommitListener(CommitListener listener);

    /**
     * Adds listener for selection changed event.
     * @see datechooser.events.SelectionChangedListener
     * @see datechooser.events.SelectionChangedEvent
     * @since 1.0
     */
    void addSelectionChangedListener(SelectionChangedListener listener);

    /**
     * Removes listener for selection change event.
     * @see datechooser.events.SelectionChangedListener
     * @see datechooser.events.SelectionChangedEvent
     * @since 1.0
     */
    void removeSelectionChangedListener(SelectionChangedListener listener);
    
    /**
     * Adds listener for cursor move event.
     * @see datechooser.events.CursorMoveListener
     * @see datechooser.events.CursorMoveEvent
     * @since 1.0
     */
    void addCursorMoveListener(CursorMoveListener listener);
    
    /**
     * Removes listener for cursor move event.
     * @see datechooser.events.CursorMoveListener
     * @see datechooser.events.CursorMoveEvent
     * @since 1.0
     */
    void removeCursorMoveListener(CursorMoveListener listener);
    
    /**
     * Clones bean.
     * @since 1.1
     */
    public DateChooserBean clone();
}

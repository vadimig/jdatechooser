/*
 * Компонент: раскрывающийся редактор.
 *
 * Created on 13 Октябрь 2006 г., 13:37
 *
 */

package datechooser.beans;

import datechooser.beans.pic.Pictures;
import datechooser.events.CommitEvent;
import datechooser.events.CommitListener;
import datechooser.events.CursorMoveListener;
import datechooser.events.SelectionChangedEvent;
import datechooser.events.SelectionChangedListener;

import datechooser.model.exeptions.IncompatibleDataExeption;
import datechooser.model.multiple.DateOutputStyle;
import datechooser.model.multiple.MultyModelBehavior;
import datechooser.model.multiple.Period;
import datechooser.model.multiple.PeriodSet;
import datechooser.view.WeekDaysStyle;
import datechooser.view.appearance.AppearancesList;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;

/**
 * Bean "Combo date editor". <br> 
 * Компонент: "Раскрываюийся редактор даты"
 * @author Androsov Vadim
 * @since 1.0
 */
public class DateChooserCombo extends DateChooserVisual {
    
    public static final long serialVersionUID = -7510689410235869108L;
    
    /**
     * Combo property's name prefix.<br>
     * Приставка к названиям всех свойтсв раскрывающегося редактора.
     * @since 1.0
     */
    public static final String COMBO_PREFIX = PREFIX + "combo_";
    
    /**
     * Property name. <br>
     * Название свойства.
     * @see DateChooserCombo#setBorder(Border)
     * @since 1.0
     */    
    public static final String PROPERTY_BORDER = COMBO_PREFIX + "border";
    
    /**
     * Property name. <br>
     * Название свойства.
     * @see DateChooserCombo#getFieldFont()
     * @since 1.0
     */    
    public static final String PROPERTY_FIELD_FONT = COMBO_PREFIX + "fieldFont";
    
    /**
     * Property name. <br>
     * Название свойства.
     * @see DateChooserCombo#getFormat()
     * @since 1.0
     */    
    public static final String PROPERTY_DATE_FORMAT = COMBO_PREFIX + "dateFormat";
    
    private static final int DROP_BUTTON_WIDTH = 25;
    private static final float GOLD = 0.62f;
    
    private DateChooserPanel chooser;
    private JPopupMenu menu;
    private JFormattedTextField field;
    private JButton bShowPopup;
    private PeriodSet selection;
    protected boolean autoEdit;
    
    private DateFormat dateFormat = null;
    
    public DateChooserCombo() {
        
        setAutoEdit(false);
        
        chooser = new DateChooserPanel();
        chooser.setCurrentNavigateIndex(1);
        chooser.addSelectionChangedListener(new OnSelectionChanged());
        chooser.addCommitListener(new CommitListener() {
            public void onCommit(CommitEvent evt) {
                menu.setVisible(false);
                dateToField();
            }
        });
        
        menu = new JPopupMenu();
        menu.add(chooser);
        menu.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuCanceled(PopupMenuEvent e) {
                restoreState();
                dateToField();
                commit();
            }
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                saveState();
            }
        });
        
        setLayout(new BorderLayout());
        field = new JFormattedTextField();
        
        bShowPopup = new JButton(new ImageIcon(
                Pictures.getResource("down.gif")));
        bShowPopup.setPressedIcon(new ImageIcon(
                Pictures.getResource("down_active.gif")));
        bShowPopup.setPreferredSize(new Dimension(DROP_BUTTON_WIDTH, field.getPreferredSize().height));
        add(field, BorderLayout.CENTER);
        add(bShowPopup, BorderLayout.EAST);
        
        setPreferredSize(new Dimension(
                (int)(chooser.getPreferredSize().width * GOLD),
                field.getPreferredSize().height));
        
        bShowPopup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDropDown();
            }
        });
//        bShowPopup.setBorderPainted(false);
        
        dateToField();
        testEditability();
        
        field.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }
            public void focusLost(FocusEvent e) {
                if (!isEditable()) return;
                fieldToDate();
            }
        });
        
        UIManager.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                SwingUtilities.updateComponentTreeUI(getComboPanel());
                SwingUtilities.updateComponentTreeUI(menu);
            }
        });
    }
    
    private DateChooserCombo getComboPanel() {
        return this;
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
    /**
     * Text in date field.<br>
     * Возвращает выбранную дату в виде строки в заданном формате.
     * @see DateChooserCombo#getFormat()
     * @return Selected date(s) in text format.<br>
     * Выбранные даты в текстовом формате.
     */
    public String getText() {
        return field.getText();
    }
    /**
     * Sets date from string, uses current format. Works only for single selection
     * behavior<br><br><CODE>
     *        dateChooserCombo.setBehavior(MultyModelBehavior.SELECT_SINGLE);<br>
     *        dateChooserCombo.setText("");
     * </CODE><br><br>
     * Устанавливает дату по строковому представлению.
     * Учитывается установленный формат.
     * Работает только для единичного выбора.
     * @param text Text to set. Null or empty string selects empty date.<br>
     * Текст с датой. Null или пустая строка позволяют сделать пустой выбор 
     * (не выбрать ни одной даты)
     * @return True if date was successfully set.<br>
     * Истина если значение даты было успешно установлено.
     * @see DateChooserCombo#getFormat()
     * @see datechooser.model.multiple.MultyModelBehavior
     */
    
    public boolean setText(String text) {
        if (!isEditable()) return false;
        if ((text == null) || (text.equals(""))) {
            chooser.getModel().selectNothing();
            field.setText("");
            return true;
        }
        field.setText(text);
        return fieldToDate();
    }
    
    private void showDropDown() {
        if (!isEnabled()) return;
        menu.show(getParent(), getX(), getY() + getHeight());
    }
    
    private void saveState() {
        selection = (PeriodSet) chooser.getSelectedPeriodSet().clone();
    }
    
    private void restoreState() {
        chooser.setSelection(selection);
    }
    
    public Dimension getCalendarPreferredSize() {
        return chooser.getCalendarPreferredSize();
    }

    public void setCalendarPreferredSize(Dimension dim) {
        chooser.setCalendarPreferredSize(dim);
        menu.revalidate();
    }
    /**
     * Date field font.<br> 
     * Шрифт поля, в котором выводится дата.
     * @since 1.0
     */
    public Font getFieldFont() {
        return field.getFont();
    }
    /**
     * @see DateChooserCombo#getFieldFont()
     */
    public void setFieldFont(Font font) {
        Font oldFont = getFieldFont();
        field.setFont(font);
        firePropertyChange(PROPERTY_FIELD_FONT, oldFont, getFieldFont());
    }
    /**
     * Date output format.<br>
     * Фомат вывода даты.
     * @see java.text.DateFormat
     * @since 1.0
     */
    public int getFormat() {
        return DateOutputStyle.getFormat();
    }
    /**
     * @see DateChooserCombo#getFormat()
     * @see java.text.DateFormat
     */
    public void setFormat(int format) {
        int oldFormat = getFormat();
        DateOutputStyle.setFormat(format);
        dateToField();
        firePropertyChange(PROPERTY_DATE_FORMAT, oldFormat, getFormat());
    }
 
    public MultyModelBehavior getBehavior() {
        return chooser.getBehavior();
    }
    
    public AppearancesList getCurrentView() {
        return chooser.getCurrentView();
    }
    
    public PeriodSet getDefaultPeriods() {
        return chooser.getDefaultPeriods();
    }
    
    public PeriodSet getForbiddenPeriods() {
        return chooser.getForbiddenPeriods();
    }
    
    public Calendar getMaxDate() {
        return chooser.getMaxDate();
    }
    
    public Calendar getMinDate() {
        return chooser.getMinDate();
    }
    
    public Calendar getSelectedDate() {
        return chooser.getSelectedDate();
    }
    
    public PeriodSet getSelectedPeriodSet() {
        return chooser.getSelectedPeriodSet();
    }
    
    public Iterable<Period> getSelection() {
        return chooser.getSelection();
    }
    
    public boolean isAutoScroll() {
        return chooser.isAutoScroll();
    }
    
    public boolean isShowOneMonth() {
        return chooser.isShowOneMonth();
    }
    
    public void setAutoScroll(boolean autoScroll) {
        chooser.setAutoScroll(autoScroll);
    }
    
    private void testEditability() {
        field.setEditable(isEditable());
    }
    
    private boolean  isEditable() {
        return getBehavior().equals(MultyModelBehavior.SELECT_SINGLE);
    }
    
    public void setBehavior(MultyModelBehavior behavior) {
        chooser.setBehavior(behavior);
        testEditability();
    }
    
    public void setCurrentView(AppearancesList aList) {
        chooser.setCurrentView(aList);
    }
    
    public void setDefaultPeriods(PeriodSet periods) throws IncompatibleDataExeption {
        chooser.setDefaultPeriods(periods);
        dateToField();
    }
    
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        chooser.setEnabled(enabled);
        field.setEnabled(enabled);
        bShowPopup.setEnabled(enabled);
    }
    
    public void setForbidden(Iterable<Period> forbiddenPeriods) {
        chooser.setForbidden(forbiddenPeriods);
    }
    
    public void setForbiddenPeriods(PeriodSet periods) throws IncompatibleDataExeption {
        chooser.setForbiddenPeriods(periods);
    }
    
    public void setMaxDate(Calendar aDate) {
        chooser.setMaxDate(aDate);
    }
    
    public void setMinDate(Calendar aDate) {
        chooser.setMinDate(aDate);
    }
    
    public void setSelectedDate(Calendar aDate) {
        chooser.setSelectedDate(aDate);
        dateToField();
    }
    
    public void setSelection(Iterable<Period> periods) {
        chooser.setSelection(periods);
        dateToField();
    }
    
    public void setSelection(PeriodSet periods) {
        chooser.setSelection(periods);
        dateToField();
    }
    
    public void setShowOneMonth(boolean showOneMonth) {
        chooser.setShowOneMonth(showOneMonth);
    }
    
    public Font getNavigateFont() {
        return chooser.getNavigateFont();
    }
    
    public void setNavigateFont(Font font) {
        chooser.setNavigateFont(font);
    }
    
    public void setLocale(Locale locale) {
        super.setLocale(locale);
        chooser.setLocale(locale);
        DateOutputStyle.setLocale(locale);
        dateToField();
    }
    
    public int getCurrentNavigateIndex() {
        return chooser.getCurrentNavigateIndex();
    }
    
    public void setCurrentNavigateIndex(int currentNavigateIndex) {
        if (currentNavigateIndex != 1) {
            //для другой панели возникают проблемы при выборе месяца -
            //меню закрывается :(
            return;
        }
        chooser.setCurrentNavigateIndex(currentNavigateIndex);
    }
    
    private void dateToField() {
        setAutoEdit(true);
        field.setText(chooser.getSelectedPeriodSet().toString(getDateFormat()));
        setAutoEdit(false);
    }
    
    private boolean fieldToDate() {
        if (isAutoEdit()) return true;
        try {
            Date dat = getDateFormat().parse(field.getText().trim());
            Calendar newDat = new GregorianCalendar();
            newDat.setTime(dat);
            setSelectedDate(newDat);
        } catch (ParseException ex) {
            dateToField();
            return false;
        }
        return true;
    }
    
    private class OnSelectionChanged implements SelectionChangedListener {
        public void onSelectionChange(SelectionChangedEvent evt) {
            dateToField();
        }
    }
    
    public void addCommitListener(CommitListener listener) {
        chooser.addCommitListener(listener);
    }
    
    public void removeCommitListener(CommitListener listener) {
        chooser.removeCommitListener(listener);
    }
    
    public void addSelectionChangedListener(SelectionChangedListener listener) {
        chooser.addSelectionChangedListener(listener);
    }
    
    public void removeSelectionChangedListener(SelectionChangedListener listener) {
        chooser.removeSelectionChangedListener(listener);
    }
    
    public void commit() {
        chooser.commit();
    }
    
    private boolean isAutoEdit() {
        return autoEdit;
    }
    
    private void setAutoEdit(boolean autoEdit) {
        this.autoEdit = autoEdit;
    }
    
    public boolean isLocked() {
        return chooser.isLocked();
    }
    
    public void setLocked(boolean lock) {
        chooser.setLocked(lock);
    }
    
    public WeekDaysStyle getWeekStyle() {
        return chooser.getWeekStyle();
    }
    
    public void setWeekStyle(WeekDaysStyle weekStyle) {
        chooser.setWeekStyle(weekStyle);
    }
    
    public void addCursorMoveListener(CursorMoveListener listener) {
        chooser.addCursorMoveListener(listener);
    }
    
    public void removeCursorMoveListener(CursorMoveListener listener) {
        chooser.removeCursorMoveListener(listener);
    }
    
    public Calendar getCurrent() {
        return chooser.getCurrent();
    }
    
    public boolean setCurrent(Calendar aDate) {
        return chooser.setCurrent(aDate);
    }
    
    public void setNothingAllowed(boolean allow) {
        chooser.setNothingAllowed(allow);
    }
    
    public boolean isNothingAllowed() {
        return chooser.isNothingAllowed();
    }

    public Color getCalendarBackground() {
        return chooser.getCalendarBackground();
    }

    public void setCalendarBackground(Color backColor) {
        chooser.setCalendarBackground(backColor);
    }

    public AppearancesList getAppearancesList() {
        return chooser.getAppearancesList();
    }

    /**
     * Get date format. If you used setDateFormat with not null parameter, 
     * setFormat will be ignored. EXPERIMENTAL!<br>
     * Экспериментальная функция. Если установлено не null - настройка с помощью метода setFormat
     * (а значит и все визуальные настройки этого свойства) будут игнорироваться. 
     * В следующих версиях политика выбора формата будет более основательно продумана.
     * @since 1.1
     */
    public DateFormat getDateFormat() {
        if (dateFormat != null) return dateFormat;
        DateFormat fmt = DateFormat.getDateInstance(getFormat(), getLocale());
        return fmt;
    }

    /**
     * Set date format. If you set not null setFormat will be ignored. 
     * To turn on setFormat (and visual format property customization)
     * call <b>setDateFormat(null)</b>. EXPERIMENTAL!<br>
     * Экспериментальная функция. Позволяет установить формат вывода и ввода
     * даты. Если установлено не null - настройка с помощью метода setFormat
     * (а значит и все визуальные настройки этого свойства) будут игнорироваться.
     * Установите null чтобы метод setFormat заработал по-старому.
     * @since 1.1
     */
    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        dateToField();
    }
}

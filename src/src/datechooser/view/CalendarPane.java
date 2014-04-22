/*
 * CalendarPane.java
 *
 * Created on 23 Май 2006 г., 12:02
 *
 */

package datechooser.view;

import datechooser.beans.DateChooserBean;
import datechooser.controller.DateChooseController;
import datechooser.model.DateChoose;
import datechooser.view.appearance.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Date selection panel. DateChooserPanel simply extends it and provides
 * some simple configuration methods.<br>
 * Панель для выбора даты. Соответствующий компонент просто наследует ее
 * и предоставляет ряд удобных методов изменения свойств.
 * @author Androsov Vadim
 * @since 1.0
 */
public class CalendarPane extends JPanel implements PropertyChangeListener {
    
    private GridPane gp;
    private AbstractNavigatePane[] navigPanes;
    private DateChoose model;
    private Locale locale;
    private int currentNavigateIndex = 0;
    
    public void initialize(DateChoose model, DateChooseController controller) {
        setPreferredSize(new Dimension(100, 100));
        gp = new GridPane();
        setLayout(new BorderLayout());
        navigPanes = new AbstractNavigatePane[] {new ComboNavigatePane(),
        new ButtonNavigatePane()};
        setNavigateFont(new Font("serif", Font.PLAIN, 11));
        setModel(model);
        gp.setModel(model);
        setLocale(Locale.getDefault());
        
        gp.setController(controller);
        add(gp, BorderLayout.CENTER);
        setCurrentNavigateIndex(0);
        add(getCurrentNavigPane(), BorderLayout.NORTH);
    }
    
    private AbstractNavigatePane getCurrentNavigPane() {
        return navigPanes[getCurrentNavigateIndex()];
    }
    
    private void setAllNavigCurrentModel() {
        for (int i = 0; i < navigPanes.length; i++) {
            navigPanes[i].setModel(getModel());
        }
    }
    
    public void reInitialize(DateChoose model, DateChooseController controller) {
        if (gp == null) return;
        setModel(model);
        gp.setModel(model);
        gp.setController(controller);
    }
    
    public CalendarPane(DateChoose model, DateChooseController controller) {
        initialize(model, controller);
    }
    
    public CalendarPane() {
    }
    
    public DateChoose getModel() {
        return model;
    }
    
    public void setModel(DateChoose model) {
        DateChoose oldModel = getModel();
        if (getModel() != null) {
            getModel().removePropertyChangeListener(this);
        }
        this.model = model;
        setAllNavigCurrentModel();
        getModel().addPropertyChangeListener(this);
        firePropertyChange("model", oldModel, model);
    }
    
    public Font getNavigateFont() {
        return getCurrentNavigPane().getFont();
    }
    
    public void setNavigateFont(Font font) {
        Font oldFont = getNavigateFont();
        for (int i = 0; i < navigPanes.length; i++) {
            navigPanes[i].setFont(font);
        }
        updateUI();
        firePropertyChange(DateChooserBean.PROPERTY_NAVIG_FONT, oldFont, font);
    }
    
    public ViewAppearance getCurrentCellAppearance() {
        return gp.getAppearance();
    }
    
    public AppearancesList getAppearancesList() {
        return gp.getAppearanceList();
    }
    
    public void setAppearancesList(AppearancesList aList) {
        AppearancesList oldView = getAppearancesList();
        gp.setAppearanceList(aList);
        firePropertyChange(DateChooserBean.PROPERTY_VIEW, oldView, aList);
    }
    
    public Locale getLocale() {
        return locale;
    }
    
    public void setLocale(Locale locale) {
        if ((getLocale() != null) && (getLocale().equals(locale))) return;
        Locale oldLocale = getLocale();
        this.locale = locale;
        model.setLocale(locale);
        gp.setLocale(locale);
        for (AbstractNavigatePane pane : navigPanes) {
            pane.setLocale(locale);
        }
        firePropertyChange(DateChooserBean.PROPERTY_LOCALE, oldLocale, locale);
    }
    
    public int getCurrentNavigateIndex() {
        return currentNavigateIndex;
    }
    
    public void setCurrentNavigateIndex(int currentNavigateIndex) {
        int newPaneIndex = 0;
        if ((currentNavigateIndex >= 0) && (currentNavigateIndex < navigPanes.length)) {
            newPaneIndex = currentNavigateIndex;
        }
        if (newPaneIndex != getCurrentNavigateIndex()) {
            int oldIndex = getCurrentNavigateIndex();
            remove(getCurrentNavigPane());
            this.currentNavigateIndex = currentNavigateIndex;
            add(getCurrentNavigPane(), BorderLayout.NORTH);
            revalidate();
            getCurrentNavigPane().setEnabled(getModel().isEnabled());
            firePropertyChange(DateChooserBean.PROPERTY_NAVIG_PANE, oldIndex, newPaneIndex);
        }
    }
    
    public WeekDaysStyle getWeekStyle() {
        return gp.getWeekStyle();
    }
    
    public void setWeekStyle(WeekDaysStyle weekStyle) {
        WeekDaysStyle oldStyle = getWeekStyle();
        gp.setWeekStyle(weekStyle);
        firePropertyChange(DateChooserBean.PROPERTY_WEEK_STYLE, oldStyle, weekStyle);
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        DateChoose model = getModel();
        gp.setEnabled(model.isEnabled());
        AbstractNavigatePane navig = getCurrentNavigPane();
        navig.setNothingSelectEnabled(model.isNothingAllowed());
        navig.setEnabled(model.isEnabled());
    }
    
    public void setGridBackground(Color color) {
        gp.setBackground(color);
    }
    
    public Color getGridBackground() {
        return gp.getBackground();
    }
}

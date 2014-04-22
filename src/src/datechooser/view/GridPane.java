/*
 * GridPane.java
 *
 * Created on 21 Май 2006 г., 1:32
 *
 */

package datechooser.view;

import datechooser.controller.DateChooseController;
import datechooser.controller.FooEventHandler;
import datechooser.model.DateChoose;
import datechooser.model.FooModel;
import datechooser.view.appearance.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.beans.*;
import java.io.*;
import java.text.DateFormatSymbols;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Days grid panel.<br>
 * Панель с сеткой дней.
 * @author Androsov Vadim
 * @since 1.0
 */
public class GridPane extends JPanel implements PropertyChangeListener {
    
    private static final int TEXT_SHIFT = 8;
    
    private DateChoose model;
    private DateChooseController controller;
    private AppearancesList appearance;
    private transient CellAppearance currentAppearance;
    private String[] weekDays;
    private Locale locale;
    
    private boolean focused;
    private boolean autoFontSize;
    private WeekDaysStyle weekStyle;
    private Rectangle cell;
    
    
    private transient int rows;
    private transient int cols;
    private transient int cellWidth;
    private transient int cellHeight;
    private transient int widthShift;
    private transient int heightShift;
    private transient int widthCounter;
    private transient int heightCounter;
    private transient int curWidth;
    private transient int curHeight;
    
    
    public GridPane() {
        this(new AppearancesList());
    }
    
    public GridPane(AppearancesList appearanceList) {
        setAppearanceList(appearanceList);
        getAppearance().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                firePropertyChange("currentAppearance", null, null);
                repaint();
            }
        });
        
        setFocused(false);
        setEnabled(true);
        
        addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                setFocused(true);
            }
            public void focusLost(FocusEvent e) {
                setFocused(false);
            }
        });
        
        cell = new Rectangle();
        
        
        setFocusable(true);
        
        setModel(new FooModel());
        setController(new FooEventHandler());
        locale = Locale.getDefault();
        weekStyle = WeekDaysStyle.NORMAL;
        initWeekDays();
    }
    
    public void setLocale(Locale l) {
        if (getLocale().equals(l)) return;
        super.setLocale(l);
        locale = l;
        initWeekDays();
        repaint();
    }
    
    public Locale getLocale() {
        return locale;
    }
    
    private void initWeekDays() {
        DateFormatSymbols dateSymbols = new DateFormatSymbols(getLocale());
        String[] allDays = null;
        switch (getWeekStyle()) {
            case FULL:
                allDays = dateSymbols.getWeekdays();
                break;
            case NORMAL:
                allDays = dateSymbols.getShortWeekdays();
                break;
            case SHORT:
                allDays = dateSymbols.getShortWeekdays();
                for (int i = 0; i < allDays.length; i++) {
                    if (allDays[i].length() < 2) continue;
                    allDays[i] = allDays[i].substring(0, 1).toLowerCase();
                }
                break;
        }
        weekDays = new String[allDays.length - 1];
        int firstWD = Calendar.getInstance(getLocale()).getFirstDayOfWeek();
        for (int i = 0; i < allDays.length - 1; i++) {
            weekDays[i] = allDays[firstWD];
            if (firstWD < (allDays.length - 1)) {
                firstWD++;
            } else {
                firstWD = 1;
            }
        }
        allDays = null;
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        getController().reBound();
        
        Rectangle bounds = getBounds();
        
        BackRenderer renderer = getAppearance().isSupportsTransparency() ?
            getAppearance().getRenderer() : null;
        if (renderer != null) renderer.render(g2d, bounds);
        
        rows = getModel().getRowsCount() + 1;
        cols = getModel().getColsCount();
        
        cellWidth = (int)bounds.getWidth() / cols;
        cellHeight = (int)bounds.getHeight() / rows;
        
        widthShift =  (int)bounds.getWidth() % cols;
        heightShift = (int)bounds.getHeight() % rows;
        
//        g2d.translate(widthShift, heightShift);
        
        curWidth = 0;
        
        widthCounter =  widthShift;
        heightCounter = heightShift;
        curHeight = cellHeight + ((heightCounter--) > 0 ? 1 : 0);
        for (int c = 0; c < cols; c++) {
            curWidth = cellWidth + ((widthCounter--) > 0 ? 1 : 0);
            paintCaption(g2d, c, curWidth, curHeight);
            g2d.translate(curWidth, 0);
        }
        g2d.translate(-cellWidth * cols - widthShift, curHeight);
        
        widthCounter =  widthShift;
        for (int i = 1; i < rows; i++) {
            curHeight = cellHeight + ((heightCounter--) > 0 ? 1 : 0);
            for (int j = 0; j < cols; j++) {
                curWidth = cellWidth + ((widthCounter--) > 0 ? 1 : 0);
                paintCell(g2d, i - 1 , j, curWidth, curHeight);
                if (j == (cols - 1)) {
                    g2d.translate(-cellWidth * (cols - 1) - widthShift, curHeight);
                    widthCounter =  widthShift;
                } else {
                    g2d.translate(curWidth, 0);
                }
            }
        }
    }
    
    private void paintCell(Graphics2D g2d, int row, int column, int width, int height) {
        boolean isText = true;
        switch (getModel().getCellState(row, column)) {
            case NORMAL :
                currentAppearance = getAppearance().getUsual();
                break;
            case NOW :
                currentAppearance = getAppearance().getNow();
                break;
            case SELECTED :
                currentAppearance = getAppearance().getSelected();
                break;
            case UNACCESSIBLE:
                currentAppearance = getAppearance().getDisabled();
                break;
            case NORMAL_SCROLL:
                isText = getModel().isShowNeighbourMonth();
                currentAppearance = getAppearance().getScroll();
                break;
        }
        if (currentAppearance == null) {
            return;
        }
        currentAppearance.render(g2d, this,
                isText ? getModel().getCellCaption(row, column) : "",
                width, height, isFocused() && getModel().isCursor(row, column));
        
    }
    
    private void paintCaption(Graphics2D g2d, int c, int width, int height) {
        getAppearance().getCaption().render(g2d, this, weekDays[c], width, height);
    }
    
    public DateChooseController getController() {
        return controller;
    }
    
    public void setController(DateChooseController controller) {
        this.controller = controller;
        controller.setView(this);
    }
    
    public DateChoose getModel() {
        return model;
    }
    
    public void setModel(DateChoose model) {
        if (getModel() != null) {
            getModel().removePropertyChangeListener(this);
        }
        this.model = model;
        getModel().addPropertyChangeListener(this);
        
    }
    
    public boolean isFocused() {
        return focused;
    }
    
    public void setFocused(boolean focused) {
        this.focused = focused;
        repaint();
    }
    
    public boolean isAutoFontSize() {
        return autoFontSize;
    }
    
    public void setAutoFontSize(boolean autoFontSize) {
        this.autoFontSize = autoFontSize;
    }
    
    public ViewAppearance getAppearance() {
        return appearance.getCurrent();
    }
    
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setFocusable(enabled);
    }
    
    public AppearancesList getAppearanceList() {
        return appearance;
    }
    
    public void setAppearanceList(AppearancesList appearance) {
        this.appearance = appearance;
    }
    
    public WeekDaysStyle getWeekStyle() {
        return weekStyle;
    }
    
    public void setWeekStyle(WeekDaysStyle weekStyle) {
        if (weekStyle == this.weekStyle) return;
        this.weekStyle = weekStyle;
        initWeekDays();
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }
    
//    private void writeObject(ObjectOutputStream out) throws IOException {
//        out.defaultWriteObject();
//    }
//    
//    private void readObject(ObjectInputStream in)
//    throws IOException, ClassNotFoundException {
//        in.defaultReadObject();
//    }
}

/*
 * YearMonthPane.java
 *
 * Created on 26 Июль 2006 г., 21:00
 *
 */

package datechooser.view;

import datechooser.model.AbstractDateChooseModel;
import datechooser.model.DateChoose;
import datechooser.view.pic.ViewPictures;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

/**
 * Combo Navigation panel.<br>
 * Навигационная панель с выпадающими списком для выбора месяца и полем для выбора года.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.view.AbstractNavigatePane
 */
public class ComboNavigatePane extends AbstractNavigatePane {
    
    private static int YEAR_SHIFT = 10000;
    
    private JComboBox months;
    private JSpinner year;
    private Calendar curDate;
    private SpinnerNumberModel yearModel;
    private JButton nullButton;
    
    public ComboNavigatePane() {
//        super();
        months  = new JComboBox();
        yearModel = new SpinnerNumberModel();
        year = new JSpinner(yearModel);
        nullButton = createNullButton();
        
        yearModel.setStepSize(1);
        setLayout(new BorderLayout());
        
        JPanel mainPane = new JPanel(new GridLayout(1,2));
        mainPane.add(months);
        mainPane.add(year);
        
        add(mainPane, BorderLayout.CENTER);
        add(nullButton, BorderLayout.WEST);
        editedManually = false;
        initMonthList();
        OnShowChange listener = new OnShowChange();
        months.addActionListener(listener);
        year.addChangeListener(listener);
        months.setEditable(false);
    }
    
    private JButton createNullButton() {
        JButton newNullButton = new JButton(new ImageIcon(
                ViewPictures.class.getResource("nothing.gif")));
        newNullButton.setFocusable(false);
        newNullButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getModel().selectNothing();
            }
        });
        return newNullButton;
    }
    
    public int getMonth() {
        return months.getSelectedIndex();
    }
    
    public int getYear() {
        return (Integer)yearModel.getValue();
    }
    
    public void setMonth(int aMonth) {
        months.setSelectedIndex(aMonth);
    }
    
    public void setYear(int aYear) {
        yearModel.setValue(aYear);
    }
    
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        months.setEnabled(enabled);
        year.setEnabled(enabled);
        nullButton.setEnabled(isNothingSelectEnabled() ? enabled : false);
    }
    
    public Font getFont() {
        if ((year == null) || (months == null) || (nullButton == null)) return null;
        return months.getFont();
    }
    
    public void setFont(Font font) {
        if ((year == null) || (months == null)) return;
        try {
            ((JSpinner.DefaultEditor) (year.getEditor())).getTextField().setFont(font);
        } catch (ClassCastException ex) {
            year.setFont(font);
        }
        months.setFont(font);
        nullButton.setFont(font);
    }
    
    public void updateMonthControl() {
        if (months == null) return;
        int selected = months.getSelectedIndex();
        months.removeAllItems();
        for (int i = 0; i < 12; i++) {
            months.addItem(monthsList[i]);
        }
        months.validate();
        months.setSelectedIndex(selected);
    }

    public void applyNothingSelectEnabled(boolean enabled) {
//        boolean wasEnabled = nullButton.isEnabled();
        nullButton.setEnabled(enabled);
//        firePropertyChange("enabled", wasEnabled, enabled);
    }
}


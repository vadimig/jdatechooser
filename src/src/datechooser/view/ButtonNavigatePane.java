/*
 * GraphNavigatePane.java
 *
 * Created on 11 Август 2006 г., 21:31
 *
 */

package datechooser.view;

import datechooser.view.pic.ViewPictures;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Button navigation panel.<br>
 * Кнопочная навигационная панель.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.view.AbstractNavigatePane
 */
public class ButtonNavigatePane extends AbstractNavigatePane {
    
    private static final int BORDER = 3;
    private JLabel showYearMonth;
    private int currentMonth;
    private int currentYear;
    private JButton bPrevYear;
    private JButton bPrevMonth;
    private JButton bNothing;
    private JButton bNextMonth;
    private JButton bNextYear;
    
    public ButtonNavigatePane() {
        showYearMonth = new JLabel("", JLabel.CENTER);
        initMonthList();
        showYearMonth.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        JPanel pRight = new JPanel(new GridLayout(1, 5));
        
        OnClick onClick = new OnClick();
        bPrevYear = new JButton(new ImageIcon(
                ViewPictures.class.getResource("prev_year.gif")));
        bPrevYear.addActionListener(onClick);
        
        bPrevMonth = new JButton(new ImageIcon(
                ViewPictures.class.getResource("prev_month.gif")));
        bPrevMonth.addActionListener(onClick);
        
        bNothing = new JButton(new ImageIcon(
                ViewPictures.class.getResource("nothing.gif")));
        bNothing.addActionListener(onClick);
        
        bNextMonth = new JButton(new ImageIcon(
                ViewPictures.class.getResource("next_month.gif")));
        bNextMonth.addActionListener(onClick);
        
        bNextYear = new JButton(new ImageIcon(
                ViewPictures.class.getResource("next_year.gif")));
        bNextYear.addActionListener(onClick);
        
        pRight.add(bPrevYear);
        pRight.add(bPrevMonth);
        pRight.add(bNothing);
        pRight.add(bNextMonth);
        pRight.add(bNextYear);
        
        setLayout(new BorderLayout(5, 0));
        add(showYearMonth, BorderLayout.WEST);
        add(pRight, BorderLayout.CENTER);
    }
    
    private String getDateText() {
        return monthsList[currentMonth] + " " + currentYear;
    }
    
    public void updateMonthControl() {
        showYearMonth.setText(getDateText());
    }
    
    public int getMonth() {
        return currentMonth;
    }
    
    public int getYear() {
        return currentYear;
    }
    
    public void setMonth(int aMonth) {
        currentMonth = aMonth;
        updateMonthControl();
    }
    
    public void setYear(int aYear) {
        currentYear = aYear;
        updateMonthControl();
    }
    
    public void setFont(Font font) {
        super.setFont(font);
        if (showYearMonth != null) {
            showYearMonth.setFont(font);
        }
    }
    
    private class OnClick implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton src = (JButton) e.getSource();
            if (src == bNothing) {
                getModel().selectNothing();
                return;
            }
            if (src == bPrevYear) {
                currentYear--;
            }
            if (src == bNextYear) {
                currentYear++;
            }
            if (src == bPrevMonth) {
                if (currentMonth > 0) {
                    currentMonth--;
                } else {
                    currentMonth = 11;
                }
            }
            if (src == bNextMonth) {
                if (currentMonth < 11) {
                    currentMonth++;
                } else {
                    currentMonth = 0;
                }
            }
            someChanged();
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        bPrevYear.setEnabled(enabled);
        bPrevMonth.setEnabled(enabled);
        bNextMonth.setEnabled(enabled);
        bNextYear.setEnabled(enabled);
        bNothing.setEnabled(isNothingSelectEnabled() ? enabled : false);
    }

    public void applyNothingSelectEnabled(boolean enabled) {
//        boolean wasEnabled = bNothing.isEnabled();
        bNothing.setEnabled(enabled);
//        firePropertyChange("enabled", wasEnabled, enabled);
    }
}

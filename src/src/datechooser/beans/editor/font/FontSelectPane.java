/*
 * FontSelectPane.java
 *
 * Created on 31 »юль 2006 г., 6:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datechooser.beans.editor.font;

import datechooser.events.*;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Font selection panel.<br>
 * ѕанель выбора параметров шрифта.
 * @author Androsov Vadim
 * @since 1.0
 */
class FontSelectPane extends JPanel {
    
    public static final String FONT_EVENT_NAME = "font";
    
    public static int MIN_FONT_SIZE = 3;
    public static int MAX_FONT_SIZE = 96;
    
    private Font font;
    private JComboBox selFont;
    private JCheckBox selStyleBold;
    private JCheckBox selStyleItalic;
    private SpinnerNumberModel selSize;
    private JSpinner selSizeSpinner;
    
    
    public FontSelectPane(Font font) {
        
        selFont = new JComboBox(new  DefaultComboBoxModel(getAllFonts()));
        selStyleBold = new JCheckBox(getEditorLocaleString("Bold"));
        selStyleItalic = new JCheckBox(getEditorLocaleString("Italic"));
        selSize = new SpinnerNumberModel(font.getSize(),
                MIN_FONT_SIZE, MAX_FONT_SIZE, 1);
        selSizeSpinner = new JSpinner(selSize);
        
        setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel style = new JPanel(new GridLayout(1,2));
        style.add(selStyleBold);
        style.add(selStyleItalic);
        add(selFont);
        add(style);
        add(selSizeSpinner);
        
        OnChange changeHandler = new OnChange();
        selFont.addActionListener(changeHandler);
        selStyleBold.addActionListener(changeHandler);
        selStyleItalic.addActionListener(changeHandler);
        selSize.addChangeListener(changeHandler);
        
        setSelectedFont(font);
    }
    
    public Font getSelectedFont() {
        Font newFont = font;
        newFont = new Font((String) selFont.getSelectedItem(), getStyle(),
                selSize.getNumber().intValue());
        return newFont;
    }
    
    public void setSelectedFont(Font font) {
        this.font = font;
        selFont.setSelectedItem(font.getFamily());
        selStyleBold.setSelected(font.isBold());
        selStyleItalic.setSelected(font.isItalic());
        selSize.setValue(font.getSize());
    }
    
    private String[] getAllFonts() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().
                getAvailableFontFamilyNames();
    }
    
    private int getStyle() {
        if (selStyleBold.isSelected() && selStyleItalic.isSelected()) {
            return Font.BOLD + Font.ITALIC;
        }
        if (selStyleBold.isSelected()) {
            return Font.BOLD;
        }
        if (selStyleItalic.isSelected()) {
            return Font.ITALIC;
        }
        return Font.PLAIN;
    }
    
    public void fireChange() {
        firePropertyChange(FONT_EVENT_NAME, null, null);
    }
    
    class OnChange implements ActionListener, ChangeListener {
        
        public void actionPerformed(ActionEvent e) {
            fireChange();
        }
        
        public void stateChanged(ChangeEvent e) {
            fireChange();
        }
        
    }
    
    
}

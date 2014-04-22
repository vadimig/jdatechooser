/*
 * RiseLowPanel.java
 *
 * Created on 31 »юль 2006 г., 21:18
 *
 */

package datechooser.beans.editor.utils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * Panel user for rised/lowered styles selection.<br>
 * ѕанель, позвол€юща€ выбрать утопленный или приподн€тый стиль.
 * @author Androsov Vadim
 * @since 1.0
 */
public class RiseLowPanel extends JPanel {
    
    private int raisedValue;
    private int loweredValue;
    
    JRadioButton bRaised;
    JRadioButton bLowered;

    public RiseLowPanel(int value, int raisedValue, int loweredValue) {
        
        setRaisedValue(raisedValue);
        setLoweredValue(loweredValue);
        
        ButtonGroup bType = new ButtonGroup();
        bRaised = new JRadioButton(getEditorLocaleString("Raised"));
        bLowered = new JRadioButton(getEditorLocaleString("Lowered"));
        bType.add(bRaised);
        bType.add(bLowered);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(bRaised);
        add(bLowered);
        setType(value);
    }
    
    public void addActionListener(ActionListener listener) {
        bRaised.addActionListener(listener);
        bLowered.addActionListener(listener);
    }
    
    public void removeActionListener(ActionListener listener) {
        bRaised.removeActionListener(listener);
        bLowered.removeActionListener(listener);
    }
    
    public int getType() {
        if (bRaised.isSelected()) {
            return getRaisedValue();
        } else {
            return getLoweredValue();
        }
    }
    
    public void setType(int type) {
        bRaised.setSelected(type == getRaisedValue());
        bLowered.setSelected(type == getLoweredValue());
    }
    
    public int getRaisedValue() {
        return raisedValue;
    }
    
    public void setRaisedValue(int raisedValue) {
        this.raisedValue = raisedValue;
    }
    
    public int getLoweredValue() {
        return loweredValue;
    }
    
    public void setLoweredValue(int loweredValue) {
        this.loweredValue = loweredValue;
    }
    
}

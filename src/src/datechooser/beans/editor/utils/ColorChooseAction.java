/*
 * ColorChooseAction.java
 *
 * Created on 31 Июль 2006 г., 21:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datechooser.beans.editor.utils;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.*;

/**
 * Button action listener, button is used as color chooser.<br>
 * Событие - обработчик нажатия на кнопку выбора цвета.
 * По сути и привращает кнопку в выборщик цвета.
 * @author Androsov Vadim
 * @since 1.0
 */
public class ColorChooseAction implements ActionListener {
    
    private static JColorChooser chooseColor = new JColorChooser();
    public static final String COLOR_CHOOSE_EVENT_NAME = "color";
    
    private ColorHolder colorHolder;
    private String prompt;
    private Component component;
    
    private PropertyChangeSupport changeSupport;
    
    public ColorChooseAction(ColorHolder colorHolder, String prompt,
            Component c) {
        changeSupport = new PropertyChangeSupport(this);
        setColorHolder(colorHolder);
        setPrompt(prompt);
        setComponent(c);
    }
    
    public void actionPerformed(ActionEvent e) {
        Color newColor = chooseColor.showDialog(
                getComponent(), getPrompt(), getColor());
        if (newColor != null) {
            setColor(newColor);
            changeSupport.firePropertyChange(COLOR_CHOOSE_EVENT_NAME, null, null);
        }
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
    public Color getColor() {
        return colorHolder.getColor();
    }
    
    public ColorHolder getColorHolder() {
        return colorHolder;
    }
    
    public void setColorHolder(ColorHolder colorHolder) {
        this.colorHolder = colorHolder;
    }
    
    public void setColor(Color color) {
        colorHolder.setColor(color);
    }
    
    public String getPrompt() {
        return prompt;
    }
    
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
    
    public Component getComponent() {
        return component;
    }
    
    public void setComponent(Component component) {
        this.component = component;
    }
}

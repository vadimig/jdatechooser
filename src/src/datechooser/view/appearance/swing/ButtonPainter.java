/*
 * ButtonPainter.java
 *
 * Created on 29 ќкт€брь 2006 г., 22:44
 *
 */

package datechooser.view.appearance.swing;

import datechooser.view.appearance.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Draws cells using JButton, Look & Feel support.<br>
 * –исовальщик €чеек-кнопок. »спользует при рисовании класс JButton.
 * ѕоддерживает Look & Feel.
 * @author Androsov Vadim
 * @since 1.0
 */
public class ButtonPainter implements Painter, SwingCellAttributes {
    
    private JButton button;
    private ButtonModel model;
    
    public ButtonPainter() {
        setButton(new JButton("?"));
        model = button.getModel();
        button.setMargin(new Insets(2, 2, 2, 2));
        button.setOpaque(true);
    }
    
    public JButton getButton() {
        return button;
    }
    
    public void setButton(JButton button) {
        this.button = button;
    }
    
    public void setText(String text) {
        button.setText(text);
    }
    
    public void setFont(Font font) {
        button.setFont(font);
    }
    
    public Font getFont() {
        return button.getFont();
    }
    
    public void setTextColor(Color color) {
        button.setForeground(color);
    }
    
    public void updateUI() {
        button.updateUI();
    }
    
    public void setSize(int width, int height) {
        button.setSize(width, height);
    }
    
    public void paint(Graphics2D g) {
        button.paint(g);
    }
    
    public Border getBorder() {
        return button.getBorder();
    }
    
    public void setPressed(boolean pressed) {
        model.setPressed(pressed);
        model.setArmed(pressed);
    }
    
    public void setEnabled(boolean enabled) {
        model.setEnabled(enabled);
    }

    public Color getTextColor() {
        return button.getForeground();
    }

    public Object clone() {
        return new ButtonPainter();
    }
    
    public void assign(CellAppearance newAppearance) {
    }

    public Component getComponent(Component c) {
        return button;
    }
}

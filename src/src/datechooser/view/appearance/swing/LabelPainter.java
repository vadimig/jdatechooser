/*
 * ButtonPainter.java
 *
 * Created on 29 ќкт€брь 2006 г., 22:44
 *
 */

package datechooser.view.appearance.swing;

import datechooser.view.appearance.*;
import java.awt.*;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Draws cells using JLabel, Look & Feel support.<br>
 * –исовальщик простых €чеек. »спользует при рисовании класс JLabel.
 * ѕоддерживает Look & Feel.
 * @author Androsov Vadim
 * @since 1.0
 */
public class LabelPainter implements Painter, SwingCellAttributes {
    
    private JLabel label;
    
    public LabelPainter() {
        setLabel(new JLabel("?"));
        getLabel().setHorizontalAlignment(JLabel.CENTER);
        getLabel().setOpaque(true);
    }
    
    public void setText(String text) {
        label.setText(text);
    }
    
    public void setFont(Font font) {
        label.setFont(font);
    }
    
    public Font getFont() {
        return label.getFont();
    }
    
    public void setTextColor(Color color) {
        label.setForeground(color);
    }
    
    public void updateUI() {
        label.updateUI();
    }
    
    public void setSize(int width, int height) {
        label.setSize(width, height);
    }
    
    public void paint(Graphics2D g) {
        label.paint(g);
    }
    
    public Border getBorder() {
        return label.getBorder();
    }
    
    public void setPressed(boolean pressed) {
    }
    
    public void setEnabled(boolean enabled) {
    }
    
    public JLabel getLabel() {
        return label;
    }
    
    public void setLabel(JLabel label) {
        this.label = label;
    }

    public Color getTextColor() {
        return label.getForeground();
    }

    public Object clone() {
        return new LabelPainter();
    }
    
    public void assign(CellAppearance newAppearance) {
        
    }

    public Component getComponent(Component c) {
        return label;
    }
}

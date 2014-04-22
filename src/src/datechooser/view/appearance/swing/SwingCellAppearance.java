/*
 * SwingCellAppearance.java
 *
 * Created on 28 ќкт€брь 2006 г., 12:38
 *
 */

package datechooser.view.appearance.swing;

import datechooser.view.appearance.*;
import datechooser.view.appearance.CellRenderer;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.ComponentUI;

/**
 * Swing cell appearance.<br>
 * Swing - стиль €чеек.
 * @author Androsov Vadim
 * @since 1.0
 */
public class SwingCellAppearance extends CellAppearance implements SwingCellAttributes {
    
    private boolean pressed;
    private boolean enabled;
    
    private SwingCellRenderer renderer;
    private Painter painter;
    private Color cursorColor;
    
    public SwingCellAppearance(Font font, Color color, Color cursorColor,
            boolean pressed, boolean enabled, Painter painter) {
        renderer = new SwingCellRenderer(this, painter);
        this.painter = painter;
        setTextColor(color);
        setFont(font);
        setPressed(pressed);
        setEnabled(enabled);
        setCursorColor(cursorColor);
    }
    
    public Painter getPainter() {
        return renderer.getPainter();
    }
    
    protected CellRenderer getRenderer() {
        return renderer;
    }
    
    public Object clone() {
        SwingCellAppearance newInst = new SwingCellAppearance(getFont(), getTextColor(),
                getCursorColor(), isPressed(), isEnabled(), (Painter) renderer.getPainter().clone());
        newInst.setSelectable(isSelectable());
        return newInst;
    }
    
    public void assign(CellAppearance newAppearance) {
        if (newAppearance == null) return;
        SwingCellAppearance swingAppearance = (SwingCellAppearance) newAppearance;
        setTextColor(swingAppearance.getTextColor());
        setFont(swingAppearance.getFont());
        setCursorColor(swingAppearance.getCursorColor());
        setSelectable(swingAppearance.isSelectable());
    }
    
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if  (this.getClass() != obj.getClass()) return false;
        SwingCellAppearance value = (SwingCellAppearance) obj;
        return  getTextColor().equals(value.getTextColor()) &&
                getCursorColor().equals(value.getCursorColor()) &&
                getFont().equals(value.getFont());
    }
    
    
    public boolean isPressed() {
        return pressed;
    }
    
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public Font getFont() {
        Font aFont  = painter.getFont();
        if (aFont == null) {
            return renderer.getDefaultFont();
        }
        return aFont;
    }

    public Color getCursorColor() {
        return cursorColor;
    }

    public void setCursorColor(Color cursorColor) {
        this.cursorColor = cursorColor;
    }

    public Color getTextColor() {
        return painter.getTextColor();
    }

    public void setFont(Font font) {
        painter.setFont(font);
    }

    public void setTextColor(Color textColor) {
        painter.setTextColor(textColor);
    }

    public Composite getComposite() {
        return null;
    }

}

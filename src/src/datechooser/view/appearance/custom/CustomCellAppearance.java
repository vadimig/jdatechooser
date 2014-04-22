/*
 * CellAppearance.java
 *
 * Created on 28 Июль 2006 г., 18:36
 *
 */

package datechooser.view.appearance.custom;

import datechooser.view.appearance.*;
import datechooser.view.appearance.CellRenderer;
import java.awt.*;
import java.io.*;
import javax.swing.border.Border;

/**
 * Customized cell appearance.<br>
 * Настраиваемый стиль отображения ячейки.
 * @author Androsov Vadim
 * @since 1.0
 */
public class CustomCellAppearance extends CellAppearance implements CustomCellAttributes {
    
    private Color cursorColor;
    private Font font;
    private Color textColor;
    private Color backgroundColor;
    private Border cellBorder;
    private float transparency;
    private transient float transparencyCash = -1;
    private transient Composite composite;
    
    private CustomCellRenderer renderer;
    
    public CustomCellAppearance(Color backgroundColor, Color textColor,
            Border cellBorder, Font font, Color cursorColor, float transparency, Image texture) {
        setBackgroundColor(backgroundColor);
        setTextColor(textColor);
        setCellBorder(cellBorder);
        setFont(font);
        setCursorColor(cursorColor);
        setTransparency(transparency);
        renderer = new CustomCellRenderer(this);
    }
    
    public CustomCellAppearance(Color backgroundColor, Color textColor,
            Border cellBorder, Font font, Color cursorColor, float transparency) {
        this(backgroundColor, textColor, cellBorder, font, cursorColor, transparency, null);
    }
    
    public Color getBackgroundColor() {
        return backgroundColor;
    }
    
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    public Border getCellBorder() {
        return cellBorder;
    }
    
    public void setCellBorder(Border cellBorder) {
        this.cellBorder = cellBorder;
    }
    
    public Object clone() {
        CellAppearance newInst = new CustomCellAppearance(getBackgroundColor(),
                getTextColor(), getCellBorder(), getFont(), getCursorColor(), getTransparency());
        newInst.setSelectable(isSelectable());
        return newInst;
    }
    
    public void assign(CellAppearance newAppearance) {
        if (newAppearance == null) return;
        CustomCellAppearance customAppearance = (CustomCellAppearance) newAppearance;
        setBackgroundColor(customAppearance.getBackgroundColor());
        setTextColor(customAppearance.getTextColor());
        setCellBorder(customAppearance.getCellBorder());
        setFont(customAppearance.getFont());
        setCursorColor(customAppearance.getCursorColor());
        setSelectable(customAppearance.isSelectable());
        setTransparency(customAppearance.getTransparency());
    }
    
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if  (this.getClass() != obj.getClass()) return false;
        CustomCellAppearance value = (CustomCellAppearance) obj;
        return getBackgroundColor().equals(value.getBackgroundColor()) &&
                getTextColor().equals(value.getTextColor()) &&
                getCursorColor().equals(value.getCursorColor()) &&
                getCellBorder().equals(value.getCellBorder()) &&
                getFont().equals(value.getFont()) && (getTransparency() == value.getTransparency());
    }
    
    protected CellRenderer getRenderer() {
        return renderer;
    }
    
    public Color getCursorColor() {
        return cursorColor;
    }
    
    public void setCursorColor(Color cursorColor) {
        this.cursorColor = cursorColor;
    }
    
    public Font getFont() {
        return font;
    }
    
    public Color getTextColor() {
        return textColor;
    }
    
    public void setFont(Font font) {
        this.font = font;
    }
    
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
    
    public float getTransparency() {
        return transparency;
    }
    
    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }
    
    public Composite getComposite() {
        if (transparency != transparencyCash) {
            transparencyCash = transparency;
            composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency);
        }
        return composite;
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        transparencyCash = -1;
    }
}

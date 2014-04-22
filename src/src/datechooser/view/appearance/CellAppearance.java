/*
 * CellAppearance.java
 *
 * Created on 25 Октябрь 2006 г., 8:46
 *
 */

package datechooser.view.appearance;

import datechooser.view.appearance.CellRenderer;
import datechooser.view.appearance.custom.CustomCellRenderer;
import java.awt.*;
import java.io.Serializable;

/**
 * Abstract class for views.<br>
 * Базовый класс для профилей внешнего вида.
 * @author Androsov Vadim
 * @see AppearancesList
 * @since 1.0
 */
public abstract class CellAppearance extends CellRenderer
        implements Serializable, Cloneable, CellAttributes {
    
    private boolean selectable;
    /**
     * @see CellRenderer
     * @since 1.0
     */
    protected abstract CellRenderer getRenderer();
    /**
     * <b>Deep</b> clone.<br>
     * Глубокое клонирование.
     * @since 1.0
     */
    public abstract Object clone();
    
    public CellAppearance() {
        setSelectable(true);
    }
    
    public boolean isSelectable() {
        return selectable;
    }
    
    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }
    
    public void render(Graphics2D g, Component c, String text, int width, int height, boolean isCursor) {
        Composite composite = getComposite();
        if (composite != null) g.setComposite(composite);
        getRenderer().render(g, c, text, width, height, isCursor);
    }
    /**
     * Get composite stile for graphical output.<br>
     * Возвращает стиль композиции при рисовании.
     * @since 1.0
     */
    public abstract Composite getComposite();
    /**
     * Cursor color.<br>
     * Цвет курсора.
     * @since 1.0
     */
    public abstract Color getCursorColor() ;
    /**
     * Set cursor color.<br>
     * Установить цвет курсора.
     * @since 1.0
     */
    public abstract void setCursorColor(Color color) ;
}

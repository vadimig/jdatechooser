/*
 * CellRenderer.java
 *
 * Created on 25 Октябрь 2006 г., 8:39
 *
 */

package datechooser.view.appearance;

import java.awt.*;
import java.io.Serializable;

/**
 * Abstract class renders cell.<br>
 * Базовый класс для рисовальщиков ячеек.
 * @author Androsov Vadim
 * @since 1.0
 */
public abstract class CellRenderer implements Serializable {
    /**
     * Доля от размера ячейки, отводимая под курсор.
     * @since 1.0
     */
    private static final int CURSOR_PART = 5;
    /**
     * Отступ курсора от всех границ в пикселях.
     * @since 1.0
     */
    private static final int CURSOR_SHIFT = 2;
    
    /**
     * Начиная с такого размера курсор будет рисоваться более жирным.
     * @since 1.0
     */
    private static final int CURSOR_BOLD = 3;
    
    private static final BasicStroke boldStroke = new BasicStroke(2f);
    private static final BasicStroke usualStroke = new BasicStroke(1f);
    private static Stroke savedStroke;
    
    /**
     * Draw cell method.<br>
     * Метод рисования ячейки.
     * @param g Graphics.<br>
     * Графический контекст.
     * @param c Palette component. <br>
     * Компонент, на котором осуществляется рисование.
     * @param text Cell text.<br>
     * Текст ячейки.
     * @param width Cell width.<br>
     * Ширина ячейки.
     * @param height Cell height.<br>
     * Высота ячейки.
     * @param isCursor Draw cursor.<br>
     * Рисовать курсор.
     * @since 1.0
     */
    public abstract void render(Graphics2D g, Component c, String text, int width, int height, boolean isCursor);
    /**
     * @see CellRenderer#render(Graphics2D, Component, String, int, int, boolean)
     * @since 1.0
     */
    public void render(Graphics2D g, Component c, String text, int width, int height) {
        render(g, c, text, width, height, false);
    }
    /**
     * Рисование курсора.
     * @since 1.0
     */
    protected void paintCursor(Graphics2D g2d, Rectangle rec, Color color) {
        g2d.setColor(color);
        int minSize = rec.height < rec.width ? rec.height : rec.width;
        int cursorSize = minSize / CURSOR_PART;
        
        savedStroke = g2d.getStroke();
        
        if (cursorSize > CURSOR_BOLD) {
            g2d.setStroke(boldStroke);
        } else {
            g2d.setStroke(usualStroke);
        }
        int cursorShift = CURSOR_SHIFT;//minSize / CURSOR_SHIFT;
        
        int x = rec.x + cursorShift;
        int y = rec.y + cursorShift;
        g2d.drawLine(x, y, x + cursorSize, y);
        g2d.drawLine(x, y, x, y + cursorSize);
        
        x = rec.x + rec.width - cursorShift;
        y = rec.y + cursorShift;
        g2d.drawLine(x, y, x - cursorSize, y);
        g2d.drawLine(x, y, x, y + cursorSize);
        
        x = rec.x + rec.width - cursorShift;
        y = rec.y + rec.height - cursorShift;
        g2d.drawLine(x, y, x - cursorSize, y);
        g2d.drawLine(x, y, x, y - cursorSize);
        
        x = rec.x + cursorShift;
        y = rec.y + rec.height - cursorShift;
        g2d.drawLine(x, y, x + cursorSize, y);
        g2d.drawLine(x, y, x, y - cursorSize);
        
        g2d.setStroke(savedStroke);
    }
}

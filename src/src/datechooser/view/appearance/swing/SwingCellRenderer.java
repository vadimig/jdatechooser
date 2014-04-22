/*
 * SwingCellRenderer.java
 *
 * Created on 28 Октябрь 2006 г., 12:43
 *
 */

package datechooser.view.appearance.swing;

import datechooser.view.*;
import datechooser.view.appearance.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Swing cell renderer.<br>
 * Swing - рисовальщик ячеек.
 * @author Androsov Vadim
 * @since 1.0
 */
public class SwingCellRenderer extends CellRenderer {
    /**
     * Указывает на кадую долю от полного размера можно отдалить границу.
     * Т.е. при значении равном 7 граница по горизонтали может быть отдалена
     * максимум на 1 / 7 ширины ячейки.
     * @since 1.0
     */
    private static final int ALLOW_DECREASE = 7;
    
    
    private Painter painter;
    private SwingCellAppearance appearance;
    private Font defaultFont;
    
    public SwingCellRenderer(SwingCellAppearance appearance, Painter painter) {
        this.painter = painter;
        defaultFont = painter.getFont();
        setAppearance(appearance);
    }
    
    /**
     * Проверяет границы и рамку ячейки, если граница занимает больше чем константа
     * ALLOW_DECREASE, приводит ее к максимально допустимому значению.
     * @see ALLOW_DECREASE
     * @param insets Проверяемый размер границы, при необходимости этот параметр модифицируется
     * @param bounds Размер ячейки. Не модифицируется
     */
    private void testDecreasing(Insets insets, Rectangle bounds) {
        int horizontalMax = bounds.width / ALLOW_DECREASE;
        int verticalMax = bounds.height / ALLOW_DECREASE;
        if (insets.left > horizontalMax) insets.left = horizontalMax;
        if (insets.right > horizontalMax) insets.right = horizontalMax;
        if (insets.top > verticalMax) insets.top = verticalMax;
        if (insets.bottom > verticalMax) insets.bottom = verticalMax;
    }
    
    public void render(Graphics2D g, Component c, String text, int width, int height, boolean isCursor) {
        
        getPainter().updateUI();
        getPainter().setText(text);
        getPainter().setSize(width, height);
        Font aFont = getAppearance().getFont();
        if (aFont != null) getPainter().setFont(aFont);
        Color aColor = getAppearance().getTextColor();
        if (aColor != null) getPainter().setTextColor(aColor);
        
        getPainter().setPressed(getAppearance().isPressed());
        getPainter().setEnabled(getAppearance().isEnabled());
        
        getPainter().paint(g);
        
        Rectangle cellRect = new Rectangle(0, 0, width, height);
        Border border = getPainter().getBorder();
        if (border != null) {
            Insets borderInsets = border.getBorderInsets(getPainter().getComponent(c));
            testDecreasing(borderInsets, cellRect);
            cellRect.setRect(borderInsets.left, borderInsets.top,
                    cellRect.width - (borderInsets.left + borderInsets.right),
                    cellRect.height - (borderInsets.top + borderInsets.bottom));
        }
        
        if (isCursor) {
            paintCursor(g, cellRect, getAppearance().getCursorColor());
        }
    }
    
    public SwingCellAppearance getAppearance() {
        return appearance;
    }
    
    public void setAppearance(SwingCellAppearance appearance) {
        this.appearance = appearance;
    }
    
    public Font getDefaultFont() {
        return defaultFont;
    }
    
    public Painter getPainter() {
        return painter;
    }
    
}

/*
 * CustomCellRenderer.java
 *
 * Created on 25 ќкт€брь 2006 г., 8:50
 *
 */

package datechooser.view.appearance.custom;

import datechooser.view.*;
import datechooser.view.appearance.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.*;
import javax.swing.border.*;

/**
 * Customized cell's renderer.<br>
 * –исовальщик настраиваемых €чеек.
 * @author Androsov Vadim
 * @since 1.0
 */
public class CustomCellRenderer extends CellRenderer {
    
    private CustomCellAppearance appearance;
    private transient Composite fillComposite;
    
    public CustomCellRenderer(CustomCellAppearance anAppearance) {
        setAppearance(anAppearance);
        fillComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
    }
    
    public void render(Graphics2D g, Component c, String text, int width, int height, boolean isCursor) {
        Border border = getAppearance().getCellBorder();
        g.setColor(getAppearance().getBackgroundColor());
        g.fillRect(0, 0, width, height);
        g.setComposite(fillComposite);
        Rectangle cellRect = new Rectangle(0, 0, width, height);
        if (border != null) {
            Insets borderInsets = border.getBorderInsets(c);
             cellRect.setRect(cellRect.x + borderInsets.left,
                    cellRect.y + borderInsets.top,
                    cellRect.width - (borderInsets.left + borderInsets.right),
                    cellRect.height - (borderInsets.top + borderInsets.bottom));
            border.paintBorder(c, g, 0, 0, width, height);
        }
        g.setFont(appearance.getFont());
        paintText(g, text, getAppearance().getTextColor(), cellRect, appearance.getFont());
        if (isCursor && getAppearance().isSelectable()) {
            paintCursor(g, cellRect, getAppearance().getCursorColor());
        }
        
    }
    
    private void paintText(Graphics2D g, String text, Color color,
            Rectangle rec, Font font) {
        g.setColor(color);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(text, context);
        // (x,y) = координаты левого верхнего угла пр€моугольника с текстом
        double x = rec.getX() + (rec.getWidth() - bounds.getWidth()) / 2;
        double y = rec.getY() + (rec.getHeight() - bounds.getHeight()) / 2;
        // добавл€ем спуск (ascent) к координате y чтобы выйти на насто€щую базовую (нижнюю) линию
        double ascent = -bounds.getY();
        double baseY = y + ascent;
        g.drawString(text, (int)x, (int)baseY);
    }
    
    public CustomCellAppearance getAppearance() {
        return appearance;
    }
    
    public void setAppearance(CustomCellAppearance appearance) {
        this.appearance = appearance;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        fillComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
     }
}

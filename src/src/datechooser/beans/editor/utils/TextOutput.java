/*
 * TextOutput.java
 *
 * Created on 3 Август 2006 г., 11:48
 *
 */

package datechooser.beans.editor.utils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Draws text (centered vertical).<br>
 * Выводит текст в заданными параметрами в нужное место (рисует).
 * Центрирует по высоте.
 * @author Androsov Vadim
 * @since 1.0
 */
public class TextOutput {
    
    private static Font defaultFont = new Font("Dialog", Font.PLAIN, 14);
    
    public static void paintBoxed(Graphics g, Rectangle bounds,
            String text, Font font) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle2D rec = font.getStringBounds(text, g2d.getFontRenderContext());
        double x = (bounds.width - rec.getWidth()) / 2;
        if (x < 0) x = 0;
        g2d.drawString(text,
                (float) x,
                (float) ((bounds.height - rec.getHeight()) / 2 - rec.getY()));
    }

    public static void paintBoxed(Graphics g, Rectangle bounds, String text) {
        paintBoxed(g, bounds, text, defaultFont);
    }
}

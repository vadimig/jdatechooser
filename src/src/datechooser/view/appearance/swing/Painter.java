/*
 * Painter.java
 *
 * Created on 29 ќкт€брь 2006 г., 22:40
 *
  */

package datechooser.view.appearance.swing;

import datechooser.view.appearance.*;
import java.awt.*;
import java.io.Serializable;
import javax.swing.border.Border;

/**
 * Common interface of swing renderers.<br>
 * ќбщий интерфейс swing - рисовальщиков.
 * @author Androsov Vadim
 * @since 1.0
 */
public interface Painter extends Serializable, CellAttributes, Cloneable {
    Object clone();
    void updateUI();
    void setSize(int width, int height);
    void paint(Graphics2D g);
    void setPressed(boolean pressed);
    void setEnabled(boolean enabled);
    void setText(String text);
    Border getBorder();

    Component getComponent(Component c);
}

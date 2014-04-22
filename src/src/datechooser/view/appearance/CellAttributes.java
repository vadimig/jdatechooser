/*
 * SwingAttributes.java
 *
 * Created on 2 Ноябрь 2006 г., 0:26
 *
 */

package datechooser.view.appearance;

import java.awt.*;

/**
 * Basic date cell attributes.<br>
 * Базовые свойства ячейки с датой.
 * @author Androsov Vadim
 * @since 1.0
 */
public interface CellAttributes {
    Font getFont();
    
    Color getTextColor();
    
    void setFont(Font font);
    
    void setTextColor(Color textColor);
    
    /**
     * Assign one cell properties for another. Not used (reserved).<br>
     * Зарезервирована если захочется сделать единые настройки для всех полей
     * @since 1.0
     */
    void assign(CellAppearance newAppearance);
}

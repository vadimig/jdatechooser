/*
 * SwingCellAppearanceDescriptor.java
 *
 * Created on 30 ќкт€брь 2006 г., 21:05
 *
 */

package datechooser.beans.editor.descriptor;

import datechooser.view.appearance.swing.SwingCellAppearance;
import java.awt.*;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see datechooser.view.appearance.swing.SwingCellAppearance
 * @author Androsov Vadim
 * @since 1.0
 */
public class SwingCellAppearanceDescriptor extends ClassDescriptor {
    
    public Class getDescriptedClass() {
        return SwingCellAppearance.class;
    }
    
    public String getJavaDescription(Object value) {
        StringBuffer buf = new StringBuffer();
        buf.append("new " + getClassName() + "(");
        //font, color, cursorColor, pressed, enabled, painter
        SwingCellAppearance appears = (SwingCellAppearance) value;
        buf.append(DescriptionManager.describeJava(appears.getFont(), Font.class));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(appears.getTextColor(), Color.class));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(appears.getCursorColor(), Color.class));
        buf.append(getSeparator());
        buf.append(appears.isPressed());
        buf.append(getSeparator());
        buf.append(appears.isEnabled());
        buf.append(getSeparator());
        buf.append("new " + appears.getPainter().getClass().getName() + "()");
        buf.append(")");
        return buf.toString();
    }
    
    public String getDescription(Object value) {
        return getClassName();
    }
    
}

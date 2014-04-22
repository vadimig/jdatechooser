/*
 * ColorDescriptor.java
 *
 * Created on 3 Август 2006 г., 21:33
 *
 */

package datechooser.beans.editor.descriptor;

import java.awt.Color;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see java.awt.Color
 * @author Androsov Vadim
 * @since 1.0
 */
public class ColorDescriptor extends ClassDescriptor {
    public Class getDescriptedClass() {
        return Color.class;
    }

    public String getJavaDescription(Object value) {
        Color colorValue = (Color) value;
        return "new " + getClassName() + "(" + colorValue.getRed() + ONE_LINE_SEPARATOR + 
                colorValue.getGreen() + ONE_LINE_SEPARATOR + colorValue.getBlue() + ")";
    }

    public String getDescription(Object value) {
        Color colorValue = (Color) value;
        return getEditorLocaleString("Color") + " " + getEditorLocaleString("red") + 
                " " + colorValue.getRed() +  " " + getEditorLocaleString("green") + 
                " " + colorValue.getGreen() + " " + getEditorLocaleString("blue") + 
                " " + colorValue.getBlue();
    }
}

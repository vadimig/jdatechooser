/*
 * FontDescriptor.java
 *
 * Created on 3 Август 2006 г., 16:05
 *
 */

package datechooser.beans.editor.descriptor;

import java.awt.Font;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see java.awt.Font
 * @author Androsov Vadim
 * @since 1.0
 */
public class FontDescriptor extends ClassDescriptor {

    public Class getDescriptedClass() {
        return Font.class;
    }
    
    public String getDescription(Object value) {
        StringBuffer buf = new StringBuffer();
        Font selFont = (Font) value;
        buf.append(selFont.getFamily());
        buf.append(", ");
        if (selFont.isItalic()) {
            buf.append(getEditorLocaleString("italic") + ", ");
        }
        if (selFont.isBold()) {
            buf.append(getEditorLocaleString("bold") + ", ");
        }
        buf.append(selFont.getSize());
        return buf.toString();
    }
    
    public String getJavaDescription(Object value) {
            StringBuffer buf = new StringBuffer();
            Font selFont = (Font) value;
            buf.append("new " + getClassName() + "(");
            buf.append('"' + selFont.getFamily() + '"');
            buf.append(ONE_LINE_SEPARATOR);
            if (selFont.isBold() && selFont.isItalic()) {
                buf.append(getClassName() + ".BOLD + " + getClassName() + ".ITALIC");
            } else if (selFont.isBold()) {
                buf.append(getClassName() + ".BOLD");
            } else if (selFont.isItalic()) {
                buf.append(getClassName() + ".ITALIC");
            } else {
                buf.append(getClassName() + ".PLAIN");
            }
            buf.append(ONE_LINE_SEPARATOR);
            buf.append(selFont.getSize());
            buf.append(")");
            return buf.toString();
    }
}

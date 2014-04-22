/*
 * ModelBehaviorDescriptor.java
 *
 * Created on 6 Август 2006 г., 14:48
 *
 */

package datechooser.beans.editor.descriptor;

import datechooser.view.WeekDaysStyle;
import java.text.MessageFormat;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see datechooser.view.WeekDaysStyle
 * @author Androsov Vadim
 * @since 1.0
 */
public class WeekDaysStyleDescriptor extends ClassDescriptor {

    public Class getDescriptedClass() {
        return WeekDaysStyle.class;
    }

    public String getJavaDescription(Object value) {
        StringBuffer buf = new StringBuffer();
        buf.append(getClassName() + ".");
        WeekDaysStyle style = (WeekDaysStyle) value;
        switch (style) {
            case FULL:
                buf.append("FULL");
                break;
            case NORMAL:
                buf.append("NORMAL");
                break;
            case SHORT:
                buf.append("SHORT");
                break;
            default:
                buf.append("NORMAL/* " + 
                MessageFormat.format(getEditorLocaleString("value_not_found"), value.toString())
                + " */");
        }
        return buf.toString();
    }

    public String getDescription(Object value) {
        WeekDaysStyle style = (WeekDaysStyle) value;
        switch (style) {
            case FULL:
                return getEditorLocaleString("WeekStyle_Full");
            case NORMAL:
                return getEditorLocaleString("WeekStyle_Normal");
            case SHORT:
                return getEditorLocaleString("WeekStyle_Short");
        }
        return value.toString();
    }
}

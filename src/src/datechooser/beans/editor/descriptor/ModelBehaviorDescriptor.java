/*
 * ModelBehaviorDescriptor.java
 *
 * Created on 6 Август 2006 г., 14:48
 *
 */

package datechooser.beans.editor.descriptor;

import datechooser.model.multiple.MultyModelBehavior;
import java.text.MessageFormat;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see datechooser.model.multiple.MultyModelBehavior
 * @author Androsov Vadim
 * @since 1.0
 */
public class ModelBehaviorDescriptor extends ClassDescriptor {

    public Class getDescriptedClass() {
        return MultyModelBehavior.class;
    }

    public String getJavaDescription(Object value) {
        StringBuffer buf = new StringBuffer();
        buf.append(getClassName() + ".");
        MultyModelBehavior behavior = (MultyModelBehavior) value;
        switch (behavior) {
            case SELECT_SINGLE:
                buf.append("SELECT_SINGLE");
                break;
            case SELECT_PERIOD:
                buf.append("SELECT_PERIOD");
                break;
            case SELECT_ALL:
                buf.append("SELECT_ALL");
                break;
            default:
                buf.append("SELECT_ALL/* " + 
                MessageFormat.format(getEditorLocaleString("value_not_found"), value.toString())
                + " */");
        }
        return buf.toString();
    }

    public String getDescription(Object value) {
        MultyModelBehavior behavior = (MultyModelBehavior) value;
        switch (behavior) {
            case SELECT_SINGLE:
                return getEditorLocaleString("Single");
            case SELECT_PERIOD:
                return getEditorLocaleString("Period");
            case SELECT_ALL:
                return getEditorLocaleString("All");
        }
        return value.toString();
    }
}

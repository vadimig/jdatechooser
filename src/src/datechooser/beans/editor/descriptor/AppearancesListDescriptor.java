/*
 * AppearanceListDescriptor.java
 *
 * Created on 8 Август 2006 г., 7:19
 *
 */

package datechooser.beans.editor.descriptor;

import datechooser.view.appearance.*;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see datechooser.view.appearance.AppearancesList
 * @author Androsov Vadim
 * @since 1.0
 */
public class AppearancesListDescriptor extends ClassDescriptor {
    
    public Class getDescriptedClass() {
        return AppearancesList.class;
    }
    
    public String getJavaDescription(Object value) {
        AppearancesList list = (AppearancesList) value;
        StringBuffer buf = new StringBuffer();
        buf.append("new " + getClassName() + "(");
        buf.append('"' + list.getCurrent().getName() + '"');
        for (String key : list.getKeys()) {
            if (!list.getAppearance(key).isEditable()) {
                continue;
            }
            buf.append(getSeparator());
            buf.append(DescriptionManager.describeJava(list.getAppearance(key),
                    ViewAppearance.class));
        }
        buf.append(")");
        return buf.toString();
    }
    
    public String getDescription(Object value) {
        return "[" + ((AppearancesList) value).getCurrent().getName() + "]...";
    }
    
}

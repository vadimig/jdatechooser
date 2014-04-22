/*
 * PeriodSetDescriptor.java
 *
 * Created on 8 Àâãóñò 2006 ã., 22:22
 *
 */

package datechooser.beans.editor.descriptor;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

import datechooser.model.multiple.*;
import java.util.Iterator;
import java.util.Locale;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see datechooser.model.multiple.PeriodSet
 * @author Androsov Vadim
 * @since 1.0
 */
public class PeriodSetDescriptor extends ClassDescriptor {
    
    public Class getDescriptedClass() {
        return PeriodSet.class;
    }
    
    public String getJavaDescription(Object value) {
        PeriodSet pList = (PeriodSet) value;
        if (pList.getCount() == 0) {
            return "new " + getClassName() + "()";
        }
        StringBuffer buf = new StringBuffer();
        buf.append("new " + getClassName() + "(");
        for (Period period : pList.getPeriods()) {
            buf.append(DescriptionManager.describeJava(period, Period.class));
            buf.append(getSeparator());
        }
        buf.delete(buf.length() - getSeparator().length(), buf.length());
        buf.append(")");
        return buf.toString();
    }
    
    public String getDescription(Object value) {
        return getDescription(value, Locale.getDefault());
    }
    
    public String getDescription(Object value, Locale locale) {
        PeriodSet pList = (PeriodSet) value;
        if (pList.getCount() == 0) {
            return getEditorLocaleString("null");
        }
        return DescriptionManager.describe(pList.getFirstPeriod(), locale) +
                (pList.getCount() > 1 ? " ..." : "");
    }
}

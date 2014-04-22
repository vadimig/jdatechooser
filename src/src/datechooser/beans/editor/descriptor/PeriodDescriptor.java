/*
 * PeriodDescriptor.java
 *
 * Created on 8 Август 2006 г., 22:10
 *
 */

package datechooser.beans.editor.descriptor;

import datechooser.model.multiple.Period;
import java.util.*;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see datechooser.model.multiple.Period
 * @author Androsov Vadim
 * @since 1.0
 */
public class PeriodDescriptor extends ClassDescriptor {
    public Class getDescriptedClass() {
        return Period.class;
    }

    public String getJavaDescription(Object value) {
        StringBuffer buf = new StringBuffer();
        Period period = (Period) value;
        Calendar start = period.getStartDate();
        Calendar end = period.getEndDate();
        buf.append("new " + getClassName() + "(");
        buf.append(DescriptionManager.describeJava(start, GregorianCalendar.class));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(end, GregorianCalendar.class));
        buf.append(")");
        return buf.toString();
    }

    public String getDescription(Object value) {
        return getDescription(value, Locale.getDefault());
    }

    public String getDescription(Object value, Locale locale) {
        Period period = (Period) value;
        return DescriptionManager.describe(period.getStartDate(), locale) + 
                (period.isOneDate() ? "" : 
                    (" - " + DescriptionManager.describe(period.getEndDate(), locale)));
    }
}

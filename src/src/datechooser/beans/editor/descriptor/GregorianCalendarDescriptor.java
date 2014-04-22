/*
 * GregorianCalendarDescriptor.java
 *
 * Created on 8 Август 2006 г., 22:04
 *
 */

package datechooser.beans.editor.descriptor;

import java.text.DateFormat;
import java.util.*;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see java.util.GregorianCalendar
 * @author Androsov Vadim
 * @since 1.0
 */
public class GregorianCalendarDescriptor extends ClassDescriptor {

    public Class getDescriptedClass() {
        return GregorianCalendar.class;
    }

    public String getJavaDescription(Object value) {
        StringBuffer buf = new StringBuffer();
        GregorianCalendar date = (GregorianCalendar) value;
        buf.append("new " + getClassName() + "(");
        buf.append(date.get(Calendar.YEAR));
        buf.append(ONE_LINE_SEPARATOR);
        buf.append(date.get(Calendar.MONTH));
        buf.append(ONE_LINE_SEPARATOR);
        buf.append(date.get(Calendar.DAY_OF_MONTH));
        buf.append(")");
        return buf.toString();
    }

    public String getDescription(Object value) {
        return getDescription(value, Locale.getDefault());
    }
    
    public String getDescription(Object value, Locale locale) {
        GregorianCalendar calendar = (GregorianCalendar) value;
        return DateFormat.getDateInstance(DateFormat.SHORT, locale).format(calendar.getTime());
    }
}

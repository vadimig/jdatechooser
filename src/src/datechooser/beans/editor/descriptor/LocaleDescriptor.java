/*
 * LocaleDescroptor.java
 *
 * Created on 10 Август 2006 г., 19:33
 *
 */

package datechooser.beans.editor.descriptor;

import java.util.Locale;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see java.util.Locale
 * @author Androsov Vadim
 * @since 1.0
 */
public class LocaleDescriptor extends ClassDescriptor {
    
    public Class getDescriptedClass() {
        return Locale.class;
    }
    
    public String getJavaDescription(Object value) {
        Locale locale = (Locale) value;
        StringBuffer buf = new StringBuffer();
        buf.append("new " + getClassName() + "(");
        buf.append('"' + locale.getLanguage() + '"');
        buf.append(ONE_LINE_SEPARATOR);
        buf.append('"' + locale.getCountry() + '"');
        if (locale.getVariant() != null) {
            buf.append(ONE_LINE_SEPARATOR);
            buf.append('"' + locale.getVariant() + '"');
        }
        buf.append(")");
        return buf.toString();
    }
    
    public String getDescription(Object value) {
        return ((Locale) value).getDisplayName((Locale) value);
    }
    
}

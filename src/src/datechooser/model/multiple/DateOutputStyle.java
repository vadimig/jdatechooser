/*
 * DateOutput.java
 *
 * Created on 14 Октябрь 2006 г., 16:07
 *
 */

package datechooser.model.multiple;

import java.text.DateFormat;
import java.text.Format;
import java.util.Locale;

/**
 * Date output style.<br>
 * Стиль вывода даты.
 * @author Androsov Vadim
 * @since 1.0
 */
public class DateOutputStyle {
    
    private static Locale locale = Locale.getDefault();
    private static int format = DateFormat.SHORT;
    
    public static int getFormat() {
        return format;
    }


    public static Locale getLocale() {
        return locale;
    }


    public static void setFormat(int aFormat) {
        format = aFormat;
    }


    public static void setLocale(Locale aLocale) {
        locale = aLocale;
    }
    
    public static DateFormat getDateFormat() {
        return DateFormat.getDateInstance(getFormat(), getLocale());
    }
 }

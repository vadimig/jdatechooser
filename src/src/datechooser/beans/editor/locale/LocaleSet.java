/*
 * LocaleSet.java
 *
 * Created on 10 Август 2006 г., 18:18
 *
 */

package datechooser.beans.editor.locale;

import java.text.DateFormatSymbols;
import java.util.*;

/**
 * Set of supported locales.<br>
 * Описывает множество поддерживаемых системой локализаций.
 * @author Androsov Vadim
 * @since 1.0
 */
public class LocaleSet {
    
    private Locale[] locales;
    private String[] names;
    private Locale current =  Locale.getDefault();
    
    public LocaleSet() {
        locales = Calendar.getAvailableLocales();
        DateFormatSymbols dfs = new DateFormatSymbols();
        names = new String[getLocales().length];
        initLocaleNames();
     }
    
    private void initLocaleNames() {
        for (int i = 0; i < getLocales().length; i++) {
            getNames()[i] = getLocales()[i].getDisplayName() + " [" + getLocales()[i].getDisplayName(getLocales()[i]) + "]";
        }
    }

    public String[] getNames() {
        return names;
    }

    public void setCurrent(String name) {
        for (int i = 0; i < getNames().length; i++) {
            if (name.equals(getNames()[i])) {
                current = getLocales()[i];
                return;
            }
        } 
        current = Locale.getDefault();
    }

    public int getIndex(Locale locale) {
        for (int i = 0; i < getLocales().length; i++) {
            if (locale.equals(getLocales()[i])) {
                return i;
            }
        }
        return 0;
    }

    public Locale getCurrent() {
        return current;
    }

    public Locale[] getLocales() {
        return locales;
    }
    
    public int size() {
        return getLocales().length;
    }
}

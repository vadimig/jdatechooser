/*
 * DateUtils.java
 *
 * Created on 9 Август 2006 г., 15:16
 *
 */

package datechooser.model;

import java.util.Calendar;

/**
 * Service class for dates, ignores time.<br>
 * Сервисный класс для работы с датами без учета времени.
 * @author Androsov Vadim
 * @since 1.0
 */
public class DateUtils {
    
    private static Calendar calendarCash = null;
    
    /**
     * Extract day.<br>
     * Получить день.
     * @since 1.0
     */
    public static int getDay(Calendar date) {
        return date.get(Calendar.DAY_OF_MONTH);
    }
    /**
     * Extract month.<br>
     * Получить месяц.
     * @since 1.0
     */
    public static int getMonth(Calendar date) {
        return date.get(Calendar.MONTH);
    }
    /**
     * Extract year.<br>
     * Получить год.
     * @since 1.0
     */
    public static int getYear(Calendar date) {
        return date.get(Calendar.YEAR);
    }
    /**
     * Compares dates (time is ignored).<br>
     * Сравнивает даты без учета времени.
     * @since 1.0
     */
    public static boolean equals(Calendar dat1, Calendar dat2) {
        return (getDay(dat1) == getDay(dat2)) &&
                (getMonth(dat1) == getMonth(dat2) &&
                (getYear(dat1) == getYear(dat2)));
    }
    /**
     * Is first date before second (time is ignored).<br> 
     * Проверяет предшествует ли одна дата другой без учета времени.
     * @since 1.0
     */
    public static boolean before(Calendar dat1, Calendar dat2) {
        if (getYear(dat1) < getYear(dat2)) {
            return true;
        } else if (getYear(dat1) > getYear(dat2)) {
            return false;
        } else if (getMonth(dat1) < getMonth(dat2)) {
            return true;
        } else if (getMonth(dat1) > getMonth(dat2)) {
            return false;
        } else if  (getDay(dat1) < getDay(dat2)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Is first date after second (time is ignored).<br> 
     * Проверяет следует ли одна дата за другой без учета времени.
     * @since 1.0
     */
    public static boolean after(Calendar dat1, Calendar dat2) {
        if (!before(dat1, dat2)) {
            return !equals(dat1, dat2);
        } else {
            return false;
        }
    }
    /**
     * Assign source date to target (time is ignored).<br> 
     * Устанавливает целевую дату равной источнику.
     * @since 1.0
     */
    public static void assign(Calendar target, Calendar source) {
        target.setTime(source.getTime());
    }
    /**
     * Are dates near (time is ignored).<br> 
     * Проверяет, граничат ли даты друг с другом без учета времени.
     * @since 1.0
     */
    public static boolean isNear(Calendar dat1, Calendar dat2) {
        Calendar before = null;
        Calendar after = null;
        
        if (before(dat1, dat2)) {
            before = dat1;
            after = dat2;
        } else {
            before = dat2;
            after = dat1;
        }
        
        initializeCash(before);
        calendarCash.add(Calendar.DAY_OF_MONTH, 1);
        return equals(calendarCash, after);
    }
    
    private static void initializeCash(Calendar date) {
        if (calendarCash == null) {
            calendarCash = (Calendar) date.clone();
        } else {
            calendarCash.setTime(date.getTime());
        }
    }
}

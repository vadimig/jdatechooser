/*
 * Period.java
 *
 * Created on 3 Июль 2006 г., 12:09
 *
 */

package datechooser.model.multiple;

import datechooser.model.DateUtils;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.*;

/**
 * Period (interval between start and end dates).<br>
 * Класс, описывающий период.
 * @author Androsov Vadim
 * @since 1.0
 */
public class Period extends DateOutputStyle implements Comparable, Serializable, Cloneable {
    
    /**
     * Начало периода
     * @since 1.0
     */
    private Calendar startDate;
    /**
     * Конец периода.
     * @since 1.0
     */
    private Calendar endDate;
    
    /**
     * Sets minimal date as startn (even if end date parameter before start).<br>
     * Вне зависимости от порядка параметров началом периода устанавливается меньшая дата.
     * @param start Period start.<br>
     * Начало периода
     * @param end Period end.<br>
     * Конец периода.
     * @since 1.0
     */
    public Period(Calendar start, Calendar end) {
        set(start, end);
    }
    
    /**
     * Creates period contained one date (start=end).<br>
     * Создает период, состоящий из одной даты.
     * @param aDate Date.<br>
     * Дата
     * @since 1.0
     */
    public Period(Calendar aDate) {
        this(aDate, aDate);
    }
    
    /**
     * Sets minimal date as startn (even if end date parameter before start).<br>
     * Вне зависимости от порядка параметров началом периода устанавливается меньшая дата.
     * @param start Period start.<br>
     * Начало периода
     * @param end Period end.<br>
     * Конец периода.
     * @since 1.0
     */
    public void set(Calendar start, Calendar end) {
        if ((start == null) || (end == null)) {
            setStartDate(start);
            setEndDate(end);
        }
        if (DateUtils.before(start, end)) {
            setStartDate(start);
            setEndDate(end);
        } else {
            setStartDate(end);
            setEndDate(start);
        }
    }
    
    /**
     * Is given date in period.<br>
     * Проверяет, лежит ли заданная дата внутри периода.
     * @since 1.0
     */
    public boolean isIn(Calendar aDate) {
        return (DateUtils.after(aDate, getStartDate()) && DateUtils.before(aDate, getEndDate()) ||
                DateUtils.equals(aDate, getStartDate()) || DateUtils.equals(aDate, getEndDate()));
    }
    
    /**
     * Get start period date.<br>
     * Возвращает начальную дату.
     * @since 1.0
     */
    public Calendar getStartDate() {
        return startDate;
    }
    
    /**
     * Sets period start.<br> 
     * Устанавливает дату начала периода. Можно передать null, переведя период
     * в недействительное состояние.
     * @since 1.0
     * @param startDate Start date. <b>null</b> make period invalid.<br>
     * Начальная дата. <b>null</b> переводит период в недействительное сосотояние.
     */
    public void setStartDate(Calendar startDate) {
        if (this.startDate == null) {
            if (startDate == null) return;
            this.startDate = (Calendar) startDate.clone();
        } else {
            if (startDate == null) {
                this.startDate = null;
            } else {
                this.startDate.setTime(startDate.getTime());
            }
        }
    }
    
    /**
     * Get end date.<br>
     * Возвращает конечную дату.
     * @since 1.0
     */
    public Calendar getEndDate() {
        return endDate;
    }
    
    /**
     * Sets period end.<br> 
     * Устанавливает дату конца периода. Можно передать null, переведя период
     * в недействительное состояние.
     * @since 1.0
     * @param endDate End date. <b>null</b> make period invalid.<br>
     * Дата конца периода. <b>null</b> переводит период в недействительное сосотояние.
     */
    public void setEndDate(Calendar endDate) {
        if (this.endDate == null) {
            if (endDate == null) return;
            this.endDate = (Calendar) endDate.clone();
        } else {
            if (endDate == null) {
                this.endDate = null;
            } else {
                this.endDate.setTime(endDate.getTime());
            }
        }
    }
    
    /**
     * Is period equals one date.<br>
     * Состоит ли период из одной даты.
     * @since 1.0
     */
    public boolean isOneDate() {
        return (getStartDate() != null) && (getEndDate() != null) &&
                (DateUtils.equals(getStartDate(), getEndDate()));
    }
    
    /**
     * Is period valid. Invalid if start or end equals null.<br> 
     * Корректен ли период.
     * Некорректен - если начало или конец null.
     * @since 1.0
     */
    public boolean isValid() {
        return (getStartDate() != null) && (getEndDate() != null);
    }
    
    /**
     * Compares periods. If they intersect, return 0.<br>
     * Сравнивает периоды. Если они пересекаются, возвращает 0.
     * @since 1.0
     */
    public int compareTo(Object o) {
        Period trg = (Period) o;
        if (isIntersects(trg)) return 0;
        return getStartDate().compareTo(trg.getStartDate());
    }
    
    /**
     * Превращает период в список дат (за каждый день, лежащий в периоде).
     * Жутко затратная (клонирование в цикле не есть гуд),
     * лучше обходится началом и концом периода.
     * @since 1.0
     */
    Collection<Calendar> listDates() {
        if (!isValid()) return null;
        ArrayList<Calendar> result = new ArrayList<Calendar>();
        Calendar buffer = (Calendar) getStartDate().clone();
        while (buffer.before(getEndDate()) || buffer.equals(getEndDate())) {
            result.add(buffer);
            buffer = (Calendar) buffer.clone();
            buffer.add(Calendar.DAY_OF_MONTH, 1);
        }
        return result;
    }
    
    /**
     * Does current period intersect another.<br>
     * Пересекает ли переданный период текущий.
     * @since 1.0
     */
    public boolean isIntersects(Period anotherPeriod) {
        return isIn(anotherPeriod.getStartDate()) ||
                isIn(anotherPeriod.getEndDate()) ||
                anotherPeriod.isIn(getStartDate()) ||
                anotherPeriod.isIn(getEndDate());
    }
    
    /**
     * Is current period near to another.<br>
     * Граничит ли переданный период с текущим.
     * @since 1.0
     */
    public boolean isNear(Period anotherPeriod) {
        return DateUtils.isNear(anotherPeriod.getEndDate(), getStartDate()) ||
                DateUtils.isNear(getEndDate(), anotherPeriod.getStartDate());
    }
    
    /**
     * Is current period near to date.<br>
     * Граничит ли переданная дата с текущим периодом.
     * @since 1.0
     */
    public boolean isNear(Calendar date) {
        return DateUtils.isNear(date, getStartDate()) ||
                DateUtils.isNear(getEndDate(), date);
    }
    
    /**
     * Unites two periods. If they don't intersect, ne period contains dates
     * between source periods.<br>
     * Объединяет периоды. Если они не пересекаются, в результирующий период
     * включаются и даты между исходными.
     * @since 1.0
     */
    public void unite(Period anotherPeriod) {
        if (getStartDate().after(anotherPeriod.getStartDate())) {
            setStartDate(anotherPeriod.getStartDate());
        }
        if (getEndDate().before(anotherPeriod.getEndDate())) {
            setEndDate(anotherPeriod.getEndDate());
        }
    }
    
    /**
     * Are periods equals. Intersecting periods are considered as equals.<br> 
     * Проверяет периоды на равенство. Пересекающиеся периоды считаются
     * (с точки зрения требований задачи) равными.
     * @since 1.0
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        return compareTo(obj) == 0;
    }
    
    /**
     * <b>Deep</b> period clone.<br>
     * Глубокое клонирование периода.
     * @since 1.0
     */
    public Object clone() {
        return new Period(
                (Calendar) getStartDate().clone(),
                (Calendar) getEndDate().clone());
    }
    
    /**
     * Cast to String.<br>
     * Преобразование в строку.
     * @since 1.0
     */
    public String toString() {
        return toString(getDateFormat());
    }
    
    /**
     * Cast to String.<br>
     * Преобразование в строку.
     * @param fmtStyle Date format style.<br>
     * Стиль отображения дат.
     * @param locale Locale.<br>
     * Локализация.
     * @since 1.0
     */
    public String toString(int fmtStyle, Locale locale) {
        return toString(DateFormat.getDateInstance(fmtStyle, locale));
    }
    
    /**
     * Cast to String.<br>
     * Преобразование в строку.
     * @param dateFormat Date format.<br>
     * Фомат дат.
     * @since 1.0
     */
    public String toString(DateFormat dateFormat) {
        if (!isValid()) return "invalid";
        StringBuffer ans = new StringBuffer();
        ans.append(dateFormat.format(getStartDate().getTime()));
        if (!isOneDate()) {
            ans.append(" - ");
            ans.append(dateFormat.format(getEndDate().getTime()));
        }
        return ans.toString();
    }
}

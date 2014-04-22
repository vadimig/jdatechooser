/*
 * CalendarModelExeption.java
 *
 * Created on 8 Октябрь 2006 г., 7:44
 *
 */

package datechooser.model.exeptions;

/**
 * Model exceptions. Meanwhile not used.<br>
 * Исключение модели. Пока не используется.
 * @author Androsov Vadim
 * @since 1.0
 */
public class CalendarModelExeption extends Exception {
    
    public CalendarModelExeption() {
    }

    public CalendarModelExeption(String mess) {
        super(mess);
    }
}

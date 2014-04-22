/*
 * WeekDaysStyle.java
 *
 * Created on 12 Ноябрь 2006 г., 17:07
 *
 */

package datechooser.view;

/**
 * Перечисление, описывающее множество вариантов отображения дней недели.
 * @author Androsov Vadim
 * @since 1.0
 */
public enum WeekDaysStyle {
/**
 * Full weekdays names.<br>
 * День недели отображается полностью
 * @since 1.0
 */    
FULL, 
/**
 * Shows some first symbols.<br>
 * Отображаются только первые несколько символов дня недели
 * @since 1.0
 */
NORMAL, 
/**
 * Shows one first symbol.<br>
 * Отображается только первый символ дня недели
 * @since 1.0
 */
SHORT
    
}

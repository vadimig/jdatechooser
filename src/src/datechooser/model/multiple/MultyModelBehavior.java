/*
 * MultyModelBehavior.java
 *
 * Created on 6 Август 2006 г., 14:07
 *
 */

package datechooser.model.multiple;

/**
 * Model selection behavior.<br>
 * Перечисление, описывающее возможности выбора для модели.
 * @author Androsov Vadim
 * @since 1.0
 */
public enum MultyModelBehavior {
    /**
     * Single date selection mode.<br>
     * Разрешен выбор только одной даты.
     * @since 1.0
     */
    SELECT_SINGLE,
    /**
     * Single period selection mode.<br>
     * Разрешен выбор только одного периода.
     * @since 1.0
     */
    SELECT_PERIOD,
    /**
     * Free selection mode (multy periods and dates selection).<br>
     * Разрешен выбор нескольких периодов (без ограничений)
     * @since 1.0
     */
    SELECT_ALL
}

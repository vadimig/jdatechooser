/*
 * PeriodChooseModel.java
 *
 * Created on 20 Май 2006 г., 19:29
 */

package datechooser.model.multiple;

import datechooser.model.*;
import java.util.Calendar;

/**
 * Interface for multy selection model.<br>
 * Интерфейс для модели, допускающей множественный выбор.
 * @author Androsov Vadim
 * @see datechooser.model.DateChoose
 * @since 1.0
 */
public interface MultyDateChoose extends DateChoose {
    /**
     * Resets selection.<br>
     * Сброс , чтобы выбирать сначала.
     * @since 1.0
     */
    void reset();
    /**
     * Sets selection mode.
     * Установка режима выбора.
     * @see datechooser.model.multiple.MultySelectModes
     * @since 1.0
     */
    void setMode(MultySelectModes mode, boolean add);
    /**
     * Get all selected dates.<br>
     * Возвращает выбранные даты.
     * @since 1.0
     */
    Iterable<Calendar> getSelectedDates();
    /**
     * Get selected periods.<br>
     * Возвращает выбранные периоды.
     * @since 1.0
     * @see datechooser.model.multiple.Period
     */ 
    Iterable<Period> getSelectedPeriods();
    /**
     * Sets period or date addition mode.<br>
     * Устанавливает режим добавления даты или периода.
     * @since 1.0
     */
    void setAdd(boolean add);
    /**
     * User started period selection.<br>
     * Указывает что было выбрано начало периода.
     * @since 1.0
     */
    void setPeriodSelectionStarted(boolean periodSelectionStarted);
    /**
     * Get multiple choise mode.<br>
     * Возвращает поведение модели (варианты множественного выбора).
     * @since 1.0
     * @see datechooser.model.multiple.MultyModelBehavior
     */ 
    MultyModelBehavior getBehavior();
    /**
     * Sets multiple choise mode.<br>
     * Устанавливает поведение модели (варианты множественного выбора).
     * @since 1.0
     * @see datechooser.model.multiple.MultyModelBehavior
     */
    void setBehavior(MultyModelBehavior behavior);
    /**
     * Get selected periods.<br>
     * Возвращает выбранные периоды.
     * @since 1.0
     * @see datechooser.model.multiple.PeriodSet
     */ 
    PeriodSet getSelectedPeriodSet();
}

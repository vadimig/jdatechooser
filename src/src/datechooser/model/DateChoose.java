/*
 * DateChooseModel.java
 *
 * Created on 20 Май 2006 г., 18:05
 */

package datechooser.model;

import datechooser.events.CommitListener;
import datechooser.events.CursorMoveListener;
import datechooser.events.SelectionChangedEvent;
import datechooser.events.SelectionChangedListener;
import datechooser.model.exeptions.IncompatibleDataExeption;
import datechooser.model.multiple.Period;
import datechooser.model.multiple.PeriodSet;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.*;

/**
 * Date selection model interface.<br>
 * Модель, необходимая для отрисовки окна выбора даты.
 * @author Androsov Vadim
 * @since 1.0
 */
public interface DateChoose extends Serializable {
    /**
     * Rows count in day selection grid.<br>
     * Количество строк в сетке выбора даты.
     * @since 1.0
     */
    int getRowsCount();
    /**
     * Columns count in day selection grid.<br>
     * Количество столбцов в сетке выбора даты.
     * @since 1.0
     */
    int getColsCount();
    /**
     * State of the specified cell.<br>
     * Состояние указанной ячейки.
     * @since 1.0
     */
    CellState getCellState(int row, int column);
    /**
     * Caption of the specified cell.<br>
     * Заголовок указанной ячейки
     * @since 1.0
     */
    String getCellCaption(int row, int column);
    /**
     * Date corresponding to the specified cell.<br>
     * Дата, соответствующая указанной ячейки.
     * @since 1.0
     */
    Calendar getCellDate(int row, int column);
    /**
     * Date selection constraints.<br>
     * Ограничения на выбор даты.
     * @since 1.0
     */
    void setConstraints(Calendar min, Calendar max);
    /**
     * Model needs full reoutput.<br>
     * Требуется ли полная перерисовка окна.
     * @since 1.0
     */
    boolean needsFullValidation();
    /**
     * Selects specified cell.<br>
     * Выбор ячейки.
     * @return Произошел ли выбор.
     * @since 1.0
     */
    boolean select(int row, int column);
    /**
     * Is specified date selected.<br>
     * Выбрана ли ячейка
     * @since 1.0
     */
    boolean isSelected(Calendar aDate);
    /**
     * Get selected date.<br>
     * Дата, соответствующая выбранной ячейки.
     * @since 1.0
     */
    Calendar getSelectedDate();
    /**
     * Sets cursor on specified date.<br>
     * Устанавливает курсор на заданную дату.
     * @since 1.0
     */
    void setSelectedDate(Calendar aDate);
    /**
     * Shows specified month and year.<br>
     * Показать отрезок за заданный месяц и год.
     * @since 1.0
     */
    void showMonthYear(int month, int year);
    /**
     * Visible date.<br>
     * Видимая дата.
     * @since 1.0
     */
    Calendar getVisibleDate();
    /**
     * Default date.<br>
     * Дата по умолчанию.
     * @since 1.0
     */
    Calendar getDefaultDate();
    /**
     * Sets default date.<br>
     * Устанавливает дату по умолчанию.
     * @since 1.0
     */
    void setDefaultDate(Calendar aDate) throws IncompatibleDataExeption;
    /**
     * Shifts cursor on specified steps count vertically and hirizontally.<br>
     * Сдвиг выбранной ячейки.
     * @since 1.0
     */
    void shift(int rowShift, int columnShift);
    /**
     * Trying select date under cursor, must be defined in child classes.<br>
     * Выбирает дату под курсором с учетом возможной блокировки. Должен быть переопределен в потомках.
     * @since 1.0
     */
    void tryApplySelection();
    /**
     * Selects null.<br>
     * Выбор null
     * @since 1.0
     */
    void selectNothing();
    /**
     * Is cursor in specified position.<br>
     * Проверяет, находится ли курсор в заданной позиции.
     * @since 1.0
     */
    boolean isCursor(int row, int column);
    /**
     * Jumps on specified months count.<br>
     * Осуществляет прыжок на заданной количество месяцев.
     * @param shift На скоько месяцев переместиться.
     * Отрицательные значения обозначают прыжок назад.
     * @since 1.0
     */
    void monthShift(int shift);
    /**
     * Jumps on specified years count.<br>
     * Осуществляет прыжок на заданной количество лет.
     * @param shift На скоько лет переместиться.
     * Отрицательные значения обозначают прыжок назад.
     * @since 1.0
     */
    void yearShift(int shift);
    /**
     * Are neighbour months visible.<br>
     * Выводить ли соседний месяц.
     * @since 1.0
     */
    boolean isShowNeighbourMonth();
    /**
     * Sets neighbour months visibility.<br>
     * Настраивает вывод дней из соседнего месяца.
     * @since 1.0
     */
    void setShowNeighbourMonth(boolean showNeighbourMonth);
    /**
     * Is model enabled.<br>
     * Доступна для изменений.
     * @since 1.0
     */
    boolean isEnabled();
    /**
     * Sets model enabled.<br>
     * Доступна для изменений.
     * @since 1.0
     */
    void setEnabled(boolean enabled);
    /**
     * Forbidden date for selection.<br>
     * Возвращает запрещенные для выбора даты.
     * @since 1.0
     */
    Iterable<Period> getForbidden();
    /**
     * Sets forbiddend for selection dates.<br>
     * Устанавливает запрещенные для выбора даты.
     * @since 1.0
     */
    void setForbidden(Iterable<Period> forbiddenPeriods);
    /**
     * Get maximal enabled date.<br>
     * Возвращает максимальную дату.
     * @since 1.0
     */
    Calendar getMaxConstraint();
    /**
     * Get minimal enabled date.<br>
     * Возвращает минимальную дату.
     * @since 1.0
     */
    Calendar getMinConstraint();
    /**
     * Sets maximal date.<br>
     * Устанавливает максимальную дату.
     * @since 1.0
     */
    void setMaxConstraint(Calendar maxConstraint);
    /**
     * Sets minimal date.<br>
     * Устанавливает минимальную дату.
     * @since 1.0
     */
    void setMinConstraint(Calendar minConstraint);    
    /**
     * Locale.<br>
     * Возвращает локализацию.
     * @since 1.0
     */
    Locale getLocale();
    /**
     * Sets locale.<br>
     * Устанавливает локализацию.
     * @since 1.0
     */
    void setLocale(Locale locale);
    /**
     * Fires "Selection changed" event.<br>
     * Рассылает событие "Изменение выбора".
     * @since 1.0
     */
    void fireSelectionChange();
    /**
     * Commits selection.<br>
     * Закрепляет выбор
     * @since 1.0
     */
    void commit();
    /**
     * Is auto month scroll enabled.<br>
     * Проверяет, включена ли автоматическая прокрутка.
     * @since 1.0
     */
    boolean isAutoScroll();
    /**
     * Sets auto month scroll enabled.<br>
     * Устанавливает режим автоматической прокрутки.
     * @since 1.0
     */
    void setAutoScroll(boolean autoScroll);
    /**
     * Is model locked (cursor is moving but no selection available).<br>
     * Проверяет не заблокирован ли компонент.
     * @since 1.0
     */
    boolean isLocked();
    /**
     * Sets lock.<br>
     * Изменяет состояние блокировки компонента.
     * @since 1.0
     */
    void setLocked(boolean locked);
    /**
     * True if no selected dates (null selection).<br>
     * Возвращает истину, если не выбрано ни одной даты.
     * @since 1.0
     */
    boolean isNothingSelected();
    /**
     * Selects nothing (null).<br>
     * Снимает выбор со всех дат.
     * @since 1.0
     */
    void setNothingSelected(boolean nothingSelected);
    /**
     * Get date under cursor.<br>
     * Возвращает дату, на которой находится курсор.
     * @since 1.0
     */
    Calendar getCurrent();
    /**
     * Selects specified date.<br>
     * Выбирает заданную дату.
     * @since 1.0
     */
    boolean select(Calendar aDate);
    /**
     * Allows null selection.<br>
     * Позволяет разрешить или запретить не выбирать ни одной даты.
     * @since 1.0
     */
    void setNothingAllowed(boolean allow);
    /**
     * Is null selection allowed.<br>
     * Разрешен ли пустой выбор.
     * @since 1.0
     */
    boolean isNothingAllowed();
    /**
     * Adds property change listener.<br>
     * Добавляет слушателя события "Изменение свойства".
     * @since 1.0
     */
    void addPropertyChangeListener(PropertyChangeListener listener);
    /**
     * Removes property change listener.<br>
     * Удаляет слушателя события "Изменение свойства".
     * @since 1.0
     */
    void removePropertyChangeListener(PropertyChangeListener listener);
    /**
     * Adds cursor move listener.<br>
     * Добавляет слушателя события "Перемещение курсора".
     * @since 1.0
     */
    void addCursorMoveListener(CursorMoveListener listener);
    /**
     * Removes cursor move listener.<br>
     * Удаляет слушателя события "Перемещение курсора".
     * @since 1.0
     */
    void removeCursorMoveListener(CursorMoveListener listener);
    /**
     * Adds selection change listener.<br>
     * Добавляет слушателя события "Изменение выбора".
     * @since 1.0
     */
    void addSelectionChangedListener(SelectionChangedListener listener);
    /**
     * Removes selection change listener.<br>
     * Удаляет слушателя события "Изменение выбора".
     * @since 1.0
     */
    void removeSelectionChangedListener(SelectionChangedListener listener);
    /**
     * Adds commit selection event listener.<br>
     * Добавляет слушателя события "Подтверждение выбора".
     * @since 1.0
     */
    void addCommitListener(CommitListener listener);
    /**
     * Removes commit selection event listener.<br>
     * Удаляет слушателя события "Подтверждение выбора".
     * @since 1.0
     */
    void removeCommitListener(CommitListener listener);
    
}

/*
 * CellStates.java
 *
 * Created on 20 Май 2006 г., 18:48
 *
 */

package datechooser.model;

/**
 * Set of cell states.<br>
 * Перечисление, задающее множество состояний клеток - дней.
 * @author Androsov Vadim
 * @since 1.0
 */
public enum CellState {
    
    /**
     * Current date.<br>
     * Текущая дата
     * @since 1.0
     */
    NOW,
    /**
     * Selected cell.<br>
     * Выбрано
     * @since 1.0
     */
    SELECTED,
    /**
     * Unaccessible for selection cell.<br>
     * Недопустимо для выбора
     * @since 1.0
     */
    UNACCESSIBLE, 
    /**
     * Usual cell.<br>
     * Обычная ячейка
     * @since 1.0
     */
    NORMAL, 
    /**
     * If select this cell month will be scrolled.<br>
     * Обычная ячейка, при ее выборе произойдет проктутка на другой месяц.
     * @since 1.0
     */
    NORMAL_SCROLL 
}

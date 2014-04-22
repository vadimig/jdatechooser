/*
 * DateChooseController.java
 *
 * Created on 4 Июль 2006 г., 19:15
 *
 */

package datechooser.controller;

import datechooser.view.*;

/**
 * Controller interface for daychooser panel.<br>
 * Общий интерфейс контроллера панели выбора даты.
 * @author Androsov Vadim
 * @since 1.0
 */
public interface DateChooseController {
    /**
     * When panel size changes.<br>
     * Используется в случаях, когда размер панели меняется.
     */
    void reBound();
    /**
     * Attaches to day selection panel.<br>
     * Устанавливает панель календаря, подключая к ней себя (контроллер).
     */
    void setView(GridPane palette);
}

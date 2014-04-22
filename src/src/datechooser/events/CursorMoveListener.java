/*
 * SelectionChangedListener.java
 *
 * Created on 24 Август 2006 г., 18:02
 *
 */

package datechooser.events;

import java.util.EventListener;

/**
 * Date selection cursor move listener.<br>
 * Слушатель перемещения курсора.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.events.CursorMoveEvent
 */
public interface CursorMoveListener extends EventListener {
    /**
     * Cursor moved.
     * To get date under cursor use getCurrent bean method.<br>
     * Метод вызывается при перемещении курсора. 
     * Дату, на которой находится курсор можно узнать, вызвав метод getCurrent компонента.
     */
    void onCursorMove(CursorMoveEvent evt);
}

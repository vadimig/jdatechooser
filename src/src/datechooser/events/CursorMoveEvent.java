/*
 * SelectionChangedEvent.java
 *
 * Created on 24 Август 2006 г., 17:58
 *
 */

package datechooser.events;

import java.util.EventObject;

/**
 * Событие "Перемещение курсора".
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.events.CursorMoveListener
 */
public class CursorMoveEvent extends EventObject {
    
    public CursorMoveEvent(Object source) {
        super(source);
    }
}

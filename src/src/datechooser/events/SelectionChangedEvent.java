/*
 * SelectionChangedEvent.java
 *
 * Created on 24 Август 2006 г., 17:58
 *
 */

package datechooser.events;

import java.util.EventObject;

/**
 * Event: "Selection changed".<br>
 * Событие "Изменение выбора".
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.events.SelectionChangedListener
 */
public class SelectionChangedEvent extends EventObject {
    
    public SelectionChangedEvent(Object source) {
        super(source);
    }
}

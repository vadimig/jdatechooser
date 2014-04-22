/*
 * SelectionChangedEvent.java
 *
 * Created on 24 Август 2006 г., 17:58
 *
 */

package datechooser.events;

import java.util.EventObject;

/**
 * Event: "selection committed".<br>
 * Событие "Подтверждение выбора"
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.events.CommitListener
 */
public class CommitEvent extends EventObject {
    
    public CommitEvent(Object source) {
        super(source);
    }
}

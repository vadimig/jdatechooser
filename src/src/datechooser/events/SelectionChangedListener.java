/*
 * SelectionChangedListener.java
 *
 * Created on 24 Август 2006 г., 18:02
 *
 */

package datechooser.events;

import java.util.EventListener;

/**
 * Selection changed listener.<br>
 * Слушатель изменения выбора.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.events.SelectionChangedEvent
 */
public interface SelectionChangedListener extends EventListener { 
    /**
     * User change selection.<br>
     * Метод вызывается при изменении выбора пользователя.
     */
    void onSelectionChange(SelectionChangedEvent evt);
}

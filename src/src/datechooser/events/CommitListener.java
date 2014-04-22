/*
 * SelectionChangedListener.java
 *
 * Created on 24 јвгуст 2006 г., 18:02
 *
 */

package datechooser.events;

import java.util.EventListener;

/**
 * Commit event listenet.<br>
 * —лушатель подтверждени€ выбора.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.events.CommitEvent
 */
public interface CommitListener extends EventListener { 
    /**
     * User finished input.<br>
     * ћетод вызываетс€ при окончании ввода пользовател€€.
     */
    void onCommit(CommitEvent evt);
}

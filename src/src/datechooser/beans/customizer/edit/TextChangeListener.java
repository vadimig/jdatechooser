/*
 * TextChangeListener.java
 *
 * Created on 8 Ноябрь 2006 г., 8:11
 *
 */

package datechooser.beans.customizer.edit;

import javax.swing.event.*;

/**
 * Listens any text changes without their differentiation.<br>
 * Введен для простоты. Позволяет следить за изменениями текста без уточнения
 * характера этих изменений.
 * @author Androsov Vadim
 * @since 1.0
 */
public abstract class TextChangeListener implements DocumentListener {
    
    public void insertUpdate(DocumentEvent e) {
        textChanged(e);
    }

    public void removeUpdate(DocumentEvent e) {
        textChanged(e);
    }

    public void changedUpdate(DocumentEvent e) {
    }
    
    /**
     * Use this method to listen changes.<br>
     * Изпользуйте этот метод для слежения за изменениями текста.
     * @since 1.0
     */
    public abstract void textChanged(DocumentEvent e);
}

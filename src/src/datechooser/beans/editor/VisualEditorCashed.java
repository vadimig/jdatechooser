/*
 * VisualEditorCashed.java
 *
 * Created on 24 Август 2006 г., 9:19
 *
 */

package datechooser.beans.editor;

import java.beans.PropertyEditorSupport;
import javax.swing.JComponent;

/**
 * Superclass for all visual properties editors in this library.
 * Caches editor component. To turn caching off your editor must extend 
 * VisualEditor.<br>
 * Предок для всех редакторов, имеющих визуальный пользовательский редактор.
 * Визуальный редактор кешируется. Для отключения кеширования просто поменяйте
 * предка своего редактора на VisualEditor.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.beans.editor.VisualEditor
 */
public abstract class VisualEditorCashed extends VisualEditor {
    
    private JComponent editor = null;

    protected JComponent getEditorCash() {
        return editor;
    }

    protected void setEditorCash(JComponent editor) {
        this.editor = editor;
    }
    
    public JComponent getCustomEditor() {
        if (getEditorCash() == null) {
            setEditorCash(createEditor());
        }
        return getEditorCash();
    }
}

/*
 * FontEditorPane.java
 *
 * Created on 31 ָ‏כü 2006 ד., 6:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datechooser.beans.editor.font;

import java.awt.*;
import java.beans.*;
import javax.swing.*;

/**
 * Panel for font editor.<br>
 * ֿאםוכü נוהאךעמנא רנטפעמג.
 * @author Androsov Vadim
 * @since 1.0
 */
class FontEditorPane extends JPanel implements PropertyChangeListener {
    
    private PropertyEditorSupport editor;
    private FontSelectPane selector;
    private FontPreviewPane preview;
    
    public FontEditorPane(PropertyEditorSupport editor) {
        setEditor(editor);
        selector = new FontSelectPane((Font) editor.getValue());
        preview = new FontPreviewPane((Font) editor.getValue());
        setLayout(new BorderLayout());
        add(selector, BorderLayout.NORTH);
        add(preview, BorderLayout.CENTER);
        
        selector.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (!FontSelectPane.FONT_EVENT_NAME.equals(evt.getPropertyName())) return;
                Font newFont = selector.getSelectedFont();
                preview.setFont(newFont);
                getEditor().setValue(newFont);
                getEditor().firePropertyChange();
            }
        });
        
    }
    
    public PropertyEditorSupport getEditor() {
        return editor;
    }
    
    public void setEditor(PropertyEditorSupport editor) {
        if (this.editor != null) this.editor.removePropertyChangeListener(this);
        this.editor = editor;
        editor.addPropertyChangeListener(this);
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        Font font = (Font) getEditor().getValue();
        selector.setSelectedFont(font);
        preview.setFont(font);
    }
}

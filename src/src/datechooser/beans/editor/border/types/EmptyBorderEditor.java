/*
 * EmptyBorderEditor.java
 *
 * Created on 31 »юль 2006 г., 18:05
 *
 */

package datechooser.beans.editor.border.types;

import datechooser.beans.editor.utils.*;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * Editor for empty borders.<br>  
 * –едактор границ типа Empty
 * @author Androsov Vadim
 * @since 1.0
 */
public class EmptyBorderEditor extends AbstractBorderEditor implements PropertyChangeListener {
    
    
    private Insets insets;
    private InsetsPanel insetsPane;
    
    public EmptyBorderEditor() {
        initialize();
        setCaption(getEditorLocaleString("Empty"));
        insetsPane = new InsetsPanel(insets);
        insetsPane.addPropertyChangeListener(this);
        add(insetsPane);
        refreshInterface();
    }
    
    public void prepareSelection() {
        setValue(new EmptyBorder(insets));
    }
    
    protected EmptyBorder getValue() {
        return (EmptyBorder) value;
    }
    
    public void refreshInterface() {
        Insets currIns = getValue().getBorderInsets();
        insets.set(currIns.top, currIns.left, currIns.bottom, currIns.right);
        insetsPane.refresh();
    }
    
    protected Border getDefaultValue() {
        insets = new Insets(1, 1, 1, 1);
        return new EmptyBorder(insets);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (!InsetsPanel.INSETS_EVENT_NAME.equals(evt.getPropertyName())) return;
        fireChange();
    }
}

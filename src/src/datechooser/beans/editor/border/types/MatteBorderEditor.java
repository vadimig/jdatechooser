/*
 * MatteBorderEditor.java
 *
 * Created on 31 »юль 2006 г., 18:06
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

/**
 * Editor for matte borders.<br>  
 * –едактор границ типа Matte
 * @author Androsov Vadim
 * @since 1.0
 */
public class MatteBorderEditor extends AbstractBorderEditor {
    
    private Insets insets;
    private ColorHolder color;
    private InsetsPanel insetsPane;
    
    public MatteBorderEditor() {
        color = new ColorHolder();
        setCaption(getEditorLocaleString("Matte"));

        initialize();
        
        insetsPane = new InsetsPanel(insets);
        insetsPane.addPropertyChangeListener(new InsetsListener());
        
        JButton bColor = new JButton(getEditorLocaleString("Matte_color"));
        ColorChooseAction colorChooseAction = new ColorChooseAction(color, 
                getEditorLocaleString("Matte_color"), this);
        colorChooseAction.addPropertyChangeListener(new ColorListener());
        bColor.addActionListener(colorChooseAction);
        
        setLayout(new GridLayout(2, 1));
        add(getCenteredPane(insetsPane));
        add(getCenteredPane(bColor));
        
        refreshInterface();
    }

    protected void prepareSelection() {
        setValue(new MatteBorder(insets, color.getColor()));
    }
    
    private void assignValueToParameters() {
        Insets currIns = getValue().getBorderInsets();
        insets.set(currIns.top, currIns.left, currIns.bottom, currIns.right);
        color.setColor(getValue().getMatteColor());
    }
    
    protected MatteBorder getValue() {
        return (MatteBorder) value;
    }

    public void refreshInterface() {
        assignValueToParameters();
        insetsPane.refresh();
    }

    protected Border getDefaultValue() {
        insets = new Insets(1, 1, 1, 1);
        return new MatteBorder(insets, Color.GRAY);
    }
    
    private class InsetsListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            if (!InsetsPanel.INSETS_EVENT_NAME.equals(evt.getPropertyName())) return;
            fireChange();
        }
    }
    
    private class ColorListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            if (!ColorChooseAction.COLOR_CHOOSE_EVENT_NAME.equals(evt.getPropertyName())) return;
            fireChange();
        }
    }
    
}

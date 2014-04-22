/*
 * NullBorderEditor.java
 *
 * Created on 3 Август 2006 г., 11:26
 *
 */

package datechooser.beans.editor.border.types;

import datechooser.beans.editor.utils.TextOutput;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Default border editing. <b>Not used</b>.<br>
 * Необходим для отображения границы по умолчанию - было решено не использовть.
 * @author Androsov Vadim
 * @since 1.0
 */
public class DefaultBorderEditor extends AbstractBorderEditor {
    
    public DefaultBorderEditor() {
        initialize();
        setCaption(getEditorLocaleString("Default"));
        refreshInterface();
    }
    
    protected Border getDefaultValue() {
        return BorderFactory.createEmptyBorder();
    }
    
    protected void prepareSelection() {
        
    }
    
    protected void refreshInterface() {
    }
}

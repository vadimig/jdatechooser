/*
 * NoBorderEditor.java
 *
 * Created on 4 јвгуст 2006 г., 6:37
 *
 */

package datechooser.beans.editor.border.types;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.border.Border;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * Lets set no border.<br>  
 * –едактор, позвол€ющий задать отсутствие границы.
 * @author Androsov Vadim
 * @since 1.0
 */
public class NoBorderEditor extends AbstractBorderEditor {
    
    public NoBorderEditor() {
        setCaption(getEditorLocaleString("No_borded"));
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(new JLabel("x"));
     }

    protected Border getDefaultValue() {
        return null;
    }

    protected void prepareSelection() {
        value = null;
    }

    protected void refreshInterface() {
    }

    public void setCurrentBorder(Border border) {
        value = null;
    }
}

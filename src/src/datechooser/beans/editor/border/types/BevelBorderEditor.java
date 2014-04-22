/*
 * BevelBorderEditor.java
 *
 * Created on 31 »юль 2006 г., 22:39
 *
 */

package datechooser.beans.editor.border.types;

import java.awt.Color;
import javax.swing.border.*;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * Editor for bevel borders.<br>  
 * –едактор границ типа Bevel
 * @author Androsov Vadim
 * @since 1.0
 */
public class BevelBorderEditor extends AbstractBevelBorderEditor {
    
    public BevelBorderEditor() {
         setCaption(getEditorLocaleString("Bevel"));
    }
    
    protected BevelBorder getBorderByParams() {
        return new BevelBorder (bevelType,
                highlightOuter.getColor(), highlightInner.getColor(),
                shadowOuter.getColor(), shadowInner.getColor());
    }

    protected Border getDefaultValue() {
        return new BevelBorder(BevelBorder.RAISED);
    }
}

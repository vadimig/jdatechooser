/*
 * SimpleBorderEditor.java
 *
 * Created on 31 »юль 2006 г., 13:46
 *
 */

package datechooser.beans.editor.border;

import datechooser.beans.editor.VisualEditorCashed;
import datechooser.beans.editor.descriptor.DescriptionManager;
import java.awt.*;
import java.beans.*;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**
 * Border editor.<br> 
 * –едактор границ.
 * @author Androsov Vadim
 * @since 1.0
 */
public class SimpleBorderEditor extends VisualEditorCashed {
    
    public String getAsText() {
        return null;
    }

    public boolean isPaintable() {
        return true;
    }

    public void paintValue(Graphics gfx, Rectangle box) {
        gfx.drawString(DescriptionManager.describe(getValue()), 
                box.x, box.y + box.height - 3);
    }

    public String getJavaInitializationString() {
        return DescriptionManager.describeJava(getValue(), Border.class);
    }

    protected JComponent createEditor() {
        return new BorderEditorPane(this);
    }
}

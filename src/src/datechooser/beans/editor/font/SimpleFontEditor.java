/*
 * FontEditor.java
 *
 * Created on 31 ָ‏כü 2006 ד., 6:28
 *
 */

package datechooser.beans.editor.font;

import datechooser.beans.editor.VisualEditorCashed;
import datechooser.beans.editor.descriptor.DescriptionManager;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyEditorSupport;
import javax.swing.JComponent;

/**
 * Font editor.<br>
 * ׀והאךעמנ רנטפעמג.
 * @author Androsov Vadim
 * @since 1.0
 */
public class SimpleFontEditor extends VisualEditorCashed {
    
    private Font outFont = new Font("Dialog", Font.PLAIN, 10);
    
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
        return DescriptionManager.describeJava(getValue(), Font.class);
    }

    protected JComponent createEditor() {
        return new FontEditorPane(this);
    }
}

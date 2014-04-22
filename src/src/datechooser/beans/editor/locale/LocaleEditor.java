/*
 * LocaleEditor.java
 *
 * Created on 10 Август 2006 г., 18:28
 *
 */

package datechooser.beans.editor.locale;

import datechooser.beans.editor.VisualEditorCashed;
import datechooser.beans.editor.descriptor.DescriptionManager;
import java.awt.*;
import java.beans.PropertyEditorSupport;
import java.util.Locale;
import javax.swing.JComponent;

/**
 * Locale editor.<br>
 * Редактор для выбора локалицации.
 * @author Androsov Vadim
 * @since 1.0
 */
public class LocaleEditor extends VisualEditorCashed{
    
    public String getAsText() {
        return null;
    }

    public void paintValue(Graphics gfx, Rectangle box) {
        int wasSize = gfx.getFont().getSize();
        gfx.setFont(new Font("Serif", Font.PLAIN, wasSize));
        gfx.drawString(DescriptionManager.describe(getValue()), 
                box.x, box.y + box.height - 3);
    }

    public boolean isPaintable() {
        return true;
    }
    
    public String getJavaInitializationString() {
        return DescriptionManager.describeJava(getValue(), Locale.class);
    }

    protected JComponent createEditor() {
        return new LocaleEditorPane(this);
    }
}

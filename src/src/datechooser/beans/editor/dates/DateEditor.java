/*
 * PeriodsEditor.java
 *
 * Created on 8 Август 2006 г., 18:13
 *
 */

package datechooser.beans.editor.dates;

import datechooser.beans.editor.VisualEditorCashed;
import datechooser.beans.editor.descriptor.DescriptionManager;
import datechooser.model.multiple.Period;
import datechooser.model.multiple.PeriodSet;
import java.awt.*;
import java.beans.PropertyEditorSupport;
import java.util.GregorianCalendar;
import javax.swing.JComponent;

/**
 * Date editor. Uses datechooser components.<br>
 * Редактор дат. Используются компоненты из этой библиотеки.
 * @author Androsov Vadim
 * @since 1.0
 */
public class DateEditor extends VisualEditorCashed {
    
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
        return DescriptionManager.describeJava(getValue(), GregorianCalendar.class);
    }

    protected JComponent createEditor() {
        return new DateEditorPane(this);
    }

}

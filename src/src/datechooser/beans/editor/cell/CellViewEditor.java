/*
 * CellEditor.java
 *
 * Created on 30 Июль 2006 г., 8:40
 *
 */

package datechooser.beans.editor.cell;

import datechooser.beans.editor.VisualEditor;
import datechooser.beans.editor.VisualEditorCashed;
import datechooser.beans.editor.descriptor.DescriptionManager;
import datechooser.view.GridPane;
import datechooser.view.appearance.CellAppearance;
import datechooser.view.appearance.custom.CustomCellAppearance;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

import java.awt.*;
import java.beans.*;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Cell appearance editor. May be used independently.
 * But it was desided do not make such properies.
 * It is used be calendar appearance editor.<br>
 * Редактор внешнего вида ячейки. Может использоваться и отдельно. Однако было
 * принято решение не делать таких свойств компонента. Но он все равно
 * используется в составе редактора внешнего вида
 * @author Androsov Vadim
 * @see datechooser.beans.editor.appear.AppearEditor
 * @since 1.0
 */
public class CellViewEditor extends VisualEditorCashed{
    
    public String getAsText() {
        return null;
    }
    
    public boolean isPaintable() {
        return true;
    }
    
    public void paintValue(Graphics gfx, Rectangle box) {
        CellAppearance look = (CellAppearance)getValue();
        look.render((Graphics2D) gfx, new JPanel(), getEditorLocaleString("Cell_preview_text"), 
                box.height * 2, box.height, true);

    }
    
    public String getJavaInitializationString() {
        return DescriptionManager.describeJava(getValue(), CustomCellAppearance.class);
    }

    protected JComponent createEditor() {
        return new MainCellEditorPane(this);
    }
}

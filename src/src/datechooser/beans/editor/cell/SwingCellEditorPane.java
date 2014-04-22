/*
 * SwingCellEditorPane.java
 *
 * Created on 29 ќкт€брь 2006 г., 13:38
 *
 */

package datechooser.beans.editor.cell;

import java.awt.*;
import javax.swing.*;

/**
 * Panel for swing appearance.<br>
 * ѕанель дл€ редактировани€ swing - представлений (дл€ отрисовки используютс€
 * визуальные компоненты библиотеки swing)
 * @see datechooser.view.appearance.swing.SwingCellAppearance
 * @author Androsov Vadim
 * @since 1.0
 */
class SwingCellEditorPane extends CellEditorPane {
    
    public SwingCellEditorPane(MainCellEditorPane parentEditor) {
        super(parentEditor);
    }
    
    protected void generateInterface() {
        setLayout(new GridLayout(1, 3, 5, 5));
        add(createFontChooseButton());
        add(createTextColorChooseButton());
        add(createCursorColorChooseButton());
       setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
     }

    protected void updateEditorState() {
    }
}

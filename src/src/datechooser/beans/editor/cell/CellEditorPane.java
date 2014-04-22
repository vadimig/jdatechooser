/*
 * CellEditorPane.java
 *
 * Created on 25 Октябрь 2006 г., 11:17
 *
 */

package datechooser.beans.editor.cell;

import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;
import datechooser.beans.editor.border.SimpleBorderEditor;
import datechooser.beans.editor.font.SimpleFontEditor;
import datechooser.beans.editor.utils.EditorDialog;
import datechooser.view.appearance.CellAppearance;
import datechooser.view.appearance.custom.CustomCellAppearance;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Abstract cell appearance editor.<br>
 * Заготовка панели редактирования внешнего вида ячейки.
 * @author Androsov Vadim
 * @since 1.0
 */
public abstract class CellEditorPane extends JPanel {
    private EditorDialog fontEditorDialog;
    protected MainCellEditorPane mainEditor;
    private JButton selCursorColorCash;
    
    public CellEditorPane(MainCellEditorPane parentEditor) {
        setMainEditor(parentEditor);
        fontEditorDialog = new EditorDialog((Frame)this.getParent(), new SimpleFontEditor());
        
        generateInterface();
    }
    
    protected MainCellEditorPane getMainEditor() {
        return mainEditor;
    }
    
    protected CellAppearance getValue() {
        return (CellAppearance) getMainEditor().getValue();
    }
    
    protected Color selectColor(Color oldColor, String prompt) {
        Color newColor = JColorChooser.showDialog(this, prompt, oldColor);
        return newColor != null ? newColor : oldColor;
    }
    
    protected void setMainEditor(MainCellEditorPane mainEditor) {
        this.mainEditor = mainEditor;
    }
    
    protected JButton createFontChooseButton() {
        JButton bFont = new JButton(getEditorLocaleString("Font"));
        bFont.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font newFont = (Font)
                fontEditorDialog.showDialog(getValue().getFont(), getEditorLocaleString("Select_font"));
                if (!fontEditorDialog.isCanceled()) {
                    getValue().setFont(newFont);
                    getMainEditor().fireLocalPropertyChange();
                }
            }
        });
        return bFont;
    }
    
    protected JButton createCursorColorChooseButton() {
        JButton selCursorColor = new JButton(getEditorLocaleString("Cursor_color"));
        
        selCursorColor.setEnabled(getValue().isSelectable());
        selCursorColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getValue().setCursorColor(
                        selectColor(getValue().getCursorColor(),
                        getEditorLocaleString("Select_cursor_color")));
                getMainEditor().fireLocalPropertyChange();
            }
        });
        selCursorColorCash = selCursorColor;
        return selCursorColor;
    }
    
    protected JButton createTextColorChooseButton() {
        JButton selTextColor = new JButton(getEditorLocaleString("Text_color"));
        selTextColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getValue().setTextColor(
                        selectColor(getValue().getTextColor(),
                        getEditorLocaleString("Select_text_color")));
                getMainEditor().fireLocalPropertyChange();
            }
        });
        return selTextColor;
    }
    
    public EditorDialog getFontEditorDialog() {
        return fontEditorDialog;
    }
    
    void updateState() {
        selCursorColorCash.setEnabled(getValue().isSelectable());
        updateEditorState();
    }
    
    protected abstract void generateInterface();
    protected abstract void updateEditorState();
}

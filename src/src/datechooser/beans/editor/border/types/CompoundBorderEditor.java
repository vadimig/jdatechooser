/*
 * CompoundBorderEditor.java
 *
 * Created on 31 »юль 2006 г., 18:04
 *
 */

package datechooser.beans.editor.border.types;

import datechooser.beans.editor.border.*;
import datechooser.beans.editor.utils.*;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Editor for compound borders.<br> 
 * –едактор границ типа Compound
 * @author Androsov Vadim
 * @since 1.0
 */
public class CompoundBorderEditor extends AbstractBorderEditor {
    
    private Border inside;
    private Border outside;
    private EditorDialog editorDialog;
    
    public CompoundBorderEditor() {
        initialize();
        setCaption(getEditorLocaleString("Compound"));
        editorDialog = new EditorDialog((Frame)getParent(), new SimpleBorderEditor());
        JPanel buttons = new JPanel(new GridLayout(2, 1, 5, 2));
        buttons.add(createOutsideChooseButton());
        buttons.add(createInsideChooseButton());
        setLayout(new BorderLayout());
        add(buttons, BorderLayout.NORTH);
        refreshInterface();
    }
    
    private JPanel createInsideChooseButton() {
        JButton bBorder = new JButton(getEditorLocaleString("Inside_border"));
        bBorder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Border newBorder = (Border)
                editorDialog.showDialog(inside, getEditorLocaleString("Select_border"));
                if (!editorDialog.isCanceled()) {
                    inside = newBorder;
                    fireChange();
                }
            }
        });
        return getCenteredPane(bBorder);
    }
    
    private JPanel createOutsideChooseButton() {
        JButton bBorder = new JButton(getEditorLocaleString("Outside_border"));
        bBorder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Border newBorder = (Border)
                editorDialog.showDialog(outside, getEditorLocaleString("Select_border"));
                if (!editorDialog.isCanceled()) {
                    outside = newBorder;
                    fireChange();
                }
            }
        });
        return getCenteredPane(bBorder);
    }
    
    public void prepareSelection() {
        value = new CompoundBorder(outside, inside);
    }
    
    public void refreshInterface() {
        CompoundBorder compoundBorder = (CompoundBorder) getCurrentBorder();
        if (compoundBorder != null) {
            inside = compoundBorder.getInsideBorder();
            outside = compoundBorder.getOutsideBorder();
        }
    }
    
    protected Border getDefaultValue() {
        return BorderFactory.createCompoundBorder();
    }
    
}

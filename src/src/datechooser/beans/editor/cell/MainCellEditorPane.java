/*
 * CellEditorPane.java
 *
 * Created on 30 »юль 2006 г., 8:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datechooser.beans.editor.cell;

import datechooser.beans.editor.border.SimpleBorderEditor;
import datechooser.beans.editor.font.SimpleFontEditor;
import datechooser.beans.editor.utils.*;
import datechooser.view.GridPane;
import datechooser.view.appearance.CellAppearance;
import datechooser.view.appearance.custom.CustomCellAppearance;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;
import datechooser.view.appearance.swing.SwingCellAppearance;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;

/**
 * Main cell appearance editing panel.<br>
 * ѕанель дл€ редактировани€ €чеек всех типов.
 * @author Androsov Vadim
 * @since 1.0
 */
public class MainCellEditorPane extends JPanel implements PropertyChangeListener {
    
    private CellViewEditor editor;
    private CellPreview preview;
    private CellAppearance value;
    private boolean tempEdit;
    private Map<Class, CellEditorPane> editPanels;
    private Class currentEditingStyle;
    
    
    public MainCellEditorPane(CellViewEditor editor)  {
         setEditor(editor);
        initializeEditors();
        preview = new CellPreview(getValue());
        generateClonedValue();
        setCurrentEditingStyle(getEditor().getValue().getClass());
        setLayout(new BorderLayout());
        addCurrentSelector();
        add(preview, BorderLayout.CENTER);
    }
    
    private void addCurrentSelector() {
        add(getSelectors(), BorderLayout.NORTH);
    }
    
    private void initializeEditors() {
        editPanels = new HashMap<Class, CellEditorPane>();
        editPanels.put(CustomCellAppearance.class, new CustomCellEditorPane(this));
        editPanels.put(SwingCellAppearance.class, new SwingCellEditorPane(this));
    }
    
    private CellEditorPane getCurrentEditorPane() {
        return editPanels.get(getCurrentEditingStyle());
    }
    
    private void generateClonedValue() {
        if (!isTempEdit()) {
            setTempEdit(true);
            getEditor().setValue(
                    ((CellAppearance)getEditor().getValue()).clone());
            getEditor().firePropertyChange();
            preview.setLook((CellAppearance) getEditor().getValue());
            setTempEdit(false);
        }
    }
    
    
    private JPanel getSelectors() {
        return getCurrentEditorPane();
    }
    
    void fireLocalPropertyChange() {
        getEditor().firePropertyChange();
    }
    
    
    CellAppearance getValue() {
        return (CellAppearance) getEditor().getValue();
    }
    
    public CellViewEditor getEditor() {
        return editor;
    }
    
    public void setEditor(CellViewEditor editor) {
        if ((getEditor() != editor) && (getEditor() != null)) {
            getEditor().removePropertyChangeListener(this);
        }
        this.editor = editor;
        if (getEditor() != null) {
            getEditor().addPropertyChangeListener(this);
        }
    }
    
    public boolean isTempEdit() {
        return tempEdit;
    }
    
    public void setTempEdit(boolean tempEdit) {
        this.tempEdit = tempEdit;
    }
    
    private Class getCurrentEditingStyle() {
        return currentEditingStyle;
    }
    
    private void setCurrentEditingStyle(Class currentEditingStyle) {
        boolean styleChanged = false;
        if ((currentEditingStyle != getCurrentEditingStyle()) && (getCurrentEditingStyle() != null)) {
            remove(getSelectors());
            styleChanged = true;
        }
        this.currentEditingStyle = currentEditingStyle;
        if (styleChanged) {
            addCurrentSelector();
            revalidate();
        }
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        setCurrentEditingStyle(getEditor().getValue().getClass());
        getCurrentEditorPane().updateState();
        generateClonedValue();
        preview.repaint();
    }
}


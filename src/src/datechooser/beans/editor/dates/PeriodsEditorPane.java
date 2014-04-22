/*
 * PeriodsEditorPane.java
 *
 * Created on 8 Август 2006 г., 21:22
 *
 */

package datechooser.beans.editor.dates;

import datechooser.beans.DateChooserPanel;
import datechooser.beans.editor.descriptor.DescriptionManager;
import datechooser.model.multiple.Period;
import datechooser.model.multiple.PeriodSet;

import java.awt.BorderLayout;
import java.beans.*;
import javax.swing.*;

/**
 * Periods editor panel.<br>
 * Панель редактирования периодов.
 * @author Androsov Vadim
 * @since 1.0
 */
class PeriodsEditorPane extends JPanel implements PropertyChangeListener {
    
    private PropertyEditorSupport editor = null;
    private DateChooserPanel chooser;
    private static int c = 0;
    private boolean innerEdit;
    public PeriodsEditorPane(PropertyEditorSupport editor) {
        setEditor(editor);
        chooser = new DateChooserPanel();
        initEditor();
        setLayout(new BorderLayout());
        add(chooser, BorderLayout.CENTER);
        chooser.getModel().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                innerSetValue(chooser.getSelectedPeriodSet());
            }
        });
  }
    
    private void initEditor() {
        if (isInnerEdit()) return;
        setInnerEdit(true);
        if (getEditorValue() != null) {
            chooser.setSelection(getEditorValue());
        }
        setInnerEdit(false);
    }
    
    private void innerSetValue(PeriodSet value) {
        if (isInnerEdit()) {
            return;
        }
        setInnerEdit(true);
        getEditor().setValue(value.clone());
        setInnerEdit(false);
    }
    
    private PeriodSet getEditorValue() {
        return (PeriodSet) editor.getValue();
    }
    
    public PropertyEditorSupport getEditor() {
        return editor;
    }
    
    public void setEditor(PropertyEditorSupport editor) {
        if (getEditor() != null) {
            getEditor().removePropertyChangeListener(this);
        }
        this.editor = editor;
        getEditor().addPropertyChangeListener(this);
    }
    
    public boolean isInnerEdit() {
        return innerEdit;
    }
    
    public void setInnerEdit(boolean innerEdit) {
        this.innerEdit = innerEdit;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        initEditor();
    }
}

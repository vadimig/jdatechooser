/*
 * PeriodsEditorPane.java
 *
 * Created on 8 Август 2006 г., 21:22
 *
 */

package datechooser.beans.editor.dates;

import datechooser.beans.DateChooserPanel;
import datechooser.model.multiple.MultyModelBehavior;
import datechooser.model.multiple.Period;
import datechooser.model.multiple.PeriodSet;

import java.awt.BorderLayout;
import java.beans.*;
import java.util.*;
import javax.swing.*;

/**
 * Date editor panel.<br>
 * Панель редактирования дат.
 * @author Androsov Vadim
 * @since 1.0
 */
class DateEditorPane extends JPanel {
    
    private PropertyEditorSupport editor;
    private DateChooserPanel chooser;
    
    private boolean innerEdit;
    
    public DateEditorPane(PropertyEditorSupport editor) {
        setEditor(editor);
        chooser = new DateChooserPanel();
        chooser.setBehavior(MultyModelBehavior.SELECT_SINGLE);
        initEditor();
        setLayout(new BorderLayout());
        add(chooser, BorderLayout.CENTER);
        chooser.getModel().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                innerSetValue(chooser.getSelectedDate());
            }
        });
        
        getEditor().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                initEditor();
            }
        });
    }
    
    private void initEditor() {
        if (!isInnerEdit()) {
            innerSetValue(getEditorValue());
            setInnerEdit(true);
            chooser.setSelectedDate(getEditorValue());
            setInnerEdit(false);
        }
    }
    
    private void innerSetValue(Calendar value) {
        if (isInnerEdit()) {
            return;
        }
        setInnerEdit(true);
        Calendar date = value != null ? (Calendar) value.clone()  : null;
        getEditor().setValue(date);
        setInnerEdit(false);
    }
    
    private Calendar getEditorValue() {
        return (Calendar) editor.getValue();
    }
    
    public PropertyEditorSupport getEditor() {
        return editor;
    }
    
    public void setEditor(PropertyEditorSupport editor) {
        this.editor = editor;
    }
    
    public boolean isInnerEdit() {
        return innerEdit;
    }
    
    public void setInnerEdit(boolean innerEdit) {
        this.innerEdit = innerEdit;
    }
    
}

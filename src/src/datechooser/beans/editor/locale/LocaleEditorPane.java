/*
 * LocaleEditorComponent.java
 *
 * Created on 10 Август 2006 г., 22:51
 *
 */

package datechooser.beans.editor.locale;

import datechooser.beans.DateChooserPanel;
import datechooser.beans.editor.descriptor.DescriptionManager;
import datechooser.view.CalendarPane;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;

/**
 * Panel for locale editor.<br>
 * Панель редактора локализации.
 * @author Androsov Vadim
 * @since 1.0
 */
class LocaleEditorPane extends JPanel {
    
    private PropertyEditorSupport editor;
    private static LocaleSet data = null;
    private JComboBox selLocale;
    private boolean innerEdit;
    private DateChooserPanel dc;
    
    public LocaleEditorPane(PropertyEditorSupport editor) {
        if (data == null) data = new LocaleSet();
        setEditor(editor);
        setInnerEdit(false);
        selLocale = new JComboBox(data.getNames());
        dc = new DateChooserPanel();
//        dc.setEnabled(false);
        dc.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createEtchedBorder()));
        
        selLocale.setFont(new Font ("Serif", Font.PLAIN, 12));
        updateValue();
        selLocale.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isInnerEdit()) return;
                getEditor().setValue(data.getLocales()[selLocale.getSelectedIndex()]);
            }
        });
        getEditor().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateValue();
            }
        });
        setLayout(new BorderLayout());
        add(selLocale, BorderLayout.NORTH);
        add(dc, BorderLayout.CENTER);
    }
    
    private void updateValue() {
        if (isInnerEdit()) return;
        setInnerEdit(true);
        selLocale.setSelectedIndex(data.getIndex(getEditorValue()));
        dc.setLocale(getEditorValue());
        setInnerEdit(false);
    }
    
    private Locale getEditorValue() {
        return (Locale) getEditor().getValue();
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

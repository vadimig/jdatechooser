/*
 * EditorDialog.java
 *
 * Created on 1 Август 2006 г., 20:15
 *
 */

package datechooser.beans.editor.utils;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyEditorSupport;
import javax.swing.*;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * Dialog which can show any visual property editor.<br>
 * Диалоговое окно, способное отображать любой графический редактор.
 * @author Androsov Vadim
 * @since 1.0
 */
public class EditorDialog extends JDialog {
    
    private PropertyEditorSupport propertyEditor;
    private JButton bOK;
    private JButton bCancel;
    private Object value;
    private Object defaultValue;
    private JPanel bPane;
    private boolean canceled;
    
    public EditorDialog(Frame owner, PropertyEditorSupport editor) {
        super(owner, true);
        propertyEditor = getClonedEditor(editor);
        setCanceled(false);
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        OnClick onClick = new OnClick();
        bOK = new JButton(getEditorLocaleString("OK"));
        bOK.addActionListener(onClick);
        bCancel = new JButton(getEditorLocaleString("Cancel"));
        bCancel.addActionListener(onClick);
        bPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bPane.add(bOK);
        bPane.add(bCancel);
        
        pane.add(bPane, BorderLayout.SOUTH);
        tryToCreateEditorPanel(false);
        pack();
    }
    
    private PropertyEditorSupport getClonedEditor(PropertyEditorSupport editor) {
        PropertyEditorSupport newInst = null;
        try {
            newInst = editor.getClass().newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        newInst.setValue(editor.getValue());
        return newInst;
    }
    
    private void tryToCreateEditorPanel(boolean forceCreation) {
        if ((getEditor().getValue() == null) && (!forceCreation)) {
            return;
        }
        getContentPane().add(getEditor().getCustomEditor(), BorderLayout.CENTER);
    }
    
    private class OnClick implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bCancel) {
                setValue(null);
                setEditorToDefault();
                setCanceled(true);
            }
            if (e.getSource() == bOK) {
                setValue(getEditor().getValue());
                setCanceled(false);
            }
            setVisible(false);
        }
    }
    
    private void setEditorToDefault() {
        getEditor().setValue(getDefaultValue());
        getEditor().firePropertyChange();
    }
    
    public Object getValue() {
        return value;
    }
    
    private void setValue(Object value) {
        this.value = value;
    }
    
    public Object showDialog(Object defaultValue, String prompt) {
        setDefaultValue(defaultValue);
        setEditorToDefault();
        tryToCreateEditorPanel(true);
        setTitle(prompt);
        setCanceled(true);
        showCentered();
        return getValue();
    }
    
    private void showCentered() {
        Dimension dim = getContentPane().getPreferredSize();
        setSize(dim.width + 100, dim.height + 80);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        if ((dim.width > screenSize.width) || (dim.height > screenSize.height)) {
            setLocation(0, 0);
        } else {
            setLocation(
                    (screenSize.width - dim.width) / 2,
                    (screenSize.height - dim.height) / 2);
        }
        setVisible(true);
    }
    
    public PropertyEditorSupport getEditor() {
        return propertyEditor;
    }
    
    public boolean isCanceled() {
        return canceled;
    }
    
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
    
    public Object getDefaultValue() {
        return defaultValue;
    }
    
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
 
}

/*
 * PropertyCellEditor.java
 *
 * Created on 7 Ќо€брь 2006 г., 20:31
 *
 */

package datechooser.beans.customizer.edit;

import datechooser.beans.customizer.PropertyDescriptorsHolder;
import datechooser.beans.editor.utils.EditorDialog;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableCellEditor;

/**
 * Property cell editor.<br>
 * –едактор €чееск со свойствами в зависимости от типа.
 * @author Androsov Vadim
 * @since 1.0
 */
public class PropertyCellEditor extends AbstractCellEditor implements TableCellEditor {
    
    private PropertyDescriptorsHolder holder;
    private String property;
    private PropertyDescriptor descriptor;
    private PropertyEditorSupport editor;
    
    private boolean needsCustomEditor;
    
    private JTextField textEditor;
    private JCheckBox boolEditor;
    private JComboBox comboEditor;
    private EditorDialog customEditor;
    private JPanel editPanel;
    private JButton customEditorButton;
    private JComponent cellEditorCashed;
    private TagsModel tagsModel;
    
    public PropertyCellEditor(PropertyDescriptorsHolder holder) {
        
        this.holder = holder;
        tagsModel = new TagsModel();
        comboEditor = new JComboBox(tagsModel);
        boolEditor = new JCheckBox();
        boolEditor.addActionListener(new OnBoolChanged());
        textEditor = new JTextField();
        textEditor.setBorder(null);
        textEditor.getDocument().addDocumentListener(new OnTextChange());
        editPanel = new JPanel(new BorderLayout());
        customEditorButton = createCustomEditorButton();
        editPanel.add(customEditorButton, BorderLayout.EAST);
        cellEditorCashed = null;
        
        UIManager.addPropertyChangeListener(new OnUIChange());
    }
    
    private JButton createCustomEditorButton() {
        JButton customEditorButton = new JButton("...");
        customEditorButton.setMargin(new Insets(2, 2, 2, 2));
        customEditorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setNeedsCustomEditor(false);
                customEditor = new EditorDialog(null, getEditor());
                Object newVal = customEditor.showDialog(getEditor().getValue(), getDescriptor().getDisplayName());
                if (!customEditor.isCanceled()) {
                    getEditor().setValue(newVal);
                    stopCellEditing();
                }
            }
        });
        return customEditorButton;
    }
    
    public Object getCellEditorValue() {
        return property;
    }
    
    public boolean shouldSelectCell(EventObject anEvent) {
        if (isNeedsCustomEditor()) {
            customEditor = new EditorDialog(null, getEditor());
            Object newVal = customEditor.showDialog(getEditor().getValue(), getDescriptor().getDisplayName());
            if (customEditor.isCanceled()) {
                cancelCellEditing();
            } else {
                getEditor().setValue(newVal);
//                getEditor().firePropertyChange();
                stopCellEditing();
            }
        }
        return true;
    }
    
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        property = (String) value;
        setDescriptor(holder.getPropertydescriptor(property));
        setEditor(holder.getPropertyEditor(property));
        setNeedsCustomEditor(false);
        JComponent cellEditor = null;
        if (getEditor().getTags() != null) {
            tagsModel.fireDataChanged();
            cellEditor = comboEditor;
        } else if (PropertyDescriptorsHolder.isBooleanDescriptor(getDescriptor())) {
            boolEditor.setSelected((Boolean) getEditor().getValue());
            cellEditor = boolEditor;
        } else if (PropertyDescriptorsHolder.isStringDescriptor(getDescriptor())) {
            textEditor.setText((String) getEditor().getValue());
            cellEditor = textEditor;
        } else if (getEditor().getCustomEditor() != null) {
            setNeedsCustomEditor(true);
            return table.getCellRenderer(row, column).getTableCellRendererComponent(
                    table, value, isSelected, true, row, column);
        }
        
        if ((getEditor().getCustomEditor() != null) && (cellEditor != null)) {
            if (cellEditorCashed != null) {
                editPanel.remove(cellEditorCashed);
            }
            editPanel.add(cellEditor, BorderLayout.CENTER);
            cellEditorCashed = cellEditor;
            editPanel.revalidate();
            return editPanel;
        }
        
        return cellEditor;
    }
    
    public PropertyDescriptor getDescriptor() {
        return descriptor;
    }
    
    public void setDescriptor(PropertyDescriptor descriptor) {
        this.descriptor = descriptor;
    }
    
    public PropertyEditorSupport getEditor() {
        return editor;
    }
    
    public void setEditor(PropertyEditorSupport editor) {
        this.editor = editor;
    }
    
    private class OnBoolChanged implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (getEditor() == null) return;
            getEditor().setValue(boolEditor.isSelected());
        }
    }
    
    private class OnTextChange extends TextChangeListener {
        public void textChanged(DocumentEvent e) {
            if (getEditor() == null) return;
            getEditor().setValue(textEditor.getText());
        }
    }
    
    private class TagsModel extends AbstractListModel implements ComboBoxModel {
        
        public int getSize() {
            if ((getEditor() == null) || (getEditor().getTags() == null)) return 0;
            return getEditor().getTags().length;
        }
        
        public Object getElementAt(int index) {
            return getEditor().getTags()[index];
        }
        
        public void setSelectedItem(Object anItem) {
            getEditor().setAsText((String) anItem);
        }
        
        public Object getSelectedItem() {
            if (getEditor() == null) return null;
            return getEditor().getAsText();
        }
        
        public void fireDataChanged() {
            fireContentsChanged(this, 0, getSize() - 1);
        }
        
    }
    
    public boolean isNeedsCustomEditor() {
        return needsCustomEditor;
    }
    
    public void setNeedsCustomEditor(boolean needsCustomEditor) {
        this.needsCustomEditor = needsCustomEditor;
    }
    
    private class OnUIChange implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            textEditor.updateUI();
            boolEditor.updateUI();
            comboEditor.updateUI();
            SwingUtilities.updateComponentTreeUI(editPanel);
        }
    }
}

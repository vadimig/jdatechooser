/*
 * BorderEditorPane.java
 *
 * Created on 31 Июль 2006 г., 14:01
 *
 */

package datechooser.beans.editor.border;

import datechooser.beans.editor.border.types.*;
import datechooser.beans.editor.utils.TextOutput;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * Border editing panel.<br>
 * Панель редактирования границы.
 * @author Androsov Vadim
 * @since 1.0
 */
public class BorderEditorPane
        extends JPanel
        implements PropertyChangeListener {
    
//    private static final int DEFAULT_EDITOR = 0;
    private static final int NO_BORDER_EDITOR = 0;
    
    private static String EMPTY = "";
    
    private BorderPreviewPane preview;
    private JPanel control;
    private SimpleBorderEditor editor;
    private JList selType;
    private JLabel typeName;
    
    private AbstractBorderEditor[] typesEditors = null;
    private int current;
    
    public BorderEditorPane(SimpleBorderEditor editor) {
        
        setEditor(editor);
        
        typeName = new JLabel();
        JPanel top = new JPanel(new BorderLayout());
        JPanel content = new JPanel(new GridLayout(2, 1, 1, 10));
        setLayout(new BorderLayout());
        control = new JPanel(new BorderLayout());
        top.add(control, BorderLayout.CENTER);
        top.add(typeName, BorderLayout.SOUTH);
        preview = new BorderPreviewPane();
        preview.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createEtchedBorder()));

        JButton reset = new JButton(getEditorLocaleString("Reset"));
        JPanel resetPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        resetPane.add(reset);
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getCurrentEditor().reset();
                refreshPreview();
            }
        });
        
        initEditors();

        content.add(top);
        content.add(preview);
        add(content, BorderLayout.CENTER);
        add(new JScrollPane(selType), BorderLayout.EAST);
        add(resetPane, BorderLayout.NORTH);
        
        revalidate();
    }
    
    private void initEditors() {
        typesEditors = new AbstractBorderEditor[] {
//            new DefaultBorderEditor(),
            new NoBorderEditor(),
            new BevelBorderEditor(),
            new CompoundBorderEditor(),
            new EmptyBorderEditor(),
            new EtchedBorderEditor(),
            new LineBorderEditor(),
            new MatteBorderEditor(),
            new SoftBevelBorderEditor(),
            new TitledBorderEditor() };
        setCurrentEditor();
        if (getEditor().getValue() != null) {
            getCurrentEditor().setCurrentBorder((Border) getEditor().getValue());
        }
//        getDefaultEditor().setCurrentBorder((Border) getEditor().getValue());
        OnBorderChange onSelect = new OnBorderChange();
        for (AbstractBorderEditor visualEditor : typesEditors) {
            visualEditor.addPropertyChangeListener(onSelect);
        }
        
        selType = new JList(new EditorsListModel());
        selType.setCellRenderer(new TypesListRenderer());
        selType.setSelectedIndex(current);
        selType.setVisibleRowCount(typesEditors.length + 1);
        selType.setOpaque(false);
        selType.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        updateCaption();
        selType.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                refreshEditor();
            }
        });
        
        refreshPreview();
   }
    
    private void refreshEditor() {
        setCurrentEditor(selType.getSelectedIndex());
        updateCaption();
        updateProperty();
        refreshPreview();
    }
    
    private AbstractBorderEditor getDefaultEditor() {
        return typesEditors[NO_BORDER_EDITOR];
    }
    
    private void updateCaption() {
        typeName.setText(" " + getCurrentEditor().getCaption());
    }
    
    private void updateProperty() {
        getEditor().setValue(getSelectedBorder());
    }
    
    private void refreshPreview() {
        preview.setSelectedBorder((Border) getEditor().getValue());
        preview.repaint();
    }
    
    private void showCurrentEditor() {
        control.removeAll();
        control.add(getCurrentEditor(), BorderLayout.CENTER);
        control.revalidate();
        control.repaint();
    }
    
    private Border getSelectedBorder() {
        return getCurrentEditor().getSelectedBorder();
    }
    
    private AbstractBorderEditor getCurrentEditor() {
        return typesEditors[current];
    }
    
    private int getCurrentEditorIndex() {
        if (editor.getValue() == null) {
            return NO_BORDER_EDITOR;
        }
        for (int i = 1; i < typesEditors.length; i++) {
            if (typesEditors[i].getSelectedBorder() == null) {
                continue;
            }
            Class curr = typesEditors[i].getSelectedBorder().getClass();
            if (curr != null) {
                if (curr.equals(editor.getValue().getClass())) {
                    return i;
                }
            }
        }
        return NO_BORDER_EDITOR;
    }
    
    private void setCurrentEditor() {
        setCurrentEditor(getCurrentEditorIndex());
    }
    
    private void setCurrentEditor(int index) {
        if (typesEditors == null) return;
        if ((index < 0) || (index >= typesEditors.length)) {
            current = 0;
        } else {
            current = index;
        }
        if (selType != null) selType.setSelectedIndex(current);
        showCurrentEditor();
    }
    
    class EditorsListModel extends AbstractListModel {
        public int getSize() {
            return typesEditors.length;
        }
        
        public Object getElementAt(int index) {
            return typesEditors[index];
        }
        
    }
    
    public SimpleBorderEditor getEditor() {
        return editor;
    }
    
    public void setEditor(SimpleBorderEditor editor) {
        if (getEditor() != null) {
            getEditor().removePropertyChangeListener(this);
        }
        this.editor = editor;
        getEditor().addPropertyChangeListener(this);
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
//        if (editor.getValue() == null) return;
        setCurrentEditor(getCurrentEditorIndex());
        getCurrentEditor().setCurrentBorder((Border) editor.getValue());
        refreshPreview();
    }
    
    private AbstractBorderEditor getTypeEditor(int index) {
        if ((index < 0) || (index >= typesEditors.length)) {
            return getDefaultEditor();
        }
        return typesEditors[index];
    }
    
    private class BorderPreviewPane extends JPanel {
        private Border selectedBorder = null;
        
        public Border getSelectedBorder() {
            return selectedBorder;
        }
        
        public void setSelectedBorder(Border selectedBorder) {
            this.selectedBorder = selectedBorder;
        }
        
        
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getSelectedBorder() == null) return;
            Rectangle rec = getBounds();
            if (getSelectedBorder().getClass().equals(EmptyBorder.class)) {
                TextOutput.paintBoxed(g, rec, getEditorLocaleString("Invisible"));
            } else {
                getSelectedBorder().paintBorder(this, g,
                        rec.width / 4,
                        rec.height / 4,
                        rec.width / 2,
                        rec.height / 2);
            }
        }
    }
    
    private class OnBorderChange implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            if (!AbstractBorderEditor.BORDER_EVENT_NAME.equals(evt.getPropertyName())) return;
            updateProperty();
            refreshPreview();
        }
    }
    
    private class TypesListRenderer implements ListCellRenderer {
        public Component getListCellRendererComponent(final JList list, Object value,
                int index, final boolean isSelected, boolean cellHasFocus) {
            JLabel label = getTypeEditor(index).getPreviewLabel();
//            label.setBackground(isSelected ?
//                list.getSelectionBackground() : list.getBackground());
            label.setForeground(isSelected ?
                list.getSelectionForeground() : list.getForeground());
            JPanel content = new JPanel(new GridLayout(1, 1));
            content.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            content.setBackground(isSelected ?
                list.getSelectionBackground() : list.getBackground());
            content.add(label);
            
            return content;
        }
    }
}

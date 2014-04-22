/*
 * ValueCellRenderer.java
 *
 * Created on 6 Ќо€брь 2006 г., 19:15
 *
 */

package datechooser.beans.customizer.render;

import datechooser.beans.customizer.*;
import java.awt.*;
import java.beans.*;
import javax.swing.*;

/**
 * Renderer for cells with property value.<br>
 * –исовальщик дл€ €чеек со значени€ми свойств.
 * @author Androsov Vadim
 * @since 1.0
 */
public class ValueCellRenderer extends CellRenderer {
    
    private RenderPane paneRenderer;
    private JLabel labelRenderer;
    private JCheckBox checkRenderer;
    private JComboBox comboRenderer;
    private JButton editorButton;
    private JPanel editorPane;
    private Component rendererCash = null;
    
    public ValueCellRenderer(PropertyDescriptorsHolder holder) {
        super(holder);
        paneRenderer = new RenderPane();
        labelRenderer = new JLabel();
        labelRenderer.setOpaque(true);
        labelRenderer.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        checkRenderer = new JCheckBox();
        comboRenderer = new JComboBox();
        comboRenderer.setBorder(null);
        editorButton = new JButton("...");
        editorButton.setMargin(new Insets(2, 2, 2, 2));
        editorPane = new JPanel(new BorderLayout());
        editorPane.add(editorButton, BorderLayout.EAST);
        
        UIManager.addPropertyChangeListener(new OnUIChange());
    }
    
    protected Component getRenderer(PropertyDescriptor propertyDescriptor, PropertyEditorSupport propertyEditorSupport, JTable table, boolean isSelected, boolean hasFocus) {
        Component renderer = null;
        if (PropertyDescriptorsHolder.isBooleanDescriptor(propertyDescriptor)) {
            checkRenderer.setSelected((Boolean)propertyEditorSupport.getValue());
            renderer = checkRenderer;
        } else if (propertyEditorSupport.getTags() != null) {
            comboRenderer.removeAllItems();
            comboRenderer.addItem(propertyEditorSupport.getAsText());
            renderer = comboRenderer;
        } else if (propertyEditorSupport.isPaintable()) {
            paneRenderer.setEditor(propertyEditorSupport);
            renderer = paneRenderer;
        } else {
            String text = propertyEditorSupport.getAsText();
            labelRenderer.setFont(table.getFont());
            labelRenderer.setText(text);
            renderer = labelRenderer;
        }
        renderer.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        renderer.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
        
        if (propertyEditorSupport.supportsCustomEditor()) {
            if (rendererCash != null) {
                editorPane.remove(rendererCash);
            }
            editorPane.add(renderer, BorderLayout.CENTER);
            rendererCash = renderer;
            editorPane.revalidate();
            return editorPane;
        }
        
        return renderer;
    }
    
    private class RenderPane extends JPanel {
        private PropertyEditor editor = null;
        
        public void setEditor(PropertyEditor editor) {
            this.editor = editor;
            setOpaque(true);
        }
        
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (editor == null) return;
            Rectangle bounds = getBounds();
            bounds.setLocation(5, 0);
            editor.paintValue(g, bounds);
        }
    }
    
    private class OnUIChange implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            SwingUtilities.updateComponentTreeUI(editorPane);
            comboRenderer.updateUI();
            labelRenderer.updateUI();
            checkRenderer.updateUI();
        }
    }
}

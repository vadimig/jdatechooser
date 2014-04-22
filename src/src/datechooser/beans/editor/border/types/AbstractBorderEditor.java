/*
 * AbstractBorderEdit.java
 *
 * Created on 31 Июль 2006 г., 18:00
 *
 */

package datechooser.beans.editor.border.types;

import datechooser.events.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Abstract editor panel for some border type. <br>
 * Заготовка для панели редактирования границы конкретного типа.
 * @author Androsov Vadim
 * @since 1.0
 */
public abstract class AbstractBorderEditor  extends JPanel {
    
    protected Border value;
    private String caption;
    private boolean changed;
    
    public static final String BORDER_EVENT_NAME = "border";
    
    public AbstractBorderEditor() {
        setCaption("");
        setChanged(false);
    }
    
    public void fireChange() {
        setChanged(true);
        firePropertyChange(BORDER_EVENT_NAME, null, null);
    }
    
    
    protected Border getCurrentBorder() {
        return value;
    }
    
    public void setCurrentBorder(Border border) {
        setValue(border);
        refreshInterface();
    }
    
    public Border getSelectedBorder() {
        if (isChanged()) {
            prepareSelection();
            setChanged(false);
        }
        return getCurrentBorder();
    }
    
    public boolean isChanged() {
        return changed;
    }
    
    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    
    public String getCaption() {
        return caption;
    }
    
    protected void setCaption(String caption) {
        this.caption = caption;
    }
    
    public String toString() {
        return getCaption();
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }
    
    protected JPanel getCenteredPane(Component component) {
        JPanel pane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        if (component != null) pane.add(component);
        return pane;
    }
    
    public void initialize() {
        if (value == null) {
            value = getDefaultValue();
        }
    }
    
    public void reset() {
        setValue(getDefaultValue());
        refreshInterface();
        fireChange();
    }
    
    public void setValue(Border value) {
        this.value = value;
    }
    
    public JLabel getPreviewLabel() {
        JLabel label = new JLabel();
        label.setBorder(value);
        label.setText(getCaption());
        label.setOpaque(false);
        return label;
    }
    protected abstract Border getDefaultValue();
    protected abstract void prepareSelection();
    protected abstract void refreshInterface();
    
}

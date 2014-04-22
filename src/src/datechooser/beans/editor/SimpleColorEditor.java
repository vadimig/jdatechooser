/*
 * SimpleColorEditor.java
 *
 * Created on 28 Ноябрь 2006 г., 20:06
 *
 */

package datechooser.beans.editor;

import datechooser.beans.editor.descriptor.DescriptionManager;
import java.awt.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Color editor.<br>
 * Редактор цвета.
 * @author Androsov Vadim
 * @since 1.0
 */
public class SimpleColorEditor extends VisualEditorCashed 
        implements ColorSelectionModel {
    
    private Set<ChangeListener> changeListeners;
    
    protected JComponent createEditor() {
        changeListeners = new HashSet<ChangeListener>();
        JColorChooser editorPane = new JColorChooser(this);
        return editorPane;
    }

    public Color getSelectedColor() {
        return (Color) getValue();
    }

    public void setSelectedColor(Color color) {
        setValue(color);
        fireStateChange();
    }

    public void addChangeListener(ChangeListener listener) {
        changeListeners.add(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        changeListeners.remove(listener);
    }

    public void fireStateChange() {
        ChangeEvent e = new ChangeEvent(this);
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(e);
        }
    }

    public String getJavaInitializationString() {
       return DescriptionManager.describeJava(getValue(), Color.class);
    }

    public boolean isPaintable() {
        return true;
    }

    public void paintValue(Graphics gfx, Rectangle box) {
        Color color = (Color) getValue();
        gfx.setColor(color);
        gfx.fillRect(2, 2, box.height - 4, box.height - 4);
        gfx.setColor(Color.BLACK);
        gfx.drawRect(2, 2, box.height - 4, box.height - 4);
        gfx.drawString("[" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + "]", 
                box.height + 2 , box.height - 4);
    }
   
}

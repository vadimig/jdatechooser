/*
 * DimensionEditor.java
 *
 * Created on 14 ќкт€брь 2006 г., 18:24
 *
 */

package datechooser.beans.editor.dimension;

import datechooser.beans.editor.VisualEditorCashed;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JComponent;

/**
 * Dimension editor.<br>
 * –едактор размеров.
 * @author Androsov Vadim
 * @since 1.0
 */
public class SimpleDimensionEditor extends VisualEditorCashed {
    
   public String getAsText() {
        return getDimension().width + " x " + getDimension().height;
    }

    public String getJavaInitializationString() {
        return "new " + Dimension.class.getName() + "(" + 
                getDimension().width + ", " + getDimension().height + ")";
    }
    
    public Dimension getDimension() {
        return (Dimension) getValue();
    }
    
    protected JComponent createEditor() {
        return new SimpleDimensionEditorPane(this);
    }
}

/*
 * AppearEditor.java
 *
 * Created on 6 Август 2006 г., 22:35
 *
 */

package datechooser.beans.editor.appear;

import datechooser.beans.editor.VisualEditor;
import datechooser.beans.editor.VisualEditorCashed;
import datechooser.beans.editor.descriptor.DescriptionManager;
import datechooser.view.appearance.*;
import java.awt.*;
import java.beans.*;
import java.io.Serializable;
import java.util.Iterator;
import javax.swing.JComponent;

/**
 * Calendar appearance editor.
 * Lets change or create user appearances (skins).<br>
 * Редактор представлений.
 * Позволяет изменять и добавлять темы отображения календаря.
 * @author Androsov Vadim
 * @see datechooser.view.appearance.AppearancesList
 * @see datechooser.view.appearance.ViewAppearance
 * @since 1.0
 */
public class AppearEditor extends VisualEditorCashed {
    
    public AppearancesList getAppearancesList() {
        return (AppearancesList) getValue();
    }

    public void setValue(Object value) {
        super.setValue(((AppearancesList)value).clone());
    }
    
    public void setInnerValue(Object value) {
        super.setValue(value);
    }
    
    public String getAsText() {
        return getAppearancesList().getCurrent().getName();
    }
    
    public void setAsText(String text) throws IllegalArgumentException {
        if (!getAppearancesList().setCurrent(text)) {
            throw new IllegalArgumentException();
        }
        setValue(getAppearancesList().notDeepClone());
    }
    
    public String[] getTags() {
       return getAppearancesList().getRegisteredNames();
    }
    
    public String getJavaInitializationString() {
        return DescriptionManager.describeJava(getValue(), AppearancesList.class);
    }
    
    protected JComponent createEditor() {
        return new AppearEditorPane(this);
    }
}

/*
 * ModelBehaviorEditor.java
 *
 * Created on 6 Август 2006 г., 14:30
 *
 */

package datechooser.beans.editor;

import datechooser.beans.editor.descriptor.DescriptionManager;
import datechooser.model.multiple.MultyModelBehavior;
import java.beans.PropertyEditorSupport;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;
import java.text.DateFormat;

/**
 * Editor for date to string conversion format.<br>
 * Редактор формата строкового отображения дат.
 * @author Androsov Vadim
 * @since 1.0
 */
public class DateFormatEditor extends PropertyEditorSupport {
    
    private String[] tagsText = {
        getEditorLocaleString("Full"), 
        getEditorLocaleString("Long"), 
        getEditorLocaleString("Medium"), 
        getEditorLocaleString("Short")};
    
    private int[] tags = {DateFormat.FULL, DateFormat.LONG,
        DateFormat.MEDIUM, DateFormat.SHORT};
    
    private int getValueIndex() {
        if (getValue() == null) return -1;
        int format = (Integer) getValue();
        switch(format) {
            case DateFormat.FULL:
                return 0;
            case DateFormat.LONG:
                return 1;
            case DateFormat.MEDIUM:
                return 2;
            case DateFormat.SHORT:
                return 3;
        }
        return -1;
    }
    
    public String[] getTags() {
        return tagsText;
    }
    
    public String getAsText() {
        return tagsText[getValueIndex()];
    }
    
    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        for (int i = 0; i < tagsText.length; i++) {
            if (tagsText[i].equals(text)) {
                setValue(tags[i]);
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getJavaInitializationString() {
        return ((Integer)getValue()).intValue() + "";
    }
}

/*
 * ModelBehaviorEditor.java
 *
 * Created on 6 Август 2006 г., 14:30
 *
 */

package datechooser.beans.editor;

import datechooser.beans.editor.descriptor.DescriptionManager;
import datechooser.view.WeekDaysStyle;
import java.beans.PropertyEditorSupport;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * Editor for weekdays output format.<br>
 * Редактор формата вывода названий дней недели.
 * @author Androsov Vadim
 * @since 1.0
 */
public class WeekDaysStyleEditor extends PropertyEditorSupport {
    
    private String[] tagsText = {
         getEditorLocaleString("WeekStyle_Full"), 
        getEditorLocaleString("WeekStyle_Normal"), 
        getEditorLocaleString("WeekStyle_Short")};
    
    private WeekDaysStyle[] tags = {WeekDaysStyle.FULL,
        WeekDaysStyle.NORMAL, WeekDaysStyle.SHORT};
    
    private int getValueIndex() {
        if (getValue() == null) return -1;
        WeekDaysStyle style = (WeekDaysStyle) getValue();
        switch(style) {
            case FULL:
                return 0;
            case NORMAL:
                return 1;
            case SHORT:
                return 2;
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
        return DescriptionManager.describeJava(getValue(), WeekDaysStyle.class);
    }
}

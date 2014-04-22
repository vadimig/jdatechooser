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

/**
 * Model behavior editor.<br>
 * Редактор поведения модели.
 * @author Androsov Vadim
 * @since 1.0
 */
public class ModelBehaviorEditor extends PropertyEditorSupport {
    private String[] tagsText = {
         getEditorLocaleString("Single"), 
        getEditorLocaleString("Period"), 
        getEditorLocaleString("Multy")};
    private MultyModelBehavior[] tags = {MultyModelBehavior.SELECT_SINGLE,
        MultyModelBehavior.SELECT_PERIOD, MultyModelBehavior.SELECT_ALL};
    
    private int getValueIndex() {
        if (getValue() == null) return -1;
        MultyModelBehavior behavior = (MultyModelBehavior) getValue();
        switch(behavior) {
            case SELECT_SINGLE:
                return 0;
            case SELECT_PERIOD:
                return 1;
            case SELECT_ALL:
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
        return DescriptionManager.describeJava(getValue(), MultyModelBehavior.class);
    }
}

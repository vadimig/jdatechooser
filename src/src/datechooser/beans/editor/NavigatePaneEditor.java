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
 * Editor lets select navigate panel.<br>
 * Редактор внешнего вида панели навигации.
 * @author Androsov Vadim
 * @since 1.0
 */
public class NavigatePaneEditor extends PropertyEditorSupport {
  private String[] tagsText = {
        getEditorLocaleString("Fields_navigator"), 
        getEditorLocaleString("Button_navigator")};
    
    public String[] getTags() {
        return tagsText;
    }
    
    public String getAsText() {
        return tagsText[getValueIndex()];
    }
    
    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        for (int i = 0; i < tagsText.length; i++) {
            if (tagsText[i].equals(text)) {
                setValue(new Integer(i));
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getJavaInitializationString() {
        return DescriptionManager.describeJava(getValue(), Integer.class);
    }

    private int getValueIndex() {
        return ((Integer) getValue()).intValue();
    }
}

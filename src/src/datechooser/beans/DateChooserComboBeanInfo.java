/*
 * DateChooserDialogBeanInfo.java
 *
 * Created on 13 ќкт€брь 2006 г., 13:55
 *
 */

package datechooser.beans;

import datechooser.beans.editor.border.SimpleBorderEditor;
import static datechooser.beans.locale.LocaleUtils.getCalendarLocaleString;
import datechooser.beans.editor.DateFormatEditor;
import datechooser.beans.editor.dimension.SimpleDimensionEditor;
import datechooser.beans.editor.font.SimpleFontEditor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * Info class for DateChooserCombo. <br>
 *  ласс с описание свойств раскрывающегос€ редатора дат.
 * @author Androsov Vadim
 * @see datechooser.beans.AbstractDateChooserBeanInfo
 * @since 1.0
 * @see datechooser.beans.DateChooserCombo
 */
public class DateChooserComboBeanInfo extends AbstractDateChooserBeanInfo {
    protected String getDisplayName() {
        return getCalendarLocaleString("Date_chooser_combo");
    }
    
    protected String getPicturePrefix() {
        return "combo";
    }
    
    protected Class getBeanClass() {
        return DateChooserCombo.class;
    }
    
    protected Class getCustomizerClass() {
        return DateChooserComboCustomizer.class;
    }
    
    /**
     * Unique descriptors for DateChooserCombo.<br>
     * ”никальные дескрипторы свойств дл€ DateChooserCombo.
     * @since 1.0
     * @return Unique descriptors for DateChooserCombo.<br>
     * ”никальные дескрипторы свойств дл€ DateChooserCombo.
     */
    public ArrayList<PropertyDescriptor> getAdditionalDescriptors() throws IntrospectionException {
        
        ArrayList<PropertyDescriptor> descriptors  = new ArrayList<PropertyDescriptor>();
        
        PropertyDescriptor border = new PropertyDescriptor( DateChooserCombo.PROPERTY_BORDER,
                getBeanClass(), "getBorder", "setBorder" );
        border.setPropertyEditorClass(SimpleBorderEditor.class);
        border.setDisplayName(getCalendarLocaleString("Border"));
        border.setShortDescription(getCalendarLocaleString("Border_descript"));
        descriptors.add(border);
        
        PropertyDescriptor fieldFont = new PropertyDescriptor( DateChooserCombo.PROPERTY_FIELD_FONT,
                getBeanClass(), "getFieldFont", "setFieldFont");
        fieldFont.setPropertyEditorClass(SimpleFontEditor.class);
        fieldFont.setDisplayName(getCalendarLocaleString("Field_font"));
        fieldFont.setShortDescription(getCalendarLocaleString("Field_font_descript"));
        descriptors.add(fieldFont);
        
        PropertyDescriptor dateFormat = new PropertyDescriptor( DateChooserCombo.PROPERTY_DATE_FORMAT,
                getBeanClass(), "getFormat", "setFormat");
        dateFormat.setPropertyEditorClass(DateFormatEditor.class);
        dateFormat.setDisplayName(getCalendarLocaleString("DateFormat"));
        dateFormat.setShortDescription(getCalendarLocaleString("DateFormat_descript"));
        descriptors.add(dateFormat);
        
        return descriptors;
    }
}

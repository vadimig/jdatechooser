/*
 * DateChooserPanelBeanInfo.java
 *
 * Created on 13 ќкт€брь 2006 г., 13:13
 *
 */

package datechooser.beans;

import datechooser.beans.DateChooserPanelCustomizer;
import static datechooser.beans.locale.LocaleUtils.getCalendarLocaleString;
import datechooser.beans.editor.border.SimpleBorderEditor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;

/**
 * Info class for DateChooserPanel. <br>
 *  ласс с описание свойств панели выбора даты.
 * @author Androsov Vadim
 * @see datechooser.beans.AbstractDateChooserBeanInfo
 * @see datechooser.beans.DateChooserPanel
 * @since 1.0
 */
public class DateChooserPanelBeanInfo extends AbstractDateChooserBeanInfo {
    
    protected String getDisplayName() {
        return getCalendarLocaleString("Date_chooser_panel");
    }
    
    protected String getPicturePrefix() {
        return "panel";
    }
    
    protected Class getBeanClass() {
        return DateChooserPanel.class;
    }
    
    protected Class getCustomizerClass() {
        return DateChooserPanelCustomizer.class;
    }
    
    /**
     * Unique descriptors for DateChooserPanel.<br>
     * ”никальные дескрипторы свойств дл€ DateChooserPanel.
     * @return Unique descriptors for DateChooserPanel.<br>
     * ”никальные дескрипторы свойств дл€ DateChooserPanel.
     * @since 1.0
     */
    public ArrayList<PropertyDescriptor> getAdditionalDescriptors() throws IntrospectionException {
        
        ArrayList<PropertyDescriptor> descriptors  = new ArrayList<PropertyDescriptor>();
        
        PropertyDescriptor border = new PropertyDescriptor( DateChooserPanel.PROPERTY_BORDER,
                getBeanClass(), "getBorder", "setBorder" );
        border.setPropertyEditorClass(SimpleBorderEditor.class);
        border.setDisplayName(getCalendarLocaleString("Border"));
        border.setShortDescription(getCalendarLocaleString("Border_descript"));
        descriptors.add(border);
        
        return descriptors;
    }
    
}

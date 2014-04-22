/*
 *
 * Created on 13 ќкт€брь 2006 г., 13:55
 *
 */

package datechooser.beans;

import datechooser.beans.editor.dimension.SimpleDimensionEditor;
import static datechooser.beans.locale.LocaleUtils.getCalendarLocaleString;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * Info class for DateChooserDialog. <br>
 *  ласс с описание свойств диалогового окна.
 * @author Androsov Vadim
 * @see datechooser.beans.AbstractDateChooserBeanInfo
 * @see datechooser.beans.DateChooserDialog
 * @since 1.0
 */
public class DateChooserDialogBeanInfo extends AbstractDateChooserBeanInfo {
    protected String getDisplayName() {
        return getCalendarLocaleString("Date_chooser_dialog");
    }
    
    protected String getPicturePrefix() {
        return "dialog";
    }
    
    protected Class getBeanClass() {
        return DateChooserDialog.class;
    }
    
    protected Class getCustomizerClass() {
        return DateChooserDialogCustomizer.class;
    }

    /**
     * Unique descriptors for DateChooserDialog.<br>
     * ”никальные дескрипторы свойств дл€ DateChooserDialog.
     * @return Unique descriptors for DateChooserDialog.<br>
     * ”никальные дескрипторы свойств дл€ DateChooserDialog.
     * @since 1.0
     */
    public ArrayList<PropertyDescriptor> getAdditionalDescriptors() throws IntrospectionException {
        
        ArrayList<PropertyDescriptor> descriptors  = new ArrayList<PropertyDescriptor>();
        
        PropertyDescriptor modal = new PropertyDescriptor( DateChooserDialog.PROPERTY_MODAL,
                getBeanClass(), "isModal", "setModal" );
        modal.setDisplayName(getCalendarLocaleString("Modal"));
        modal.setShortDescription(getCalendarLocaleString("Modal_descript"));
        descriptors.add(modal);

        PropertyDescriptor caption = new PropertyDescriptor( DateChooserDialog.PROPERTY_CAPTION,
                getBeanClass(), "getCaption", "setCaption" );
        caption.setDisplayName(getCalendarLocaleString("Caption"));
        caption.setShortDescription(getCalendarLocaleString("Caption_descript"));
        descriptors.add(caption);

        return descriptors;
    }
}

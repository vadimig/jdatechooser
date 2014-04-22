/*
 * DateChooserDialogCustomizer.java
 *
 * Created on 8 Ноябрь 2006 г., 7:53
 *
 */

package datechooser.beans;

import datechooser.beans.customizer.DateChooserCustomizer;
import java.beans.IntrospectionException;

/**
 * Customizer for DateChooserDialog.<br>
 * Настройщик раскрывающегося редатороа дат.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.beans.customizer.DateChooserCustomizer
 */
public class DateChooserDialogCustomizer extends DateChooserCustomizer {
    
    public DateChooserDialogCustomizer() throws IntrospectionException {
        super(new DateChooserDialogBeanInfo());
    }
    
}

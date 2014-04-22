/*
 * DateChooserPanelCustomizer.java
 *
 * Created on 8 Ноябрь 2006 г., 7:44
 *
 */

package datechooser.beans;

import datechooser.beans.customizer.DateChooserCustomizer;
import java.beans.*;

/**
 * Customizer for DateChooserPanel.<br>
 * Настройщик раскрывающегося редатороа дат.
 * @author Androsov Vadim
 * @since 1.0
 */
public class DateChooserPanelCustomizer extends DateChooserCustomizer {
    
    public DateChooserPanelCustomizer() throws IntrospectionException {
        super(new DateChooserPanelBeanInfo());
    }
    
}

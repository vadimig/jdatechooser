package datechooser.autorun;

import datechooser.beans.DateChooserPanel;
import datechooser.beans.DateChooserPanelCustomizer;

import java.awt.*;
import java.beans.*;
import javax.swing.*;

/**
 * DateChooser panel and its customizer.
 * @author Androsov Vadim
 * @see datechooser.beans.DateChooserPanel
 * @see datechooser.beans.DateChooserPanelCustomizer
 * @since 1.0
 */
public class ConfigPanel extends ConfigBean {
    
    /**
     * Sets component and customizer classes using parent (super) constructor.
     * @throws java.beans.IntrospectionException If component and customizer are incompatible.
     * @since 1.0
     */
    public ConfigPanel() throws IntrospectionException {
        super(new DateChooserPanel(), new DateChooserPanelCustomizer());
        initializeInterface();
    }

     private void initializeInterface() {
        setLayout(new GridLayout(1, 2, 2, 2));
        add((JComponent) getBean());
        add(getCustomizer());
    }

    /**
     * Returns properties file extension for panel component.
     * @return <B>.dchp</B>
     * @since 1.0
     */
    public String getFileExt() {
        return "dchp";
    }
    
}

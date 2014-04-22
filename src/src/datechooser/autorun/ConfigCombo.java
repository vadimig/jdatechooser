package datechooser.autorun;

import datechooser.beans.DateChooserCombo;
import datechooser.beans.DateChooserComboCustomizer;

import java.awt.*;
import java.beans.*;
import javax.swing.*;

/**
 * DateChooser ñombobox and its customizer.
 * @author Androsov Vadim
 * @see datechooser.beans.DateChooserCombo
 * @see datechooser.beans.DateChooserComboCustomizer
 * @since 1.0
 */
public class ConfigCombo extends ConfigBean {
    
    /**
     * Sets component and customizer classes using parent (super) constructor.
     * @throws java.beans.IntrospectionException If component and customizer are incompatible.
     * @since 1.0
     */
    public ConfigCombo() throws IntrospectionException {
        super(new DateChooserCombo(), new DateChooserComboCustomizer());
        initializeInterface();
    }

    private void initializeInterface() {
        setLayout(new GridLayout(1, 2, 2, 2));
        JPanel beanCell = new JPanel(new FlowLayout(FlowLayout.CENTER));
        beanCell.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(), 
                BorderFactory.createEmptyBorder(20, 2, 2, 2)));
        beanCell.add((JComponent) getBean());
        add(beanCell);
        add(getCustomizer());
    }

    /**
     * Returns properties file extension for combo component.
     * @return <B>.dchc</B>
     * @since 1.0
     */
    public String getFileExt() {
        return "dchc";
    }
    
}

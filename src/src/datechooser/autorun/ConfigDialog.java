package datechooser.autorun;

import datechooser.beans.DateChooserDialog;
import datechooser.beans.DateChooserDialogCustomizer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import javax.swing.*;

/**
 * DateChooser dialog and its customizer.<br>
 * @author Androsov Vadim
 * @see datechooser.beans.DateChooserDialog
 * @see datechooser.beans.DateChooserDialogCustomizer
 * @since 1.0
 */
public class ConfigDialog extends ConfigBean {
    
    /**
     * Sets component and customizer classes using parent (super) constructor.
     * @throws java.beans.IntrospectionException If component and customizer are incompatible.
     * @since 1.0
     */
    public ConfigDialog() throws IntrospectionException {
        super(new DateChooserDialog(), new DateChooserDialogCustomizer());
        initializeInterface();
    }

    private void initializeInterface() {
        setLayout(new GridLayout(1, 2, 2, 2));
        JPanel beanCell = new JPanel(new FlowLayout(FlowLayout.CENTER));
        beanCell.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(), 
                BorderFactory.createEmptyBorder(20, 2, 2, 2)));
        JButton showDialog = new JButton(getBeanDisplayName());
        showDialog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ((DateChooserDialog)getBean()).showDialog(null);
            }
        });
        beanCell.add(showDialog);
        add(beanCell);
        add(getCustomizer());
    }

    /**
     * Returns properties file extension for dialog component.
     * @return <B>.dchd</B>
     * @since 1.0
     */
    public String getFileExt() {
        return "dchd";
    }
    
}

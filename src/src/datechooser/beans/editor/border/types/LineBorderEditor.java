/*
 * LineBorderEditor.java
 *
 * Created on 31 »юль 2006 г., 18:05
 *
 */

package datechooser.beans.editor.border.types;

import datechooser.beans.editor.utils.*;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * Editor for line borders.<br>  
 * –едактор границ типа Line
 * @author Androsov Vadim
 * @since 1.0
 */
public class LineBorderEditor extends AbstractBorderEditor implements PropertyChangeListener {

    
    private static int MIN_THICK = 1;
    private static int MAX_THICK = 20;
    
    private ColorHolder color;
    private int thickness;
    private boolean roundedCorners;
    
    private JCheckBox rounded;
    private SpinnerNumberModel thickModel;
    
    public LineBorderEditor() {

        initialize();
        
        color = new ColorHolder();
        setCaption(getEditorLocaleString("Line"));
        assignValueToParameters();
        
        rounded = new JCheckBox(getEditorLocaleString("Rounded_corners"));
        rounded.setSelected(roundedCorners);
        rounded.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                roundedCorners = rounded.isSelected();
                fireChange();
            }
        });
        
        thickModel = new SpinnerNumberModel(thickness, MIN_THICK, MAX_THICK, 1);
        SpinPane thickPane = new SpinPane(thickModel, getEditorLocaleString("Thickness"));
        thickModel.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                thickness = thickModel.getNumber().intValue();
                fireChange();
            }
        });
        
        JButton bColor = new JButton(getEditorLocaleString("Color"));
        ColorChooseAction colorChooseAction = new ColorChooseAction(color, 
                getEditorLocaleString("Color"), this);
        colorChooseAction.addPropertyChangeListener(this);
        bColor.addActionListener(colorChooseAction);
        
       setLayout(new BorderLayout());
        JPanel pane = new JPanel(new GridLayout(1, 2));
        pane.add(getCenteredPane(rounded));
        pane.add(getCenteredPane(thickPane));
        JPanel paneTotal = new JPanel(new GridLayout(2, 1));
        paneTotal.add(pane);
        paneTotal.add(getCenteredPane(bColor));
        add(paneTotal, BorderLayout.NORTH);
        
        refreshInterface();
    }

    protected void prepareSelection() {
        setValue(new LineBorder(color.getColor(), thickness, roundedCorners));
    }
    
    private void assignValueToParameters() {
        color.setColor(getValue().getLineColor());
        thickness = getValue().getThickness();
        roundedCorners = getValue().getRoundedCorners();
    }
    
    protected LineBorder getValue() {
        return (LineBorder) value;
    }

    public void refreshInterface() {
        assignValueToParameters();
        rounded.setSelected(roundedCorners);
        thickModel.setValue(new Integer(thickness));
    }

    protected Border getDefaultValue() {
        return new LineBorder(Color.BLACK);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (!ColorChooseAction.COLOR_CHOOSE_EVENT_NAME.equals(evt.getPropertyName())) return;
        fireChange();
    }
}

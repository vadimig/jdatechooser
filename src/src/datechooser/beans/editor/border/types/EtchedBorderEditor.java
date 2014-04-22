/*
 * EtchedBorderEditor.java
 *
 * Created on 31 »юль 2006 г., 18:05
 *
 */

package datechooser.beans.editor.border.types;

import datechooser.beans.editor.utils.*;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Editor for etched borders.<br>  
 * –едактор границ типа Etched
 * @author Androsov Vadim
 * @since 1.0
 */
public class EtchedBorderEditor extends AbstractBorderEditor implements PropertyChangeListener {
    
    private int etchType;
    private ColorHolder highlight;
    private ColorHolder shadow;
    
    private RiseLowPanel selType;
    
    
    public EtchedBorderEditor() {

        initialize();
        
        highlight = new ColorHolder();
        shadow = new ColorHolder();
        
        setCaption(getEditorLocaleString("Etched"));
        assignValueToParameters();
        
        selType = new RiseLowPanel(etchType, 
                EtchedBorder.RAISED, EtchedBorder.LOWERED);
        selType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                etchType = selType.getType();
                fireChange();
            }
        });
        
        JButton bHigh = new JButton(getEditorLocaleString("Highlight"));
        ColorChooseAction colorChooseAction = new ColorChooseAction(highlight, 
                getEditorLocaleString("Highlight"), this);
        colorChooseAction.addPropertyChangeListener(this);
        bHigh.addActionListener(colorChooseAction);
        
        JButton bShadow = new JButton(getEditorLocaleString("Shadow"));
        colorChooseAction = new ColorChooseAction(shadow, 
                getEditorLocaleString("Shadow"), this);
        colorChooseAction.addPropertyChangeListener(this);
        bShadow.addActionListener(colorChooseAction);
        
        setLayout(new BorderLayout());
        JPanel totalPane = new JPanel(new GridLayout(2, 1));
        totalPane.add(selType);
        JPanel buttonPane = new JPanel(new GridLayout(1, 2));
        buttonPane.add(getCenteredPane(bHigh));
        buttonPane.add(getCenteredPane(bShadow));
        totalPane.add(buttonPane);
        add(totalPane, BorderLayout.NORTH);
        
        refreshInterface();
    }
    
    protected void prepareSelection() {
        setValue(new EtchedBorder(etchType, highlight.getColor(), shadow.getColor()));
    }
    
    private void assignValueToParameters() {
        etchType = getValue().getEtchType();
        highlight.setColor(getValue().getHighlightColor());
        shadow.setColor(getValue().getShadowColor());
    }
    
    protected EtchedBorder getValue() {
        return (EtchedBorder) value;
    }

    public void refreshInterface() {
        assignValueToParameters();
    }

    protected Border getDefaultValue() {
        return new EtchedBorder();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (!ColorChooseAction.COLOR_CHOOSE_EVENT_NAME.equals(evt.getPropertyName())) return;
        fireChange();
    }
    
}

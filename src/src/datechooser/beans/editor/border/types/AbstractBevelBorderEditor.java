/*
 * BevelBorderEditor.java
 *
 * Created on 31 »юль 2006 г., 18:04
 *
 */

package datechooser.beans.editor.border.types;

import datechooser.beans.editor.utils.ColorChooseAction;
import datechooser.beans.editor.utils.ColorHolder;
import datechooser.beans.editor.utils.RiseLowPanel;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Editor for bevel borders.<br> 
 * ќбобщенный редактор дл€ bevel границ.
 * @author Androsov Vadim
 * @since 1.0
 */
public abstract class AbstractBevelBorderEditor
        extends AbstractBorderEditor implements PropertyChangeListener {
    
    protected int bevelType;
    protected ColorHolder highlightOuter;
    protected ColorHolder highlightInner;
    protected ColorHolder shadowOuter;
    protected ColorHolder shadowInner;
    
    private RiseLowPanel selType;
    private JButton bHighOut;
    private JButton bHighIn;
    private JButton bShadowOut;
    private JButton bShadowIn;
    
    protected abstract Border getBorderByParams();
    
    public AbstractBevelBorderEditor() {
        
        highlightInner = new ColorHolder();
        highlightOuter = new ColorHolder();
        shadowInner = new ColorHolder();
        shadowOuter = new ColorHolder();
        
        initialize();
        
        selType = new RiseLowPanel(bevelType,
                BevelBorder.RAISED, BevelBorder.LOWERED);
        selType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bevelType = selType.getType();
                fireChange();
            }
        });
        
        bHighIn = new JButton(getEditorLocaleString("Highlight_innner"));
        ColorChooseAction colorChooseAction = new ColorChooseAction(highlightInner,
                getEditorLocaleString("Highlight_innner"), this);
        colorChooseAction.addPropertyChangeListener(this);
        bHighIn.addActionListener(colorChooseAction);
        
        bHighOut = new JButton(getEditorLocaleString("Highlight_outher"));
        colorChooseAction = new ColorChooseAction(highlightOuter,
                getEditorLocaleString("Highlight_outher"), this);
        colorChooseAction.addPropertyChangeListener(this);
        bHighOut.addActionListener(colorChooseAction);
        
        bShadowIn = new JButton(getEditorLocaleString("Shadow_innner"));
        colorChooseAction = new ColorChooseAction(shadowInner,
                getEditorLocaleString("Shadow_innner"), this);
        bShadowIn.addActionListener(colorChooseAction);
        
        bShadowOut = new JButton(getEditorLocaleString("Shadow_outher"));
        colorChooseAction = new ColorChooseAction(shadowOuter,
                getEditorLocaleString("Shadow_outher"), this);
        bShadowOut.addActionListener(colorChooseAction);
        
        setLayout(new BorderLayout());
        JPanel confPane = new JPanel(new BorderLayout());
        confPane.add(selType, BorderLayout.NORTH);
        JPanel buttonPane = new JPanel(new GridLayout(2, 2));
        buttonPane.add(getCenteredPane(bHighIn));
        buttonPane.add(getCenteredPane(bHighOut));
        buttonPane.add(getCenteredPane(bShadowIn));
        buttonPane.add(getCenteredPane(bShadowOut));
        confPane.add(buttonPane, BorderLayout.SOUTH);
        add(confPane, BorderLayout.NORTH);
        
        refreshInterface();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (!ColorChooseAction.COLOR_CHOOSE_EVENT_NAME.equals(evt.getPropertyName())) return;
        firePropertyChange(BORDER_EVENT_NAME, null, null);
    }
    
    public void prepareSelection() {
        setValue(getBorderByParams());
    }
    
    private void assignValueToParameters() {
        bevelType = getValue().getBevelType();
        highlightOuter.setColor(getValue().getHighlightOuterColor());
        highlightInner.setColor(getValue().getHighlightInnerColor());
        shadowOuter.setColor(getValue().getShadowOuterColor());
        shadowInner.setColor(getValue().getShadowInnerColor());
    }
    
    protected BevelBorder getValue() {
        return (BevelBorder) value;
    }
    
    public void refreshInterface() {
        assignValueToParameters();
        selType.setType(bevelType);
    }
}

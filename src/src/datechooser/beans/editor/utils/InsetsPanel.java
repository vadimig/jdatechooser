/*
 * InsetsPanel.java
 *
 * Created on 31 Июль 2006 г., 22:11
 *
 */

package datechooser.beans.editor.utils;

import java.awt.*;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.event.*;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * Panel for insets editing.<br>
 * Панель для редактирования вкладок (insets).
 * @author Androsov Vadim
 * @since 1.0
 */
public class InsetsPanel extends JPanel {
    
    private static int MIN_VALUE = 1;
    private static int MAX_VALUE = 50;
    
    public static final String INSETS_EVENT_NAME = "insets";
    
    private Insets insets;
    private SpinnerNumberModel left;
    private SpinnerNumberModel top;
    private SpinnerNumberModel right;
    private SpinnerNumberModel bottom;
    
    private boolean autoEditing;
    
    public InsetsPanel(Insets insets) {
        setAutoEditing(false);
        this.insets = insets;
        OnChange onChange = new OnChange();
        left = new SpinnerNumberModel(getInsets().left, MIN_VALUE, MAX_VALUE, 1);
        left.addChangeListener(onChange);
        top = new SpinnerNumberModel(getInsets().top, MIN_VALUE, MAX_VALUE, 1);
        top.addChangeListener(onChange);
        right = new SpinnerNumberModel(getInsets().right, MIN_VALUE, MAX_VALUE, 1);
        right.addChangeListener(onChange);
        bottom = new SpinnerNumberModel(getInsets().bottom, MIN_VALUE, MAX_VALUE, 1);
        bottom.addChangeListener(onChange);
        
        setLayout(new GridLayout(3, 3));
//        foo   top     foo
//        left  foo     right
//        foo   bottom  foo
        add(getFoo());
        add(new SpinPane(top, getEditorLocaleString("top")));
        add(getFoo());
        
        add(new SpinPane(left, getEditorLocaleString("left")));
        add(getFoo());
        add(new SpinPane(right, getEditorLocaleString("right")));
        
        add(getFoo());
        add(new SpinPane(bottom, getEditorLocaleString("bottom")));
        add(getFoo());
    }
    
    private Component getFoo() {
        return new JPanel();
    }
    
    public Insets getInsets() {
        return insets;
    }
        
    class OnChange implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            if (isAutoEditing()) return;
            insets.set(top.getNumber().intValue(), left.getNumber().intValue(),
                    bottom.getNumber().intValue(), right.getNumber().intValue());
            firePropertyChange(INSETS_EVENT_NAME, null, null);
        }
    }
    
    public void setInsets(Insets insets) {
        this.insets = insets;
        refresh();
    }
    
    public void refresh() {
        setAutoEditing(true);
        left.setValue(new Integer(insets.left));
        right.setValue(new Integer(insets.right));
        top.setValue(new Integer(insets.top));
        bottom.setValue(new Integer(insets.bottom));
        setAutoEditing(false);
    }
    
    public boolean isAutoEditing() {
        return autoEditing;
    }
    
    public void setAutoEditing(boolean autoEditing) {
        this.autoEditing = autoEditing;
    }
    
}

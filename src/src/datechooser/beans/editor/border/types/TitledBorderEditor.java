/*
 * TitledBorderEditor.java
 *
 * Created on 31 »юль 2006 г., 18:07
 *
 */

package datechooser.beans.editor.border.types;

import datechooser.beans.editor.border.SimpleBorderEditor;
import datechooser.beans.editor.font.SimpleFontEditor;
import datechooser.beans.editor.utils.*;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * Editor for titled borders.<br>  
 * –едактор границ типа Titled
 * @author Androsov Vadim
 * @since 1.0
 */
public class TitledBorderEditor extends AbstractBorderEditor implements PropertyChangeListener {
    
    
    private static String SAMPLE_TEXT = getEditorLocaleString("sample");
    
    private String title;
    private int titleJustification;
    private int titlePosition;
    private Font titleFont;
    private ColorHolder titleColor;
    private Border titledBorder;
    private Dimension comboDim = new Dimension(100, 20);
    private JComboBox comboPos;
    private JComboBox comboJust;
    
    private EditorDialog borderEditorDialog;
    private EditorDialog fontEditorDialog;
    
    public TitledBorderEditor() {
        
        initialize();
        
        titleColor = new ColorHolder();
        borderEditorDialog = new EditorDialog((Frame)getParent(), new SimpleBorderEditor());
        fontEditorDialog = new EditorDialog((Frame)getParent(), new SimpleFontEditor());
        setCaption(getEditorLocaleString("Titled"));
        assignValueToParameters();
        
        setLayout(new BorderLayout());
        JPanel pControls = new JPanel(new GridLayout(3, 1));
        pControls.add(createCaptionField());
        JPanel buttons = getCenteredPane(null);
        buttons.add(createFontChooseButton());
        buttons.add(createBorderChooseButton());
        buttons.add(createColorChooseButton());
        pControls.add(buttons);
        JPanel selectors = getCenteredPane(null);
        selectors.add(createTitlePositionSelector());
        selectors.add(createTitleJustifySelector());
        pControls.add(selectors);
        add(pControls, BorderLayout.NORTH);
        
        refreshInterface();
    }
    
    private void setText(String text) {
        title = text;
        fireChange();
    }
    
    private JPanel createCaptionField() {
        final JTextField field = new JTextField(15);
        field.setText(SAMPLE_TEXT);
        field.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                setText(field.getText());
            }
            public void insertUpdate(DocumentEvent e) {
                setText(field.getText());
            }
            public void removeUpdate(DocumentEvent e) {
                setText(field.getText());
            }
        });
        return getCenteredPane(field);
    }
    
    private JPanel createBorderChooseButton() {
        JButton bBorder = new JButton(getEditorLocaleString("Border"));
        bBorder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Border newBorder = (Border)
                borderEditorDialog.showDialog(titledBorder, getEditorLocaleString("Select_border"));
                if (!borderEditorDialog.isCanceled()) {
                    titledBorder = newBorder;
                    fireChange();
                }
            }
        });
        return getCenteredPane(bBorder);
    }
    
    private JPanel createColorChooseButton() {
        JButton bColor = new JButton(getEditorLocaleString("Color"));
        ColorChooseAction colorChooseAction = new ColorChooseAction(titleColor,
                getEditorLocaleString("Title_color"), this);
        colorChooseAction.addPropertyChangeListener(this);
        bColor.addActionListener(colorChooseAction);
        return getCenteredPane(bColor);
    }
    
    private JPanel createTitleJustifySelector() {
        NamedInt[] data = new NamedInt[] {
            new NamedInt(getEditorLocaleString("left"), TitledBorder.LEFT),
            new NamedInt(getEditorLocaleString("center"), TitledBorder.CENTER),
            new NamedInt(getEditorLocaleString("right"), TitledBorder.RIGHT),
            new NamedInt(getEditorLocaleString("leading"), TitledBorder.LEADING),
            new NamedInt(getEditorLocaleString("trailing"), TitledBorder.TRAILING) };
        comboJust = new JComboBox(data);
        comboJust.setPreferredSize(comboDim);
        setSelectedInt(comboJust, titleJustification);
        comboJust.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                titleJustification = ((NamedInt)comboJust.getSelectedItem()).getValue();
                fireChange();
            }
        });
        return getCenteredPane(comboJust);
    }
    
    private JPanel createTitlePositionSelector() {
        NamedInt[] data = new NamedInt[] {
            new NamedInt(getEditorLocaleString("above_top"), TitledBorder.ABOVE_TOP),
            new NamedInt(getEditorLocaleString("top"), TitledBorder.TOP),
            new NamedInt(getEditorLocaleString("below_top"), TitledBorder.BELOW_TOP),
            new NamedInt(getEditorLocaleString("above_bottom"), TitledBorder.ABOVE_BOTTOM),
            new NamedInt(getEditorLocaleString("bottom"), TitledBorder.BOTTOM),
            new NamedInt(getEditorLocaleString("below_bottom"), TitledBorder.BELOW_BOTTOM)};
        comboPos = new JComboBox(data);
        setSelectedInt(comboPos, titlePosition);
        comboPos.setPreferredSize(comboDim);
        comboPos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                titlePosition = ((NamedInt)comboPos.getSelectedItem()).getValue();
                fireChange();
            }
        });
        return getCenteredPane(comboPos);
    }
    
    private void setSelectedInt(JComboBox combo, int intValue) {
        int items = combo.getItemCount();
        for (int i = 0; i < items; i++) {
            if (((NamedInt)combo.getItemAt(i)).getValue() == intValue) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private JPanel createFontChooseButton() {
        JButton bFont = new JButton(getEditorLocaleString("Font"));
        bFont.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Font newFont = (Font)
                fontEditorDialog.showDialog(titleFont, getEditorLocaleString("Select_font"));
                if (!fontEditorDialog.isCanceled()) {
                    titleFont = newFont;
                    fireChange();
                }
            }
        });
        return getCenteredPane(bFont);
    }
    
    protected void prepareSelection() {
        setValue(new TitledBorder(titledBorder, title, titleJustification, titlePosition, titleFont, titleColor.getColor()));
    }
    
    private void assignValueToParameters() {
        titledBorder = getValue().getBorder();
        title = getValue().getTitle();
        titleJustification = getValue().getTitleJustification();
        titlePosition = getValue().getTitlePosition();
        titleFont = getValue().getTitleFont();
        titleColor.setColor(getValue().getTitleColor());
    }
    
    protected TitledBorder getValue() {
        return (TitledBorder) value;
    }
    
    public void refreshInterface() {
        assignValueToParameters();
        setSelectedInt(comboPos, titlePosition);
        setSelectedInt(comboJust, titleJustification);
    }
    
    protected Border getDefaultValue() {
        return new TitledBorder(BorderFactory.createEtchedBorder(), SAMPLE_TEXT);
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        if (!ColorChooseAction.COLOR_CHOOSE_EVENT_NAME.equals(evt.getPropertyName())) return;
        fireChange();
    }
}

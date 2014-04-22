/*
 * CustomCellEditorPane.java
 *
 * Created on 25 ќкт€брь 2006 г., 10:58
 *
 */

package datechooser.beans.editor.cell;

import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;
import datechooser.beans.editor.border.SimpleBorderEditor;
import datechooser.beans.editor.font.SimpleFontEditor;
import datechooser.beans.editor.utils.EditorDialog;
import datechooser.view.appearance.custom.CustomCellAppearance;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Panel for custom appearance.<br>
 * ѕанель дл€ свободно редактируемых €чеек (полностью рисуютс€ вручную)
 * @see datechooser.view.appearance.custom.CustomCellAppearance
 * @author Androsov Vadim
 * @since 1.0
 */
class CustomCellEditorPane extends CellEditorPane {
    
    private EditorDialog borderEditorDialog;
    private boolean innerEdit;
    private JSlider slider;
    
    public CustomCellEditorPane(MainCellEditorPane parentEditor) {
        super(parentEditor);
        setInnerEdit(false);
        borderEditorDialog = new EditorDialog((Frame)this.getParent(), new SimpleBorderEditor());
    }
    
    
    protected void updateEditorState() {
        if (isInnerEdit()) return;
        if (slider == null) return;
        slider.setValue((int)(getCustomValue().getTransparency() * 100d));
    }
    
    protected void generateInterface() {
        setLayout(new BorderLayout(2, 2));
        JPanel buttons = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel line1 = new JPanel(new GridLayout(1, 2, 5, 5));
        JPanel line2 = new JPanel(new GridLayout(1, 3, 5, 5));
        JPanel line3 = new JPanel(new GridLayout(1, 1, 5, 5));
        
        line1.add(createBackgroundChooseButton());
        line1.add(createCursorColorChooseButton());
        line2.add(createTextColorChooseButton());
        line2.add(createFontChooseButton());
        line2.add(createBorderChooseButton());
        line3.add(createTransparencySlider());
       
        buttons.add(line1);
        buttons.add(line2);
        add(buttons, BorderLayout.NORTH);
        add(line3, BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    
    private JPanel createTransparencySlider() {
        JPanel sliderPane = new JPanel(new BorderLayout(2, 5));
        JLabel sliderText = new JLabel(getEditorLocaleString("Transparency"));
        slider = new JSlider(0, 100, 100);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(10);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setInverted(true);
        slider.setExtent(10);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                setInnerEdit(true);
                getCustomValue().setTransparency(slider.getValue() / 100f);
                getMainEditor().fireLocalPropertyChange();
                setInnerEdit(false);
            }
        });
        sliderPane.add(sliderText, BorderLayout.WEST);
        sliderPane.add(slider, BorderLayout.CENTER);
        return sliderPane;
    }
    
    private JButton createBackgroundChooseButton() {
        JButton selBackColor = new JButton(getEditorLocaleString("Background_color"));
        selBackColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getCustomValue().setBackgroundColor(
                        selectColor(getCustomValue().getBackgroundColor(),
                        getEditorLocaleString("Select_background_color")));
                getMainEditor().fireLocalPropertyChange();
            }
        });
        return selBackColor;
    }
    
    private JButton createBorderChooseButton() {
        JButton bBorder = new JButton(getEditorLocaleString("Border"));
        bBorder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Border newBorder = (Border)
                borderEditorDialog.showDialog(getCustomValue().getCellBorder(), getEditorLocaleString("Select_border"));
                if (!borderEditorDialog.isCanceled()) {
                    getCustomValue().setCellBorder(newBorder);
                    getMainEditor().fireLocalPropertyChange();
                }
            }
        });
        return bBorder;
    }
    
    private CustomCellAppearance getCustomValue() {
        return (CustomCellAppearance) getValue();
    }

    public boolean isInnerEdit() {
        return innerEdit;
    }

    public void setInnerEdit(boolean innerEdit) {
        this.innerEdit = innerEdit;
    }
}

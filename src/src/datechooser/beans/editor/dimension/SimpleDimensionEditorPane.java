/*
 * DimensionEditorPane.java
 *
 * Created on 14 ќкт€брь 2006 г., 18:29
 *
 */

package datechooser.beans.editor.dimension;

import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

import java.awt.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Panel for dimension editor.<br>
 * ѕанель редактора размеров.
 * @author Androsov Vadim
 * @since 1.0
 */
class SimpleDimensionEditorPane extends JPanel {
    
    private PropertyEditorSupport editor;
    private Preview preview;
    private JPanel controls;
    
    public SimpleDimensionEditorPane(PropertyEditorSupport editor) {
        this.editor = editor;
        
        final Dimension dim = getValue();
        final SpinnerNumberModel width = new SpinnerNumberModel(dim.width, 10, 20000, 1);
        final SpinnerNumberModel height = new SpinnerNumberModel(dim.height, 10, 20000, 1);
        
        controls = new JPanel(new GridLayout(1, 2));
        JPanel widthPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel heightPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        widthPane.add(new JLabel(getEditorLocaleString("Width")));
        widthPane.add(new JSpinner(width));
        heightPane.add(new JLabel(getEditorLocaleString("Height")));
        heightPane.add(new JSpinner(height));
        controls.add(widthPane);
        controls.add(heightPane);
        controls.revalidate();
        
        preview = new Preview();
        
        setLayout(new BorderLayout());
        add(preview, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);
        revalidate();
        ChangeListener onChange = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                dim.setSize(width.getNumber().intValue(),
                        height.getNumber().intValue());
                setValue(dim);
                repaint();
            }
        };
        
        width.addChangeListener(onChange);
        height.addChangeListener(onChange);
    }
    
    private Dimension getValue() {
        return (Dimension) editor.getValue();
    }
    
    private void setValue(Dimension value) {
        editor.setValue(value);
    }
    
     private class Preview extends JPanel {
        
        private Point startRec = new Point();
        
        protected void paintComponent(Graphics g) {
            Rectangle rec = getBounds();
            Dimension dim = getValue();
            if (dim.width < rec.width) {
                startRec.x = (rec.width - dim.width) / 2;
            } else {
                startRec.x = 0;
            }
            if (dim.height < rec.height) {
                startRec.y = (rec.height - dim.height) / 2;
            } else {
                startRec.y = 0;
            }
            g.setColor(Color.BLUE);
            g.fillRect(startRec.x, startRec.y, dim.width, dim.height);
        }
        
        public Dimension getPreferredSize() {
            return new Dimension(100, 200);
        }
    }
}

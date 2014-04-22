/*
 * BackRendererEditorPane.java
 *
 * Created on 29 Ноябрь 2006 г., 23:33
 *
 */

package datechooser.beans.editor.backrender;

import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;
import datechooser.view.BackRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;

/**
 * Visual panel to set background picture.<br>
 * Визуальная панель установки изображения на фон.
 * @author Androsov Vadim
 * @since 1.0
 */
public class BackRendererEditorPane extends JPanel implements PropertyChangeListener {
    
    private PropertyEditorSupport editor = null;
    private JComboBox selStyle;
    private Preview previewPane;
    private JFileChooser fileChoose;
    
    public BackRendererEditorPane(PropertyEditorSupport editor) {
        fileChoose = new JFileChooser();
        setEditor(editor);
        generateInterface();
        updateInterface();
    }
    
    public void setEditor(PropertyEditorSupport editor) {
        if (this.editor != null) {
            this.editor.removePropertyChangeListener(this);
        }
        this.editor = editor;
        editor.addPropertyChangeListener(this);
    }
    
    private void updateInterface() {
        BackRenderer renderer = getEditorValue();
        if (renderer == null) return;
        selStyle.setSelectedIndex(renderer.getStyle());
        previewPane.repaint();
    }
    
    private void generateInterface() {
        selStyle = createStyleChooser();
        previewPane = new Preview();
        JButton bFile = createFileButton();
        
        previewPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createEtchedBorder()));
        setLayout(new BorderLayout());
        JPanel controlPane = new JPanel(new GridLayout(1, 2, 2, 2));
        JPanel selStylePane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPane.add(bFile);
        selStylePane.add(selStyle);
        controlPane.add(selStylePane);
        controlPane.add(buttonPane);
        add(controlPane, BorderLayout.NORTH);
        add(previewPane, BorderLayout.CENTER);
        revalidate();
    }
    
    private JComboBox createStyleChooser() {
        JComboBox combo = new JComboBox(new String[] {
            getEditorLocaleString("BackImageCenter"),
            getEditorLocaleString("BackImageFill"),
            getEditorLocaleString("BackImageTile")
        });
        
        combo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setStyle(selStyle.getSelectedIndex());
            }
        });
        
        return combo;
    }
    
    private JButton createFileButton() {
        JButton button = new JButton(getEditorLocaleString("Load"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChoose.showOpenDialog(null);
                if (result != JFileChooser.APPROVE_OPTION) return;
                try {
                    setURL(fileChoose.getSelectedFile().toURL());
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return button;
    }
    
    private void setStyle(int style) {
        BackRenderer value = getEditorValue();
        if ((value == null) || (style == value.getStyle())) return;
        editor.setValue(new BackRenderer(style,
                value != null ? value.getUrl() : null,
                getEditorValue().getImage()));
    }
    
    private void setURL(URL url) {
        BackRenderer value = getEditorValue();
        if ((value != null) && (url.equals(value.getUrl()))) return;
        editor.setValue(new BackRenderer(selStyle.getSelectedIndex(), url));
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        updateInterface();
    }
    
    private BackRenderer getEditorValue() {
        return (BackRenderer) editor.getValue();
    }
    
    private class Preview extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            BackRenderer renderer = getEditorValue();
            if (renderer == null) return;
            renderer.render((Graphics2D) g, getBounds());
        }
        
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }
    }
}

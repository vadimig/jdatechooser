/*
 * FontPreviewPane.java
 *
 * Created on 31 »юль 2006 г., 9:19
 *
 */

package datechooser.beans.editor.font;

import datechooser.beans.editor.utils.TextOutput;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;
import java.awt.*;
import javax.swing.*;

/**
 * Font preview panel.<br>
 * ѕанель предварительного просмотра шрифта.
 * @author Androsov Vadim
 * @since 1.0
 */
class FontPreviewPane extends JPanel {
    
    private String sampleText;
    private Font font;
    private Rectangle bounds;
    
    public FontPreviewPane(Font font) {
        setFont(font);
        setBorder(BorderFactory.createEtchedBorder());
        setSampleText(getEditorLocaleString("sample_text"));
    }

    public String getSampleText() {
        return sampleText;
    }

    public void setSampleText(String sampleText) {
        this.sampleText = sampleText;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
//        if (font == null) return;
        this.font = font;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bounds = getBounds();
        TextOutput.paintBoxed(g, bounds, getSampleText(), getFont());
    }
}

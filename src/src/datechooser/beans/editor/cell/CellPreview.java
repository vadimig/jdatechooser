/*
 * CellPreview.java
 *
 * Created on 5 јвгуст 2006 г., 22:19
 *
 */

package datechooser.beans.editor.cell;

import datechooser.beans.editor.utils.*;
import datechooser.beans.pic.Pictures;
import datechooser.view.GridPane;
import datechooser.view.appearance.CellAppearance;
import datechooser.view.appearance.custom.CustomCellAppearance;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Cell appearance preview.<br>
 * ѕанель предварительного просмотра редактируемой €чейки.
 * @author Androsov Vadim
 * @since 1.0
 */
class CellPreview extends JPanel {
    private static final int MIN_SIZE = 25;
    
    private CellDraw drawer;
    private SpinnerNumberModel width;
    private SpinnerNumberModel height;
    private boolean autoEdit;
    
    public CellPreview(CellAppearance look) {
        
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createEtchedBorder()));
        drawer = new CellDraw(look);
        setAutoEdit(false);
        
        width = new SpinnerNumberModel(MIN_SIZE, MIN_SIZE, 10000, 1);
        height = new SpinnerNumberModel(MIN_SIZE, MIN_SIZE, 10000, 1);
        JPanel spinnersPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        SpinPane spinWidth = new SpinPane(width, getEditorLocaleString("Width"));
        SpinPane spinHeight = new SpinPane(height, getEditorLocaleString("Height"));
        
        initListeners();
        
        spinnersPane.add(spinWidth);
        spinnersPane.add(spinHeight);
        setLayout(new BorderLayout());
        add(spinnersPane, BorderLayout.NORTH);
        add(drawer, BorderLayout.CENTER);
        
        updateCellSize();
    }
    
    public void setLook(CellAppearance look) {
        drawer.setLook(look);
        drawer.repaint();
    }
    
    private void initListeners() {
        
        width.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (isAutoEdit()) return;
                setAutoValue(width, drawer.setCellWidth(width.getNumber().intValue()));
                drawer.repaint();
            }
        });
        
        height.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (isAutoEdit()) return;
                setAutoValue(height, drawer.setCellHeight(height.getNumber().intValue()));
                drawer.repaint();
            }
        });
        
        drawer.addComponentListener(new ComponentListener() {
            public void componentHidden(ComponentEvent e) {
            }
            public void componentMoved(ComponentEvent e) {
            }
            public void componentResized(ComponentEvent e) {
                updateCellSize();
            }
            public void componentShown(ComponentEvent e) {
            }
        });
    }
    
    private void updateCellSize() {
        drawer.processCurrentSize();
        setAutoValue(width, drawer.getCellWidth());
        setAutoValue(height, drawer.getCellHeight());
    }
    
    private void setAutoValue(SpinnerNumberModel model, Integer value) {
        setAutoEdit(true);
        model.setValue(value);
        setAutoEdit(false);
    }
    
    public boolean isAutoEdit() {
        return autoEdit;
    }
    
    public void setAutoEdit(boolean autoEdit) {
        this.autoEdit = autoEdit;
    }
}

class CellDraw extends JPanel {
    
    private static final int PART = 2;
    private static final int BORDER = 5;
    
    private CellAppearance look;
    private Rectangle cellBounds;
    private boolean manualEdited;
    private transient Image backImage;
    
    public CellDraw(CellAppearance look) {
        try {
            backImage = ImageIO.read(Pictures.getDefaultPicture());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        cellBounds = new Rectangle();
        setLook(look);
        refreshSize();
        
    }
    
    private void refreshSize() {
        setManualEdited(false);
        initSize();
    }
    
    private void initSize() {
        Rectangle panelBounds = getBounds();
        if (!isManualEdited()) {
            cellBounds.width = panelBounds.width / PART;
            cellBounds.height = panelBounds.height / PART;
        }
        cellBounds.x = (panelBounds.width - cellBounds.width) / 2;
        cellBounds.y = (panelBounds.height - cellBounds.height) / 2;
    }
    
    public void processCurrentSize() {
        if (!isManualEdited()) {
            refreshSize();
        } else {
            Rectangle panelBounds = getBounds();
            if ((getCellWidth() + BORDER >= panelBounds.width) ||
                    (getCellHeight() + BORDER >= panelBounds.height)) {
                refreshSize();
            } else {
                initSize();
            }
        }
    }
    
    private void drawCenteredImage(Graphics2D g) {
        Rectangle rec = getBounds();
        g.drawImage(backImage,
                (rec.width - backImage.getWidth(null)) / 2,
                (rec.height - backImage.getHeight(null)) / 2,
                backImage.getWidth(null), backImage.getHeight(null), null);
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        processCurrentSize();
        Graphics2D g2d = (Graphics2D) g;
        if (look.getComposite() != null) {
            drawCenteredImage(g2d);
        }
        g2d.translate(cellBounds.x, cellBounds.y);
        look.render(g2d, this, getEditorLocaleString("26"), cellBounds.width, cellBounds.height, true);
    }
    
    public CellAppearance getLook() {
        return look;
    }
    
    public void setLook(CellAppearance look) {
        this.look = look;
    }
    
    public boolean isManualEdited() {
        return manualEdited;
    }
    
    public void setManualEdited(boolean manualEdited) {
        this.manualEdited = manualEdited;
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }
    
    private void changeWidth(int width, int panelWidth) {
        cellBounds.x = (panelWidth - width) / 2;
        cellBounds.width = width;
    }
    
    private void changeHeight(int height, int panelHeight) {
        cellBounds.y = (panelHeight - height) / 2;
        cellBounds.height = height;
    }
    
    public int setCellWidth(int width) {
        Rectangle panelBounds = getBounds();
        if ((width + BORDER) < panelBounds.getWidth()) {
            changeWidth(width, panelBounds.width);
            setManualEdited(true);
        }
        return getCellWidth();
    }
    
    public int setCellHeight(int height) {
        Rectangle panelBounds = getBounds();
        if ((height + BORDER) < panelBounds.getHeight()) {
            changeHeight(height, panelBounds.height);
            setManualEdited(true);
        }
        return getCellHeight();
    }
    
    public int getCellWidth() {
        return cellBounds.width;
    }
    
    public int getCellHeight() {
        return cellBounds.height;
    }
    
    public int getMaxWidth() {
        Rectangle panelBounds = getBounds();
        return panelBounds.width - BORDER;
    }
    
    public int getMaxHeight() {
        Rectangle panelBounds = getBounds();
        return panelBounds.height - BORDER;
    }
    
}

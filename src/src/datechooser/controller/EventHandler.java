/*
 * MouseHandler.java
 *
 * Created on 21 Май 2006 г., 15:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datechooser.controller;

import datechooser.model.DateChoose;
import datechooser.view.GridPane;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.*;
import javax.swing.KeyStroke;

/**
 * Handles mouse events: click (select/unselect), drag;
 * keyboard support.<br>
 * Позволяет интерпретировать поведение мыши:
 * Щелчок (выделение, снятие выделения).
 * Кроме того, поддерживает работу с клавиатурой.
 * Перетаскиваие.
 * @author Androsov Vadim
 * @since 1.0
 */
public class EventHandler
        implements MouseListener,
        MouseMotionListener,
        KeyListener,
        DateChooseController,
        MouseWheelListener,
        Serializable {
    
    private Rectangle gridBounds;
    private GridSelection tempSel;
    private GridPane view;
    private boolean mouseDown;
    private boolean dragging;
    private boolean dragStarted;
    private boolean autoScroll;
    private int previousLocation;
    private int previousKey;
    private long previousTime;
    
    
    
    public EventHandler() {
        tempSel = new GridSelection();
        mouseDown = false;
        setDragging(false);
        setDragStarted(false);
        previousLocation = KeyEvent.KEY_LOCATION_UNKNOWN;
        previousKey = KeyEvent.VK_UNDEFINED;
        previousTime = 0;
    }
    
    public void reBound() {
        gridBounds = getView().getBounds();
    }
    
    private GridSelection getCell(int x, int y) {
        tempSel.setColumn(
                x / (gridBounds.width / getView().getModel().getColsCount()));
        tempSel.setRow(
                y / (gridBounds.height / (getView().getModel().getRowsCount() + 1)));
        return tempSel;
    }
    
    protected void doSelect(MouseEvent e) {
        GridSelection sel = getCell(e.getX(), e.getY());
        if (sel.getRow() == 0) return;
        if (getModel().select(sel.getRow() - 1, sel.getColumn())) {
            if (!isMask(e.getModifiersEx(), MouseEvent.ALT_DOWN_MASK)) {
                getModel().tryApplySelection();
            }
        }
        getView().requestFocus();
    }
    
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() > 1) {
            getModel().commit();
            return;
        }
        doSelect(e);
    }
    
    public void mousePressed(MouseEvent e) {
    }
    
    public void mouseReleased(MouseEvent e) {
        setDragging(false);
        setDragStarted(false);
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
        setDragging(false);
        setDragStarted(false);
    }
    
    public void mouseDragged(MouseEvent e) {
        setDragging(true);
        doSelect(e);
    }
    
    public void mouseMoved(MouseEvent e) {
    }
    
    public void setView(GridPane palette) {
        this.view = palette;
        palette.addMouseListener(this);
        palette.addMouseMotionListener(this);
        palette.addKeyListener(this);
        palette.addMouseWheelListener(this);
    }
    
    public GridPane getView() {
        return view;
    }
    
    public DateChoose getModel() {
        return getView().getModel();
    }
    
    private boolean isDuplet(int key, int keyLocation) {
//        System.out.println("timing is " + (System.currentTimeMillis() - previousTime));
        if (previousTime == 0) {
            previousTime = System.currentTimeMillis();
        } else if ((System.currentTimeMillis() - previousTime) > 130) {
            previousTime = System.currentTimeMillis();
            previousKey = key;
            previousLocation = keyLocation;
            return false;
        }
        boolean result = (key == previousKey) && (keyLocation != previousLocation);
        if (!result) {
            previousKey = key;
            previousLocation = keyLocation;
        }
        return result;
    }
    
    protected void doShift(KeyEvent e) {
        int code = e.getKeyCode();
        
        // Тут отладить на обычной клавиатуре
        if (isDuplet(code, e.getKeyLocation())) return;
        
        switch (code) {
            case KeyEvent.VK_UP:
                getModel().shift(-1, 0);
                break;
            case KeyEvent.VK_DOWN:
                getModel().shift(1, 0);
                break;
            case KeyEvent.VK_LEFT:
                getModel().shift(0, -1);
                break;
            case KeyEvent.VK_RIGHT:
                getModel().shift(0, 1);
                break;
            case KeyEvent.VK_PAGE_DOWN:
                getModel().monthShift(-1);
                break;
            case KeyEvent.VK_PAGE_UP:
                getModel().monthShift(1);
                break;
            case KeyEvent.VK_END:
                getModel().yearShift(-1);
                break;
            case KeyEvent.VK_HOME:
                getModel().yearShift(1);
                break;
        }
    }
    
    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
        doShift(e);
    }
    
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            getModel().tryApplySelection();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            getModel().commit();
        }
    }
    
    protected boolean isDragging() {
        return dragging;
    }
    
    protected void setDragging(boolean dragging) {
        this.dragging = dragging;
    }
    
    protected boolean isDragStarted() {
        return dragStarted;
    }
    
    protected void setDragStarted(boolean dragStarted) {
        if ((!dragStarted) && isDragStarted()) {
            onDragEnd();
        }
        this.dragStarted = dragStarted;
        if (dragStarted) {
            onDragStart();
        }
    }
    
    protected void onDragStart() {
        setAutoScroll(getModel().isAutoScroll());
        getModel().setAutoScroll(false);
    }
    
    protected void onDragEnd() {
        getModel().setAutoScroll(isAutoScroll());
    }
    
    public boolean isAutoScroll() {
        return autoScroll;
    }
    
    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
    }
    
    public void mouseWheelMoved(MouseWheelEvent e) {
        getModel().shift(0, e.getWheelRotation() < 0 ? -1 : 1);
    }
    
    
    public static boolean isMask(int modifier, int mask) {
        return (modifier & mask) == mask;
    }
}

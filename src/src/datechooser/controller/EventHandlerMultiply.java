/*
 * MouseHandlerMultiply.java
 *
 * Created on 3 Июль 2006 г., 13:49
 *
 */

package datechooser.controller;

import datechooser.model.DateChoose;
import datechooser.model.multiple.MultyDateChooseModel;
import datechooser.model.multiple.MultySelectModes;

import java.awt.event.*;
import javax.swing.*;

/**
 * Handles input events (from mouse and keyboard).
 * Supports multiple selection.<br>
 * Обрабатывае события ввода (клавиатура и мышь).
 * Обеспечивает возможность множественного выбора.
 * @author Androsov Vadim
 * @since 1.0
 */


public class EventHandlerMultiply  extends EventHandler {
    
    private  void setSelectionModes(int modifiers) {
       if (isDragging()) {
           if (!isMask(modifiers, MouseEvent.SHIFT_DOWN_MASK)) {
                if (!isDragStarted()) {
                    setDragStarted(true);
                    getModel().setMode(MultySelectModes.SINGLE, 
                        isMask(modifiers, MouseEvent.CTRL_DOWN_MASK));
                    getModel().setPeriodSelectionStarted(false);
                } else {
                    getModel().setMode(MultySelectModes.PERIOD, true);
//                    getModel().setPeriodSelectionStarted(false);
                }
            }
        } else
        if (isMask(modifiers, MouseEvent.SHIFT_DOWN_MASK) &&
                isMask(modifiers, MouseEvent.CTRL_DOWN_MASK)) {
            getModel().setMode(MultySelectModes.PERIOD, true);
            getModel().setPeriodSelectionStarted(true);
        } else
        if (isMask(modifiers, MouseEvent.SHIFT_DOWN_MASK)) {
            getModel().setMode(MultySelectModes.PERIOD, false);
            getModel().setPeriodSelectionStarted(true);
        } else
        if (isMask(modifiers, MouseEvent.CTRL_DOWN_MASK)) {
            getModel().setMode(MultySelectModes.SINGLE, true);
            getModel().setPeriodSelectionStarted(false);
        } else {
            getModel().setMode(MultySelectModes.SINGLE, false);
            getModel().setPeriodSelectionStarted(false);
        }
    }
    
    protected void doSelect(MouseEvent e) {
        setSelectionModes(e.getModifiersEx());
        super.doSelect(e);
    }

    protected void doShift(KeyEvent e) {
        setSelectionModes(e.getModifiersEx());
        super.doShift(e);
    }

    public MultyDateChooseModel getModel() {
        return (MultyDateChooseModel)getView().getModel();
    }

}

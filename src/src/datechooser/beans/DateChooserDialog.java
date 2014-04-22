/*
 * DateChooserDialog.java
 *
 * Created on 13 Октябрь 2006 г., 13:37
 *
 */

package datechooser.beans;

import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;
import datechooser.events.CommitEvent;
import datechooser.events.CommitListener;
import datechooser.events.CursorMoveListener;
import datechooser.events.SelectionChangedListener;
import datechooser.model.exeptions.IncompatibleDataExeption;
import datechooser.model.multiple.MultyModelBehavior;
import datechooser.model.multiple.Period;
import datechooser.model.multiple.PeriodSet;
import datechooser.view.BackRenderer;
import datechooser.view.WeekDaysStyle;
import datechooser.view.appearance.AppearancesList;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.Serializable;
import java.util.*;
import javax.swing.*;

/**
 * Bean: dialog for date selection. Not visual.<br>
 * Компонент: диалоговое окно для выбора дат. Не визуальный.
 * @author Androsov Vadim
 * @since 1.0
 */
public class DateChooserDialog implements DateChooserBean, Serializable, PropertyChangeListener {
    
    public static final long serialVersionUID = 8940585643117164408L;
    
    /**
     * Dialog property's name prefix.<br>
     * Приставка к названиям всех свойтсв диалогового окна выбора даты.
     * @since 1.0
     */
    public static final String DIALOG_PREFIX = PREFIX + "dialog_";
    
    /**
     * Property name. <br>
     * Название свойства.
     * @see DateChooserDialog#isModal()
     * @since 1.0
     */
    public static final String PROPERTY_MODAL = DIALOG_PREFIX + "modal";
    
    /**
     * Property name. <br>
     * Название свойства.
     * @see DateChooserDialog#getCaption()
     * @since 1.0
     */
    public static final String PROPERTY_CAPTION = DIALOG_PREFIX + "caption";
    
    private DateChooserPanel chooser;
    private JPanel dialogPanel;
    private JDialog dialog = null;
    private Iterable<Period> state;
    private String caption;
    private boolean modal;
    
    private PropertyChangeSupport changeSupport;
    
    
    public DateChooserDialog() {
        changeSupport = new PropertyChangeSupport(this);
        setModal(true);
        chooser = new DateChooserPanel();
        chooser.addPropertyChangeListener(this);
        dialogPanel = getDialogPane();
        setCaption("");
        
        addCommitListener(new CommitListener() {
            public void onCommit(CommitEvent evt) {
                disposeDialog();
            }
        });
        
        UIManager.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                SwingUtilities.updateComponentTreeUI(dialogPanel);
            }
        });
    }
    
    /**
     * Modal dialog.<br>
     * Модальность окна.
     * @since 1.0
     */
    public boolean isModal() {
        return modal;
    }
    /**
     * @see DateChooserDialog#isModal()
     * @since 1.0
     */
    public void setModal(boolean modal) {
        boolean old = isModal();
        this.modal = modal;
        changeSupport.firePropertyChange(PROPERTY_MODAL, old, isModal());
    }
    /**
     * Dialog caption.<br>
     * Заголовок окна.
     * @since 1.0
     */
    public String getCaption() {
        return caption;
    }
    /**
     * @see DateChooserDialog#getCaption()
     * @since 1.0
     */
    public void setCaption(String caption) {
        String old = getCaption();
        this.caption = caption;
        changeSupport.firePropertyChange(PROPERTY_CAPTION, old, getCaption());
    }
    
    /**
     * Displays dialog window using "modal" property.<br>
     * Отображает диалоговое окно учитывая свойство "Модальное"
     * @see DateChooserDialog#showDialog(Frame, boolean)
     * @see DateChooserDialog#isModal()
     * @since 1.0
     */
    public void showDialog(Frame owner) {
        showDialog(owner, isModal());
    }
    
    /**
     * Displays dialog window centered in relation to the parent.<br>
     * Отображает диалоговое окно по центру относительно родителя.
     * @param owner Parent component.<br>
     * Родительский компонент.
     * @param isModal Is dialog modal.<br> Модальность
     * @since 1.0
     */
    public void showDialog(Frame owner, boolean isModal) {
        Dimension ownerSize = null;
        if (owner != null) {
            ownerSize = owner.getSize();
        } else {
            ownerSize = Toolkit.getDefaultToolkit().getScreenSize();
        }
        Dimension dim = dialogPanel.getPreferredSize();
        Point location = new Point(
                (ownerSize.width - dim.width) / 2,
                (ownerSize.height - dim.height) / 2);
        showDialog(owner, isModal, location);
    }
    
    /**
     * Displays dialog window with specified location.<br>
     * Отображает диалоговое в заданной позиции.
     * @param owner Parent component.<br>
     * Родительский компонент.
     * @param isModal Is dialog modal.<br> Модальность
     * @since 1.1
     */
    public void showDialog(Frame owner, boolean isModal, Point location) {
        dialog = new JDialog(owner, isModal);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new OnClose());
        dialog.setContentPane(dialogPanel);
        dialog.setTitle(getCaption());
        saveState();
        Dimension dim = dialogPanel.getPreferredSize();
        dialog.setSize(dim.width, dim.height + 80);
        dialog.setLocation(location);
        dialog.pack();
        dialog.setVisible(true);
    }
    
    public MultyModelBehavior getBehavior() {
        return chooser.getBehavior();
    }
    
    public AppearancesList getCurrentView() {
        return chooser.getCurrentView();
    }
    
    public PeriodSet getDefaultPeriods() {
        return chooser.getDefaultPeriods();
    }
    
    public PeriodSet getForbiddenPeriods() {
        return chooser.getForbiddenPeriods();
    }
    
    public Calendar getMaxDate() {
        return chooser.getMaxDate();
    }
    
    public Calendar getMinDate() {
        return chooser.getMinDate();
    }
    
    public Calendar getSelectedDate() {
        return chooser.getSelectedDate();
    }
    
    public PeriodSet getSelectedPeriodSet() {
        return chooser.getSelectedPeriodSet();
    }
    
    public Iterable<Period> getSelection() {
        return chooser.getSelection();
    }
    
    public boolean isAutoScroll() {
        return chooser.isAutoScroll();
    }
    
    public boolean isEnabled() {
        return chooser.isEnabled();
    }
    
    public boolean isShowOneMonth() {
        return chooser.isShowOneMonth();
    }
    
    public void setAutoScroll(boolean autoScroll) {
        chooser.setAutoScroll(autoScroll);
    }
    
    public void setBehavior(MultyModelBehavior behavior) {
        chooser.setBehavior(behavior);
    }
    
    public void setCurrentView(AppearancesList aList) {
        chooser.setCurrentView(aList);
    }
    
    public void setDefaultPeriods(PeriodSet periods) throws IncompatibleDataExeption {
        chooser.setDefaultPeriods(periods);
    }
    
    public void setEnabled(boolean enabled) {
        chooser.setEnabled(enabled);
    }
    
    public void setForbidden(Iterable<Period> forbiddenPeriods) {
        chooser.setForbidden(forbiddenPeriods);
    }
    
    public void setForbiddenPeriods(PeriodSet periods) throws IncompatibleDataExeption {
        chooser.setForbiddenPeriods(periods);
    }
    
    public void setMaxDate(Calendar aDate) {
        chooser.setMaxDate(aDate);
    }
    
    public void setMinDate(Calendar aDate) {
        chooser.setMinDate(aDate);
    }
    
    public void setSelectedDate(Calendar aDate) {
        chooser.setSelectedDate(aDate);
    }
    
    public void setSelection(Iterable<Period> periods) {
        chooser.setSelection(periods);
    }
    
    public void setSelection(PeriodSet periods) {
        chooser.setSelection(periods);
    }
    
    public void setShowOneMonth(boolean showOneMonth) {
        chooser.setShowOneMonth(showOneMonth);
    }
    
    public Dimension getPreferredSize() {
        return chooser.getPreferredSize();
    }
    
    public Font getNavigateFont() {
        return chooser.getNavigateFont();
    }
    
    public void setNavigateFont(Font font) {
        chooser.setNavigateFont(font);
    }
    
    public Locale getLocale() {
        return chooser.getLocale();
    }
    
    public void setLocale(Locale locale) {
        chooser.setLocale(locale);
    }
    
    public int getCurrentNavigateIndex() {
        return chooser.getCurrentNavigateIndex();
    }
    
    public void setCurrentNavigateIndex(int currentNavigateIndex) {
        chooser.setCurrentNavigateIndex(currentNavigateIndex);
    }
    
    public boolean isLocked() {
        return chooser.isLocked();
    }
    
    public void setLocked(boolean lock) {
        chooser.setLocked(lock);
    }
    
    public WeekDaysStyle getWeekStyle() {
        return chooser.getWeekStyle();
    }
    
    public void setWeekStyle(WeekDaysStyle weekStyle) {
        chooser.setWeekStyle(weekStyle);
    }
    
    public Calendar getCurrent() {
        return chooser.getCurrent();
    }
    
    public boolean setCurrent(Calendar aDate) {
        return chooser.setCurrent(aDate);
    }
    
    public void setNothingAllowed(boolean allow) {
        chooser.setNothingAllowed(allow);
    }
    
    public boolean isNothingAllowed() {
        return chooser.isNothingAllowed();
    }
    
    public Dimension getCalendarPreferredSize() {
        return chooser.getCalendarPreferredSize();
    }
    
    public void setCalendarPreferredSize(Dimension dim) {
        chooser.setCalendarPreferredSize(dim);
    }
    
    public void commit() {
        chooser.commit();
    }
    
    
    private JPanel getDialogPane() {
        
        JButton bOK = new JButton(getEditorLocaleString("OK"));
        JButton bCancel = new JButton(getEditorLocaleString("Cancel"));
        bOK.addActionListener(new OnOK());
        bCancel.addActionListener(new OnCancel());
        
        JPanel buttonPane = new JPanel(new GridLayout(1,2));
        buttonPane.add(bOK);
        buttonPane.add(bCancel);
        JPanel dialogPane = new JPanel(new BorderLayout());
        dialogPane.add(chooser, BorderLayout.CENTER);
        dialogPane.add(buttonPane, BorderLayout.SOUTH);
        
        return dialogPane;
    }
    
    private void saveState() {
        state = chooser.getSelection();
    }
    
    private void restoreState() {
        chooser.setSelection(state);
    }
    
    private void disposeDialog() {
        if (dialog == null) return;
        dialog.setVisible(false);
        dialog.dispose();
        dialog = null;
    }
    
    private void commitAction() {
        commit();
        disposeDialog();
    }
    
    
    private void cancelAction() {
        restoreState();
        commit();
        disposeDialog();
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        if (chooser.isDateChooserPanelProperty(evt.getPropertyName())) {
            changeSupport.firePropertyChange(evt);
        }
    }
    
    public void addCommitListener(CommitListener listener) {
        chooser.addCommitListener(listener);
    }
    
    public void removeCommitListener(CommitListener listener) {
        chooser.removeCommitListener(listener);
    }
    
    public void addSelectionChangedListener(SelectionChangedListener listener) {
        chooser.addSelectionChangedListener(listener);
    }
    
    public void removeSelectionChangedListener(SelectionChangedListener listener) {
        chooser.removeSelectionChangedListener(listener);
    }
    
    public void addCursorMoveListener(CursorMoveListener listener) {
        chooser.addCursorMoveListener(listener);
    }
    
    public void removeCursorMoveListener(CursorMoveListener listener) {
        chooser.removeCursorMoveListener(listener);
    }
    
    public Color getCalendarBackground() {
        return chooser.getCalendarBackground();
    }
    
    public void setCalendarBackground(Color backColor) {
        chooser.setCalendarBackground(backColor);
    }

    public AppearancesList getAppearancesList() {
        return chooser.getAppearancesList();
    }

    public DateChooserDialog clone() {
        return (DateChooserDialog) BeanUtils.cloneBean(this);
    }
    
    private class OnClose implements WindowListener {
        public void windowOpened(WindowEvent e) {
        }
        
        public void windowClosing(WindowEvent e) {
            disposeDialog();
        }
        
        public void windowClosed(WindowEvent e) {
        }
        
        public void windowIconified(WindowEvent e) {
        }
        
        public void windowDeiconified(WindowEvent e) {
        }
        
        public void windowActivated(WindowEvent e) {
        }
        
        public void windowDeactivated(WindowEvent e) {
        }
        
    }
    
    
    private class OnOK implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            commitAction();
        }
    }
    
    private class OnCancel implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            cancelAction();
        }
        
    }
    
}

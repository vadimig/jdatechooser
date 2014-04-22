/*
 * AppearEditorPane.java
 *
 * Created on 7 Август 2006 г., 11:45
 *
 */

package datechooser.beans.editor.appear;

import datechooser.beans.editor.backrender.BackRendererEditor;
import datechooser.beans.editor.cell.CellViewEditor;
import datechooser.beans.editor.descriptor.DescriptionManager;
import datechooser.beans.editor.utils.EditorDialog;
import datechooser.beans.locale.LocaleUtils;
import datechooser.controller.EventHandler;
import datechooser.events.CursorMoveEvent;
import datechooser.events.CursorMoveListener;
import datechooser.model.FooModel;
import datechooser.view.BackRenderer;
import datechooser.view.GridPane;
import datechooser.view.appearance.*;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.MessageFormat;
import java.util.AbstractList;
import javax.swing.*;
import javax.swing.event.ListDataListener;

/**
 * Visual panel for appearance customizing.<br>
 * Визуальная панель редактирования представлений.
 * @author Androsov Vadim
 * @since 1.0
 */
public class AppearEditorPane extends JPanel
        implements PropertyChangeListener, CursorMoveListener {
    
    private String[] cellTypes = { getEditorLocaleString("disabled"),
    getEditorLocaleString("usual"),
    getEditorLocaleString("selected"),
    getEditorLocaleString("now"),
    getEditorLocaleString("scroll"),
    getEditorLocaleString("caption")};
    private AppearEditor editor;
    private FooModel model;
    private JComboBox cellViews;
    private boolean autoEdit;
    private EditorDialog eDialog;
    private EditorDialog bDialog;
    private GridPane gp;
    private boolean tempEdit;
    private JComboBox views;
    private JButton bEditCellView;
    private JButton bDeleteCellView;
    private JButton bSetBackPicture;
    
    public AppearEditorPane(AppearEditor editor) {
        LocaleUtils.prepareStandartDialogButtonText();
        setEditor(editor);
        setAutoEdit(false);
        
        model = new FooModel();
        model.addCursorMoveListener(this);
        
        cellViews = createCellTypeCombo();
        gp = createGridPane();
        
        eDialog = new EditorDialog((Frame)this.getParent(), new CellViewEditor());
        bDialog = new EditorDialog((Frame)this.getParent(), new BackRendererEditor());
        
        JPanel control = new JPanel(new GridLayout(2, 4, 5, 5));
        
        bSetBackPicture = createBackRendererButton();
        
        createCellEditorButton();
        createDeleteViewButton();
        
        control.add(new JLabel(getEditorLocaleString("View"), JLabel.RIGHT));
        control.add(createViewManagerCombo());
        control.add(createNewViewButton());
        control.add(bDeleteCellView);
        
        
        control.add(new JLabel(getEditorLocaleString("Cell"), JLabel.RIGHT));
        control.add(cellViews);
        control.add(bEditCellView);
        control.add(bSetBackPicture);
        
        setLayout(new BorderLayout());
        add(control, BorderLayout.NORTH);
        add(gp, BorderLayout.CENTER);
        
    }
    
    private JPanel getEditorPane() {
        return this;
    }
    
    private JButton createDeleteViewButton() {
        bDeleteCellView = new JButton(getEditorLocaleString("Delete"));
        bDeleteCellView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selViewName = getAppList().getCurrent().getName();
                int ans = JOptionPane.showConfirmDialog(
                        getEditorPane().getParent(),
                        MessageFormat.format(getEditorLocaleString("Delete_view_prompt"), selViewName),
                        getEditorLocaleString("Confirm_delete"), JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.YES_OPTION)  {
                    if (getAppList().unRegisterAppearance(selViewName)) {
                        getAppList().setCurrent(AppearancesList.DEFAULT);
                        updateEditable();
                        refreshViews();
                        fireLocalPropertyChange();
                    } else {
                        JOptionPane.showMessageDialog(
                                getEditorPane().getParent(),
                                MessageFormat.format(getEditorLocaleString("Cant_delete_view"), selViewName),
                                getEditorLocaleString("Undelitable_view"), JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        return bDeleteCellView;
    }
    
    
    private JComboBox createViewManagerCombo() {
        ViewListModel mm = null;
        views = new JComboBox(new ViewListModel());
        updateEditable();
        views.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getAppList().setCurrent((String) views.getSelectedItem());
                updateEditable();
                gp.repaint();
            }
        });
        return views;
    }
    
    private JButton createBackRendererButton() {
        JButton button = new JButton(getEditorLocaleString("BackRenderer"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewAppearance currentAppearance = getAppList().getCurrent();
                BackRenderer newValue = (BackRenderer) bDialog.showDialog(
                        currentAppearance.getRenderer(), 
                        getEditorLocaleString("BackRenderer"));
                if (!bDialog.isCanceled()) {
                    currentAppearance.setRenderer(newValue);
                    fireLocalPropertyChange();
                }
                gp.repaint();
            }
        });
        return button;
    }
    
    private void updateEditable() {
        ViewAppearance currentAppearance = getAppList().getCurrent();
        bEditCellView.setEnabled(currentAppearance.isEditable());
        bDeleteCellView.setEnabled(currentAppearance.isEditable());
        bSetBackPicture.setEnabled(currentAppearance.isSupportsTransparency() && 
                currentAppearance.isEditable());
    }
    
    private void refreshViews() {
        views.revalidate();
        views.repaint();
    }
    
    private void registerViewAppearance(String newViewName) {
        ViewAppearance newApp = (ViewAppearance) getAppList().getCurrent().clone();
        newApp.setName(newViewName);
        newApp.setEditable(true);
        getAppList().registerAppearance(newApp);
        getAppList().setCurrent(newViewName);
        views.setSelectedItem(newViewName);
        fireLocalPropertyChange();
        refreshViews();
    }
    
    private JButton createNewViewButton() {
        JButton bSave = new JButton(getEditorLocaleString("Create_view_clone"));
        bSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newViewName =
                        JOptionPane.showInputDialog(getEditorLocaleString("View_name_prompt"));
                if (newViewName == null) return;
                if (getAppList().isRegistered(newViewName)) {
                    int ans = JOptionPane.showConfirmDialog(
                            getEditorPane().getParent(),
                            MessageFormat.format(getEditorLocaleString("View_exist_prompt"), newViewName),
                            getEditorLocaleString("Existing_view"), JOptionPane.YES_NO_OPTION);
                    if (ans == JOptionPane.YES_OPTION) {
                        if (getAppList().getAppearance(newViewName).isEditable()) {
                            getAppList().unRegisterAppearance(newViewName);
                        } else {
                            JOptionPane.showMessageDialog(
                                    getEditorPane().getParent(),
                                    MessageFormat.format(getEditorLocaleString("Cant_replace_view"), newViewName),
                                    getEditorLocaleString("Uneditable_view"), JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        return;
                    }
                }
                registerViewAppearance(newViewName);
            }
        });
        return bSave;
    }
    
    private void generateClonedValue() {
        if (isTempEdit()) return;
        setTempEdit(true);
        getEditor().setInnerValue(getAppList().notDeepClone());
        gp.setAppearanceList(getAppList());
        setTempEdit(false);
    }
    
    private void fireLocalPropertyChange() {
        getEditor().firePropertyChange();
    }
    
    private CellAppearance getEditing() {
        ViewAppearance curApp = getAppList().getCurrent();
        switch (cellViews.getSelectedIndex()) {
            case 0:
                return curApp.getDisabled();
            case 1:
                return curApp.getUsual();
            case 2:
                return curApp.getSelected();
            case 3:
                return curApp.getNow();
            case 4:
                return curApp.getScroll();
            case 5:
                return curApp.getCaption();
        }
        return null;
    }
    
    private JButton createCellEditorButton() {
        bEditCellView = new JButton(getEditorLocaleString("Edit"));
        bEditCellView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                CellAppearance newValue = (CellAppearance) eDialog.showDialog(
                        getEditing(), MessageFormat.format(getEditorLocaleString("Edit_cell"), cellViews.getSelectedItem()));
                if (!eDialog.isCanceled()) {
                    getEditing().assign(newValue);
                    fireLocalPropertyChange();
                }
                gp.repaint();
            }
        });
        return bEditCellView;
    }
    
    private JComboBox createCellTypeCombo() {
        cellViews = new JComboBox(cellTypes);
        cellViews.setSelectedIndex(5);
        cellViews.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isAutoEdit()) return;
                setAutoEdit(true);
                model.setTypeSelected(cellViews.getSelectedIndex());
                setAutoEdit(false);
            }
        });
        return cellViews;
    }
    
    private GridPane createGridPane() {
        GridPane gp = new GridPane(getAppList());
        gp.setFocusable(false);
        gp.setFocused(true);
        gp.setPreferredSize(new Dimension(200, 200));
        gp.setModel(model);
        gp.setController(new EventHandler());
        return gp;
    }
    
    public AppearEditor getEditor() {
        return editor;
    }
    
    public void setEditor(AppearEditor editor) {
        if (getEditor() != null) {
            getEditor().removePropertyChangeListener(this);
        }
        this.editor = editor;
        setTempEdit(false);
        if (getEditor() != null) {
            getEditor().addPropertyChangeListener(this);
        }
    }
    
    public boolean isAutoEdit() {
        return autoEdit;
    }
    
    public void setAutoEdit(boolean autoEdit) {
        this.autoEdit = autoEdit;
    }
    
    public boolean isTempEdit() {
        return tempEdit;
    }
    
    public void setTempEdit(boolean tempEdit) {
        this.tempEdit = tempEdit;
    }
    
    private AppearancesList getAppList() {
        return getEditor().getAppearancesList();
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        generateClonedValue();
        gp.repaint();
    }
    
    public void onCursorMove(CursorMoveEvent evt) {
        if (isAutoEdit()) return;
        setAutoEdit(true);
        if (!model.isSomeSelected()) return;
        int index = 0;
        switch (model.getSelectedCellState()) {
            case NORMAL:
                index = 1;
                break;
            case SELECTED:
                index = 2;
                break;
            case NOW:
                index = 3;
                break;
            case NORMAL_SCROLL:
                index = 4;
                break;
            case UNACCESSIBLE:
                index = 0;
                break;
        }
        cellViews.setSelectedIndex(index);
        setAutoEdit(false);
    }
    
    class ViewListModel extends AbstractListModel implements ComboBoxModel, PropertyChangeListener {
        
        public ViewListModel() {
            getEditor().addPropertyChangeListener(this);
        }
        
        public void setSelectedItem(Object anItem) {
            String itemName = (String) anItem;
            getAppList().setCurrent(itemName);
        }
        
        public Object getSelectedItem() {
            return getAppList().getCurrent().getName();
        }
        
        public int getSize() {
            return getAppList().getRegisteredCount();
        }
        
        public Object getElementAt(int index) {
            return getAppList().getRegisteredNames()[index];
        }
        
        public void propertyChange(PropertyChangeEvent evt) {
            this.fireContentsChanged(this, 0, getSize() - 1);
        }
    }
}

/*
 * DateChooserCustomizer.java
 *
 * Created on 3 Ноябрь 2006 г., 13:42
 *
 */

package datechooser.beans.customizer;

import datechooser.beans.customizer.edit.PropertyCellEditor;
import datechooser.beans.customizer.render.CaptionCellRenderer;
import datechooser.beans.customizer.render.ValueCellRenderer;

import java.awt.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Generalized bean customizer.
 * It need only BeanInfo class.
 * Looks like it can work with any beans, but it was tested only with
 * DateChooserXXX components. Supports custom editors. To make your own 
 * customizer class you have to make class extending DateChooserCustomizer with
 * default constructor.<br>
 * Обобщенный настройщик для компонент.
 * Для работы требует только информационный класс компонента.
 * Теоретически может работать с любыми компонентами, но тестировался только на
 * компонентах, поставляемых с этой библиотекой. Полная поддержка пользовательских
 * редакторов. Класс полностью готов к работе. Все настройщики просто наследуют
 * данный класс и переопределяют конструктор (нужен без параметров), передавая
 * конструктору этого класса необходимый информационный файл.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.beans.DateChooserPanelCustomizer
 * @see datechooser.beans.DateChooserComboCustomizer
 * @see datechooser.beans.DateChooserDialogCustomizer
 */
public class DateChooserCustomizer extends JPanel implements Customizer, PropertyChangeListener {
    
    private PropertyDescriptorsHolder holder;
    private JTextArea description;
    private PropertyChangeSupport changeSupport;
    private JTable table;
    
    /**
     * Constructor. Your customizer class must have deafult constructor.<br>
     * Конструктор. Ваш класс-настройщик должен иметь конструктор по умолчанию.
     * @param beanInfo BeanInfo class for bean to customize.<br>
     * Информационный файл настраиваемого компонента.
     * @since 1.0
     */
    public DateChooserCustomizer(BeanInfo beanInfo) throws IntrospectionException {
        
        changeSupport = new PropertyChangeSupport(this);
        
        holder = new PropertyDescriptorsHolder(beanInfo);
        getHolder().addPropertyChangeListener(this);
        setLayout(new BorderLayout(2, 2));
        
        description = new JTextArea("");
        description.setRows(2);
        description.setEditable(false);
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setOpaque(false);
        
        table = new JTable(new PropertyTableModel(getHolder()));
        table.setRowHeight(20);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(1).setCellEditor(new PropertyCellEditor(getHolder()));
        table.setDefaultRenderer(CaptionCellRenderer.class, new CaptionCellRenderer(getHolder()));
        table.setDefaultRenderer(ValueCellRenderer.class, new ValueCellRenderer(getHolder()));
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                updateDescriptor();
            }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(new JScrollPane(description), BorderLayout.SOUTH);
    }
    
    /**
     * Get bean information class.<br>
     * Получить информационный класс компонента.
     * @return BeanInfo class.<br>
     * Информационный класс.
     * @since 1.0
     */
    public BeanInfo getBeanInfo() {
        return getHolder().getBeanInfo();
    }
    
    private void updateDescriptor() {
        PropertyDescriptor propDescr = getHolder().getPropertydescriptor(getSelectedProperty());
        String text = propDescr.getShortDescription();
        if (text == null) text = propDescr.getDisplayName();
        if (text == null) text = propDescr.getName();
        description.setText(text);
    }
    
    private String getSelectedProperty() {
        return (String) table.getValueAt(table.getSelectedRow(), 0);
    }
    
    /**
     * Sets bean to customize.<br>
     * Устанавливает настраиваемый компонент.
     * @param bean Bean to customize.<br>
     * Настраиваемый компонент.
     * @since 1.0
     */
    public void setObject(Object bean) {
        getHolder().prepareForBean(bean);
    }
    
    /**
     * Get object of PropertyDescriptorsHolder class.<br>
     * Получить объект класса PropertyDescriptorsHolder.
     * @since 1.0
     */
    public PropertyDescriptorsHolder getHolder() {
        return holder;
    }

    /**
     * 
     * @since 1.0
     */
    public void propertyChange(PropertyChangeEvent evt) {
        changeSupport.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }

    /**
     * 
     * @since 1.0
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * 
     * @since 1.0
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}

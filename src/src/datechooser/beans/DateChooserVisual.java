/*
 * DateChooserVisual.java
 *
 * Created on 28 Июнь 2007 г.
 *
 */

package datechooser.beans;

import java.io.*;
import javax.swing.JPanel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Basic class for all visual datechooser components.
 * Базовый класс для всех визуальных компонентов выбора даты.
 * @author Androsov Vadim
 * @since 1.1
 */
public abstract class DateChooserVisual extends JPanel implements DateChooserBean, Serializable {
    /**
     * Bean mutates into TableCellEditor object.
     * Editor refers new (cloned) bean.
     * Преобразует компонент в редактор ячеек таблицы.
     * Редактор ссылается на клонированный компонент.
     * @since 1.1
     * @return TableCellEditor object
     */
    public static BeanTableCellEditor createTableCellEditor(DateChooserVisual bean) {
        return new BeanTableCellEditor(bean.clone());
    }
    /**
     * Bean mutates into TableCellRenderer object.
     * Renderer refers new (cloned) bean.
     * Преобразует компонент в рисовальщик ячеек таблицы.
     * Рисовальщик ссылается на клонированный компонент.
     * @since 1.1
     * @return TableCellRenderer object
     */
    public static BeanTableCellRenderer createTableCellRenderer(DateChooserVisual bean) {
        return new BeanTableCellRenderer(bean.clone());
    }
    
    public DateChooserVisual clone() {
        return (DateChooserVisual) BeanUtils.cloneBean(this);
    }

    /**
     * Bean mutates into TableCellRenderer object.
     * Renderer refers new (cloned) bean.
     * Преобразует компонент в рисовальщик ячеек таблицы.
     * Рисовальщик ссылается на клонированный компонент.
     * @since 1.1
     * @return TableCellRenderer object
     */
    public BeanTableCellRenderer createTableCellRenderer() {
        return createTableCellRenderer(this);
    }

    /**
     * Bean mutates into TableCellEditor object.
     * Editor refers new (cloned) bean.
     * Преобразует компонент в редактор ячеек таблицы.
     * Редактор ссылается на клонированный компонент.
     * @since 1.1
     * @return TableCellEditor object
     */
    public BeanTableCellEditor createTableCellEditor() {
        return createTableCellEditor(this);
    }
}

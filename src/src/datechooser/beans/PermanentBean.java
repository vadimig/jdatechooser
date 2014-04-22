/*
 * PermanentBean.java
 *
 * Created on 26 Ноябрь 2006 г., 16:37
 *
 */

package datechooser.beans;

import datechooser.beans.customizer.DateChooserCustomizer;
import java.io.*;



/**
 * Service class. It is user for loading any components properies from file.
 * Files are created by customizer (run library file to access customizer).<br>
 * Сервисный класс. Применяется при необходимости загрузки настроек компонента
 * (любого из представленных в библиотеке) из файла. Файлы создаются натройщиком,
 * доступ к которому можно получить, запустив библиотеку.
 * @author Androsov Vadim
 * @since 1.0
 * @see datechooser.beans.customizer.DateChooserCustomizer
 */
public class PermanentBean {
    
    private static final DateChooserCustomizer customizers[] =
            new DateChooserCustomizer[] {null, null, null};
    
    private static final int PANEL = 0;
    private static final int DIALOG = 0;
    private static final int COMBO = 0;
    
    
    
    private static boolean saveBeanContext = false;
    /**
     * Clear cached customizer classes.
     * Use only if you turned on property "save component's context"
     * (saveBeanContext). <br>
     * Удаляет ссылки на кешированные классы настройщиков.
     * Вызывать метод имеет смысл только если вы включали свойство
     * "Сохранять контекст компонентов" (saveBeanContext).
     * @see PermanentBean#isSaveBeanContext()
     * @since 1.0
     */
    public static void dispose() {
        for (int i = 0; i < customizers.length; i++) {
            customizers[i] = null;
        }
    }
    /**
     * Lets store component's context.
     * For properties saving needs to create customizer class instance and initialize
     * it by bean's properties. This is a long operation. You can turn on customizer's
     * caching (setting parameter "true"). But if you do not plan load/save component's
     * properties frequently turn it off to save memory. You need to call dispose
     * method if turned this ptoperty on after all loading/saving operations finished.<br>
     *
     * Позволяет сохранять контекст компонента.
     * Дело в том что при сохранении требуется создать экземпляр класса-настройщика
     * и проинициализировать его свойствами компонента. Это довольно долгая операция,
     * требуется загрузить сразу много компонентов можно включить сохранение созданных
     * настройщиков (установив значение свойства true). Если же загрузка требуется только
     * однажды, свойство лучше отключить, чтобы избежать сохранения в памяти ненужных
     * объектов. Если Вы включали свойство - когда загрузка классов больше не
     * понадобится, вызовите метод dispose.
     * @see PermanentBean#dispose()
     * @since 1.0
     */
    public static boolean isSaveBeanContext() {
        return saveBeanContext;
    }
    /**
     *
     * @see PermanentBean#isSaveBeanContext()
     * @since 1.0
     */
    public static void setSaveBeanContext(boolean aSaveBeanContext) {
        saveBeanContext = aSaveBeanContext;
    }
    
    private static String loadBeanParameters(int beanID, Class customizerClass , Object bean, InputStream from) {
        DateChooserCustomizer customizer = customizers[beanID];
        if (customizer == null) {
            try {
                customizer = (DateChooserCustomizer) customizerClass.newInstance();
            } catch (Exception ex) {
                return ex.getClass().getName();
            }
        }
        customizer.setObject(bean);
        String result = customizer.getHolder().readFromStream(from);
        if (isSaveBeanContext()) {
            customizers[beanID] = customizer;
        } else {
            customizers[beanID] = null;
        }
        return result;
    }
    /**
     * Loads bean's parameters from input stream.
     * Загружает параметры панели из потока ввода.
     * @param panel Components for properties assignment.<br>
     * Компонент, которому будут назначены свойства.
     * @since 1.0
     */
    public static String loadBeanParameters(DateChooserPanel panel, InputStream from) {
        return loadBeanParameters(PANEL, DateChooserPanelCustomizer.class, panel, from);
    }
    /**
     * Loads bean's parameters from input stream.
     * Загружает параметры панели из потока ввода.
     * @param dialog Components for properties assignment.<br>
     * Компонент, которому будут назначены свойства.
     * @since 1.0
     */
    public static String loadBeanParameters(DateChooserDialog dialog, InputStream from) {
        return loadBeanParameters(DIALOG, DateChooserDialogCustomizer.class, dialog, from);
    }
    /**
     * Loads bean's parameters from input stream.
     * Загружает параметры панели из потока ввода.
     * @param combo Components for properties assignment.<br>
     * Компонент, которому будут назначены свойства.
     * @since 1.0
     */
    public static String loadBeanParameters(DateChooserCombo combo, InputStream from) {
        return loadBeanParameters(COMBO, DateChooserComboCustomizer.class, combo, from);
    }
    /**
     * Loads bean's parameters from input stream.
     * Загружает параметры панели из потока ввода.
     * @param panel Components to store properties.<br>
     * Компонент, свойства которого должны быть сохранены.
     * @since 1.0
     * @throws java.io.FileNotFoundException If properies file not found.<br>
     * Возникает, если файл не найден.
     */
    public static String loadBeanParameters(DateChooserPanel panel, File from) throws FileNotFoundException {
        return loadBeanParameters(panel, new FileInputStream(from));
    }
    /**
     * Loads bean's parameters from input stream.
     * Загружает параметры панели из потока ввода.
     * @param dialog Components for properties assignment.<br>
     * Компонент, которому будут назначены свойства.
     * @since 1.0
     * @throws java.io.FileNotFoundException If properies file not found.<br>
     * Возникает, если файл не найден.
     */
    public static String loadBeanParameters(DateChooserDialog dialog, File from) throws FileNotFoundException {
        return loadBeanParameters(dialog, new FileInputStream(from));
    }
    /**
     * Loads bean's parameters from input stream.
     * Загружает параметры панели из потока ввода.
     * @param combo Components for properties assignment.<br>
     * Компонент, которому будут назначены свойства.
     * @since 1.0
     * @throws java.io.FileNotFoundException If properies file not found.<br>
     * Возникает, если файл не найден.
     */
    public static String loadBeanParameters(DateChooserCombo combo, File from) throws FileNotFoundException {
        return loadBeanParameters(combo, new FileInputStream(from));
    }
}

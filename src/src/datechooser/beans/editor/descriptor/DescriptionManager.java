/*
 * DescriptionManager.java
 *
 * Created on 3 Август 2006 г., 16:09
 *
 */

package datechooser.beans.editor.descriptor;

import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

import java.text.MessageFormat;
import java.util.*;

/**
 * Describes all properties used in this library. Descriptions: text (for users)
 * and java (for IDEs).<br>
 * Класс, позволяющий получить описание всех свойств компонентов, используемых
 * в библиотеке. Предоставляется описание двух основных типов: текстовое
 * (для вывода на экран) и java (для генерации кода).<br>
 * Бы принято решение не включать соответствующие методы в классы свойств по
 * нескольким причинам:<br>
 * 1) Вывод таких описаний логически не относятся к функциям классов.<br>
 * 2) Некоторые классы свойств из  стандартной библиотеки (String, boolean, ...)
 *    поэтому нет возможности встроить в них соответствующий механизм. Поэтому
 *    для избежания путаницы лучше вынести описание классов за их пределы.<br>
 * Описатели каждого типа предсавлены объектами отдельных классов, которые
 * регистрируются в этом.
 * @author Androsov Vadim
 * @see ClassDescriptor
 * @since 1.0
 */
public class DescriptionManager {
    
    private static DescriptionManager instance = new DescriptionManager();
    private ArrayList<ClassDescriptor> descriptors =  new ArrayList<ClassDescriptor>();
    
    private DescriptionManager() {
        registerDescriptor(new FontDescriptor());
        registerDescriptor(new ColorDescriptor());
        registerDescriptor(new BorderDescriptor());
        registerDescriptor(new CustomCellAppearanceDescriptor());
        registerDescriptor(new SwingCellAppearanceDescriptor());
        registerDescriptor(new ModelBehaviorDescriptor());
        registerDescriptor(new ViewDescriptor());
        registerDescriptor(new AppearancesListDescriptor());
        registerDescriptor(new GregorianCalendarDescriptor());
        registerDescriptor(new PeriodDescriptor());
        registerDescriptor(new PeriodSetDescriptor());
        registerDescriptor(new LocaleDescriptor());
        registerDescriptor(new IntegerDescriptor());
        registerDescriptor(new StringDescriptor());
        registerDescriptor(new WeekDaysStyleDescriptor());
        registerDescriptor(new BackRendererDescriptor());
    }
    /**
     * Lets register new class descriptor.<br> 
     * Позволяет зарегистрировать собственный описатель класса.
     * @param descriptor Desriptor class for user object.<br>
     * Класс, описывающий, объекты пользовательского типа.
     * @see ClassDescriptor
     */
    public void registerDescriptor(ClassDescriptor descriptor) {
        descriptors.add(descriptor);
    }
    
    private ClassDescriptor getDescriptor(Class aClass) {
        for (ClassDescriptor elem : descriptors) {
            if (elem.getDescriptedClass().isAssignableFrom(aClass) ) {
                return elem;
            }
        }
        return null;
    }
    /**
     * Text description for component users.<br>
     * Возвращает описание объекта для вывода на экран.
     * @param value Object for description.<br>
     * Описываемый объект.
     * @return Object description.<br>
     * Описание объекта
     */
    public static String describe(Object value) {
        return describe(value, null);
    }
    /**
     * Text description for component users.<br>
     * Возвращает описание объекта для вывода на экран.
     * @param value Object for description.<br>
     * Описываемый объект.
     * @param locale Locale.<br>
     * Локализация
     * @return Object description.<br>
     * Описание объекта
     */
    public static String describe(Object value, Locale locale) {
        if (value == null) {
            return  getEditorLocaleString("null");
        }
        ClassDescriptor descriptor = instance.getDescriptor(value.getClass());
        if (descriptor == null) return MessageFormat.format(
                getEditorLocaleString("cant_describe_message"),
                value.getClass().getName());
        return locale == null ?
            descriptor.getDescription(value) :
            descriptor.getDescription(value, locale);
    }
    
    /**
     * Java initialization code.<br>
     * Код инициализации объекта на Java
     * @param value Object for description.<br>
     * Описываемый объект.
     * @param aClass Class of describing object. Used when value = <b>null</b>.<br>
     * Класс описываемого значение.
     * Используется только в случаях, когда первый параметр равен null.
     * @return Java initialization code.<br>
     * Java код
     */
    public static String describeJava(Object value, Class aClass) {
        ClassDescriptor descriptor = instance.getDescriptor(aClass);
        if (descriptor == null) return "new " + value.getClass().getName() +
                "() /*" + getEditorLocaleString("no_java_init_default_used") + "*/";
        if ((value == null) && (!descriptor.canProcessNull())) {
            return  "(" + aClass.getName() +  ")null";
        }
        return descriptor.getJavaDescription(value);
    }
}

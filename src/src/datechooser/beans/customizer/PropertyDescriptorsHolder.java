/*
 * PropertyDescriptorsHolder.java
 *
 * Created on 4 Ноябрь 2006 г., 14:21
 *
 */

package datechooser.beans.customizer;

import datechooser.beans.editor.BooleanEditorFoo;
import datechooser.beans.editor.StringEditor;
import java.beans.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.JOptionPane;
import static datechooser.beans.locale.LocaleUtils.getErrorsLocaleString;

/**
 * Lets save / load beans properies.<br>
 * Класс, реализующий непосредственную работу со свойствами компонент.
 * Обеспечивает подключение нового компонента, загрузку и сохранение свойств.
 * Краеугольный камень всей системы классов настройки компонент.
 * @author Androsov Vadim
 * @since 1.0
 */
public class PropertyDescriptorsHolder implements PropertyChangeListener {
    
    /**
     * Constant "Operation completed successfully".<br>
     * Константа "Операция завершена успешно.
     * @since 1.0
     */
    public static final String OK = "ok";
    
    private Map<String, PropertyEditorSupport> editorsCash;
    private Map<String, PropertyDescriptor> descriptorsCash;
    private PropertyDescriptor[] descriptorsCashArray;
    private Map<Class, Class> additionalDescriptor;
    private BeanInfo info;
    private Object bean;
    private boolean beanAttached;
    private PropertyChangeSupport changeSupport;
    
    /**
     * Creates holder not linked with bean.<br>
     * Создает хранитель свойств, не связанный с конктетным компонентом.
     * @since 1.0
     */
    public PropertyDescriptorsHolder(BeanInfo beanInfo) throws IntrospectionException {
        this(beanInfo, null);
    }
    
    /**
     * Creates holder linked with bean.<br>
     * Создает хранитель свойств, связанный с конктетным компонентом.
     * @since 1.0
     */
    public PropertyDescriptorsHolder(BeanInfo beanInfo, Object bean) throws IntrospectionException {
        editorsCash = new HashMap<String, PropertyEditorSupport>();
        descriptorsCash = new HashMap<String, PropertyDescriptor>();
        changeSupport = new PropertyChangeSupport(this);
        initializeAdditionalDescriptors();
        setBeanInfo(beanInfo);
        initialize(beanInfo);
        prepareForBean(bean);
        getBeanInfo().getPropertyDescriptors();
    }
    
    private void initializeAdditionalDescriptors() {
        additionalDescriptor = new HashMap<Class, Class>();
        additionalDescriptor.put(String.class, StringEditor.class);
        additionalDescriptor.put(boolean.class, BooleanEditorFoo.class);
        additionalDescriptor.put(Boolean.class, BooleanEditorFoo.class);
    }
    
    private Class getAdditionalDescriptorClass(Class key) {
        return additionalDescriptor.get(key);
    }
    
    private void initialize(BeanInfo beanInfo) {
        PropertyDescriptor[] descriptors = getBeanInfo().getPropertyDescriptors();
        for (PropertyDescriptor elem : descriptors) {
            Class editorClass = elem.getPropertyEditorClass();
            if (editorClass == null) {
                editorClass = getAdditionalDescriptorClass(elem.getReadMethod().getReturnType());
            }
            if (editorClass != null) {
                createPropertyEditor(elem.getName(), editorClass);
                registerDescriptor(elem);
            }
        }
        descriptorsCashArray = descriptorsCash.values().toArray(new PropertyDescriptor[descriptorsCash.size()]);
    }
    
    /**
     * Is descriptor instance of Boolean or boolean type.<br>
     * Проверяет, относится ли дескриптор к логическому типу (Boolean или boolean)
     * @since 1.0
     * @return True if descriptor is instance of boolean type.<br>
     * Истину, если дескриптор описывает логический тип.
     * @param propertyDescriptor Descriptor to test.<br>
     * Проверяемый дескриптор.
     */
    public static boolean isBooleanDescriptor(PropertyDescriptor propertyDescriptor) {
        Class type = propertyDescriptor.getReadMethod().getReturnType();
        return (type == boolean.class) || (type == Boolean.class);
    }
    
    /**
     * Is descriptor instance of String type.<br>
     * Проверяет, относится ли дескриптор к строковому типу.
     * @param propertyDescriptor Descriptor to test.<br>
     * Проверяемый дескриптор.
     * @return True if descriptor is instance of string type.<br>
     * Истину, если дескриптор описывает строку.
     * @since 1.0
     */    
    public static boolean isStringDescriptor(PropertyDescriptor propertyDescriptor) {
        Class type = propertyDescriptor.getReadMethod().getReturnType();
        return type == String.class;
    }
    
    /**
     * Prepares bean for customizing.<br>
     * Готовит компонент к настройке.
     * @since 1.0
     */
    public void prepareForBean(Object bean) {
        if (bean == null) return;
        setBeanAttached(false);
        testBean(bean);
        this.bean = bean;
        PropertyDescriptor[] descriptors = getPropertyDescriptors();
        PropertyEditorSupport editor = null;
        for (PropertyDescriptor elem : descriptors) {
            try {
                editor = getPropertyEditor(elem.getName());
                editor.setValue(elem.getReadMethod().invoke(bean));
                editor.firePropertyChange();
            } catch (Exception ex) {
                showExeption(ex);
            }
        }
        setBeanAttached(true);
    }
    
    private void testBean(Object bean) {
        String mustHaveInfo = bean.getClass().getName() + "BeanInfo";
        if (!mustHaveInfo.equals(getBeanInfo().getClass().getName())) {
            throw new RuntimeException("Incorrect Bean class name (" +
                    getBeanInfo().getClass().getName() + ')');
        }
    }
    
    /**
     * Get editor for specified property.<br>
     * Получает редактор для нужного свойства.
     * @since 1.0
     * @param property Property name.<br>
     * Название свойства.
     * @return Editor for specified property.<br>
     * Редактор для нужного свойства.
     */
    public PropertyEditorSupport getPropertyEditor(String property) {
        return editorsCash.get(property);
    }
    
    /**
     * Get descriptor for specified property.<br>
     * Получает класс-описатель нужного свойства.
     * @param property Property name.<br>
     * Название свойства.
     * @return Descriptor for specified property.<br>
     * Описатель для нужного свойства.
     * @since 1.0
     */
    public PropertyDescriptor getPropertydescriptor(String property) {
        return descriptorsCash.get(property);
    }
    
    private void registerDescriptor(PropertyDescriptor descr) {
        descriptorsCash.put(descr.getName(), descr);
    }
    
    private void createPropertyEditor(String property, Class editorClass) {
        if (editorsCash.containsKey(property)) {
            return;
        } else {
            try {
                PropertyEditorSupport newEditor;
                newEditor = (PropertyEditorSupport)
                Class.forName(editorClass.getName()).newInstance();
                newEditor.addPropertyChangeListener(this);
                editorsCash.put(property, newEditor);
                return;
            } catch (Exception ex) {
                showExeption(ex);
            }
        }
    }
    
    private Object getProperty(String name) {
        PropertyDescriptor descriptor = getPropertydescriptor(name);
        try {
            return descriptor.getReadMethod().invoke(bean);
        } catch (Exception ex) {
            showExeption(ex);
            return null;
        }
    }
    
    /**
     * Updates bean when property was customized.<br>
     * Обновляет компонент, когда было изменено его свойсвтво.
     * Записывает новое значение в компонент.
     * @since 1.0
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (!isBeanAttached()) return;
        PropertyDescriptor[] descriptors = getBeanInfo().getPropertyDescriptors();
        PropertyEditorSupport editor = null;
        for (PropertyDescriptor elem : descriptors) {
            if (elem.getWriteMethod() == null) continue;
            editor = getPropertyEditor(elem.getName());
            if (evt.getSource() != editor) continue;
            try {
                elem.getWriteMethod().invoke(bean, editor.getValue());
                firePropertyChange(elem.getName(), null, null);
            } catch (Exception ex) {
                setProperty(this.bean, elem.getName(), getProperty(elem.getName()));
                showExeption(ex);
            }
        }
    }
    
    private void showExeption(Exception ex) {
        Throwable cause = ex;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        JOptionPane.showMessageDialog(null, 
                "<html>" +  cause.getMessage() + "<br>(<i>" + cause.getClass().getName() + "</i>)", 
                getErrorsLocaleString("Exception"), JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * How many properties current bean has.<br>
     * Сколько свойств у компонента.
     * @return Properties count.<br>
     * Количество свойств.
     * 
     * @since 1.0
     */
    public int getPropertyCount() {
        return descriptorsCash.size();
    }
    
    /**
     * Get all property descriptors.<br>
     * Получить все зарегистрвированные объекты класса PropertyDescriptor.
     * @since 1.0
     */
    public PropertyDescriptor[] getPropertyDescriptors() {
        return descriptorsCashArray;
    }
    
    /**
     * Is bean to customize.<br>
     * Есть ли компонент для настройки.
     * @since 1.0
     * @see datechooser.beans.customizer.PropertyDescriptorsHolder#prepareForBean(Object)
     */
    public boolean isBeanAttached() {
        return beanAttached;
    }
    
    private void setBeanAttached(boolean beanAttached) {
        this.beanAttached = beanAttached;
    }
    
    /**
     * Get object of BeanInfo class.<br>
     * Получить объект класса BeanInfo.
     * @since 1.0
     */
    public BeanInfo getBeanInfo() {
        return info;
    }
    
    /**
     * Set object of BeanInfo class.<br>
     * Установить объект класса BeanInfo.
     * @since 1.0
     */
    public void setBeanInfo(BeanInfo info) {
        this.info = info;
    }
    
    /**
     * Write properties to file.<br>
     * Записать свойства в файл.
     * @since 1.0
     */
    public String writeToFile(File file) {
        try {
            ObjectOutputStream dataOut = new ObjectOutputStream(
                    new BufferedOutputStream(
                    new FileOutputStream(file)));
            PropertyPair[] pairs = new PropertyPair[editorsCash.size()];
            int i = 0;
            for (String elem : editorsCash.keySet()) {
                pairs[i] = new PropertyPair(elem, editorsCash.get(elem).getValue());
                ++i;
            }
            dataOut.writeObject(pairs);
            dataOut.flush();
            dataOut.close();
            return OK;
        } catch (Exception ex) {
            return ex.getClass().getName() + " : " + ex.getMessage();
        }
    }
    
    private void setProperty(Object bean, String name, Object value) {
        getPropertyEditor(name).setValue(value);
    }
    
    /**
     * Read properties from input stream.
     * Use if you plan to store properties file in jar<br>
     * Прочитать свойства из потока ввода.
     * Изпользуйте если планируете хранить файлы настроек в jar-файле.
     * @since 1.0
     */
    public String readFromStream(InputStream from) {
        try {
            ObjectInputStream dataIn = new ObjectInputStream(
                    new BufferedInputStream(from));
            PropertyPair[] pairs = (PropertyPair[]) dataIn.readObject();
            for (PropertyPair pair : pairs) {
                setProperty(bean, pair.propertyName, pair.propertyValue);
            }
            dataIn.close();
            return OK;
        } catch (Exception ex) {
            return ex.getClass().getName() + " : " + ex.getMessage();
        }
    }
    
    /**
     * Reads properies from file.<br>
     * Читает свойства из файла.
     * @since 1.0
     */
    public String readFromFile(File file) {
        try {
            return readFromStream(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            return ex.getClass().getName() + " : " + ex.getMessage();
        }
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
    
    /**
     * 
     * @since 1.0
     */
    public void firePropertyChange(String name, Object oldVal, Object newVal) {
        changeSupport.firePropertyChange(name, oldVal, newVal);
    }
}

class PropertyPair implements Serializable {
    
    public PropertyPair(String propertyName, Object propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }
    
    String propertyName;
    Object propertyValue;
}


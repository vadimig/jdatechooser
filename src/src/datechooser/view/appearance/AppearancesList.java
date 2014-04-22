/*
 * AppearancesList.java
 *
 * Created on 28 Июль 2006 г., 18:50
 *
 */

package datechooser.view.appearance;

import datechooser.beans.pic.Pictures;
import datechooser.view.appearance.custom.CustomCellAppearance;
import datechooser.view.appearance.swing.ButtonPainter;
import datechooser.view.appearance.swing.LabelPainter;
import datechooser.view.appearance.swing.SwingCellAppearance;

import java.awt.*;
import java.beans.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Bean's appearances list. It has some hardcoded views, allows add new views.<br> 
 * Список профилей внешнего вида компонента.
 * Имеет ряд жеско закодированных профилей, позволяет добавлять новые.
 * @see CellAppearance
 * @author Androsov Vadim
 * @since 1.0
 */
public class AppearancesList
        implements PropertyChangeListener, Cloneable, Serializable {
    
    /**
     * Default appearance.<br>
     * Внешний вид по умолчанию.
     */
    public static String DEFAULT = "Swing";
    /**
     * Editable view.<br>
     * Редактируемый профиль.
     */
    public static String CUSTOM = "custom";
    
    transient private PropertyChangeSupport changeSupport;
    private HashMap<String, ViewAppearance> appearances;
    private ViewAppearance current = null;
    
    /**
     * Default constructor creates only hardcoded profiles and one custom view.<br>
     * Конструктор по умолчанию. Создает жестко закодированные профили и один
     * редактируемый.
     * @since 1.0
     */
    public AppearancesList() {
        this(true);
    }
    
    
    /**
     * Constructor creates hardcoded profiles and allows control editable
     * view creation.<br>
     * Конструктор с управлением создания редактируемого профиля.
     * @param autoInit Does editable view must be created (as a clone of default view).<br>
     * Создавать ли автоматически редактируемый профиль
     * (на основе клонирования профиля по умолчанию).
     * @since 1.0
     */
    public AppearancesList(boolean autoInit) {
        changeSupport = new PropertyChangeSupport(this);
        appearances = new HashMap<String, ViewAppearance>();
        registerHardCoded();
        setCurrent(DEFAULT);
        if (autoInit) {
            ViewAppearance customView = (ViewAppearance) getDefaultAppearance().clone();
            customView.setName(CUSTOM);
            customView.setEditable(true);
            registerAppearance(customView);
        }
    }
    
    /**
     * Конструктор для очень поверсхностного копирования.
     * @since 1.0
     */
    private AppearancesList(HashMap<String, ViewAppearance> data,
            PropertyChangeSupport changer,
            ViewAppearance current) {
        
        changeSupport = new PropertyChangeSupport(this);
        movePropertyChangeListeners(changer, changeSupport);
        
        appearances = data;
        this.current = current;
    }
    
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        changeSupport = new PropertyChangeSupport(this);
    }
    
    private void registerHardCoded() {
        
        registerHardCodedAppearance(new ViewAppearance("Contrast",
                new CustomCellAppearance(new Color(0, 0, 0),
                new Color(255, 255, 255),
                BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
                new Font("Serif", Font.BOLD, 12),
                new Color(255, 255, 255), 1),
                new CustomCellAppearance(new Color(0, 0, 0),
                new Color(255, 255, 255),
                BorderFactory.createLineBorder(new Color(255, 255, 255), 1),
                new Font("Serif", Font.BOLD, 16),
                new Color(0, 255, 0), 1),
                new CustomCellAppearance(new Color(0, 0, 0),
                new Color(255, 255, 255),
                BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
                new Font("Serif", Font.BOLD, 14),
                new Color(0, 255, 0), 1),
                new CustomCellAppearance(new Color(0, 0, 0),
                new Color(250, 250, 250),
                BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
                new Font("Serif", Font.ITALIC, 12),
                new Color(0, 0, 255), 1),
                new CustomCellAppearance(new Color(0, 0, 0),
                new Color(255, 255, 255),
                BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
                new Font("Serif", Font.BOLD, 12),
                new Color(0, 0, 255), 1),
                new CustomCellAppearance(new Color(0, 0, 0),
                new Color(180, 180, 180),
                BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
                new Font("Serif", Font.PLAIN, 10),
                new Color(255, 0, 0), 1),
                null,
                true
                ));
        
        registerHardCodedAppearance(new ViewAppearance("Light",
                new CustomCellAppearance(new Color(255, 255, 255),
                new Color(0, 0, 0),
                (Border)null,
                new Font("Serif", Font.PLAIN, 12),
                new Color(0, 0, 153), 1),
                new CustomCellAppearance(new Color(153, 153, 255),
                new Color(0, 0, 0),
                (Border)null,
                new Font("Serif", Font.PLAIN, 12),
                new Color(0, 0, 102), 1),
                new CustomCellAppearance(new Color(204, 255, 204),
                new Color(51, 255, 51),
                (Border)null,
                new Font("Serif", Font.PLAIN, 12),
                new Color(0, 0, 153), 1),
                new CustomCellAppearance(new Color(255, 255, 255),
                new Color(0, 0, 102),
                (Border)null,
                new Font("Serif", Font.ITALIC, 10),
                new Color(0, 0, 255), 1),
                new CustomCellAppearance(new Color(255, 255, 255),
                new Color(0, 0, 0),
                (Border)null,
                new Font("Serif", Font.BOLD, 12),
                new Color(0, 0, 255), 1),
                new CustomCellAppearance(new Color(255, 255, 255),
                new Color(255, 0, 0),
                (Border)null,
                new Font("Serif", Font.PLAIN, 12),
                new Color(255, 0, 0), 1),
                null,
                true
                ));
        
        registerHardCodedAppearance(new ViewAppearance("Bordered",
                new CustomCellAppearance(new Color(204, 204, 204),
                new Color(0, 0, 249),
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED,
                (Color)null,
                (Color)null),
                new Font("Serif", Font.PLAIN, 12),
                new Color(0, 0, 0), 1),
                new CustomCellAppearance(new Color(204, 204, 204),
                new Color(0, 0, 255),
                BorderFactory.createBevelBorder(BevelBorder.LOWERED,
                (Color)null,
                (Color)null,
                new Color(0, 0, 0),
                (Color)null),
                new Font("Serif", Font.BOLD, 12),
                new Color(0, 0, 102), 1),
                new CustomCellAppearance(new Color(204, 204, 204),
                new Color(0, 51, 0),
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED,
                (Color)null,
                (Color)null),
                new Font("Serif", Font.PLAIN, 12),
                new Color(0, 102, 0), 1),
                new CustomCellAppearance(new Color(204, 204, 204),
                new Color(102, 102, 102),
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED,
                (Color)null,
                (Color)null),
                new Font("Serif", Font.PLAIN, 10),
                new Color(0, 0, 255), 1),
                new CustomCellAppearance(new Color(204, 204, 204),
                new Color(0, 0, 0),
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED,
                (Color)null,
                (Color)null),
                new Font("Serif", Font.BOLD, 12),
                new Color(0, 0, 255), 1),
                new CustomCellAppearance(new Color(204, 204, 204),
                new Color(153, 153, 153),
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED,
                (Color)null,
                (Color)null),
                new Font("Serif", Font.PLAIN, 12),
                new Color(255, 0, 0), 1),
                null,
                true
                ));
        
        registerHardCodedAppearance(new ViewAppearance("Grey",
                new CustomCellAppearance(new Color(120, 120, 120),
                new Color(255, 255, 255),
                BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
                new Font("Serif", Font.PLAIN, 12),
                new Color(255, 255, 255), 1),
                new CustomCellAppearance(new Color(100, 100, 100),
                new Color(255, 255, 255),
                BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
                new Font("Serif", Font.PLAIN, 12),
                new Color(0, 255, 0), 1),
                new CustomCellAppearance(new Color(120, 120, 120),
                new Color(0, 255, 0),
                BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
                new Font("Serif", Font.PLAIN, 12),
                new Color(0, 255, 0), 1),
                new CustomCellAppearance(new Color(160, 160, 160),
                new Color(250, 250, 250),
                BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
                new Font("Serif", Font.PLAIN, 12),
                new Color(0, 0, 255), 1),
                new CustomCellAppearance(new Color(100, 100, 100),
                new Color(255, 255, 255),
                BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
                new Font("Serif", Font.PLAIN, 12),
                new Color(0, 0, 255), 1),
                new CustomCellAppearance(new Color(120, 120, 120),
                new Color(180, 180, 180),
                BorderFactory.createLineBorder(new Color(0, 0, 0), 1),
                new Font("Serif", Font.PLAIN, 12),
                new Color(255, 0, 0), 1),
                null,
                true
                ));
        
        registerHardCodedAppearance(new datechooser.view.appearance.ViewAppearance("Dali",
                new datechooser.view.appearance.custom.CustomCellAppearance(new java.awt.Color(0, 0, 0),
                new java.awt.Color(255, 255, 255),
                (javax.swing.border.Border)null,
                new java.awt.Font("Serif", java.awt.Font.PLAIN, 12),
                new java.awt.Color(0, 0, 153),
                0.4f),
                new datechooser.view.appearance.custom.CustomCellAppearance(new java.awt.Color(153, 153, 255),
                new java.awt.Color(255, 255, 0),
                (javax.swing.border.Border)null,
                new java.awt.Font("Serif", java.awt.Font.BOLD, 14),
                new java.awt.Color(0, 0, 102),
                0.2f),
                new datechooser.view.appearance.custom.CustomCellAppearance(new java.awt.Color(0, 0, 0),
                new java.awt.Color(51, 255, 51),
                (javax.swing.border.Border)null,
                new java.awt.Font("Serif", java.awt.Font.PLAIN, 12),
                new java.awt.Color(0, 0, 153),
                0.5f),
                new datechooser.view.appearance.custom.CustomCellAppearance(new java.awt.Color(204, 204, 204),
                new java.awt.Color(0, 0, 102),
                (javax.swing.border.Border)null,
                new java.awt.Font("Serif", java.awt.Font.ITALIC, 10),
                new java.awt.Color(0, 0, 255),
                0.4f),
                new datechooser.view.appearance.custom.CustomCellAppearance(new java.awt.Color(0, 0, 0),
                new java.awt.Color(255, 255, 255),
                (javax.swing.border.Border)null,
                new java.awt.Font("Serif", java.awt.Font.BOLD, 12),
                new java.awt.Color(0, 0, 255),
                0.4f),
                new datechooser.view.appearance.custom.CustomCellAppearance(new java.awt.Color(255, 0, 0),
                new java.awt.Color(255, 0, 0),
                (javax.swing.border.Border)null,
                new java.awt.Font("Serif", java.awt.Font.PLAIN, 12),
                new java.awt.Color(255, 0, 0),
                0.3f),
                new datechooser.view.BackRenderer( 1, Pictures.getResource("dali.gif")),
                true,
                true));
        
        registerHardCodedAppearance(new ViewAppearance(DEFAULT,
                /*usual*/new SwingCellAppearance(null, null, Color.BLUE,
                false, true, new ButtonPainter()),
                /*selected*/new SwingCellAppearance(null, null, Color.BLUE,
                true, true, new ButtonPainter()),
                /*now*/new SwingCellAppearance(null, Color.BLUE, Color.BLUE,
                false, true, new ButtonPainter()),
                /*scroll*/new SwingCellAppearance(null, Color.GRAY, Color.BLUE,
                false, true, new LabelPainter()),
                /*caption*/new SwingCellAppearance(null, null, Color.BLUE,
                false, true, new LabelPainter()),
                /*disabled*/new SwingCellAppearance(null, null, Color.RED,
                false, false, new ButtonPainter()), null, false
                ));
    }
    
    /**
     * This constructor is used for automatic initialization code.<br>
     * Этот конструктор используется при автоматической генерации кода.
     * @param current Selected view name.<br>
     * Имя активного профиля внешнего вида.
     * @param cellViews List of accessible views.<br>
     * Доступные профили внешнего вида.
     * @since 1.0
     */
    public AppearancesList(String current, ViewAppearance... cellViews) {
        this(false);
        for (ViewAppearance view : cellViews) {
            registerAppearance(view);
        }
        setCurrent(current);
    }
    
    /**
     * Registered views count.<br>
     * Количество зарегистрированных профилей.
     * @since 1.0
     */
    public int getRegisteredCount() {
        return appearances.size();
    }
    
    /**
     * Is profile with specified name registered.<br>
     * Проверяет, зарегистрирован ли профиль с заданным именем.
     * @since 1.0
     */
    public boolean isRegistered(String name) {
        return appearances.containsKey(name);
    }
    
    /**
     * Get array of registered views names.<br>
     * Получение массива имен зарегистрированных профилей.
     * @since 1.0
     */
    public String[] getRegisteredNames() {
        String[] result = new String[appearances.size()];
        result[0] = getAppearance(CUSTOM).getName();
        result[1] = getAppearance(DEFAULT).getName();
        int index = 2;
        for (String key : getKeys()) {
            if (key.equals(DEFAULT) || key.equals(CUSTOM)) {
                continue;
            }
            result[index] = appearances.get(key).getName();
            index++;
        }
        return result;
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        changeSupport.firePropertyChange(evt);
    }
    
    /**
     * Get view with specified name.<br>
     * Получить профиль по имени.
     * @param name View name.<br>
     * Имя профиля
     * @return ViewAppearance class, null if view with specified name was not registered.<br>
     * Профиль. null, если профиля с таким именем не зарегистрировано.
     * @see datechooser.view.appearance.ViewAppearance
     * @since 1.0
     */
    public ViewAppearance getAppearance(String name) {
        return appearances.get(name);
    }
    
    /**
     * Get default view.<br> 
     * Получить профиль по умолчанию.
     * @since 1.0
     */
    public ViewAppearance getDefaultAppearance() {
        return appearances.get(DEFAULT);
    }
    
    /**
     * Activates view with specified name.<br>
     * Активирует профиль с указанным именем.
     * @param name Name of activating view.<br>
     * Имя активируемого профиля.
     * @since 1.0
     */
    public boolean setCurrent(String name) {
        if (isRegistered(name)) {
            current = appearances.get(name);
            return true;
        } else {
            current = getDefaultAppearance();
            return false;
        }
    }
    
    /**
     * Get current view.<br>
     * Возвращает активный профиль.
     * @since 1.0
     */
    public ViewAppearance getCurrent() {
        return current;
    }
    
    /**
     * Registers new view.<br>
     * Регистрирует новый профиль
     * @see ViewAppearance
     * @since 1.0
     */
    public void registerAppearance(ViewAppearance anAppearance) {
        appearances.put(anAppearance.getName(), anAppearance);
        anAppearance.addPropertyChangeListener(this);
        changeSupport.firePropertyChange("Appearance list", null, null);
    }
    /**
     * Register new uneditable view.<br>
     * Регистрирует новый профиль, помечая его как нередактируемый.
     * @see ViewAppearance
     * @since 1.0
     */
    public void registerHardCodedAppearance(ViewAppearance anAppearance) {
        anAppearance.setEditable(false);
        registerAppearance(anAppearance);
    }
    /**
     * Регистрирует профиль. Если профиль с таким именем существует - заменяет.
     * @since 1.0
     */
    private void reRegisterAppearance(ViewAppearance anAppearance) {
        unRegisterAnyAppearance(anAppearance.getName());
        registerAppearance(anAppearance);
    }
    /**
     * Deletes view with specified name if it is not (default or uneditable).<br>
     * Удаляет профиль с заданным именем, если это не профиль по умолчанию или
     * настраиваемый.
     * @since 1.0
     */
    public boolean unRegisterAppearance(String aName) {
        if (aName.equals(DEFAULT) || aName.equals(CUSTOM)) {
            return false;
        }
        unRegisterAnyAppearance(aName);
        return true;
    }
    /**
     * Удаляет профиль с заданным именем.
     * @since 1.0
     */
    private void unRegisterAnyAppearance(String aName) {
        if (appearances.containsKey(aName)) {
            getAppearance(aName).removePropertyChangeListener(this);
            appearances.remove(aName);
            changeSupport.firePropertyChange("Appearance list", null, null);
        }
    }
    
    private void movePropertyChangeListeners(PropertyChangeSupport from, PropertyChangeSupport to) {
        PropertyChangeListener[] listeners = from.getPropertyChangeListeners();
        for (PropertyChangeListener theListener : listeners) {
            to.addPropertyChangeListener(theListener);
//            from.removePropertyChangeListener(theListener);
        }
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
    /**
     * <b>Deep</b> clone.<br>
     * Глубокое клонирование.
     * @since 1.0
     */
    public Object clone() {
        AppearancesList newInst = new AppearancesList(false);
        movePropertyChangeListeners(changeSupport, newInst.changeSupport);
        for (String key : appearances.keySet()) {
            ViewAppearance view = appearances.get(key);
            if (!view.isEditable()) continue;
            newInst.registerAppearance((ViewAppearance) view.clone());
        }
        newInst.setCurrent(getCurrent().getName());
        return newInst;
    }
    
    /**
     * Not deep clone.<br>
     * Поверхностное клонирование.
     * @since 1.0
     */
    public AppearancesList notDeepClone() {
        return new AppearancesList(appearances, changeSupport, current);
    }
    
    /**
     * Get collection of registered views names.<br>
     * Возвращает список имен зарегистрированых профилей.
     * @since 1.0
     */
    public Iterable<String> getKeys() {
        return appearances.keySet();
    }
}

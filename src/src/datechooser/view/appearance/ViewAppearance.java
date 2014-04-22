/*
 * ViewAppearance.java
 *
 * Created on 16 »юль 2006 г., 18:45
 *
 */

package datechooser.view.appearance;

import datechooser.view.BackRenderer;
import java.awt.*;
import java.beans.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * View profile. Contains rendering options for each cell's type.<br>
 * ѕрофиль внешнего вида €чейки. —одержит настройки рисовани€ дл€ €чеек каждого типа.
 * @author Androsov Vadim
 * @since 1.0
 */
public class ViewAppearance implements Cloneable, Serializable {
    
    transient private PropertyChangeSupport changeSupport;
    /**
     * ќбычна€ €чейка.
     * @since 1.0
     */
    private CellAppearance usual;
    /**
     * ¬ыбранна€ €чейка.
     * @since 1.0
     */
    private CellAppearance selected;
    /**
     * “екуща€ €чейка.
     * @since 1.0
     */
    private CellAppearance now;
    /**
     * ѕрокручиваема€ €чейка. (принадлежаща€ соседнему мес€цу)
     * @since 1.0
     */
    private CellAppearance scroll;
    /**
     * ячейка заголовок (выводит день недели).
     * @since 1.0
     */
    private CellAppearance caption;
    /**
     * Ќедоступна€ €чейка.
     * @since 1.0
     */
    private CellAppearance disabled;
    private boolean editable;
    private boolean supportsTransparency;
    private BackRenderer renderer;
    
    private String name;
    
    /**
     * Default constructor, does not make ready to use object.<br>
     *  онструктор по умолчанию не создает объекта, готового к использованию.
     * @since 1.0
     */
    public ViewAppearance() {
        setRenderer(null);
        setSupportsTransparency(false);
        changeSupport = new PropertyChangeSupport(this);
    }
    
    /**
     * Constructs editable calendar view.<br> 
     * —оздает профиль внешнего вида календар€.
     * —о свойством editable = true;
     * @see ViewAppearance#ViewAppearance(String, CellAppearance, CellAppearance,
     * CellAppearance, CellAppearance, CellAppearance, CellAppearance,
     * BackRenderer, boolean, boolean)
     * @since 1.0
     */
    public ViewAppearance(String name, CellAppearance usual,
            CellAppearance selected, CellAppearance now, CellAppearance scroll,
            CellAppearance caption, CellAppearance disabled,
            BackRenderer renderer, boolean canBeTransparent) {
        this(name, usual, selected, now, scroll, caption, disabled,
                renderer, canBeTransparent, true);
    }
    
    /**
     * Constructs calendar view.<br> 
     * —оздает профиль внешнего вида календар€.
     * @param name View name.<br>
     * »м€ профил€.
     * @param usual Render parameters for <b>usual</b> cells.<br>
     * ѕараметры отображени€ обычных €чеек.
     * @param selected Render parameters for <b>selected</b> cells.<br>
     * ѕараметры отображени€ выбранных €чеек.
     * @param now Render parameters for <b>now</b> cells.<br>
     * ѕараметры отображени€ текущих €чеек.
     * @param scroll Render parameters for <b>scroll</b> cells.<br>
     * ѕараметры отображени€ прокручиваемых €чеек
     * (принадлежащих соседнему мес€цу).
     * @param caption Render parameters for <b>caption</b> cells.<br>
     * ѕараметры отображени€ €чеек заголовка (с дн€ми недели).
     * @param disabled Render parameters for <b>disabled</b> cells.<br>
     * ѕараметры отображени€ недоступных €чеек.
     * @param renderer Cell renderer.<br>
     * –исовальщик €чеек.
     * @param canBeTransparent Does this view allow transparency.<br>
     * ƒопускает ли профиль настройки прозрачности €чеек.
     * @param editable Is view editable.<br>
     * ƒопускает ли профиль визуальное редактирование.
     * @since 1.0
     */
    public ViewAppearance(String name, CellAppearance usual,
            CellAppearance selected, CellAppearance now, CellAppearance scroll,
            CellAppearance caption, CellAppearance disabled, BackRenderer renderer,
            boolean canBeTransparent, boolean editable) {
        this();
        this.name = name;
        this.usual = usual;
        this.selected = selected;
        this.now = now;
        this.scroll = scroll;
        this.renderer = renderer;
        scroll.setSelectable(false);
        this.caption = caption;
        caption.setSelectable(false);
        this.disabled = disabled;
        setEditable(editable);
        setSupportsTransparency(canBeTransparent);
    }
    
    /**
     * @since 1.0
     */
    public CellAppearance getUsual() {
        return usual;
    }
    
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        changeSupport = new PropertyChangeSupport(this);
    }
    
    /**
     * @since 1.0
     */
    public void setUsual(CellAppearance usual) {
        CellAppearance oldValue = this.usual;
        this.usual = usual;
        changeSupport.firePropertyChange("usual", oldValue, usual);
    }
    
    /**
     * @since 1.0
     */
    public CellAppearance getSelected() {
        return selected;
    }
    
    /**
     * @since 1.0
     */
    public void setSelected(CellAppearance selected) {
        CellAppearance oldValue = this.selected;
        this.selected = selected;
        changeSupport.firePropertyChange("selected", oldValue, selected);
    }
    
    /**
     * @since 1.0
     */
    public CellAppearance getNow() {
        return now;
    }
    
    /**
     * @since 1.0
     */
    public void setNow(CellAppearance now) {
        CellAppearance oldValue = this.now;
        this.now = now;
        changeSupport.firePropertyChange("now", oldValue, now);
    }
    
    /**
     * @since 1.0
     */
    public CellAppearance getScroll() {
        return scroll;
    }
    
    /**
     * @since 1.0
     */
    public void setScroll(CellAppearance scroll) {
        CellAppearance oldValue = this.scroll;
        scroll.setSelectable(false);
        this.scroll = scroll;
        changeSupport.firePropertyChange("scroll", oldValue, scroll);
    }
    
    /**
     * Get view name.<br>
     * ¬озвращает им€ профил€.
     * @since 1.0
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set view name.<br>
     * ”станавливает им€ профил€.
     * @since 1.0
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @since 1.0
     */
    public CellAppearance getCaption() {
        return caption;
    }
    
    /**
     * @since 1.0
     */
    public void setCaption(CellAppearance caption) {
        CellAppearance oldValue = this.caption;
        caption.setSelectable(false);
        this.caption = caption;
        changeSupport.firePropertyChange("caption", oldValue, caption);
    }
    
    /**
     * @since 1.0
     */
    public CellAppearance getDisabled() {
        return disabled;
    }
    
    /**
     * @since 1.0
     */
    public void setDisabled(CellAppearance disabled) {
        CellAppearance oldValue = this.disabled;
        this.disabled = disabled;
        changeSupport.firePropertyChange("disabled", oldValue, disabled);
    }
    
    public Object clone() {
        ViewAppearance newInst = new ViewAppearance(getName(),
                (CellAppearance) getUsual().clone(),
                (CellAppearance) getSelected().clone(),
                (CellAppearance) getNow().clone(),
                (CellAppearance) getScroll().clone(),
                (CellAppearance) getCaption().clone(),
                (CellAppearance) getDisabled().clone(),
                getRenderer(),
                isSupportsTransparency(),
                isEditable());
        PropertyChangeListener[] listeners = changeSupport.getPropertyChangeListeners();
        for (PropertyChangeListener theListener : listeners) {
            newInst.addPropertyChangeListener(theListener);
        }
        return newInst;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
    /**
     * Is view editable.<br>
     * явл€етс€ ли профиль редактируемым.
     * @since 1.0
     */
    public boolean isEditable() {
        return editable;
    }
    
    /**
     * Set view editability.<br>
     * ”станавливает редактируемость €чейки.
     * @since 1.0
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    /**
     * Get cell renderer.<br>
     * ¬озвращает рисовальщик €чеек.
     * @since 1.0
     */
    public BackRenderer getRenderer() {
        return renderer;
    }
    
    /**
     * Set cell renderer.<br>
     * ”станавливает рисовальщик €чеек.
     * @see CellRenderer
     * @since 1.0
     */
    public void setRenderer(BackRenderer renderer) {
        this.renderer = renderer;
    }
    
    /**
     * Does view support transparency.<br>
     * ѕоддерживает ли €чейка настройки прозрачности.
     * @since 1.0
     */
    public boolean isSupportsTransparency() {
        return supportsTransparency;
    }
    
    /**
     * Set transparency support.<br>
     * ”станавливает дл€ профил€ настройки прозрачности.
     * @since 1.0
     */
    public void setSupportsTransparency(boolean supportsTransparency) {
        this.supportsTransparency = supportsTransparency;
    }
}

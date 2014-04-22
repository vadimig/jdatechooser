package datechooser.autorun;

import datechooser.beans.DateChooserBean;
import datechooser.beans.customizer.DateChooserCustomizer;
import datechooser.beans.customizer.PropertyDescriptorsHolder;

import java.beans.*;
import java.io.*;
import javax.swing.*;

/**
 * Displays customizer/component pair.
 * Provides save/load properties functions and links component and customizer.
 * 
 * @author Androsov Vadim
 * @since 1.0
 */
public abstract class ConfigBean extends JPanel implements PropertyChangeListener {
    
    /**
     * Successfull operation result.
     * @since 1.0
     */
    public static final String OK = PropertyDescriptorsHolder.OK;
    
    /**
     * Customized bean .
     */
    private DateChooserBean bean;
    /**
     * Bean customizer.
     */
    private DateChooserCustomizer customizer;
    /**
     * File containing bean config.
     */
    private File file;
    /**
     * All changes saved flag.
     */
    private boolean saved;
    
    protected ConfigBean(DateChooserBean bean, DateChooserCustomizer customizer) {
        setFile(null);
        setBean(bean);
        setCustomizer(customizer);
        getCustomizer().setObject(getBean());
        getCustomizer().addPropertyChangeListener(this);
        setSaved(true);
        
        setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
    }
    
    /**
     * Describes component. <br>
     * @return Component's description.
     * @since 1.0
     */
    public String toString() {
        return getBeanInfo().getBeanDescriptor().getDisplayName();
    }
    
    /**
     * Handles "property changed" event in customizer.
     * Set "saved" flag = false and repaint component (if it is visual).
     * @param evt Event description.
     * @since 1.0
     */
    public void propertyChange(PropertyChangeEvent evt) {
        setSaved(false);
        if (getBean() instanceof  JComponent) {
            ((JComponent)getBean()).repaint();
        }
    }
    
    protected DateChooserBean getBean() {
        return bean;
    }
    
    private void setBean(DateChooserBean bean) {
        this.bean = bean;
    }
    
    protected DateChooserCustomizer getCustomizer() {
        return customizer;
    }
    
    private void setCustomizer(DateChooserCustomizer customizer) {
        this.customizer = customizer;
    }
    
    protected BeanInfo getBeanInfo() {
        return getCustomizer().getBeanInfo();
    }
    
    /**
     * Short component's description. 
     * @return Component's description.
     * @since 1.0
     */
    public String getBeanDisplayName() {
        try {
            return getBeanInfo().getBeanDescriptor().getDisplayName();
        } catch (NullPointerException ex) {
            return "?";
        }
    }
    
    /**
     * Save customized properties to file.
     * @param file File to write in.
     * @return OK is operation successfull, error description otherwise.
     * @since 1.0
     */
    public String writeToFile(File file) {
        setFile(file);
        setSaved(true);
        return getCustomizer().getHolder().writeToFile(file);
    }
    
    /**
     * Reads properies form file.
     * @param file Properies file.
     * @return OK if operation successfull.
     * @since 1.0
     */
    public String readFromFile(File file) {
        setFile(file);
        setSaved(true);
        return getCustomizer().getHolder().readFromFile(file);
    }
    
    /**
     * File extension for corresponding component properties.
     * @return Extension.
     * @since 1.0
     */
    public abstract String getFileExt();
    
    /**
     * Saved properties file.
     * @return Properties file, null if there component was never saved.
     * @since 1.0
     */
    public File getFile() {
        return file;
    }
    
    /**
     * Set save file.
     * @param file Properties file.
     * @since 1.0
     */
    public void setFile(File file) {
        this.file = file;
    }
    
    /**
     * Get "all properties saved" flag.
     * @return true if there is no unsaved properties.
     * @since 1.0
     */
    public boolean isSaved() {
        return saved;
    }
    
    /**
     * Sets "saved" flag. <br>
     * @param saved "saved" flag.
     * @since 1.0
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}

/*
 * ClassDescriptor.java
 *
 * Created on 3 Август 2006 г., 15:58
 *
 */

package datechooser.beans.editor.descriptor;

import java.util.Locale;
import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;

/**
 * Abstract object descriptor class.
 * You must extend it to register own class in DescriptionManager.
 * 
 * @author Androsov Vadim
 * @see DescriptionManager
 * @since 1.0
 */
public abstract class ClassDescriptor {
    
    protected static final String ONE_LINE_SEPARATOR = ", ";
    protected static final String NEW_LINE_SEPARATOR = ",\n";
    
    private static boolean newLineParameters = true;
    
    /**
     * Class of descripting object.
     *
     */
    public abstract Class getDescriptedClass();
    
    /**
     * Java initialization code.
     *
     */
    public abstract String getJavaDescription(Object value);
    
    /**
     * Get object description using specified locale.
     *
     * @see ClassDescriptor#getDescription(Object, Locale)
     */
    public abstract String getDescription(Object value);
    
    /**
     * Describes object for output.
     *
     * @param value Descripting object.
     * @param locale Localization.
     *
     * @return Object description.
     *
     */
    public String getDescription(Object value, Locale locale) {
        return getDescription(value) + " (" + 
                getEditorLocaleString("default_locale_used") + ")";
    }
    
    /**
     * Returns full class name.
     */
    protected String getClassName() {
        return getDescriptedClass().getName();
    }
    
    /**
     * Returns parameter's delimiter: comma or comma with new line.
     * 
     */
    protected String getSeparator() {
        return isNewLineParameters() ? NEW_LINE_SEPARATOR : ONE_LINE_SEPARATOR;
    }
    
    /**
     * Fit parameters list in one line.
     * Used for java code generation - if code is too long, it is more readable
     * if each parameter starts from new line.
     * 
     */
    public static boolean isNewLineParameters() {
        return newLineParameters;
    }
    
    /**
     * @see ClassDescriptor#isNewLineParameters()
     */
    public static void setNewLineParameters(boolean aNewLineParameters) {
        newLineParameters = aNewLineParameters;
    }
    
    /**
     * Can class process <b>null</b> values.
     * 
     */
    public boolean canProcessNull() {
        return false;
    }
}

/*
 * IntegerDescriptor.java
 *
 * Created on 18 Август 2006 г., 14:29
 *
 */

package datechooser.beans.editor.descriptor;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see java.lang.Integer
 * @author Androsov Vadim
 * @since 1.0
 */
public class IntegerDescriptor extends ClassDescriptor {
    
    public Class getDescriptedClass() {
        return Integer.class;
    }

    public String getJavaDescription(Object value) {
        return value != null ? ((Integer) value).intValue() + "" : "0";
    }

    public String getDescription(Object value) {
        return value != null ? ((Integer) value).intValue() + "" : "0";
    }

    public boolean canProcessNull() {
        return true;
    }
    
}

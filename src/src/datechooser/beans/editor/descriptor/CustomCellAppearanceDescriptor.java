/*
 * AppearDescriptor.java
 *
 * Created on 5 Август 2006 г., 15:28
 *
 */

package datechooser.beans.editor.descriptor;

import datechooser.view.appearance.custom.CustomCellAppearance;
import java.awt.*;
import javax.swing.border.Border;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see datechooser.view.appearance.custom.CustomCellAppearance
 * @author Androsov Vadim
 * @since 1.0
 */
public class CustomCellAppearanceDescriptor extends ClassDescriptor {
    
    public Class getDescriptedClass() {
        return CustomCellAppearance.class;
    }
    
    public String getJavaDescription(Object value) {
        StringBuffer buf = new StringBuffer();
        buf.append("new " + getClassName() + "(");
        CustomCellAppearance appears = (CustomCellAppearance) value;
        buf.append(DescriptionManager.describeJava(appears.getBackgroundColor(), Color.class));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(appears.getTextColor(), Color.class));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(appears.getCellBorder(), Border.class));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(appears.getFont(), Font.class));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(appears.getCursorColor(), Color.class));
        buf.append(getSeparator());
        buf.append(appears.getTransparency());
        buf.append("f");
        buf.append(")");
        return buf.toString();
    }
    
    public String getDescription(Object value) {
        return getClassName();
    }
    
}

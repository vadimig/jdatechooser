/*
 * ViewDescriptor.java
 *
 * Created on 8 Август 2006 г., 6:54
 *
 */

package datechooser.beans.editor.descriptor;

import datechooser.view.BackRenderer;
import datechooser.view.appearance.custom.CustomCellAppearance;
import datechooser.view.appearance.ViewAppearance;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see datechooser.view.appearance.ViewAppearance
 * @author Androsov Vadim
 * @since 1.0
 */
public class ViewDescriptor extends ClassDescriptor {
    public Class getDescriptedClass() {
        return ViewAppearance.class;
    }

    public String getJavaDescription(Object value) {
        StringBuffer buf = new StringBuffer();
        ViewAppearance view = (ViewAppearance) value;
        buf.append("new " + getClassName() + '(');
        buf.append('"' + view.getName() + '"');
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(view.getUsual(), view.getUsual().getClass()));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(view.getSelected(), view.getSelected().getClass()));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(view.getNow(), view.getNow().getClass()));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(view.getScroll(), view.getScroll().getClass()));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(view.getCaption(), view.getCaption().getClass()));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(view.getDisabled(), view.getDisabled().getClass()));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(view.getRenderer(), BackRenderer.class));
        buf.append(getSeparator());
        buf.append(view.isSupportsTransparency() ? "true" : "false");
        buf.append(getSeparator());
        buf.append(view.isEditable() ? "true" : "false");
        buf.append(")");
        return buf.toString();
    }

    public String getDescription(Object value) {
        return ((ViewAppearance) value).getName();
    }
}

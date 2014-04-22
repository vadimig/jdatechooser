/*
 * BackRendererDescriptor.java
 *
 * Created on 30 Ноябрь 2006 г., 22:09
 *
 */

package datechooser.beans.editor.descriptor;

import datechooser.view.BackRenderer;
import java.net.URL;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see datechooser.view.BackRenderer
 * @author Androsov Vadim
 * @since 1.0
 */
public class BackRendererDescriptor extends ClassDescriptor {
    
    public Class getDescriptedClass() {
        return BackRenderer.class;
    }

    public String getJavaDescription(Object value) {
        BackRenderer renderer = (BackRenderer) value;
        StringBuffer descr = new StringBuffer("new ");
        descr.append(getClassName());
        descr.append("( " + renderer.getStyle());
        descr.append(getSeparator());
        descr.append('"');
        descr.append(renderer.getUrl().getFile());
        descr.append("\")");
        return descr.toString();
    }

    public String getDescription(Object value) {
        return ((BackRenderer) value).getUrl().getPath();
    }
    
}

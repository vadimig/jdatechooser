/*
 * BorderDescriptor.java
 *
 * Created on 3 ¿‚„ÛÒÚ 2006 „., 17:08
 *
 */

package datechooser.beans.editor.descriptor;

import static datechooser.beans.locale.LocaleUtils.getEditorLocaleString;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.border.*;

/**
 * @see DescriptionManager
 * @see ClassDescriptor
 * @see javax.swing.BorderFactory
 * @author Androsov Vadim
 * @since 1.0
 */
public class BorderDescriptor extends ClassDescriptor {
    private Border value;
    public Class getDescriptedClass() {
        return Border.class;
    }
    
    public String getJavaDescription(Object value) {
        this.value = (Border) value;
        StringBuffer buf = new StringBuffer();
        buf.append(BorderFactory.class.getName() + ".create" +
                getJavaName(value) + "Border(");
        if (value instanceof SoftBevelBorder) {
            buf.append(getBevelJava());
        } else
            if (value instanceof BevelBorder) {
            buf.append(getBevelJava());
            } else
                if (value instanceof CompoundBorder) {
            buf.append(getCompoundJava());
                } else
                    if (value instanceof MatteBorder) {
            buf.append(getMatteJava());
                    } else
                        if (value instanceof EmptyBorder) {
            buf.append(getEmptyJava());
                        } else
                            if (value instanceof EtchedBorder) {
            buf.append(getEtchedJava());
                            } else
                                if (value instanceof LineBorder) {
            buf.append(getLineJava());
                                } else
                                    if (value instanceof TitledBorder) {
            buf.append(getTitledJava());
                                    }
        buf.append(")");
        return buf.toString();
    }
    
    public String getDescription(Object value) {
        if (value instanceof SoftBevelBorder) {
            return getEditorLocaleString("SoftBevel");
        }
        if (value instanceof BevelBorder) {
            return getEditorLocaleString("Bevel");
        }
        if (value instanceof CompoundBorder) {
            return getEditorLocaleString("Compound");
        }
        if (value instanceof MatteBorder) {
            return getEditorLocaleString("Matte");
        }
        if (value instanceof EmptyBorder) {
            return getEditorLocaleString("Empty");
        }
        if (value instanceof EtchedBorder) {
            return getEditorLocaleString("Etched");
        }
        if (value instanceof LineBorder) {
            return getEditorLocaleString("Line");
        }
        if (value instanceof TitledBorder) {
            return getEditorLocaleString("Titled");
        }
        return "?";
    }
    
    public String getJavaName(Object value) {
        if (value instanceof SoftBevelBorder) {
            return "SoftBevel";
        }
        if (value instanceof BevelBorder) {
            return "Bevel";
        }
        if (value instanceof CompoundBorder) {
            return "Compound";
        }
        if (value instanceof MatteBorder) {
            return "Matte";
        }
        if (value instanceof EmptyBorder) {
            return "Empty";
        }
        if (value instanceof EtchedBorder) {
            return "Etched";
        }
        if (value instanceof LineBorder) {
            return "Line";
        }
        if (value instanceof TitledBorder) {
            return "Titled";
        }
        return getClassName();
    }
    
    private String getBevelJava() {
        BevelBorder bevelValue = (BevelBorder) value;
        StringBuffer buf = new StringBuffer();
        buf.append(BevelBorder.class.getName() +
                (bevelValue.getBevelType() == BevelBorder.LOWERED ?
                    ".LOWERED" : ".RAISED"));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(bevelValue.getHighlightOuterColor(), Color.class));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(bevelValue.getHighlightInnerColor(), Color.class));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(bevelValue.getShadowOuterColor(), Color.class));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(bevelValue.getShadowInnerColor(), Color.class));
        return buf.toString();
    }
    
    private String getCompoundJava() {
        CompoundBorder compoundValue = (CompoundBorder) value;
        StringBuffer buf = new StringBuffer();
        Border out  = compoundValue.getOutsideBorder();
        if (out != null) {
            buf.append(DescriptionManager.describeJava(out, out.getClass()));
        } else {
            buf.append("null");
        }
        buf.append(getSeparator());
        Border inside = compoundValue.getInsideBorder();
        if (inside != null) {
            buf.append(DescriptionManager.describeJava(inside, inside.getClass()));
        } else {
            buf.append("null");
        }
        return buf.toString();
    }
    
    private String getInsetsJava(Insets insets) {
        return "new " + Insets.class.getName() + "(" +
                insets.top + ONE_LINE_SEPARATOR + insets.left +
                ONE_LINE_SEPARATOR + insets.bottom + ONE_LINE_SEPARATOR +
                insets.right + ")";
    }
    
    private String getEmptyJava() {
        EmptyBorder emptyValue = (EmptyBorder) value;
        Insets insets = emptyValue.getBorderInsets();
        return insets.top + ONE_LINE_SEPARATOR + insets.left + ONE_LINE_SEPARATOR +
                insets.bottom + ONE_LINE_SEPARATOR + insets.right;
    }
    
    private String getEtchedJava() {
        EtchedBorder etchedValue = (EtchedBorder) value;
        StringBuffer buf = new StringBuffer();
        buf.append(EtchedBorder.class.getName() +
                (etchedValue.getEtchType() == EtchedBorder.LOWERED ?
                    ".LOWERED" : ".RAISED"));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(etchedValue.getHighlightColor(), Color.class));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(etchedValue.getShadowColor(), Color.class));
        return buf.toString();
    }
    
    private String getLineJava() {
        LineBorder lineValue = (LineBorder) value;
        StringBuffer buf = new StringBuffer();
        buf.append(DescriptionManager.describeJava(lineValue.getLineColor(), Color.class));
        buf.append(ONE_LINE_SEPARATOR);
        buf.append(lineValue.getThickness());
        return buf.toString();
    }
    
    private String getMatteJava() {
        MatteBorder matteValue = (MatteBorder) value;
        StringBuffer buf = new StringBuffer();
        buf.append(getInsetsJava(matteValue.getBorderInsets()));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(matteValue.getMatteColor(), Color.class));
        return buf.toString();
    }
    
    private String getTitledJava() {
        TitledBorder titledValue = (TitledBorder) value;
        String className = TitledBorder.class.getName();
        StringBuffer buf = new StringBuffer();
        buf.append(DescriptionManager.describeJava(titledValue.getBorder(), Border.class));
        buf.append(getSeparator());
        buf.append('"' + titledValue.getTitle() + '"');
        buf.append(getSeparator());
        switch (titledValue.getTitleJustification()) {
            case TitledBorder.LEFT:
                buf.append(className + ".LEFT");
                break;
            case TitledBorder.CENTER:
                buf.append(className + ".CENTER");
                break;
            case TitledBorder.RIGHT:
                buf.append(className + ".RIGHT");
                break;
            case TitledBorder.LEADING:
                buf.append(className + ".LEADING");
                break;
            case TitledBorder.TRAILING:
                buf.append(className + ".TRAILING");
                break;
            default:
                buf.append(className + ".DEFAULT_JUSTIFICATION");
        }
        buf.append(getSeparator());
        switch (titledValue.getTitlePosition()) {
            case TitledBorder.ABOVE_TOP:
                buf.append(className + ".ABOVE_TOP");
                break;
            case TitledBorder.TOP:
                buf.append(className + ".TOP");
                break;
            case TitledBorder.BELOW_TOP:
                buf.append(className + ".BELOW_TOP");
                break;
            case TitledBorder.ABOVE_BOTTOM:
                buf.append(className + ".ABOVE_BOTTOM");
                break;
            case TitledBorder.BOTTOM:
                buf.append(className + ".BOTTOM");
                break;
            case TitledBorder.BELOW_BOTTOM:
                buf.append(className + ".BELOW_BOTTOM");
                break;
            default:
                buf.append(className + ".DEFAULT_POSITION ");
                
        }
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(titledValue.getTitleFont(), Font.class));
        buf.append(getSeparator());
        buf.append(DescriptionManager.describeJava(titledValue.getTitleColor(), Color.class));
        return buf.toString();
    }
}

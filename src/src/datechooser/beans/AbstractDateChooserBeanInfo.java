package datechooser.beans;

import datechooser.beans.editor.SimpleColorEditor;
import datechooser.beans.editor.WeekDaysStyleEditor;
import datechooser.beans.editor.dimension.SimpleDimensionEditor;
import static datechooser.beans.locale.LocaleUtils.getCalendarLocaleString;
import datechooser.beans.editor.ModelBehaviorEditor;
import datechooser.beans.editor.NavigatePaneEditor;
import datechooser.beans.editor.appear.AppearEditor;
import datechooser.beans.editor.dates.DateEditor;
import datechooser.beans.editor.font.SimpleFontEditor;
import datechooser.beans.editor.dates.PeriodsEditor;
import datechooser.beans.editor.locale.LocaleEditor;
import datechooser.events.CommitListener;
import datechooser.events.CursorMoveListener;
import datechooser.events.SelectionChangedListener;

import java.awt.*;
import java.beans.*;
import java.util.*;

/**
 * BeanInfo class for common properties of all components.
 * @author Androsov Vadim
 * @since 1.0
 */
public abstract class AbstractDateChooserBeanInfo extends SimpleBeanInfo {
    
    /**
     * Default constructor.
     * @since 1.0
     */
    public AbstractDateChooserBeanInfo() {
        
    }
    
    /**
     * <b>Creates</b> array of common events descriptors.
     * @return Array of EventSetDescriptor.
     * @since 1.0
     */
    public EventSetDescriptor[] getEventSetDescriptors() {
        try {
            EventSetDescriptor commit = new EventSetDescriptor(getBeanClass(),
                    "commit", CommitListener.class, "onCommit");
            commit.setDisplayName(getCalendarLocaleString("commit"));
            commit.setShortDescription(getCalendarLocaleString("commit_descript"));
            
            EventSetDescriptor selChanged = new EventSetDescriptor(getBeanClass(),
                    "selectionChanged", SelectionChangedListener.class, "onSelectionChange");
            selChanged.setDisplayName(getCalendarLocaleString("selChange"));
            selChanged.setShortDescription(getCalendarLocaleString("selChange_descript"));
            
            EventSetDescriptor cursorMoved = new EventSetDescriptor(getBeanClass(),
                    "cursorMove", CursorMoveListener.class, "onCursorMove");
            cursorMoved.setDisplayName(getCalendarLocaleString("onCursorMove"));
            cursorMoved.setShortDescription(getCalendarLocaleString("onCursorMove_descript"));
            
            return new EventSetDescriptor[] {commit, selChanged, cursorMoved};
        } catch (IntrospectionException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    /**
     * <b>Creates</b> array of common properties descriptors.
     * @return Array of PropertyDescriptor.
     * @since 1.0
     */
    public PropertyDescriptor[] getPropertyDescriptors() {
        ArrayList<PropertyDescriptor> descriptors = new ArrayList<PropertyDescriptor>();
        try {
            PropertyDescriptor defDate = new PropertyDescriptor( DateChooserBean.PROPERTY_DEFAULT_DATES,
                    getBeanClass(), "getDefaultPeriods", "setDefaultPeriods" );
            defDate.setPropertyEditorClass(PeriodsEditor.class);
            defDate.setDisplayName(getCalendarLocaleString("Default"));
            defDate.setShortDescription(getCalendarLocaleString("Default_descript"));
            descriptors.add(defDate);
            
            PropertyDescriptor calSize = new PropertyDescriptor( DateChooserDialog.PROPERTY_CALENDAR_SIZE,
                    getBeanClass(), "getCalendarPreferredSize", "setCalendarPreferredSize" );
            calSize.setPropertyEditorClass(SimpleDimensionEditor.class);
            calSize.setDisplayName(getCalendarLocaleString("Calend_size"));
            calSize.setShortDescription(getCalendarLocaleString("Calend_size_descript"));
            descriptors.add(calSize);
            
            PropertyDescriptor forbidDate = new PropertyDescriptor( DateChooserBean.PROPERTY_FORBID_DATES,
                    getBeanClass(), "getForbiddenPeriods", "setForbiddenPeriods" );
            forbidDate.setPropertyEditorClass(PeriodsEditor.class);
            forbidDate.setDisplayName(getCalendarLocaleString("Forbidden"));
            forbidDate.setShortDescription(getCalendarLocaleString("Forbidden_descript"));
            descriptors.add(forbidDate);
            
            PropertyDescriptor maxDate = new PropertyDescriptor( DateChooserBean.PROPERTY_MAX_DATE,
                    getBeanClass(), "getMaxDate", "setMaxDate" );
            maxDate.setPropertyEditorClass(DateEditor.class);
            maxDate.setDisplayName(getCalendarLocaleString("Maximal_date"));
            maxDate.setShortDescription(getCalendarLocaleString("Maximal_date_descript"));
            descriptors.add(maxDate);
            
            PropertyDescriptor minDate = new PropertyDescriptor( DateChooserBean.PROPERTY_MIN_DATE,
                    getBeanClass(), "getMinDate", "setMinDate" );
            minDate.setPropertyEditorClass(DateEditor.class);
            minDate.setDisplayName(getCalendarLocaleString("Minimal_date"));
            minDate.setShortDescription(getCalendarLocaleString("Minimal_date_descript"));
            descriptors.add(minDate);
            
            PropertyDescriptor autoScroll = new PropertyDescriptor( DateChooserBean.PROPERTY_AUTOSCROLL,
                    getBeanClass(), "isAutoScroll", "setAutoScroll" );
            autoScroll.setDisplayName(getCalendarLocaleString("Auto_scroll"));
            autoScroll.setShortDescription(getCalendarLocaleString("Auto_scroll_descript"));
            descriptors.add(autoScroll);
            
            PropertyDescriptor oneMonth = new PropertyDescriptor( DateChooserBean.PROPERTY_ONE_MONTH,
                    getBeanClass(), "isShowOneMonth", "setShowOneMonth" );
            oneMonth.setDisplayName(getCalendarLocaleString("Show_one_month"));
            oneMonth.setShortDescription(getCalendarLocaleString("Show_one_month_descript"));
            descriptors.add(oneMonth);
            
            PropertyDescriptor enabled = new PropertyDescriptor( DateChooserBean.PROPERTY_ENABLED,
                    getBeanClass(), "isEnabled", "setEnabled");
            enabled.setDisplayName(getCalendarLocaleString("Enabled"));
            enabled.setShortDescription(getCalendarLocaleString("Enabled_descript"));
            descriptors.add(enabled);
            
            PropertyDescriptor locked = new PropertyDescriptor( DateChooserBean.PROPERTY_LOCKED,
                    getBeanClass(), "isLocked", "setLocked");
            locked.setDisplayName(getCalendarLocaleString("Locked"));
            locked.setShortDescription(getCalendarLocaleString("Locked_descript"));
            descriptors.add(locked);
            
            PropertyDescriptor nothingAllowed = new PropertyDescriptor( DateChooserBean.PROPERTY_NOTHING_ALLOWED,
                    getBeanClass(), "isNothingAllowed", "setNothingAllowed");
            nothingAllowed.setDisplayName(getCalendarLocaleString("NothingAllowed"));
            nothingAllowed.setShortDescription(getCalendarLocaleString("NothingAllowed_descript"));
            descriptors.add(nothingAllowed);
            
            PropertyDescriptor navFont = new PropertyDescriptor( DateChooserBean.PROPERTY_NAVIG_FONT,
                    getBeanClass(), "getNavigateFont", "setNavigateFont");
            navFont.setPropertyEditorClass(SimpleFontEditor.class);
            navFont.setDisplayName(getCalendarLocaleString("Navig_font"));
            navFont.setShortDescription(getCalendarLocaleString("Navig_font_descript"));
            descriptors.add(navFont);
            
            PropertyDescriptor behavior = new PropertyDescriptor( DateChooserBean.PROPERTY_BEHAVIOR,
                    getBeanClass(), "getBehavior", "setBehavior");
            behavior.setPropertyEditorClass(ModelBehaviorEditor.class);
            behavior.setDisplayName(getCalendarLocaleString("Behavior"));
            behavior.setShortDescription(getCalendarLocaleString("Behavior_descript"));
            descriptors.add(behavior);
            
            PropertyDescriptor weekDayStyle = new PropertyDescriptor( DateChooserBean.PROPERTY_WEEK_STYLE,
                    getBeanClass(), "getWeekStyle", "setWeekStyle");
            weekDayStyle.setPropertyEditorClass(WeekDaysStyleEditor.class);
            weekDayStyle.setDisplayName(getCalendarLocaleString("WeekDayStyle"));
            weekDayStyle.setShortDescription(getCalendarLocaleString("WeekDayStyle_descript"));
            descriptors.add(weekDayStyle);
            
            PropertyDescriptor view = new PropertyDescriptor( DateChooserBean.PROPERTY_VIEW,
                    getBeanClass(), "getCurrentView", "setCurrentView");
            view.setPropertyEditorClass(AppearEditor.class);
            view.setDisplayName(getCalendarLocaleString("View"));
            view.setShortDescription(getCalendarLocaleString("View_descript"));
            descriptors.add(view);
            
            PropertyDescriptor locale = new PropertyDescriptor( DateChooserBean.PROPERTY_LOCALE,
                    getBeanClass(), "getLocale", "setLocale");
            locale.setPropertyEditorClass(LocaleEditor.class);
            locale.setDisplayName(getCalendarLocaleString("Locale"));
            locale.setShortDescription(getCalendarLocaleString("Locale_descript"));
            descriptors.add(locale);
            
            PropertyDescriptor navigPane = new PropertyDescriptor( DateChooserBean.PROPERTY_NAVIG_PANE,
                    getBeanClass(), "getCurrentNavigateIndex", "setCurrentNavigateIndex");
            navigPane.setPropertyEditorClass(NavigatePaneEditor.class);
            navigPane.setDisplayName(getCalendarLocaleString("NavigatePane"));
            navigPane.setShortDescription(getCalendarLocaleString("NavigatePane_descript"));
            descriptors.add(navigPane);
            
            PropertyDescriptor backColor = new PropertyDescriptor( DateChooserBean.PROPERTY_BACK_COLOR,
                    getBeanClass(), "getCalendarBackground", "setCalendarBackground");
            backColor.setPropertyEditorClass(SimpleColorEditor.class);
            backColor.setDisplayName(getCalendarLocaleString("BackColor"));
            backColor.setShortDescription(getCalendarLocaleString("BackColor_descript"));
            descriptors.add(backColor);
            
            descriptors.addAll(getAdditionalDescriptors());
            
            return descriptors.toArray(new PropertyDescriptor[descriptors.size()]);
                    /*new PropertyDescriptor[] {defDate, prefSize, behavior,
            autoScroll, oneMonth, border, enabled, navFont,
            view, forbidDate, maxDate, minDate, locale, navigPane};*/
            
        } catch(IntrospectionException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    /**
     * Default propery index.
     * @return Default propery index.
     * @since 1.0
     */
    public int getDefaultPropertyIndex() {
        return 1;
    }
    
    /**
     * Describes components.
     * @return Bean description.
     * @since 1.0
     */
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor bd = new BeanDescriptor(getBeanClass(), getCustomizerClass());
        bd.setDisplayName(getDisplayName());
        return bd;
    }
    
    /**
     * Bean icon.
      * @param iconKind Icon properties.
     * SimpleBeanInfo.ICON_COLOR_32x32, SimpleBeanInfo.ICON_COLOR_16x16,
     * SimpleBeanInfo.ICON_MONO_32x32, SimpleBeanInfo.ICON_MONO_16x16.
     * @return Bean icon.
     */
    public Image getIcon(int iconKind) {
        String name = "";
        switch (iconKind) {
            case ICON_COLOR_32x32:
                name = "col32";
                break;
            case ICON_COLOR_16x16:
                name = "col16";
                break;
            case ICON_MONO_32x32:
                name = "bw32";
                break;
            case ICON_MONO_16x16:
                name = "bw16";
                break;
        }
        return loadImage("/datechooser/beans/pic/" + getPicturePrefix() + "_" + name + ".gif");
    }

    protected abstract String getDisplayName();

    protected abstract String getPicturePrefix();

    protected abstract Class getBeanClass();

    protected abstract ArrayList<PropertyDescriptor> getAdditionalDescriptors() throws IntrospectionException;

    protected abstract Class getCustomizerClass();
}

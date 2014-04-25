JDateChooser
============

DateChooser is a JavaBeans library with swing components for date selection. It contains 3 datepicker beans: the panel, combo editor and the dialog window. All components support visual property customization without any IDE

<ul>
<li><b>Date manipulations</b>. You can use components to select one date, the period or several periods. It is possible to limit selection (for example you can allow only one date or period selection). Additional bounding properies: minimal and maximal dates, forbidden dates or periods.
<li><b>Controls</b>. Use arrows to move day cursor, PgUp/PgDown to change months, Home/End for year selection. To select several dates you can use mouse drag or click on cells with Ctrl and-or Shift buttons pressed. Space key can be used for cell selection too. With Alt button pressed you can use mouse click to move cursor to the need position without date selection.
<li><b>Appearance customization</b>. Two types of appearance profiles: Swing and Custom. Swing has a small amount of parameters, and its main aim is natural look within standart swing interface.
<li><b>Table cell editors and renderers</b>. Use methods bean.createTableCellEditor and bean.createTableCellRenderer to convert existing bean into TableCellXXX
<li><b>Visual editors for all properties</b>. If IDE does not support visual components customization, it is possible to run library file (DateChooser.jar) and configue need components using visual editors with full preview. This changes could be saved to the file and then loaded using a special service class (PermanentBean)
<li><b>Universal customizer classes (DateCooserXXXCusomizer)</b>. They work only on the basis of the analysis the BeanInfo classes. So this customizers can be used for other beans.
<li><b>Localization</b>. The component supports all dates localizations presented in JDK. The user editors and properties descriptions are localized for two languages: Russian and English.
</ul>

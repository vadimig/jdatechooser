package datechooser.autorun;

import datechooser.beans.locale.LocaleUtils;
import static datechooser.beans.locale.LocaleUtils.getConfigLocaleString;
import datechooser.beans.pic.Pictures;

import datechooser.events.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 * Component's configuration window.
 * @author Androsov Vadim
 * @since 1.0
 */
public class ConfigWindow extends JFrame implements  CommitListener, SelectionChangedListener{
    
    private static final int WIDTH = 630;
    private static final int HEIGHT = 380;
    private static final String MENU_PROPERTY_ID = "MenuItemID";
    private static final String MENU_LOAD = "load";
    private static final String MENU_SAVE = "save";
    private static final String MENU_SAVE_AS = "save as";
    
    private JPanel mainPane;
    private ConfigBean[] configurators;
    private JLabel selectedBeanNameLabel;
    private int selected;
    private JTextArea output;
    private ButtonGroup selLookFeel;
    private String about;
    private ImageIcon aboutImage;
    private JFileChooser fileChooser;
    
    /**
     * Create window.
     * @throws java.beans.IntrospectionException If any CongigBean panel contains incompatible component and customizer.<br>
     * @since 1.0
     * @see datechooser.autorun.ConfigCombo
     * @see datechooser.autorun.ConfigDialog
     * @see datechooser.autorun.ConfigPanel
     */
    public ConfigWindow() throws IntrospectionException {
        LocaleUtils.prepareStandartDialogButtonText();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle(getConfigLocaleString("Caption"));
        mainPane = new JPanel(new BorderLayout(5, 2));
        configurators = new ConfigBean[] {
            new ConfigPanel(),
            new ConfigCombo(),
            new ConfigDialog()
        };
        selected = 0;
        setJMenuBar(createMenuBar());
        selectedBeanNameLabel = createBeanNameOutput();
        mainPane.add(selectedBeanNameLabel, BorderLayout.NORTH);
        mainPane.add(getCurrentConfigBean(), BorderLayout.CENTER);
        output = createOutput();
        mainPane.add(output, BorderLayout.SOUTH);
        
        setContentPane(mainPane);
        updateOutput();
        setCentered();
        synchronized(getTreeLock()) {
            validateTree();
        }
        registerAsListener();
        setAllSaved();
        
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new ComponentFileFilter());
        
        addWindowListener(new OnWindowClose());
    }
    
    private void setAllSaved() {
        for (ConfigBean configBean : configurators) {
            configBean.setSaved(true);
        }
    }
    
    private JLabel createBeanNameOutput() {
        JLabel selectedBean = new JLabel(getCurrentConfigBean().getBeanDisplayName());
        selectedBean.setHorizontalAlignment(JLabel.CENTER);
        selectedBean.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return selectedBean;
    }
    
    private JTextArea createOutput() {
        JTextArea output = new JTextArea("");
        output.setRows(2);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        output.setOpaque(false);
        output.setEditable(false);
        return output;
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        menuBar.add(createMenuFile());
        menuBar.add(createMenuBean());
        menuBar.add(createMenuLF());
        menuBar.add(createMenuHelp());
        
        return menuBar;
    }
    
    private JFrame getFrame() {
        return this;
    }
    
    private JMenu createMenuHelp() {
        
        JMenu menuHelp = new JMenu(getConfigLocaleString("Help"));
        JMenuItem menuAbout = new JMenuItem(getConfigLocaleString("About"));
        
        StringBuffer aboutString = new StringBuffer("<html>");
        aboutString.append(getConfigLocaleString("ProjectName"));
        aboutString.append("<br><br>(c) ");
        aboutString.append(getConfigLocaleString("Author"));
        aboutString.append(", 2014<br>");
        aboutString.append("e-mail: <i>androsovvi@gmail.com</i>");
        about = aboutString.toString();
        
        aboutImage = new ImageIcon(Pictures.getResource("logo_small.gif"));
        
        menuAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(getFrame(), about,
                        getConfigLocaleString("About"),
                        JOptionPane.INFORMATION_MESSAGE,
                        aboutImage);
            }
        });
        menuHelp.add(menuAbout);
        return menuHelp;
    }
    
    private JMenu createMenuLF() {
        JMenu menuLF = new JMenu(getConfigLocaleString("LookFeel"));
        UIManager.LookAndFeelInfo[] lf = UIManager.getInstalledLookAndFeels();
        selLookFeel = new ButtonGroup();
        LookAndFeel current = UIManager.getLookAndFeel();
        OnLookAndFeelChange onChange = new OnLookAndFeelChange();
        for (UIManager.LookAndFeelInfo lookAndFeel : lf) {
            String lfName = lookAndFeel.getName();
            JCheckBoxMenuItem miLF = new JCheckBoxMenuItem(lfName);
            miLF.putClientProperty(MENU_PROPERTY_ID, lookAndFeel.getClassName());
            miLF.addActionListener(onChange);
            if ((current != null) && (current.getName().equals(lfName))) {
                miLF.setSelected(true);
            }
            selLookFeel.add(miLF);
            menuLF.add(miLF);
        }
        return menuLF;
    }
    
    private JMenu createMenuFile() {
        OnStoreAction onStore = new OnStoreAction();
        JMenu menuFile = new JMenu(getConfigLocaleString("File"));
        JMenuItem menuLoad = new JMenuItem(getConfigLocaleString("Load"));
        menuLoad.putClientProperty(MENU_PROPERTY_ID, MENU_LOAD);
        menuLoad.addActionListener(onStore);
        menuFile.add(menuLoad);
        menuFile.addSeparator();
        JMenuItem menuSave = new JMenuItem(getConfigLocaleString("Save"));
        menuSave.putClientProperty(MENU_PROPERTY_ID, MENU_SAVE);
        menuSave.addActionListener(onStore);
        menuFile.add(menuSave);
        JMenuItem menuSaveAs = new JMenuItem(getConfigLocaleString("SaveAs"));
        menuSaveAs.putClientProperty(MENU_PROPERTY_ID, MENU_SAVE_AS);
        menuSaveAs.addActionListener(onStore);
        menuFile.add(menuSaveAs);
        menuFile.addSeparator();
        JMenuItem menuExit = new JMenuItem(getConfigLocaleString("Exit"));
        menuExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tryToExit();
            }
        });
        menuFile.add(menuExit);
        return menuFile;
    }
    
    private void tryToExit() {
        String unsaved = getUnsaved();
        if (!getUnsaved().equals("")) {
            int ans = JOptionPane.showConfirmDialog(getFrame(),
                    "<html> " + getConfigLocaleString("Exit_no_save") +
                    " <br><UL>" + unsaved + "</UL>",
                    getConfigLocaleString("Exit"), JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.NO_OPTION) return;
        }
        System.exit(0);
    }
    
    private String getUnsaved() {
        StringBuffer unsaved = new StringBuffer("");
        for (ConfigBean configurator : configurators) {
            if (!configurator.isSaved()) {
                unsaved.append("<LI>");
                unsaved.append(configurator.getBeanDisplayName());
            }
        }
        return unsaved.toString();
    }
    
    private JMenu createMenuBean() {
        JMenu menuBean = new JMenu(getConfigLocaleString("Bean"));
        OnBeanChange onBeanChange = new OnBeanChange();
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < configurators.length; i++) {
            String beanName = configurators[i].getBeanDisplayName();
            JCheckBoxMenuItem menuCurrentBean = new JCheckBoxMenuItem(beanName);
            menuCurrentBean.putClientProperty(MENU_PROPERTY_ID, new Integer(i));
            menuCurrentBean.addActionListener(onBeanChange);
            menuCurrentBean.setSelected(getSelected() == i);
            group.add(menuCurrentBean);
            menuBean.add(menuCurrentBean);
        }
        return menuBean;
    }
    
    private void registerAsListener() {
        for (ConfigBean beanConfigurator : configurators) {
            beanConfigurator.getBean().addCommitListener(this);
            beanConfigurator.getBean().addSelectionChangedListener(this);
        }
    }
    
    private int getSelected() {
        return selected;
    }
    
    private void updateOutput() {
        output.setText(getCurrentConfigBean().getBean().getSelectedPeriodSet().toString());
    }
    
    private ConfigBean getCurrentConfigBean() {
        return configurators[getSelected()];
    }
    
    private void setSelected(int selected) {
        if (selected == getSelected()) return;
        mainPane.remove(getCurrentConfigBean());
        this.selected = selected;
        mainPane.add(getCurrentConfigBean(), BorderLayout.CENTER);
        selectedBeanNameLabel.setText(getCurrentConfigBean().getBeanDisplayName());
        mainPane.validate();
        updateOutput();
    }
    
    private void setCentered() {
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        if ((getWidth() > screenSize.width) || (getHeight() > screenSize.height)) {
            setLocation(0, 0);
        } else {
            setLocation(
                    (screenSize.width - getWidth()) / 2,
                    (screenSize.height - getHeight()) / 2);
        }
        
    }
    
    /**
     * Handles "commit" component event (updates list of selected dates).
     * @param evt Event object.<br>
     * @since 1.0
     */
    public void onCommit(CommitEvent evt) {
        updateOutput();
    }
    
    /**
     * Handles "selection change" component event (updates list of selected dates).
     * @param evt Event object.<br>
     * @since 1.0
     */
    public void onSelectionChange(SelectionChangedEvent evt) {
        updateOutput();
    }
    
    private File getFileName(File start, String title, String approveName, boolean isOpen) {
        fileChooser.setDialogType(isOpen ? JFileChooser.OPEN_DIALOG : JFileChooser.SAVE_DIALOG);
        if (start != null) fileChooser.setSelectedFile(start);
        if (approveName != null) fileChooser.setApproveButtonText(approveName);
        fileChooser.setApproveButtonText(approveName);
        fileChooser.setDialogTitle(title);
        int result = fileChooser.showDialog(this, null);
        if (result != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        return fileChooser.getSelectedFile();
    }
    
    private class OnWindowClose extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            tryToExit();
        }
    }
    
    private class OnStoreAction implements ActionListener {
        
        public void actionPerformed(ActionEvent e) {
            String action = (String) ((JMenuItem)e.getSource()).getClientProperty(MENU_PROPERTY_ID);
            if (action.equals(MENU_LOAD)) {
                loadFromFile();
            }
            if (action.equals(MENU_SAVE)) {
                saveToFile(false);
            }
            if (action.equals(MENU_SAVE_AS)) {
                saveToFile(true);
            }
        }
        
        private void sayIfNotOK(String status) {
            if (status.equals(ConfigBean.OK)) return;
            StringBuffer mess = new StringBuffer("<html><i>");
            mess.append(getConfigLocaleString("OperationErrorMessage"));
            mess.append("</i><br><br>");
            mess.append(status);
            JOptionPane.showMessageDialog(getFrame(),
                    mess.toString(),
                    getConfigLocaleString("OperationError"),
                    JOptionPane.ERROR_MESSAGE);
        }
        
        private void loadFromFile() {
            ConfigBean configBean = getCurrentConfigBean();
            if (!configBean.isSaved()) {
                int result = JOptionPane.showConfirmDialog(getFrame(),
                        getConfigLocaleString("ConfigLost"),
                        getConfigLocaleString("Load"),
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.CANCEL_OPTION) return;
            }
            File file = getFileName(configBean.getFile(),
                    configBean.getBeanDisplayName(),
                    getConfigLocaleString("Load"), true);
            if (file == null) return;
            sayIfNotOK(configBean.readFromFile(file));
        }
        
        private void saveToFile(boolean saveAs) {
            ConfigBean configBean = getCurrentConfigBean();
            if ((!saveAs) && configBean.isSaved() &&(configBean.getFile() != null)) return;
            File file = configBean.getFile();
            if (saveAs || (file == null)) {
                if (file == null) file = new File(configBean.getBeanInfo().getBeanDescriptor().getName());
                file = getFileName(file,
                        configBean.getBeanDisplayName(),
                        getConfigLocaleString("Save"), false);
                if (file == null) return;
                if (!file.getPath().endsWith(configBean.getFileExt())) {
                    file = new File(file.getPath() + "." + configBean.getFileExt());
                }
            }
            sayIfNotOK(configBean.writeToFile(file));
        }
    }
    
    private class ComponentFileFilter extends FileFilter {
        
        public boolean accept(File f) {
            return f.isDirectory() ||
                    f.getName().endsWith(getCurrentConfigBean().getFileExt());
        }
        
        public String getDescription() {
            return getCurrentConfigBean().getBeanDisplayName();
        }
    }
    
    private class OnLookAndFeelChange implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String newLFClassName = (String) ((JCheckBoxMenuItem)e.getSource()).getClientProperty(MENU_PROPERTY_ID);
            if (newLFClassName.equals(UIManager.getLookAndFeel().getClass().getName())) return;
            try {
                UIManager.setLookAndFeel(newLFClassName);
                SwingUtilities.updateComponentTreeUI(getFrame());
                for (ConfigBean beanConfigurator : configurators) {
                    SwingUtilities.updateComponentTreeUI(beanConfigurator);
                    SwingUtilities.updateComponentTreeUI(fileChooser);
                }
                getFrame().repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private class OnBeanChange implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Integer newBeanIndex = (Integer) ((JCheckBoxMenuItem)e.getSource()).getClientProperty(MENU_PROPERTY_ID);
            setSelected(newBeanIndex);
        }
    }
}
package core.plugin.monkey.win.device;

import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;

import core.plugin.monkey.log.LogLevel;
import core.plugin.monkey.core.Monkey;
import core.plugin.monkey.util.DataUtil;
import core.plugin.monkey.util.TextUtil;
import core.plugin.monkey.win.base.DecimalDoc;

/**
 * @author DrkCore
 * @since 2017-01-28
 */
public class GeneralPanel extends JPanel implements BuilderDlg.ConfigPanel {
    
    private JPanel contentPanel;
    private JTextField pkgsTxt;
    private JTextField categoriesTxt;
    private JComboBox logLevelCombo;
    private JFormattedTextField throttleTxt;
    private JFormattedTextField countTxt;
    private JTextField textField1;
    private JCheckBox infinityCheckBox;
    
    public GeneralPanel() {
        throttleTxt.setDocument(new DecimalDoc());
        countTxt.setDocument(new DecimalDoc());
    }
    
    @Override
    public void adapt(Monkey.Builder config) {
        pkgsTxt.setText("");
        List<String> pkgs = config.getAllowedPackages();
        if (pkgs != null) {
            pkgs.forEach(s -> {
                if (!TextUtil.isEmpty(s)) {
                    pkgsTxt.setText(pkgsTxt.getText() + s + " ");
                }
            });
        }
        
        categoriesTxt.setText("");
        List<String> categories = config.getMainCategories();
        if (categories != null) {
            categories.forEach(s -> {
                if (!TextUtil.isEmpty(s)) {
                    categoriesTxt.setText(categoriesTxt.getText() + s + " ");
                }
            });
        }
        
        LogLevel level = config.getLogLevel() != null ? config.getLogLevel() : LogLevel.SIMPLE;
        logLevelCombo.setSelectedIndex(level.ordinal());
        
        throttleTxt.setText(String.valueOf(config.getThrottle()));
        countTxt.setText(String.valueOf(config.getCount()));
        
        infinityCheckBox.setSelected(config.getTimes() == Monkey.INFINITE);
    }
    
    @Override
    public void apply(Monkey.Builder config) {
        String[] pkgs = !TextUtil.isEmpty(pkgsTxt.getText()) ? pkgsTxt.getText().split(" ") : null;
        config.setAllowedPackages(pkgs);
        
        String[] categories = !TextUtil.isEmpty(categoriesTxt.getText()) ? categoriesTxt.getText().split(" ") : null;
        config.setMainCategories(categories);
        
        long throllte = DataUtil.parseLong(throttleTxt.getText());
        if (throllte > 0) {
            config.setThrottle(throllte);
        }
        
        long count = DataUtil.parseLong(countTxt.getText());
        config.setCount(count);
        
        int idx = logLevelCombo.getSelectedIndex();
        config.setLogLevel(LogLevel.values()[idx]);
        
        config.setTimes(infinityCheckBox.isSelected() ? Monkey.INFINITE : 1);
    }
    
}

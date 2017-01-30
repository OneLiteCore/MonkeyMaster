package core.plugin.monkey.win;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;

import core.plugin.monkey.core.Builder;
import core.plugin.monkey.core.LogLevel;
import core.plugin.monkey.util.DataUtil;
import core.plugin.monkey.util.TextUtil;

/**
 * @author DrkCore
 * @since 2017-01-28
 */
public class MainPanel extends JPanel implements ConfigDlg.ConfigPanel {
    
    private static final FileChooserDescriptor DESCRIPTOR = new FileChooserDescriptor(false, true, false, false, false, false);
    
    private JPanel contentPanel;
    private JTextField pkgsTxt;
    private JTextField categoriesTxt;
    private JComboBox logLevelCombo;
    private TextFieldWithBrowseButton choseFileTxt;
    private JFormattedTextField throttleTxt;
    private JFormattedTextField countTxt;
    
    public MainPanel() {
        choseFileTxt.addBrowseFolderListener(new TextBrowseFolderListener(DESCRIPTOR));
        throttleTxt.setDocument(new DecimalDoc());
        countTxt.setDocument(new DecimalDoc());
    }
    
    @Override
    public void adapt(Builder config) {
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
    }
    
    @Override
    public void apply(Builder config) {
        String[] pkgs = !TextUtil.isEmpty(pkgsTxt.getText()) ? pkgsTxt.getText().split(" ") : null;
        config.setAllowedPackages(pkgs);
        
        String[] categories = !TextUtil.isEmpty(categoriesTxt.getText()) ? categoriesTxt.getText().split(" ") : null;
        config.setMainCategories(categories);
        
        long throllte = DataUtil.parseLong(throttleTxt.getText());
        if (throllte > 0) {
            config.setThrottle(throllte);
        }
        
        long count = DataUtil.parseLong(countTxt.getText());
        if (count > 0) {
            config.setCount(count);
        }
        
        int idx = logLevelCombo.getSelectedIndex();
        config.setLogLevel(LogLevel.values()[idx]);
    }
    
    @Nullable
    public File getOutputDir() {
        String path = choseFileTxt.getText();
        File dir = !TextUtil.isEmpty(path) ? new File(path) : null;
        return dir != null && dir.isDirectory() ? dir : null;
    }
    
    
}

package core.plugin.monkey.plugin.config;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import core.plugin.monkey.core.Builder;
import core.plugin.monkey.plugin.ConfigDlg;

/**
 * @author DrkCore
 * @since 2017-01-28
 */
public class IgnorePanel extends JPanel implements ConfigDlg.ConfigPanel {
    
    private JCheckBox ignoreCrashsCheckBox;
    private JCheckBox ignoreTimeoutsCheckBox;
    private JCheckBox ignoreSecurityExceptionsCheckBox;
    private JCheckBox monitorNativeCrashsCheckBox;
    private JPanel contentPanel;
    private JCheckBox ignoreNativeCrashsCheckBox;
    
    @Override
    public void adapt(Builder config) {
        ignoreCrashsCheckBox.setSelected(config.isIgnoreCrashes());
        ignoreTimeoutsCheckBox.setSelected(config.isIgnoreTimeouts());
        ignoreSecurityExceptionsCheckBox.setSelected(config.isIgnoreSecurityExceptions());
        monitorNativeCrashsCheckBox.setSelected(config.isMonitorNativeCrashes());
        ignoreNativeCrashsCheckBox.setSelected(config.isIgnoreNativeCrashes());
    }
    
    @Override
    public void apply(Builder config) {
        config.setIgnoreCrashes(ignoreCrashsCheckBox.isSelected());
        config.setIgnoreTimeouts(ignoreTimeoutsCheckBox.isSelected());
        config.setIgnoreSecurityExceptions(ignoreSecurityExceptionsCheckBox.isSelected());
        config.setMonitorNativeCrashes(monitorNativeCrashsCheckBox.isSelected());
        config.setIgnoreNativeCrashes(ignoreNativeCrashsCheckBox.isSelected());
    }
}

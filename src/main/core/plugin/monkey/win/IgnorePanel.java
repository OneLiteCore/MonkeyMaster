package core.plugin.monkey.win;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import core.plugin.monkey.core.Builder;

/**
 * @author DrkCore
 * @since 2017-01-28
 */
public class IgnorePanel extends JPanel implements ConfigDlg.ConfigPanel {
    
    private JCheckBox ignoreCrashesCheckBox;
    private JCheckBox ignoreTimeoutsCheckBox;
    private JCheckBox ignoreSecurityExceptionsCheckBox;
    private JCheckBox monitorNativeCrashesCheckBox;
    private JPanel contentPanel;
    private JCheckBox ignoreNativeCrashesCheckBox;
    
    @Override
    public void adapt(Builder config) {
        ignoreCrashesCheckBox.setSelected(config.isIgnoreCrashes());
        ignoreTimeoutsCheckBox.setSelected(config.isIgnoreTimeouts());
        ignoreSecurityExceptionsCheckBox.setSelected(config.isIgnoreSecurityExceptions());
        monitorNativeCrashesCheckBox.setSelected(config.isMonitorNativeCrashes());
        ignoreNativeCrashesCheckBox.setSelected(config.isIgnoreNativeCrashes());
    }
    
    @Override
    public void apply(Builder config) {
        config.setIgnoreCrashes(ignoreCrashesCheckBox.isSelected());
        config.setIgnoreTimeouts(ignoreTimeoutsCheckBox.isSelected());
        config.setIgnoreSecurityExceptions(ignoreSecurityExceptionsCheckBox.isSelected());
        config.setMonitorNativeCrashes(monitorNativeCrashesCheckBox.isSelected());
        config.setIgnoreNativeCrashes(ignoreNativeCrashesCheckBox.isSelected());
    }
}

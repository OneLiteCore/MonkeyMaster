package core.plugin.monkey.win.device;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import core.plugin.monkey.core.Builder;

/**
 * @author DrkCore
 * @since 2017-01-28
 */
public class DebugPanel extends JPanel implements BuilderDlg.ConfigPanel {
    
    private JPanel contentPanel;
    private JCheckBox ignoreCrashesCheckBox;
    private JCheckBox ignoreTimeoutsCheckBox;
    private JCheckBox ignoreSecurityExceptionsCheckBox;
    private JCheckBox monitorNativeCrashesCheckBox;
    private JCheckBox ignoreNativeCrashesCheckBox;
    private JCheckBox dbgNoEventsCheckBox;
    private JCheckBox hprofCheckBox;
    private JCheckBox killProcessAfterErrorCheckBox;
    private JCheckBox waitDbgCheckBox;
    
    @Override
    public void adapt(Builder config) {
        ignoreCrashesCheckBox.setSelected(config.isIgnoreCrashes());
        ignoreTimeoutsCheckBox.setSelected(config.isIgnoreTimeouts());
        ignoreSecurityExceptionsCheckBox.setSelected(config.isIgnoreSecurityExceptions());
        monitorNativeCrashesCheckBox.setSelected(config.isMonitorNativeCrashes());
        ignoreNativeCrashesCheckBox.setSelected(config.isIgnoreNativeCrashes());
        dbgNoEventsCheckBox.setSelected(config.isDbgNoEvents());
        hprofCheckBox.setSelected(config.isHprof());
        killProcessAfterErrorCheckBox.setSelected(config.isKillProcessAfterError());
        waitDbgCheckBox.setSelected(config.isWaitDbg());
    }
    
    @Override
    public void apply(Builder config) {
        config.setIgnoreCrashes(ignoreCrashesCheckBox.isSelected());
        config.setIgnoreTimeouts(ignoreTimeoutsCheckBox.isSelected());
        config.setIgnoreSecurityExceptions(ignoreSecurityExceptionsCheckBox.isSelected());
        config.setMonitorNativeCrashes(monitorNativeCrashesCheckBox.isSelected());
        config.setIgnoreNativeCrashes(ignoreNativeCrashesCheckBox.isSelected());
        config.setDbgNoEvents(dbgNoEventsCheckBox.isSelected());
        config.setHprof(hprofCheckBox.isSelected());
        config.setKillProcessAfterError(killProcessAfterErrorCheckBox.isSelected());
        config.setWaitDbg(waitDbgCheckBox.isSelected());
    }
}

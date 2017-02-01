package core.plugin.monkey.win;

import com.intellij.openapi.project.Project;
import com.intellij.ui.content.Content;

import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import core.plugin.monkey.WinFactory;
import core.plugin.monkey.core.Builder;
import core.plugin.monkey.core.LogManager;
import core.plugin.monkey.core.Monkey;
import core.plugin.monkey.core.TextPrinter;
import core.plugin.monkey.win.base.BaseWin;
import core.plugin.monkey.win.device.BuilderDlg;

/**
 * @author DrkCore
 * @since 2017-01-26
 */
public class DeviceWin extends BaseWin {
    
    private JPanel contentPanel;
    private JButton runBtn;
    private JButton stopBtn;
    private JScrollPane scrollPanel;
    private JTextArea logTextArea;
    private JButton settingBtn;
    private JButton clearBtn;
    private JCheckBox scrollCheckBox;
    
    public static final String TAG_DEVICE = "Device:";
    private final String device;
    private final Monkey monkey;
    
    @Override
    public JPanel getContentPanel() {
        return contentPanel;
    }
    
    @Override
    public void onAttached(Project project, WinFactory factory, Content content) {
        super.onAttached(project, factory, content);
        content.setCloseable(true);
    }
    
    public DeviceWin(String device) {
        super(TAG_DEVICE + device);
        this.device = device;
        this.monkey = new Monkey(device);
        runBtn.addActionListener(e -> startMonkey(false));
        stopBtn.addActionListener(e -> stop());
        settingBtn.addActionListener(e -> startMonkey(true));
        clearBtn.addActionListener(e -> logPrinter.clearLog());
        
        logPrinter = new TextPrinter(logTextArea);
        
        scrollCheckBox.addChangeListener(e -> {
            System.out.println(scrollCheckBox.isSelected());
            logPrinter.setAutoScroll(scrollCheckBox.isSelected());
        });
    }
    
    /*运行*/
    
    private TextPrinter logPrinter;
    private Builder config;
    
    private void startMonkey(boolean needConfig) {
        if (config == null) {
            needConfig = true;
        }
        
        if (needConfig) {
            new BuilderDlg().setListener((config, cmd) -> {
                DeviceWin.this.config = config;
                doRun(cmd);
            }).show();
        } else {
            String cmd = config.build();
            doRun(cmd);
        }
    }
    
    private void doRun(String cmd) {
        String basePath = getProject().getBasePath();
        File logfile = LogManager.getInstance().newLogFile(basePath);
        monkey.submit(cmd, logPrinter, logfile);
    }
    
    private void stop() {
        try {
            monkey.terminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

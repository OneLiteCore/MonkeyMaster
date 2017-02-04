package core.plugin.monkey.win;

import com.intellij.openapi.project.Project;
import com.intellij.ui.content.Content;

import java.io.File;

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
import core.plugin.monkey.task.MonkeyTask;
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
    private TextPrinter logPrinter;

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

        logPrinter = new TextPrinter(logTextArea);

        this.monkey = new Monkey(device, logPrinter);
        runBtn.addActionListener(e -> startMonkey(false));
        stopBtn.addActionListener(e -> monkey.terminal());
        settingBtn.addActionListener(e -> startMonkey(true));
        clearBtn.addActionListener(e -> logPrinter.clearLog());

        scrollCheckBox.addChangeListener(e -> {
            System.out.println(scrollCheckBox.isSelected());
            logPrinter.setAutoScroll(scrollCheckBox.isSelected());
        });
    }
    
    /*运行*/

    private Builder config;

    private void startMonkey(boolean needConfig) {
        if (config == null) {
            needConfig = true;
        }

        if (needConfig) {
            new BuilderDlg().setCallback((config) -> {
                DeviceWin.this.config = config;
                doRun(config.build());
            }).show();
        } else {
            doRun(config.build());
        }
    }

    private void doRun(MonkeyTask.Params params) {
        String basePath = getProject().getBasePath();
        File logfile = LogManager.getInstance().newLogFile(basePath);

        ConsoleWin console = getFactory().getConsoleWin();
        console.log("Monkey: " + params.cmd);
        if (params.times == MonkeyTask.INFINITE) {
            console.log("Infinity: true");
        }
        console.log("Device: " + monkey.getDevice());
        console.log("logfile: " + logfile);
        console.log("_____________________________________\n");

        monkey.submit(params, logfile);
    }

}

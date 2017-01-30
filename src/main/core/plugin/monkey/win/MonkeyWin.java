package core.plugin.monkey.win;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import core.plugin.monkey.core.Builder;
import core.plugin.monkey.core.Monkey;
import core.plugin.monkey.core.MultiLogPrinter;
import core.plugin.monkey.core.TextPrinter;

/**
 * @author DrkCore
 * @since 2017-01-26
 */
public class MonkeyWin {
    
    private JPanel contentPanel;
    private JButton runBtn;
    private JButton stopBtn;
    private JScrollPane scrollPanel;
    private JTextArea logTextArea;
    private JButton settingBtn;
    private JButton clearBtn;
    private JCheckBox scrollCheckBox;
    
    public JPanel getContentPanel() {
        return contentPanel;
    }
    
    public MonkeyWin() {
        runBtn.addActionListener(e -> start());
        stopBtn.addActionListener(e -> stop());
        settingBtn.addActionListener(e -> setting());
        clearBtn.addActionListener(e -> clearLog());
        
        logPrinter = new TextPrinter(logTextArea);
        
        scrollCheckBox.addChangeListener(e -> {
            System.out.println(scrollCheckBox.isSelected());
            logPrinter.setAutoScroll(scrollCheckBox.isSelected());
        });
    }
    
    /*运行*/
    
    private final Monkey monkey = Monkey.getInstance();
    private TextPrinter logPrinter;
    
    private void start() {
        new SelectDeviceDlg().setCallback(s -> {
            String cmd = new Builder()
                    .addAllowedPackages("core.demo")
                    .setIgnoreAll()
                    .setLogAll()
                    .setTotalTime(10 * 1000).build();
            
            MultiLogPrinter printer = new MultiLogPrinter();
            printer.addConsolePrinter().add(logPrinter);
            
            monkey.submit(cmd, s, printer);
        }).show();
    }
    
    private void stop() {
        try {
            monkey.terminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void setting() {
        new ConfigDlg().show();
    }
    
    private void clearLog() {
        logPrinter.clearLog();
    }
}

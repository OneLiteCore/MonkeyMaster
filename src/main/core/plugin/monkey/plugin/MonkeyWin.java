package core.plugin.monkey.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;

import org.jetbrains.annotations.NotNull;

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
public class MonkeyWin implements ToolWindowFactory, Condition<Project> {
    
    private JPanel panel;
    private JButton runBtn;
    private JButton stopBtn;
    private JScrollPane scrollPanel;
    private JTextArea logTextArea;
    private JButton settingBtn;
    private JButton clearBtn;
    private JCheckBox scrollCheckBox;
    
    public JPanel getPanel() {
        return panel;
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
    
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentManager mgr = toolWindow.getContentManager();
        Content content = mgr.getFactory().createContent(panel, "", false);
        mgr.addContent(content);
    }
    
    @Override
    public void init(ToolWindow window) {
        
    }
    
    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }
    
    @Override
    public boolean isDoNotActivateOnStart() {
        return true;
    }
    
    @Override
    public boolean value(Project project) {
        return true;
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

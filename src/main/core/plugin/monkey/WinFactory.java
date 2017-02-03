package core.plugin.monkey;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import core.plugin.monkey.win.ConsoleWin;
import core.plugin.monkey.win.DeviceWin;
import core.plugin.monkey.win.base.BaseWin;

/**
 * @author DrkCore
 * @since 2017-01-30
 */
public class WinFactory implements ToolWindowFactory, Condition<Project> {
    
    private Project project;
    private ToolWindow toolWin;
    private ContentManager mgr;
    
    @Override
    public void init(ToolWindow window) {
    }
    
    private ConsoleWin consoleWin;
    private List<DeviceWin> deviceWins;
    
    public ConsoleWin getConsoleWin() {
        return consoleWin;
    }
    
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        this.project = project;
        this.toolWin = toolWindow;
        this.mgr = toolWin.getContentManager();
        
        consoleWin = new ConsoleWin();
        attach(consoleWin);
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

    /*Tab*/
    
    public void attach(BaseWin win) {
        attach(win, false);
    }
    
    public void attach(BaseWin win, boolean focus) {
        Content content = mgr.getFactory().createContent(win.getContentPanel(), win.getTitle(), false);
        win.onAttached(project, this, content);
        mgr.addContent(content);
        
        if (focus) {
            mgr.setSelectedContent(content);
        }
    }
    
    public void attachDeviceWin(String device) {
        String title = DeviceWin.TAG_DEVICE + device;
        Content content = mgr.findContent(title);
        if (content != null) {
            mgr.setSelectedContent(content);
        } else {
            attach(new DeviceWin(device), true);
        }
    }
    
    public boolean remove(BaseWin win) {
        boolean result = mgr.removeContent(win.getContent(), true);
        win.onRemoved();
        return result;
    }
}

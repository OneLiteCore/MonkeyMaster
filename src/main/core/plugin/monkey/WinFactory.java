package core.plugin.monkey;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;

import org.jetbrains.annotations.NotNull;

import core.plugin.monkey.win.MonkeyWin;

/**
 * @author DrkCore
 * @since 2017-01-30
 */
public class WinFactory implements ToolWindowFactory, Condition<Project> {
    
    private ToolWindow toolWin;
    
    @Override
    public void init(ToolWindow window) {
        this.toolWin = window;
    }
    
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentManager mgr = toolWindow.getContentManager();
        MonkeyWin monkeyWin = new MonkeyWin();
        Content content = mgr.getFactory().createContent(monkeyWin.getContentPanel(), "", false);
        mgr.addContent(content);
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
    
}

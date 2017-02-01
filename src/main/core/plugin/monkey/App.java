package core.plugin.monkey;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;

import org.jetbrains.annotations.NotNull;

/**
 * @author DrkCore
 * @since 2017-01-26
 */
public class App implements ApplicationComponent{
    
    public static App getInstance() {
        return ApplicationManager.getApplication().getComponent(App.class);
    }
    
    @Override
    public void initComponent() {
        
    }
    
    @Override
    public void disposeComponent() {
        
    }
    
    @NotNull
    @Override
    public String getComponentName() {
        return "MonkeyMaster.App";
    }
    
}

package core.plugin.monkey.plugin;

import com.intellij.openapi.components.ApplicationComponent;

import org.jetbrains.annotations.NotNull;

/**
 * @author DrkCore
 * @since 2017-01-26
 */
public class AppComponent implements ApplicationComponent {
    
    @Override
    public void initComponent() {
        
    }
    
    @Override
    public void disposeComponent() {
        
    }
    
    @NotNull
    @Override
    public String getComponentName() {
        return "MonkeyMaster";
    }
}

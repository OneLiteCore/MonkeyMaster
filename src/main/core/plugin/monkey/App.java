package core.plugin.monkey;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import core.plugin.monkey.core.Config;

/**
 * @author DrkCore
 * @since 2017-01-26
 */
@State(name = "win", storages = @Storage(id = "win", file = StoragePathMacros.WORKSPACE_FILE + "/win.xml"))
public class App implements ApplicationComponent, PersistentStateComponent<Config> {
    
    public static App getInstance() {
        return ApplicationManager.getApplication().getComponent(App.class);
    }
    
    private Config config;
    
    @com.sun.istack.internal.NotNull
    public Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }
    
    @Nullable
    @Override
    public Config getState() {
        return getConfig();
    }
    
    @Override
    public void loadState(Config config) {
        this.config = config;
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

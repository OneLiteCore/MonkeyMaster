package core.plugin.monkey.win.device;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;

import core.plugin.monkey.core.Builder;
import core.plugin.monkey.util.Callback;
import core.plugin.monkey.util.SimpleCallback;
import core.plugin.monkey.win.base.BaseDlg;

/**
 * @author DrkCore
 * @since 2017-01-28
 */
public class BuilderDlg extends BaseDlg {
    
    public interface ConfigPanel {
        
        void adapt(Builder config);
        
        void apply(Builder config);
        
    }
    
    private JPanel contentPanel;
    private GeneralPanel generalPanel;
    private DebugPanel debugPanel;
    private EventPanel eventPanel;
    
    private final Builder config;
    
    public BuilderDlg() {
        this(null);
    }
    
    public BuilderDlg(@Nullable Builder config) {
        super("Config Monkey Runner");
        
        this.config = config != null ? config.clone() : new Builder();
    }
    
    private Callback<Builder> callback;
    
    public BuilderDlg setCallback(Callback<Builder> callback) {
        this.callback = callback;
        return this;
    }
    
    @NotNull
    @Override
    protected Action[] createActions() {
        return new Action[]{new DialogWrapperAction("Run") {
            
            @SuppressWarnings("unchecked")
            @Override
            protected void doAction(ActionEvent actionEvent) {
                dispose();
                generalPanel.apply(config);
                debugPanel.apply(config);
                eventPanel.apply(config);
                
                SimpleCallback.call(config, callback);
            }
        }, getCancelAction()};
    }
    
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPanel;
    }
    
}

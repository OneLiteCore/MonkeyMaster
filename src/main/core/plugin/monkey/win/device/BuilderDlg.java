package core.plugin.monkey.win.device;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;

import core.plugin.monkey.core.Builder;
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
    
    public interface Listener {
        
        void onBuilt(Builder config, String cmd);
        
    }
    
    private Listener listener;
    
    public BuilderDlg setListener(Listener listener) {
        this.listener = listener;
        return this;
    }
    
    @NotNull
    @Override
    protected Action[] createActions() {
        return new Action[]{new DialogWrapperAction("Run") {
            
            @Override
            protected void doAction(ActionEvent actionEvent) {
                dispose();
                generalPanel.apply(config);
                debugPanel.apply(config);
                eventPanel.apply(config);
                if (listener != null) {
                    listener.onBuilt(config, config.build());
                }
            }
        }, getCancelAction()};
    }
    
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPanel;
    }
    
}

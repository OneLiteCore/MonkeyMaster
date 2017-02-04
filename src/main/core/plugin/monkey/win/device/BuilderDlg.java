package core.plugin.monkey.win.device;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;

import core.plugin.monkey.core.Monkey;
import core.plugin.monkey.util.Callback;
import core.plugin.monkey.util.SimpleCallback;
import core.plugin.monkey.win.base.BaseDlg;

/**
 * @author DrkCore
 * @since 2017-01-28
 */
public class BuilderDlg extends BaseDlg {
    
    public interface ConfigPanel {
        
        void adapt(Monkey.Builder config);
        
        void apply(Monkey.Builder config);
        
    }
    
    private JPanel contentPanel;
    private GeneralPanel generalPanel;
    private DebugPanel debugPanel;
    private EventPanel eventPanel;
    
    private List<ConfigPanel> panels;
    
    private final Monkey.Builder config;
    
    public BuilderDlg() {
        this(null);
    }
    
    public BuilderDlg(@Nullable Monkey.Builder config) {
        super("Config Monkey Runner");
        
        this.config = config != null ? config.clone() : new Monkey.Builder();
        
        panels = Arrays.asList(generalPanel, debugPanel, eventPanel);
        panels.forEach(configPanel -> configPanel.adapt(BuilderDlg.this.config));
    }
    
    private Callback<Monkey.Builder> callback;
    
    public BuilderDlg setCallback(Callback<Monkey.Builder> callback) {
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
                
                panels.forEach(configPanel -> configPanel.apply(BuilderDlg.this.config));
                
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


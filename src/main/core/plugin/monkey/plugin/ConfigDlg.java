package core.plugin.monkey.plugin;

import com.intellij.openapi.ui.DialogWrapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;

import core.plugin.monkey.core.Builder;
import core.plugin.monkey.plugin.config.IgnorePanel;
import core.plugin.monkey.plugin.config.MainPanel;
import core.plugin.monkey.plugin.config.PercentPanel;

/**
 * @author DrkCore
 * @since 2017-01-28
 */
public class ConfigDlg extends DialogWrapper {
    
    public interface ConfigPanel {
        
        void adapt(Builder config);
        
        void apply(Builder config);
        
    }
    
    private JPanel contentPanel;
    private MainPanel mainPanel;
    private IgnorePanel ignorePanel;
    private PercentPanel percentPanel;
    
    private final Builder config;
    
    protected ConfigDlg() {
        this(null);
    }
    
    protected ConfigDlg(Builder config) {
        super(false);
        init();
        setTitle("Config Monkey Runner");
        pack();
      
        this.config = config != null?config.clone():new Builder();
    }
    
    @NotNull
    @Override
    protected Action[] createActions() {
        return new Action[]{new DialogWrapperAction("Run") {
            
            @Override
            protected void doAction(ActionEvent actionEvent) {
                run();
            }
        }, getCancelAction()};
    }
    
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPanel;
    }
    
    private void run() {
        Builder config = new Builder();
        mainPanel.apply(config);
        ignorePanel.apply(config);
        percentPanel.apply(config);
        System.out.println(config.build());
    }
}

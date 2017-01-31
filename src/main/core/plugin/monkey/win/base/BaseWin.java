package core.plugin.monkey.win.base;

import com.intellij.ui.content.Content;

import javax.swing.JPanel;

import core.plugin.monkey.Icons;
import core.plugin.monkey.WinFactory;
import core.plugin.monkey.util.TextUtil;

/**
 * @author DrkCore
 * @since 2017-01-30
 */
public abstract class BaseWin implements Icons {
    
    private String title;
    
    public String getTitle() {
        return !TextUtil.isEmpty(title) ? title : getClass().getSimpleName();
    }
    
    public BaseWin(String title) {
        this.title = title;
    }
    
    private WinFactory factory;
    private Content content;
    
    public WinFactory getFactory() {
        return factory;
    }
    
    public Content getContent() {
        return content;
    }
    
    public void onAttached(WinFactory factory, Content content) {
        if (this.factory != null || this.content != null) {
            throw new IllegalStateException("Win could only be attached once");
        }
        this.factory = factory;
        this.content = content;
    }
    
    public void onRemoved() {
        this.content = null;
    }
    
    public abstract JPanel getContentPanel();
    
}

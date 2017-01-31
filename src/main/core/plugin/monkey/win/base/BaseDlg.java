package core.plugin.monkey.win.base;

import com.intellij.openapi.ui.DialogWrapper;

/**
 * @author DrkCore
 * @since 2017-01-30
 */
public abstract class BaseDlg extends DialogWrapper {
    
    protected BaseDlg() {
        this(false);
    }
    
    protected BaseDlg(boolean canBeParent) {
        super(canBeParent);
    }
}

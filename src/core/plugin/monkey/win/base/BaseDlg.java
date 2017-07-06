package core.plugin.monkey.win.base;

import com.intellij.openapi.ui.DialogWrapper;

/**
 * @author DrkCore
 * @since 2017-01-30
 */
public abstract class BaseDlg extends DialogWrapper {
    
    protected BaseDlg(String title) {
        this(title, false);
    }
    
    protected BaseDlg(String title, boolean canBeParent) {
        super(canBeParent);
        setTitle(title);
        setResizable(false);
    }
    
    private boolean initFlag;
    
    @Override
    public void show() {
        if(!initFlag){
            initFlag = true;
            init();
            pack();
        }
        super.show();
    }
}

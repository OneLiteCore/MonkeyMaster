package core.plugin.monkey.win;

import javax.swing.JPanel;

import core.plugin.monkey.win.base.BaseWin;

/**
 * @author DrkCore
 * @since 2017-01-30
 */
public class LogfileWin extends BaseWin {
    
    private JPanel contentPanel;
    
    @Override
    public JPanel getContentPanel() {
        return contentPanel;
    }
    
    private static final String TITLE = "Logfile";
    
    public LogfileWin() {
        super(TITLE);
    }
    
}

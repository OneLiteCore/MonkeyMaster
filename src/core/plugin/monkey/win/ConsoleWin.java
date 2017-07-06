package core.plugin.monkey.win;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import core.plugin.monkey.win.base.BaseWin;
import core.plugin.monkey.win.console.SelectDeviceDlg;

/**
 * @author DrkCore
 * @since 2017-01-30
 */
public class ConsoleWin extends BaseWin {
    
    private JPanel contentPanel;
    private JTextArea logTxt;
    private JButton createBtn;
    
    public ConsoleWin() {
        super(TITLE);
        createBtn.addActionListener(e -> showDeviceDlg());
    }
    
    @Override
    public JPanel getContentPanel() {
        return contentPanel;
    }
    
    public static final String TITLE = "Console";
    
    private void showDeviceDlg() {
        new SelectDeviceDlg().setCallback(s -> getFactory().attachDeviceWin(s)).show();
    }
    
    public synchronized void log(String log) {
        logTxt.append(log);
        logTxt.append("\n");
    }
}
 
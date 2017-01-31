package core.plugin.monkey.win.console;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;

import core.plugin.monkey.core.Monkey;
import core.plugin.monkey.util.Callback;
import core.plugin.monkey.util.DataUtil;
import core.plugin.monkey.util.SimpleCallback;
import core.plugin.monkey.util.TextUtil;
import core.plugin.monkey.win.base.BaseDlg;

public class SelectDeviceDlg extends BaseDlg {
    
    private JPanel contentPane;
    private JList deviceList;
    
    public SelectDeviceDlg() {
        init();
        setTitle("Devices");
        pack();
        setResizable(false);
        
        refresh();
    }
    
    private DefaultListModel<String> model = new DefaultListModel<>();
    
    @SuppressWarnings("unchecked")
    public void refresh() {
        model.clear();
        try {
            List<String> devices = Monkey.findDevices();
            if (!DataUtil.isEmpty(devices)) {
                devices.forEach(model::addElement);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        deviceList.setModel(model);
    }
    
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPane;
    }
    
    private Callback<String> callback;
    
    public SelectDeviceDlg setCallback(Callback<String> callback) {
        this.callback = callback;
        return this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void doOKAction() {
        super.doOKAction();
        int idx = deviceList.getSelectedIndex();
        String device = idx != -1 ? model.getElementAt(idx) : null;
        if (!TextUtil.isEmpty(device)) {
            SimpleCallback.call(device, callback);
        }
    }
}


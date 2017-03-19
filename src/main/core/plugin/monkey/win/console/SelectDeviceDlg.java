package core.plugin.monkey.win.console;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import core.plugin.monkey.core.FindDevicesTask;
import core.plugin.monkey.util.Callback;
import core.plugin.monkey.util.DataUtil;
import core.plugin.monkey.util.OnTaskListenerImpl;
import core.plugin.monkey.util.SimpleCallback;
import core.plugin.monkey.util.TextUtil;
import core.plugin.monkey.win.base.BaseDlg;

public class SelectDeviceDlg extends BaseDlg {
    
    private JPanel contentPanel;
    private JList deviceList;
    private JLabel emptyLabel;
    
    private static final String CARD_DEVICES = "deviceListCard";
    private static final String CARD_EMPTY = "emptyLabelCard";
    
    public SelectDeviceDlg() {
        super("Devices");
        setSize(240, 160);
        refresh();
    }
    
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPanel;
    }
    
    @NotNull
    @Override
    protected Action[] createLeftSideActions() {
        return new Action[]{new DialogWrapperAction("Refresh") {
            @Override
            protected void doAction(ActionEvent actionEvent) {
                refresh();
            }
        }};
    }
    
    private DefaultListModel<String> model = new DefaultListModel<>();
    
    @SuppressWarnings("unchecked")
    public void refresh() {
        new FindDevicesTask().addListener(new OnTaskListenerImpl<Void, List<String>>() {
            @Override
            public void onSuccess(List<String> devices) {
                model.clear();

                if (!DataUtil.isEmpty(devices)) {
                    devices.forEach(model::addElement);
                }

                deviceList.setModel(model);
                CardLayout layout = (CardLayout) contentPanel.getLayout();
                layout.show(contentPanel, !model.isEmpty() ? CARD_DEVICES : CARD_EMPTY);
            }
        }).exec();
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


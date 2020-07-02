package core.plugin.monkey.win.device;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;

import core.plugin.monkey.core.Monkey;

/**
 * @author DrkCore
 * @since 2017-01-28
 */
public class EventPanel extends JPanel implements BuilderDlg.ConfigPanel {
    
    private JPanel contentPanel;
    private JSlider touchPctSlider;
    private JSlider motionPctSlider;
    private JSlider trackballPctSlider;
    private JSlider syskeysPctSlider;
    private JSlider navPctSlider;
    private JSlider majornavPctSlider;
    private JSlider appSwitchPctSlider;
    private JSlider flipPctSlider;
    private JSlider anyeventPctSlider;
    private JSlider pinchzoomPctSlider;
    private JCheckBox touchPercentCheckBox;
    private JCheckBox motionPercentCheckBox;
    private JCheckBox trackballPercentCheckBox;
    private JCheckBox syskeysPercentCheckBox;
    private JCheckBox navPercentCheckBox;
    private JCheckBox majornavPercentCheckBox;
    private JCheckBox appSwitchPercentCheckBox;
    private JCheckBox flipPercentCheckBox;
    private JCheckBox anyeventPercentCheckBox;
    private JCheckBox pinchzoomPercentCheckBox;
    
    private Monkey.Builder config;
    
    public EventPanel setConfig(Monkey.Builder config) {
        this.config = config;
        return this;
    }
    
    public EventPanel() {
        bind(touchPercentCheckBox, touchPctSlider);
        bind(motionPercentCheckBox, motionPctSlider);
        bind(trackballPercentCheckBox, trackballPctSlider);
        bind(syskeysPercentCheckBox, syskeysPctSlider);
        bind(navPercentCheckBox, navPctSlider);
        bind(majornavPercentCheckBox, majornavPctSlider);
        bind(appSwitchPercentCheckBox, appSwitchPctSlider);
        bind(flipPercentCheckBox, flipPctSlider);
        bind(anyeventPercentCheckBox, anyeventPctSlider);
        bind(pinchzoomPercentCheckBox, pinchzoomPctSlider);
    }
    
    private void bind(JCheckBox checkBox, JSlider slider) {
        slider.setEnabled(checkBox.isSelected());
        checkBox.addItemListener(e -> slider.setEnabled(checkBox.isSelected()));
    }
    
    private void set(JCheckBox checkBox, JSlider slider, Float val) {
        checkBox.setSelected(val != null);
        int intVal = val != null ? val.intValue() : 0;
        slider.setValue(intVal);
    }
    
    @Override
    public void adapt(Monkey.Builder config) {
        set(touchPercentCheckBox, touchPctSlider, config.getPctTouch());
        set(motionPercentCheckBox, motionPctSlider, config.getPctMotion());
        set(trackballPercentCheckBox, trackballPctSlider, config.getPctTrackball());
        set(syskeysPercentCheckBox, syskeysPctSlider, config.getPctSyskeys());
        set(navPercentCheckBox, navPctSlider, config.getPctNav());
        set(majornavPercentCheckBox, majornavPctSlider, config.getPctMajornav());
        set(appSwitchPercentCheckBox, appSwitchPctSlider, config.getPctAppswitch());
        set(flipPercentCheckBox, flipPctSlider, config.getPctFlip());
        set(anyeventPercentCheckBox, anyeventPctSlider, config.getPctAnyevent());
        set(pinchzoomPercentCheckBox, pinchzoomPctSlider, config.getPctPinchzoom());
    }
    
    @Override
    public void apply(Monkey.Builder config) {
        config.setPctTouch(touchPercentCheckBox.isSelected() ? (float) touchPctSlider.getValue() : null);
        config.setPctMotion(motionPercentCheckBox.isSelected() ? (float) motionPctSlider.getValue() : null);
        config.setPctTrackball(trackballPercentCheckBox.isSelected() ? (float) trackballPctSlider.getValue() : null);
        config.setPctSyskeys(syskeysPercentCheckBox.isSelected() ? (float) syskeysPctSlider.getValue() : null);
        config.setPctNav(navPercentCheckBox.isSelected() ? (float) navPctSlider.getValue() : null);
        config.setPctMajornav(majornavPercentCheckBox.isSelected() ? (float) majornavPctSlider.getValue() : null);
        config.setPctAppswitch(appSwitchPercentCheckBox.isSelected() ? (float) appSwitchPctSlider.getValue() : null);
        config.setPctFlip(flipPercentCheckBox.isSelected() ? (float) flipPctSlider.getValue() : null);
        config.setPctAnyevent(anyeventPercentCheckBox.isSelected() ? (float) anyeventPctSlider.getValue() : null);
        config.setPctPinchzoom(pinchzoomPercentCheckBox.isSelected() ? (float) pinchzoomPctSlider.getValue() : null);
    }
}

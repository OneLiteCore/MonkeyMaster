package core.plugin.monkey.core;

import java.io.ByteArrayOutputStream;

/**
 * @author DrkCore
 * @since 2017-02-01
 */
public class SimpleRunnerListener implements Runner.Listener {
    
    private Runner.Listener listener;
    
    public SimpleRunnerListener() {
    }
    
    public SimpleRunnerListener(Runner.Listener listener) {
        this.listener = listener;
    }
    
    @Override
    public void print(String line) {
        if (listener != null) {
            listener.print(line);
        }
    }
    
    @Override
    public void onStart() {
        if (listener != null) {
            listener.onStart();
        }
    }
    
    @Override
    public void onFinish(ByteArrayOutputStream out) {
        if (listener != null) {
            listener.onFinish(out);
        }
    }
}

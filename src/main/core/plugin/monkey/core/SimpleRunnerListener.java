package core.plugin.monkey.core;

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
    public void onStart(Runner runner) {
        if (listener != null) {
            listener.onStart(runner);
        }
    }
    
    @Override
    public void onFinish(Runner runner) {
        if (listener != null) {
            listener.onFinish(runner);
        }
    }
}

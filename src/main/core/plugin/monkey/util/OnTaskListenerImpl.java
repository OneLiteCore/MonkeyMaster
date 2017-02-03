package core.plugin.monkey.util;

/**
 * @author DrkCore
 * @since 2017-02-04
 */
public abstract class OnTaskListenerImpl<Progress, Result> implements Task.OnTaskListener<Progress, Result> {
    @Override
    public void onStart() {
        
    }
    
    @Override
    public void onProgress(Progress progress) {
        
    }
    
    @Override
    public void onError(Exception e) {
        
    }
    
    
    @Override
    public void onDone() {
        
    }
}

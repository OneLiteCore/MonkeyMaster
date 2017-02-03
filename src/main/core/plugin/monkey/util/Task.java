package core.plugin.monkey.util;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

/**
 * @author DrkCore
 * @since 2017-02-03
 */
public abstract class Task<Params, Progress, Result> {
    
    private static final Application APPLICATION = ApplicationManager.getApplication();
    
    private FutureTask<Result> task;
    
    public final synchronized Task<Params, Progress, Result> exec(Executor executor, Params params) {
        if (task != null) {
            throw new IllegalStateException();
        }
        
        onStart();
        executor.execute(task = new FutureTask<Result>(() -> doInBack(params)) {
            
            @Override
            protected void done() {
                super.done();
                try {
                    postResult(get(), null);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    postResult(null, e);
                }
            }
        });
        return this;
    }
    
    protected final void publishProgress(Progress progress) {
        APPLICATION.invokeLater(() -> onProgress(progress));
    }
    
    private void postResult(final Result result, final Exception e) {
        APPLICATION.invokeLater(() -> {
            if (e != null) {
                onError(e);
            } else {
                onSuccess(result);
            }
            onDone();
        });
    }
    
    protected abstract Result doInBack(Params params) throws Exception;
    
    public final boolean isCancelled() {
        return task != null && task.isCancelled();
    }
    
    public final boolean isDone() {
        return task != null && task.isDone();
    }
    
    public final boolean cancel(boolean mayInterruptIfRunning) {
        return task != null && task.cancel(mayInterruptIfRunning);
    }
    
    /*Lifecycle*/
    
    public interface OnTaskListener<Progress, Result> {
        
        void onStart();
        
        void onProgress(Progress progress);
        
        void onError(Exception e);
        
        void onSuccess(Result result);
        
        void onDone();
        
    }
    
    private List<OnTaskListener<Progress, Result>> listeners;
    
    public void addListener(OnTaskListener<Progress, Result> listener) {
        if (listener != null) {
            if (listeners == null) {
                listeners = new ArrayList<>();
            }
            listeners.add(listener);
        }
    }
    
    protected void onStart() {
        for (int i = 0, len = DataUtil.getSize(listeners); i < len; i++) {
            listeners.get(i).onStart();
        }
    }
    
    protected void onProgress(Progress progress) {
        for (int i = 0, len = DataUtil.getSize(listeners); i < len; i++) {
            listeners.get(i).onProgress(progress);
        }
    }
    
    protected void onError(Exception e) {
        for (int i = 0, len = DataUtil.getSize(listeners); i < len; i++) {
            listeners.get(i).onError(e);
        }
    }
    
    protected void onSuccess(Result result) {
        for (int i = 0, len = DataUtil.getSize(listeners); i < len; i++) {
            listeners.get(i).onSuccess(result);
        }
    }
    
    protected void onDone() {
        for (int i = 0, len = DataUtil.getSize(listeners); i < len; i++) {
            listeners.get(i).onDone();
        }
    }
}

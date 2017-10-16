package core.plugin.monkey.util.task;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import core.plugin.monkey.util.DataUtil;

/**
 * @author DrkCore
 * @since 2017-02-03
 */
public abstract class AbsTask<Params, Progress, Result> {
    
    private final FutureTask<Result> task = new FutureTask<Result>(() -> doInBack(getParams())) {
        
        @Override
        protected void done() {
            super.done();
            try {
                if (!isCancelled()) {
                    onSuccess(get());
                } else {
                    throw new CancellationException();
                }
            } catch (Exception e) {
                onError(e);
            }
            
            onDone();
        }
    };
    
    public final AbsTask<Params, Progress, Result> exec() {
        return exec(null);
    }
    
    public final AbsTask<Params, Progress, Result> exec(Params params) {
        return exec(null, params);
    }
    
    private Params params;
    
    public Params getParams() {
        return params;
    }
    
    private volatile boolean executed;
    
    public boolean isExecuted() {
        return executed;
    }
    
    public final synchronized AbsTask<Params, Progress, Result> exec(@Nullable Executor executor, Params params) {
        synchronized (this) {
            if (executed) {
                throw new IllegalStateException();
            }
            executed = true;
        }
        
        this.params = params;
        onStart();
        if (executor != null) {
            executor.execute(task);
        } else {
            new Thread(task).start();
        }
        return this;
    }
    
    protected final void publishProgress(Progress progress) {
        onProgress(progress);
    }
    
    protected abstract Result doInBack(Params params) throws Exception;

    /*task*/
    
    public final boolean isCancelled() {
        return task.isCancelled();
    }
    
    public final boolean isDone() {
        return task.isDone();
    }
    
    public final boolean cancel(boolean mayInterruptIfRunning) {
        return task.cancel(mayInterruptIfRunning);
    }
    
    public boolean waitFor() {
        try {
            get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Result get() throws InterruptedException, ExecutionException {
        return task.get();
    }
    
    public Result get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return task.get(timeout, unit);
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
    
    public AbsTask<Params, Progress, Result> addListener(OnTaskListener<Progress, Result> listener) {
        if (listener != null) {
            if (listeners == null) {
                listeners = new ArrayList<>();
            }
            listeners.add(listener);
        }
        return this;
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

package core.plugin.monkey.util;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author DrkCore
 * @since 2017-02-03
 */
public abstract class Task<Params, Progress, Result> {

    private static final Application APPLICATION = ApplicationManager.getApplication();

    private final FutureTask<Result> task = new FutureTask<Result>(() -> doInBack(getParams())) {

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
    };

    private static final Executor DEFAULT_EXECUTOR = Executors.newSingleThreadExecutor();

    public final Task<Params, Progress, Result> exec() {
        return exec(null);
    }

    public final Task<Params, Progress, Result> exec(Params params) {
        return exec(DEFAULT_EXECUTOR, params);
    }

    private Params params;

    public Params getParams() {
        return params;
    }

    private boolean executed;

    public boolean isExecuted() {
        return executed;
    }

    public final synchronized Task<Params, Progress, Result> exec(Executor executor, Params params) {
        if (executed) {
            throw new IllegalStateException();
        }
        executed = true;

        this.params = params;
        APPLICATION.invokeLater(this::onStart);
        executor.execute(task);
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

    public Result get() throws InterruptedException, ExecutionException {
        return task.get();
    }

    public Result get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return task.get(timeout, unit);
    }

    /*Lifecycle*/

    /**
     * @param <Progress>
     * @param <Result>
     */
    public interface OnTaskListener<Progress, Result> {

        void onStart();

        void onProgress(Progress progress);

        void onError(Exception e);

        void onSuccess(Result result);

        void onDone();

    }

    private List<OnTaskListener<Progress, Result>> listeners;

    public Task<Params, Progress, Result> addListener(OnTaskListener<Progress, Result> listener) {
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

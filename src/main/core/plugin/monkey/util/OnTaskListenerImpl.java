package core.plugin.monkey.util;

/**
 * @author DrkCore
 * @since 2017-02-04
 */
public class OnTaskListenerImpl<Progress, Result> implements Task.OnTaskListener<Progress, Result> {

    private final Callback<Void> callback;

    public OnTaskListenerImpl() {
        this(null);
    }

    public OnTaskListenerImpl(Callback<Void> callback) {
        this.callback = callback;
    }

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
    public void onSuccess(Result result) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onDone() {
        SimpleCallback.call(null, callback);
    }
}

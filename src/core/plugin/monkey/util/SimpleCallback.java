package core.plugin.monkey.util;

import org.jetbrains.annotations.NotNull;

/**
 * @author DrkCore
 * @since 2016/12/22
 */
public abstract class SimpleCallback<Result> implements Callback<Result> {

    public void onCall(Result result) {
        if (result != null) {
            onNotNull(result);
        } else {
            onNull();
        }
    }

    public void onNotNull(@NotNull Result result) {

    }

    public void onNull() {

    }

    public static void call(Callback<?> callback) {
        call(callback, null);
    }

    public static <Result> void call(Callback<Result> callback, Result result) {
        if (callback != null) {
            callback.onCall(result);
        }
    }

}

package core.plugin.monkey.util;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

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
    
    public static <Result> void call(Result result, Callback<Result>... callbacks) {
        for (int i = 0, len = DataUtil.getSize(callbacks); i < len; i++) {
            if (callbacks[i] != null) {
                callbacks[i].onCall(result);
            }
        }
    }
    
    public static <Result> void call(Result result, Collection<Callback<Result>> callbacks) {
        if (!DataUtil.isEmpty(callbacks)) {
            for (Callback<Result> callback : callbacks) {
                if (callback != null) {
                    callback.onCall(result);
                }
            }
        }
    }
    
}

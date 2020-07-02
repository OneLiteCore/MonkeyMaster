package core.plugin.monkey.util.task;

import com.intellij.openapi.util.Pair;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import core.plugin.monkey.util.Callback;

public abstract class Task<Param, Result> {

    public abstract Result exec(Param param) throws Exception;

    /*Convert*/

    public Runnable runnable(Param param) {
        return runnable(param, null);
    }

    public Runnable runnable(Param param, @Nullable Callback<Pair<Result, Exception>> onDone) {
        return () -> {
            Result result = null;
            Exception exception = null;

            try {
                result = exec(param);
            } catch (Exception e) {
                exception = e;
            }

            if (onDone != null) {
                onDone.onCall(Pair.create(result, exception));
            }
        };
    }

    public Callable<Pair<Result, Exception>> callable(Param param) {
        return callable(param, null);
    }

    public Callable<Pair<Result, Exception>> callable(Param param, @Nullable Callback<Pair<Result, Exception>> onDone) {
        return () -> {
            Result result = null;
            Exception exception = null;

            try {
                result = exec(param);
            } catch (Exception e) {
                exception = e;
            }

            Pair<Result, Exception> pair = Pair.create(result, exception);

            if (onDone != null) {
                onDone.onCall(pair);
            }
            return pair;
        };
    }

    public RunnableFuture<Pair<Result, Exception>> future(Param param) {
        return future(param, null);
    }

    public RunnableFuture<Pair<Result, Exception>> future(Param param, @Nullable Callback<Pair<Result, Exception>> onDone) {
        return new FutureTask<>(callable(param, onDone));
    }
}

package core.plugin.monkey.util.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author DrkCore
 * @since 10/16/17
 */
public class Tasks {

    private static final ConcurrentHashMap<Class, Task> CACHE = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <Param, Result, T extends Task<Param, Result>> T get(Class<? extends Task<Param, Result>> clz) {
        T task = (T) CACHE.get(clz);
        if (task == null) {
            synchronized (CACHE) {
                task = (T) CACHE.get(clz);
                if (task == null) {
                    try {
                        task = (T) clz.newInstance();
                        CACHE.put(clz, task);
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        }
        return task;
    }

    public static <Result> Task<Void, Result> from(Result result) {
        return new Task<Void, Result>() {
            @Override
            public Result exec(Void aVoid) throws Exception {
                return result;
            }
        };
    }

    public static <Result> Task<Void, Result> from(Callable<Result> callable) {
        return new Task<Void, Result>() {
            @Override
            public Result exec(Void aVoid) throws Exception {
                return callable.call();
            }
        };
    }
}

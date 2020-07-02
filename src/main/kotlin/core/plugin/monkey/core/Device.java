package core.plugin.monkey.core;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import core.plugin.monkey.log.ILogCreator;
import core.plugin.monkey.log.LogManager;
import core.plugin.monkey.util.Callback;
import core.plugin.monkey.util.OnTaskListenerImpl;
import core.plugin.monkey.util.Task;
import core.plugin.monkey.util.TextUtil;

/**
 * @author DrkCore
 * @since 2017-01-23
 */
public class Device {
    
    private final String device;
    
    public String getDevice() {
        return device;
    }
    
    public Device(String device) {
        if (TextUtil.isEmpty(device)) {
            throw new IllegalArgumentException("Device must not be null");
        }
        this.device = device;
    }
    
    /*monkey*/
    
    private final Executor executor = Executors.newCachedThreadPool();
    private Task.OnTaskListener<MonkeyTask.Progress, Void> listener;
    
    public Device setListener(Task.OnTaskListener<MonkeyTask.Progress, Void> listener) {
        this.listener = listener;
        return this;
    }
    
    private MonkeyTask task;
    
    public void submit(Monkey monkey) {
        submit(monkey, LogManager.getInstance().getCreator());
    }
    
    public synchronized void submit(Monkey monkey, @Nullable ILogCreator logCreator) {
        terminal(aVoid -> {
            this.task = new MonkeyTask();
            task.addListener(this.listener);
            task.exec(executor, new MonkeyTask.Params(monkey, this, logCreator));
        });
    }
    
    public void terminal() {
        terminal(null);
    }
    
    public synchronized void terminal(@Nullable Callback<Void> callback) {
        if (task != null) {
            task.cancel(false);
            task.waitFor();
            task = null;
        }
        
        new KillMonkeyTask().addListener(new OnTaskListenerImpl<>(callback)).exec(executor, device);
    }
    
}

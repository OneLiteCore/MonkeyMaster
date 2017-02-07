package core.plugin.monkey.core;

import com.intellij.openapi.application.ApplicationManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import core.plugin.monkey.util.Callback;
import core.plugin.monkey.util.FileUtil;
import core.plugin.monkey.util.IOUtil;
import core.plugin.monkey.util.OnTaskListenerImpl;
import core.plugin.monkey.util.Task;
import core.plugin.monkey.util.TextUtil;

/**
 * @author DrkCore
 * @since 2017-01-23
 */
public class Device {
    
    private final String device;
    private final Task.OnTaskListener<String, ByteArrayOutputStream> listener;
    
    public String getDevice() {
        return device;
    }
    
    public Device(String device, Task.OnTaskListener<String, ByteArrayOutputStream> listener) {
        if (TextUtil.isEmpty(device)) {
            throw new IllegalArgumentException("Device must not be null");
        }
        this.device = device;
        this.listener = listener;
    }
    
    /*monkey*/
    
    private class WriteLogListener extends OnTaskListenerImpl<String, ByteArrayOutputStream> {
        
        private final File logfile;
        
        public WriteLogListener(@NotNull File logfile) {
            this.logfile = logfile;
        }
        
        @Override
        public void onSuccess(ByteArrayOutputStream out) {
            super.onSuccess(out);
            ApplicationManager.getApplication().invokeLater(() -> {
                ApplicationManager.getApplication().runWriteAction(() -> {
                    FileOutputStream fos = null;
                    try {
                        File target = FileUtil.getOrCreateFile(logfile);
                        fos = new FileOutputStream(target);
                        out.writeTo(fos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        IOUtil.close(fos);
                    }
                });
            });
        }
    }
    
    private WriteLogListener newWriteListener(@Nullable File logfile) {
        return logfile != null ? new WriteLogListener(logfile) : null;
    }
    
    private final Executor executor = Executors.newSingleThreadExecutor();
    
    private MonkeyTask task;
    
    public void submit(Monkey monkey) {
        submit(monkey, null);
    }
    
    public synchronized void submit(Monkey monkey, @Nullable File logfile) {
        terminal(aVoid -> {
            this.task = new MonkeyTask();
            task.addListener(this.listener);
            task.addListener(newWriteListener(logfile));
            task.exec(executor, new MonkeyTask.Params(monkey, this));
        });
    }
    
    public void terminal() {
        terminal(null);
    }
    
    public synchronized void terminal(@Nullable Callback<Void> callback) {
        if (task != null) {
            task.cancel(false);
            task = null;
        }
        
        new KillMonkeyTask().addListener(new OnTaskListenerImpl<>(callback)).exec(executor, device);
    }
    
}

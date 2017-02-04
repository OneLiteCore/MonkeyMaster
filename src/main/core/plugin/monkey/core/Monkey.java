package core.plugin.monkey.core;

import com.intellij.openapi.application.WriteAction;
import core.plugin.monkey.task.KillMonkeyTask;
import core.plugin.monkey.task.MonkeyTask;
import core.plugin.monkey.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author DrkCore
 * @since 2017-01-23
 */
public class Monkey {

    private final String device;
    private final Task.OnTaskListener<String, ByteArrayOutputStream> listener;

    public String getDevice() {
        return device;
    }

    public Monkey(String device, Task.OnTaskListener<String, ByteArrayOutputStream> listener) {
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
            try {
                WriteAction.run(() -> {
                    FileOutputStream fos = null;
                    try {
                        File target = FileUtil.getOrCreateFile(logfile);
                        fos = new FileOutputStream(target);
                        out.writeTo(fos);
                    } finally {
                        IOUtil.close(fos);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private WriteLogListener newWriteListener(@Nullable File logfile) {
        return logfile != null ? new WriteLogListener(logfile) : null;
    }

    private final Executor executor = Executors.newSingleThreadExecutor();

    private MonkeyTask task;

    public void submit(MonkeyTask.Params params) {
        submit(params, null);
    }

    public void submit(MonkeyTask.Params params, @Nullable File logfile) {
        terminal(aVoid -> {
            this.task = new MonkeyTask();
            task.addListener(this.listener);
            task.addListener(newWriteListener(logfile));
            task.exec(executor, params);
        });
    }

    public void terminal() {
        terminal(null);
    }

    public void terminal(@Nullable Callback<Void> callback) {
        if (task != null) {
            task.cancel(true);
            task = null;
        }

        new KillMonkeyTask().addListener(new OnTaskListenerImpl<>(callback)).exec(executor, device);
    }

}

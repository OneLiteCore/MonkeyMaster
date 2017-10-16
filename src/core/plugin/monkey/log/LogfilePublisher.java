package core.plugin.monkey.log;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

import core.plugin.monkey.core.MonkeyTask;
import core.plugin.monkey.util.FileUtil;
import core.plugin.monkey.util.IOUtil;
import core.plugin.monkey.util.task.OnTaskListenerImpl;

/**
 * @author DrkCore
 * @since 2017-03-19
 */
public abstract class LogfilePublisher extends OnTaskListenerImpl<MonkeyTask.Progress, Void> {
    
    public LogfilePublisher() {
        this(null);
    }
    
    private final Executor executor;
    
    public LogfilePublisher(@Nullable Executor executor) {
        this.executor = executor;
    }
    
    @Override
    public void onProgress(MonkeyTask.Progress progress) {
        super.onProgress(progress);
        ensureThread().offer(progress);
    }
    
    private volatile ReadThread thread;
    
    private ReadThread ensureThread() {
        if (thread == null) {
            synchronized (this) {
                if (thread == null) {
                    thread = new ReadThread();
                    if (executor != null) {
                        executor.execute(thread);
                    } else {
                        thread.start();
                    }
                }
            }
        }
        return thread;
    }
    
    private static final long SLEEP_TIME = 500;
    
    private class ReadThread extends Thread {
        
        private Queue<MonkeyTask.Progress> queue = new ConcurrentLinkedQueue<>();
        private volatile boolean running = true;
        
        ReadThread offer(MonkeyTask.Progress progress) {
            queue.offer(progress);
            return this;
        }
        
        @Override
        public final void run() {
            while (running) {
                MonkeyTask.Progress progress = queue.poll();
                if (progress != null && FileUtil.canRead(progress.output)) {
                    try {
                        read(progress.process, progress.output);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
                
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        private void read(Process process, File logfile) throws Throwable {
            try (BufferedReader reader = IOUtil.open(logfile)) {
                String line;
                while (running) {
                    line = reader.readLine();
                    if (line != null) {
                        onLog(line);
                        
                    } else if (process.isAlive()) {//Wait for next log
                        try {
                            Thread.sleep(SLEEP_TIME);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                        
                    } else {
                        break;
                    }
                }
            }
        }
    }
    
    public abstract void onLog(String line);
}

package core.plugin.monkey.core;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

import core.plugin.monkey.util.IOUtil;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class Runner {
    
    public interface Listener {
        
        void onStart();
        
        void print(String line);
        
        void onFinish(ByteArrayOutputStream out);
    }
    
    public static final int TIMES_INFINITE = -1;
    
    private final String cmd;
    private final int times;
    private Monkey mMonkey;
    private Listener listener;
    
    public Listener getListener() {
        return listener;
    }
    
    public Runner setListener(Listener listener) {
        this.listener = listener;
        return this;
    }
    
    public String getCmd() {
        return cmd;
    }
    
    public int getTimes() {
        return times;
    }
    
    public Runner setup(Monkey monkey) {
        if (monkey == null) {
            throw new IllegalArgumentException("Monkey must not be null");
        }
        this.mMonkey = monkey;
        return this;
    }
    
    Runner(String cmd, int times) {
        this.cmd = cmd;
        if (times <= 0) {
            times = TIMES_INFINITE;
        }
        this.times = times;
    }
    
    /*执行*/
    
    private FutureTask<ByteArrayOutputStream> task;
    
    void submit(Executor executor) {
        if (task != null) {
            throw new IllegalStateException();
        }
        
        if (listener != null) {
            listener.onStart();
        }
        task = new FutureTask<ByteArrayOutputStream>(this::exec) {
            
            @Override
            protected void done() {
                super.done();
                if (listener != null) {
                    try {
                        listener.onFinish(get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
    
    private ByteArrayOutputStream exec() throws IOException {
        Monkey monkey = mMonkey;
        if (monkey == null) {
            throw new IllegalStateException("Runner has not been setup yet");
        }
        
        int times = this.times;
        ByteArrayOutputStream log = new ByteArrayOutputStream();
        try {
            while (!task.isCancelled() && (this.times == TIMES_INFINITE || times-- > 0)) {
                doExec(monkey, log);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return log;
    }
    
    private static final byte[] LINE_SEPERATOR = "\n".getBytes();
    
    private void doExec(Monkey monkey, ByteArrayOutputStream log) throws IOException {
        Process process = monkey.execShell(cmd);
        if (listener != null) {
            InputStream in = process.getInputStream();
            if (in != null) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while (!task.isCancelled() && (line = reader.readLine()) != null) {
                        log.write(line.getBytes());
                        log.write(LINE_SEPERATOR);
                        listener.print(line);
                    }
                } finally {
                    IOUtil.close(reader);
                }
            }
        }
    }
    
    void cancel() {
        if (task != null) {
            task.cancel(false);
        }
    }
}

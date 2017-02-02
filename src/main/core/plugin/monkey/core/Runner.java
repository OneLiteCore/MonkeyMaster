package core.plugin.monkey.core;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import core.plugin.monkey.util.IOUtil;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class Runner extends Thread {
    
    public interface Listener {
        
        void onStart(Runner runner);
        
        void print(String line);
        
        void onFinish(Runner runner);
    }
    
    private final String cmd;
    private final int times;
    
    public String getCmd() {
        return cmd;
    }
    
    public int getTimes() {
        return times;
    }
    
    public static final int TIMES_INFINITE = -1;
    
    Runner(String cmd, int times) {
        this.cmd = cmd;
        if (times <= 0) {
            times = TIMES_INFINITE;
        }
        this.times = times;
    }
    
    private Monkey mMonkey;
    @Nullable
    private Listener mListener;
    
    public Runner setup(Monkey monkey) {
        if (monkey == null) {
            throw new IllegalArgumentException("Monkey must not be null");
        }
        this.mMonkey = monkey;
        return this;
    }
    
    @Nullable
    public Listener getListener() {
        return mListener;
    }
    
    public Runner setListener(@Nullable Listener listener) {
        this.mListener = listener;
        return this;
    }
    
    private ByteArrayOutputStream log;
    
    public ByteArrayOutputStream getLog() {
        return log;
    }
    
    @Override
    public void run() {
        super.run();
        Monkey monkey = mMonkey;
        if (monkey == null) {
            throw new IllegalStateException("Runner has not been setup yet");
        }
        
        if (mListener != null) {
            mListener.onStart(this);
        }
        
        int times = this.times;
        log = new ByteArrayOutputStream();
        try {
            while (!isInterrupted() && (this.times == TIMES_INFINITE || times-- > 0)) {
                doExec(monkey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (mListener != null) {
            mListener.onFinish(this);
        }
    }
    
    private static final byte[] LINE_SEPERATOR = "\n".getBytes();
    
    private void doExec(Monkey monkey) throws IOException {
        Process process = monkey.execShell(cmd);
        if (mListener != null) {
            InputStream in = process.getInputStream();
            if (in != null) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while (!isInterrupted() && (line = reader.readLine()) != null) {
                        log.write(line.getBytes());
                        log.write(LINE_SEPERATOR);
                        mListener.print(line);
                    }
                } finally {
                    IOUtil.close(reader);
                }
            }
        }
    }
    
}

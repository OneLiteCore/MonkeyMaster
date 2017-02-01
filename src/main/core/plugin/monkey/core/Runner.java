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
class Runner extends Thread {
    
    public interface Listener {
        
        void onStart(Runner runner);
        
        void print(String line);
        
        void onFinish(Runner runner);
    }
    
    private final Monkey monkey;
    private final String cmd;
    @Nullable
    private final Listener listener;
    private final int times;
    
    public String getCmd() {
        return cmd;
    }
    
    public int getTimes() {
        return times;
    }
    
    private static final int TIMES_INFINITE = Monkey.TIMES_INFINITE;
    
    Runner(Monkey monkey, String cmd, @Nullable Listener listener, int times) {
        this.monkey = monkey;
        this.cmd = cmd;
        this.listener = listener;
        if (times <= 0) {
            times = TIMES_INFINITE;
        }
        this.times = times;
    }
    
    private ByteArrayOutputStream log;
    
    public ByteArrayOutputStream getLog() {
        return log;
    }
    
    @Override
    public void run() {
        super.run();
        if (listener != null) {
            listener.onStart(this);
        }
        
        int times = this.times;
        log = new ByteArrayOutputStream();
        try {
            while (!isInterrupted() && (this.times == TIMES_INFINITE || times-- > 0)) {
                doExec();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (listener != null) {
            listener.onFinish(this);
        }
    }
    
    private static final byte[] LINE_SEPERATOR = "\n".getBytes();
    
    private void doExec() throws IOException {
        Process process = monkey.execShell(cmd);
        if (listener != null) {
            InputStream in = process.getInputStream();
            if (in != null) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while (!isInterrupted() && (line = reader.readLine()) != null) {
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
    
}

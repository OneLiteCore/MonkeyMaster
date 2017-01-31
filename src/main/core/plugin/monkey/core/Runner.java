package core.plugin.monkey.core;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import core.plugin.monkey.util.IOUtil;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
class Runner extends Thread {
    
    private final Monkey monkey;
    private final String cmd;
    @Nullable
    private final LogPrinter printer;
    private final int times;
    
    public String getCmd() {
        return cmd;
    }
    
    public int getTimes() {
        return times;
    }
    
    private static final int TIMES_INFINITE = Monkey.TIMES_INFINITE;
    
    Runner(Monkey monkey, String cmd, @Nullable LogPrinter printer, int times) {
        this.monkey = monkey;
        this.cmd = cmd;
        this.printer = printer;
        if (times <= 0) {
            times = TIMES_INFINITE;
        }
        this.times = times;
    }
    
    interface Listener{
        
        void onStart(Runner runner);
        
        void onDone(Runner runner);
        
    }
    
    private Listener listener;
    
    public Runner setListener(Listener listener) {
        this.listener = listener;
        return this;
    }
    
    @Override
    public void run() {
        super.run();
        if(listener != null){
            listener.onStart(this);
        }
        
        int times = this.times;
        try {
            while (!isInterrupted() && (this.times == TIMES_INFINITE || times-- > 0)) {
                doExec();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtil.close(printer);
        }
    
        if(listener != null){
            listener.onDone(this);
        }
    }
    
    private void doExec() throws IOException {
        BufferedReader reader = null;
        try {
            Process process = monkey.execShell(cmd);
            if (printer != null) {
                InputStream in = process.getInputStream();
                if (in != null) {
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while (!isInterrupted() && (line = reader.readLine()) != null) {
                        printer.print(line);
                    }
                }
            }
        } finally {
            IOUtil.close(reader);
        }
    }
}

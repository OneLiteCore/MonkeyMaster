package core.plugin.monkey.cmd;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import core.plugin.monkey.log.Log;
import core.plugin.monkey.util.IOUtil;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class Runner extends Thread {
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    private final String cmd;
    private final String device;
    @Nullable
    private final Log log;
    private final int times;
    
    private static final int TIMES_INFINITE = Builder.TIMES_INFINITE;
    
    Runner(String cmd, String device, @Nullable Log log, int times) {
        this.cmd = cmd;
        this.device = device;
        this.log = log;
        this.times = times;
        if (times <= 0) {
            times = TIMES_INFINITE;
        }
    }
    
    @Override
    public void run() {
        super.run();
        int times = this.times;
        try {
            while (!isInterrupted() && (times == TIMES_INFINITE || times-- > 0)) {
                doExec();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(log);
        }
    }
    
    private void doExec() throws IOException {
        BufferedReader reader = null;
        try {
            Process process = Monkey.getInstance().execShell(cmd, device);
            if (log != null) {
                InputStream in = process.getInputStream();
                if (in != null) {
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while (!isInterrupted() && (line = reader.readLine()) != null) {
                        log.write(line);
                    }
                }
            }
        } finally {
            IOUtil.close(reader);
        }
    }
}

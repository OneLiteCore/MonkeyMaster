package core.plugin.monkey.core;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import core.plugin.monkey.util.IOUtil;
import core.plugin.monkey.util.TextUtil;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class Runner extends Thread {
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    private String cmd;
    private String device;
    @Nullable
    private LogPrinter logPrinter;
    private int times;
    
    private static final int TIMES_INFINITE = Builder.TIMES_INFINITE;
    
    Runner(String cmd, String device, @Nullable LogPrinter logPrinter, int times) {
        this.cmd = cmd;
        this.device = device;
        this.logPrinter = logPrinter;
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
            if (TextUtil.isEmpty(device)) {
                device = Monkey.getInstance().findFirstDevices();
            }
            while (!isInterrupted() && (times == TIMES_INFINITE || times-- > 0)) {
                doExec();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(logPrinter);
        }
    }
    
    private void doExec() throws IOException {
        BufferedReader reader = null;
        try {
            Process process = Monkey.getInstance().execShell(cmd, device);
            if (logPrinter != null) {
                InputStream in = process.getInputStream();
                if (in != null) {
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while (!isInterrupted() && (line = reader.readLine()) != null) {
                        logPrinter.print(line);
                    }
                }
            }
        } finally {
            IOUtil.close(reader);
        }
    }
}

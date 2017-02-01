package core.plugin.monkey.core;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.WriteAction;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import core.plugin.monkey.util.DataUtil;
import core.plugin.monkey.util.FileUtil;
import core.plugin.monkey.util.IOUtil;
import core.plugin.monkey.util.TextUtil;

/**
 * @author DrkCore
 * @since 2017-01-23
 */
public class Monkey {
    
    private String device;
    
    public String getDevice() {
        return device;
    }
    
    public Monkey(String device) {
        if (TextUtil.isEmpty(device)) {
            throw new IllegalArgumentException("Device must not be null");
        }
        this.device = device;
    }
    
    /*exec*/
    
    private Runner runner;
    
    public static final int TIMES_INFINITE = -1;
    
    public void submit(String cmd, Runner.Listener printer, @NotNull File logfile) {
        submit(cmd, printer, logfile, 1);
    }
    
    public void submit(String cmd, Runner.Listener listener, @NotNull File logfile, int times) {
        doSubmit(new Runner(this, cmd, new SimpleRunnerListener(listener) {
            @Override
            public void onFinish(Runner runner) {
                super.onFinish(runner);
                ByteArrayOutputStream out = runner.getLog();
                if (out != null) {
                    writeLog(out, logfile);
                }
            }
        }, times));
    }
    
    private void writeLog(ByteArrayOutputStream log, File logfile) {
        ApplicationManager.getApplication().invokeLater(() -> {
            try {
                WriteAction.run(() -> {
                    FileOutputStream fos = null;
                    try {
                        File target = FileUtil.getOrCreateFile(logfile);
                        fos = new FileOutputStream(target);
                        log.writeTo(fos);
                    } finally {
                        IOUtil.close(fos);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    private void doSubmit(Runner runner) {
        clearRunner();
        this.runner = runner;
        runner.start();
    }
    
    private void clearRunner() {
        if (runner != null) {
            try {
                runner.interrupt();
                runner.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                runner = null;
            }
        }
    }
    
    public void terminal() throws IOException {
        clearRunner();
        killMonkeyProcess();
    }
    
    Process execShell(String cmd) throws IOException {
        ensureAdb();
        cmd = "cmd.exe /c adb -s " + device + " shell " + cmd;
        return RUNTIME.exec(cmd);
    }
    
    void killMonkeyProcess() throws IOException {
        ensureAdb();
        String pid = findMonkeyPid();
        if (pid != null) {
            RUNTIME.exec("cmd.exe /c adb -s " + device + " shell kill " + pid);
        }
    }
    
    String findMonkeyPid() throws IOException {
        ensureAdb();
        Process process = RUNTIME.exec("cmd.exe /c adb -s " + device + " shell \"ps|grep monkey\"");
        String info = IOUtil.readQuietly(process.getInputStream(), Charset.defaultCharset());
        //root      9542  76    1234904 40084 ffffffff b7726369 S com.android.commands.monkey
        String[] items = info != null ? info.split(" ") : null;
        String pid = null;
        for (int i = 0, count = 2, len = items != null ? items.length : 0; i < len; i++) {
            String item = items[i];
            if (item != null && !"".equals(item)) {
                if (--count == 0) {
                    pid = item;
                    break;
                }
            }
        }
        return pid;
    }
    
    /*cmd*/
    
    private static final Runtime RUNTIME = Runtime.getRuntime();
    
    private static void ensureAdb() {
        try {
            Process process = RUNTIME.exec("cmd.exe /c adb start-server");
            //读出缓冲区数据并等待进结束
            IOUtil.readQuietly(process.getInputStream());
            IOUtil.readQuietly(process.getErrorStream());
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @NotNull
    public static List<String> findDevices() throws IOException {
        ensureAdb();
        Process process = RUNTIME.exec("cmd.exe /c adb devices");
        String info = IOUtil.readQuietly(process.getInputStream(), Charset.defaultCharset());
        String[] lines = info != null ? info.split("\r|\n") : null;
        
        int len = lines != null ? lines.length : 0;
        List<String> devices = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            String line = lines[i];
            if (line != null && !"".equals(line) && !"List of devices attached".equals(line)) {
                int idx = line.indexOf('\t');
                if (idx != -1) {
                    line = line.substring(0, idx);
                }
                devices.add(line);
            }
        }
        return devices;
    }
    
    public static String findFirstDevices() throws IOException {
        List<String> devices = findDevices();
        return DataUtil.getFirstQuietly(devices);
    }
    
}

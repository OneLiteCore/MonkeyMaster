package core.plugin.monkey.cmd;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import core.plugin.monkey.util.DataUtil;
import core.plugin.monkey.util.IOUtil;

/**
 * @author DrkCore
 * @since 2017-01-23
 */
public class Monkey {
    
    private static final Monkey INSTANCE = new Monkey();
    private static final Runtime RUNTIME = Runtime.getRuntime();
    
    private Monkey() {
    }
    
    public static Monkey getInstance() {
        return INSTANCE;
    }
    
    /*执行*/
    
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    
    void ensureAdb() {
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
    
    public void submit(Runner runner) {
        executor.submit(runner);
    }
    
    public void terminal() throws IOException {
        executor.shutdown();
        killMonkeyProcess();
    }
    
    Process execShell(String cmd) throws IOException {
        return execShell(cmd, null);
    }
    
    Process execShell(String cmd, @Nullable String device) throws IOException {
        ensureAdb();
        StringBuilder builder = new StringBuilder().append("cmd.exe /c adb ");
        if (device != null) {
            builder.append("-s ").append(device).append(" ");
        }
        builder.append("shell ").append(cmd);
        return RUNTIME.exec(builder.toString());
    }
    
    public void killMonkeyProcess() throws IOException {
        ensureAdb();
        String pid = findMonkeyPid();
        if (pid != null) {
            RUNTIME.exec("cmd.exe /c adb shell kill " + pid);
        }
    }
    
    String findMonkeyPid() throws IOException {
        ensureAdb();
        Process process = RUNTIME.exec("cmd.exe /c adb shell \"ps|grep monkey\"");
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
    
    @NotNull
    public List<String> findDevices() throws IOException {
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
    
    public String findFisrtDevices() throws IOException {
        List<String> devices = findDevices();
        return DataUtil.getFirstQuietly(devices);
    }
    
}

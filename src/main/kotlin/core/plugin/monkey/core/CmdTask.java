package core.plugin.monkey.core;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

import core.plugin.monkey.util.FileUtil;
import core.plugin.monkey.util.OS;
import core.plugin.monkey.util.Task;
import core.plugin.monkey.util.TextUtil;

/**
 * @author DrkCore
 * @since 2017-02-04
 */
public abstract class CmdTask<Params, Progress, Result> extends Task<Params, Progress, Result> {
    
    public static final Runtime RUNTIME = Runtime.getRuntime();
    
    protected Process execCmd(String cmd) throws IOException {
        return execCmd(cmd, null);
    }
    
    private static final OS CUR_OS = OS.get();
    
    protected Process execCmd(String cmd, File out) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(CUR_OS.execCmd).append(" ").append(cmd).append(" ");
        if (out != null) {
            File dir = out.getParentFile();
            FileUtil.confirmDir(dir);
            
            builder.append("> ").append(out.getAbsolutePath());
        }
        cmd = builder.toString();
        return RUNTIME.exec(cmd);
    }

    /*Adb*/
    
    protected Process ensureAdb() throws IOException {
        return execCmd("adb start-server");
    }
    
    protected Process execShell(String shellCmd) throws IOException, InterruptedException {
        return execShell(shellCmd, null);
    }
    
    protected Process execShell(String shellCmd, @Nullable String device) throws IOException, InterruptedException {
        return execShell(shellCmd, device, null);
    }
    
    public Process execShell(String shellCmd, @Nullable String device, @Nullable File out) throws IOException, InterruptedException {
        ensureAdb().waitFor();
        if (!TextUtil.isEmpty(device)) {
            shellCmd = "adb -s " + device + " shell " + shellCmd;
        } else {
            shellCmd = "adb shell " + shellCmd;
        }
        return execCmd(shellCmd, out);
    }
}

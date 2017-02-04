package core.plugin.monkey.core;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import core.plugin.monkey.util.IOUtil;
import core.plugin.monkey.util.Task;

/**
 * @author DrkCore
 * @since 2017-02-04
 */
public abstract class CommandTask<Params, Result> extends Task<Params, String, Result> {

    private static final Runtime RUNTIME = Runtime.getRuntime();

    private static final byte[] LINE_SEPARATOR = "\n".getBytes();

    protected final void ensureAdb() throws Exception {
        ensureAdb(null);
    }

    protected final void ensureAdb(@Nullable ByteArrayOutputStream out) throws Exception {
        execCommand("cmd.exe /c adb start-server", out);
    }

    protected final void execShell(String shellCmd, String device) throws Exception {
        execShell(shellCmd, device, null);
    }

    protected final void execShell(String shellCmd, String device, @Nullable ByteArrayOutputStream out) throws Exception {
        ensureAdb();
        shellCmd = "cmd.exe /c adb -s " + device + " shell " + shellCmd;
        execCommand(shellCmd, out);
    }

    protected final void execCommand(String cmd) throws Exception {
        execCommand(cmd, null);
    }

    protected final void execCommand(String cmd, @Nullable ByteArrayOutputStream out) throws Exception {
        publishProgress("exec:" + cmd);

        Process process = RUNTIME.exec(cmd);
        InputStream in = process.getInputStream();
        if (in != null) {
            if (out != null) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while (!isCancelled() && (line = reader.readLine()) != null) {
                        out.write(line.getBytes());
                        out.write(LINE_SEPARATOR);

                        publishProgress(line);
                    }
                } finally {
                    IOUtil.close(reader);
                }
            } else {
                IOUtil.waste(in);
            }
        }

        IOUtil.waste(process.getErrorStream());

        process.waitFor();
    }
}

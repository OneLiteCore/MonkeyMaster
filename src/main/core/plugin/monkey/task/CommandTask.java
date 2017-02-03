package core.plugin.monkey.task;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;

import core.plugin.monkey.util.IOUtil;
import core.plugin.monkey.util.Task;

/**
 * @author DrkCore
 * @since 2017-02-04
 */
public class CommandTask extends Task<CommandTask.Params, String, ByteArrayOutputStream> {
    
    public static final int INFINITE = -1;
    
    public static class Params {
        
        private final String cmd;
        private final int times;
        
        public Params(String cmd, int times) {
            this.cmd = cmd;
            this.times = times;
            if (times < INFINITE || times == 0) {
                throw new IllegalArgumentException();
            }
        }
    }
    
    private static final Runtime RUNTIME = Runtime.getRuntime();
    
    public CommandTask exec(Executor executor, String cmd) {
        exec(executor, new Params(cmd, 1));
        return this;
    }
    
    @Override
    protected ByteArrayOutputStream doInBack(Params params) throws Exception {
        int times = params.times;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (!isCancelled() && (params.times == INFINITE || times-- > 0)) {
            doExec(params.cmd, out);
        }
        return out;
    }
    
    private static final byte[] LINE_SEPERATOR = "\n".getBytes();
    
    private void doExec(String cmd, ByteArrayOutputStream out) throws IOException {
        Process process = RUNTIME.exec(cmd);
        InputStream in = process.getInputStream();
        if (in != null) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while (!isCancelled() && (line = reader.readLine()) != null) {
                    out.write(line.getBytes());
                    out.write(LINE_SEPERATOR);
                    publishProgress(line);
                }
            } finally {
                IOUtil.close(reader);
            }
        }
    }
}

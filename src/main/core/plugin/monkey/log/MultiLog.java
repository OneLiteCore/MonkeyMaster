package core.plugin.monkey.log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class MultiLog implements Log {
    
    private final List<Log> logs = new ArrayList<>();
    
    public MultiLog(Log... logs) {
        Collections.addAll(this.logs, logs);
    }
    
    public synchronized void add(Log log) {
        if (log == this) {
            throw new IllegalArgumentException();
        }
        logs.add(log);
    }
    
    @Override
    public synchronized void write(String line) throws IOException {
        IOException firstException = null;
        for (int i = 0, len = logs.size(); i < len; i++) {
            try {
                logs.get(i).write(line);
            } catch (IOException e) {
                e.printStackTrace();
                if (firstException == null) {
                    firstException = e;
                }
            }
        }
        if (firstException != null) {
            throw firstException;
        }
    }
    
    @Override
    public synchronized void close() throws IOException {
        IOException firstException = null;
        for (int i = 0, len = logs.size(); i < len; i++) {
            try {
                logs.get(i).close();
            } catch (IOException e) {
                e.printStackTrace();
                if (firstException == null) {
                    firstException = e;
                }
            }
        }
        if (firstException != null) {
            throw firstException;
        }
    }
    
    @Override
    public synchronized void flush() throws IOException {
        IOException firstException = null;
        for (int i = 0, len = logs.size(); i < len; i++) {
            try {
                logs.get(i).flush();
            } catch (IOException e) {
                e.printStackTrace();
                if (firstException == null) {
                    firstException = e;
                }
            }
        }
        if (firstException != null) {
            throw firstException;
        }
    }
}

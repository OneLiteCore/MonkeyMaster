package core.plugin.monkey.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class MultiLogPrinter implements LogPrinter {
    
    private final List<LogPrinter> logPrinters = new ArrayList<>();
    
    public MultiLogPrinter(LogPrinter... logPrinters) {
        Collections.addAll(this.logPrinters, logPrinters);
    }
    
    public synchronized void add(LogPrinter logPrinter) {
        if (logPrinter == this) {
            throw new IllegalArgumentException();
        }
        logPrinters.add(logPrinter);
    }
    
    @Override
    public synchronized void print(String line) throws IOException {
        IOException firstException = null;
        for (int i = 0, len = logPrinters.size(); i < len; i++) {
            try {
                logPrinters.get(i).print(line);
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
        for (int i = 0, len = logPrinters.size(); i < len; i++) {
            try {
                logPrinters.get(i).close();
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
        for (int i = 0, len = logPrinters.size(); i < len; i++) {
            try {
                logPrinters.get(i).flush();
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

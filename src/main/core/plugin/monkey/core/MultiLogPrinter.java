package core.plugin.monkey.core;

import java.io.File;
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
    
    public MultiLogPrinter() {
        
    }
    
    public MultiLogPrinter(LogPrinter... logPrinters) {
        Collections.addAll(this.logPrinters, logPrinters);
    }
    
    public synchronized MultiLogPrinter add(LogPrinter logPrinter) {
        if (logPrinter == this) {
            throw new IllegalArgumentException();
        }
        logPrinters.add(logPrinter);
        return this;
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

    /*简化*/
    
    
    public MultiLogPrinter addLogFile(String logPath) {
        return addLogFile(new File(logPath));
    }
    
    public MultiLogPrinter addLogFile(File log) {
        return add(new FilePrinter(log));
    }
    
    public MultiLogPrinter addCurrentLogFile() {
        return addLogFile(LogManager.getInstance().newLogFile());
    }
    
    public MultiLogPrinter addConsolePrinter() {
        return add(new ConsolePrinter());
    }
    
}

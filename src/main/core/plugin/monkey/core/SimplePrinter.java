package core.plugin.monkey.core;

import java.io.IOException;

/**
 * @author DrkCore
 * @since 2017-01-26
 */
public abstract class SimplePrinter implements LogPrinter {
    
    public void printQuietly(String line) {
        try {
            print(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void close() throws IOException {
        
    }
    
    @Override
    public void flush() throws IOException {
        
    }
}

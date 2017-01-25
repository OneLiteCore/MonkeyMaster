package core.plugin.monkey.log;

import java.io.IOException;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class ConsoleLog implements Log {
    
    @Override
    public void write(String line) throws IOException {
        System.out.println(line);
    }
    
    @Override
    public void close() throws IOException {
        
    }
    
    @Override
    public void flush() throws IOException {
        
    }
}

package core.plugin.monkey.core;

import java.io.IOException;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class ConsolePrinter extends SimplePrinter{
    
    @Override
    public void print(String line) throws IOException {
        System.out.println(line);
    }
}

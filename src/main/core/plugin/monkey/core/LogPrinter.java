package core.plugin.monkey.core;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public interface LogPrinter extends Flushable, Closeable {
    
    void print(String line) throws IOException;
}

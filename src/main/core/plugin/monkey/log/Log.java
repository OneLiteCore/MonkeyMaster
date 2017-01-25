package core.plugin.monkey.log;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public interface Log extends Flushable, Closeable {
    
    void write(String line) throws IOException;
}

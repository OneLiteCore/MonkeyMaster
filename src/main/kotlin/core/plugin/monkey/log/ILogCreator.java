package core.plugin.monkey.log;

import java.io.File;
import java.io.IOException;

/**
 * @author DrkCore
 * @since 2017-03-19
 */
public interface ILogCreator {
    
    File newLogFile() throws IOException;
    
}

package core.plugin.monkey.log;

import java.io.File;
import java.io.IOException;

import core.plugin.monkey.util.FileUtil;

/**
 * @author DrkCore
 * @since 2017-03-19
 */
public class FileLogCreator implements ILogCreator {
    
    private final File logfile;
    
    public FileLogCreator(String path) {
        this(new File(path));
    }
    
    public FileLogCreator(File logfile) {
        this.logfile = logfile;
    }
    
    @Override
    public File newLogFile() throws IOException {
        return FileUtil.confirmFile(logfile);
    }
}

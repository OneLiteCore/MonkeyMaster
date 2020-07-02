package core.plugin.monkey.log;

import org.jetbrains.annotations.Nullable;

import java.io.File;

import core.plugin.monkey.util.TextUtil;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class LogManager {
    
    private static volatile LogManager instance;
    
    public static LogManager getInstance() {
        return getInstance(null);
    }
    
    public static LogManager getInstance(@Nullable String project) {
        if (instance == null) {
            synchronized (LogManager.class) {
                if (instance == null) {
                    if (!TextUtil.isEmpty(project)) {
                        instance = new LogManager(project);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
        return instance;
    }
    
    public static final String LOG_DIR = "monkey";
    public static final String LOG_EXT = "monkey";
    
    private final String project;
    private final File logDir;
    
    private LogManager(String project) {
        this.project = project;
        this.logDir = new File(project, LOG_DIR);
    }
    
    /*ILogCreator*/
    
    private volatile ILogCreator creator;
    private final byte[] logCreatorLoc = new byte[0];
    
    public ILogCreator getCreator() {
        if (creator == null) {
            synchronized (logCreatorLoc) {
                if (creator == null) {
                    creator = new SimpleLogCreator(logDir, LOG_EXT);
                }
            }
        }
        return creator;
    }
    
}

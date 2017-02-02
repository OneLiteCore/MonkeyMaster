package core.plugin.monkey.core;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class LogManager {
    
    private static final LogManager INSTANCE = new LogManager();
    
    private LogManager() {
    }
    
    public static LogManager getInstance() {
        return INSTANCE;
    }
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    public static final String LOG_DIR = "/monkey";
    public static final String LOG_EXT = "monkey";
    
    private String lastLog;
    private long lastTime;
    
    public synchronized String newLogName() {
        long time = System.currentTimeMillis();
        if (time < lastTime) {
            time = lastTime + 1000;
        }
        String log = dateFormat.format(time);
        while (log.equals(lastLog)) {
            time += 1000;
            log = dateFormat.format(time);
        }
        lastLog = log;
        lastTime = time;
        String name = log + "." + LOG_EXT;
        return name;
    }
    
    public File newLogFile(String projectDir) {
        projectDir += LOG_DIR;
        String name = newLogName();
        return new File(projectDir, name);
    }
}

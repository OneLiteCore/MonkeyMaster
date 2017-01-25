package core.plugin.monkey.log;

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
    
    private File outputDir;
    
    public File getOutputDir() {
        if (outputDir == null) {
            outputDir = new File("monkey");
        }
        if(!outputDir.isDirectory()){
            outputDir.mkdirs();
        }
        return outputDir;
    }
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    public static final String LOG_EXT = "log";
    
    public File newLogFile() {
        String name = dateFormat.format(System.currentTimeMillis()) + "." + LOG_EXT;
        return new File(getOutputDir(), name);
    }
}

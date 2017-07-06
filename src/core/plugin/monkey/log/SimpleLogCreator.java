package core.plugin.monkey.log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import core.plugin.monkey.util.FileUtil;

/**
 * @author DrkCore
 * @since 2017-03-19
 */
public class SimpleLogCreator implements ILogCreator {
    
    private final String logDir;
    private final String extName;
    
    public SimpleLogCreator(File logDir, String extName) {
        this(logDir.getAbsolutePath(), extName);
    }
    
    public SimpleLogCreator(String logDir, String extName) {
        this.logDir = logDir;
        this.extName = extName;
    }
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    
    private String lastLog;
    private long lastTime;
    
    private synchronized String newLogName() {
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
        return log + "." + extName;
    }
    
    public File newLogFile() throws IOException {
        String name = newLogName();
        return FileUtil.confirmFile(new File(logDir, name));
    }
    
}

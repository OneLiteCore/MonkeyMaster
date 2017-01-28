package core.plugin.monkey.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class Builder {
    
    private final Map<String, String> map = new HashMap<>();
    
    Builder() {
        
    }
    
    private Builder appendParams(Object... objs) {
        int len = objs != null ? objs.length : 0;
        if (len >= 1) {
            String key = objs[0].toString();
            String value = "";
            for (int i = 1; i < len; i++) {
                value += objs[i].toString();
            }
            map.put(key, value);
        }
        return this;
    }
    
    public Builder reset() {
        map.clear();
        count = DEFAULT_COUNT;
        return this;
    }
    
    public Runner build() {
        StringBuilder builder = new StringBuilder();
        builder.append("monkey ");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append(entry.getKey()).append(" ").append(entry.getValue()).append(" ");
        }
        builder.append(count);
        String cmd = builder.toString();
        
        return new Runner(cmd, device, logPrinter, times);
    }

    /*参数*/
    
    private String device;
    
    public Builder setDevice(String device) {
        this.device = device;
        return this;
    }
    
    private LogPrinter logPrinter;
    
    public Builder setLogPrinter(LogPrinter logPrinter) {
        this.logPrinter = logPrinter;
        return this;
    }
    
    public static final int TIMES_INFINITE = -1;
    private int times = 1;
    
    /**
     * 设置执行次数。
     * <p>
     * 如果times小于等于则表示无限次。
     *
     * @param times
     * @return
     */
    public Builder setTimes(int times) {
        if (times <= 0) {
            this.times = TIMES_INFINITE;
        } else {
            this.times = times;
        }
        return this;
    }
    
    public Builder setPctTouch(float percent) {
        return appendParams("--pct-touch", percent);
    }
    
    public Builder setPctMotion(float percent) {
        return appendParams("--pct-motion", percent);
    }
    
    public Builder setPctTrackball(float percent) {
        return appendParams("--pct-trackball", percent);
    }
    
    public Builder setPctSyskeys(float percent) {
        return appendParams("--pct-syskeys", percent);
    }
    
    public Builder setPctNav(float percent) {
        return appendParams("--pct-nav", percent);
    }
    
    public Builder setPctMajornav(float percent) {
        return appendParams("--pct-majornav", percent);
    }
    
    public Builder setPctAppswitch(float percent) {
        return appendParams("--pct-appswitch", percent);
    }
    
    public Builder setPctFlip(float percent) {
        return appendParams("--pct-flip", percent);
    }
    
    public Builder setPctAnyevent(float percent) {
        return appendParams("--pct-anyevent", percent);
    }
    
    public Builder setPctPinchzoom(float percent) {
        return appendParams("--pct-pinchzoom", percent);
    }
    
    public Builder setHprof() {
        return appendParams("--hprof");
    }
    
    public Builder setBugReport() {
        return appendParams("--bugreport");
    }
    
    public Builder setIgnoreCrashes() {
        return appendParams("--ignore-crashes");
    }
    
    public Builder setIgnoreTimeouts() {
        return appendParams("--ignore-timeouts");
    }
    
    public Builder setWaitDbg() {
        return appendParams("--wait-dbg");
    }
    
    public Builder setRandomizeThrottle() {
        return appendParams("--randomize-throttle");
    }
    
    public Builder setRandomizeScript() {
        return appendParams("--randomize-script");
    }
    
    public Builder setScriptLog() {
        return appendParams("--script-logPrinter");
    }
    
    public Builder setPeriodicBugreport() {
        return appendParams("--periodic-bugreport");
    }
    
    public Builder setDbgNoEvents() {
        return appendParams("--dbg-no-events");
    }
    
    public Builder setIgnoreSecurityExceptions() {
        return appendParams("--ignore-security-exceptions");
    }
    
    public Builder setMonitorNativeCrashes() {
        return appendParams("--monitor-native-crashes");
    }
    
    public Builder setIgnoreNativeCrashes() {
        return appendParams("--ignore-native-crashes");
    }
    
    public Builder setKillProcessAfterError() {
        return appendParams("--kill-process-after-error");
    }
    
    public Builder setAllowedPackage(String... pkgs) {
        for (String item : pkgs) {
            appendParams("-p", item);
        }
        return this;
    }
    
    public Builder setMainCategory(String... categories) {
        for (String item : categories) {
            appendParams("-c", item);
        }
        return this;
    }
    
    public Builder setScriptFile(String... files) {
        for (String item : files) {
            appendParams("-f", item);
        }
        return this;
    }
    
    public Builder setPackageBlacklistFile(String file) {
        return appendParams("--pkg-blacklist-file", file);
    }
    
    public Builder setPackageWhitelistFile(String file) {
        return appendParams("--pkg-whitelist-file", file);
    }
    
    public Builder setDeviceSleepTime(long milliSec) {
        return appendParams("--device-sleep-time", milliSec);
    }
    
    public Builder setScriptFile(String file) {
        return appendParams("--setup", file);
    }
    
    public Builder setThrottle(long milliSec) {
        return appendParams("--throttle", milliSec);
    }
    
    public Builder setPort(int port) {
        return appendParams("--port", port);
    }
    
    public Builder setSeed(int seed) {
        return appendParams("-s", seed);
    }
    
    public static final int LEVEL_SIMPLE = 1;
    public static final int LEVEL_NORMAL = 2;
    public static final int LEVEL_ALL = 3;
    
    public Builder setLogLevel(int level) {
        if (level < LEVEL_SIMPLE || level > LEVEL_ALL) {
            throw new IllegalArgumentException();
            
        }
        switch (level) {
            case LEVEL_SIMPLE:
                appendParams("-v");
                break;
            
            case LEVEL_NORMAL:
                appendParams("-v", "-v");
                break;
            
            case LEVEL_ALL:
                appendParams("-v", "-v -v");
                break;
        }
        return this;
    }
    
    public Builder setLogAll() {
        return setLogLevel(LEVEL_ALL);
    }
    
    public Builder setProfileWait(long milliSec) {
        return appendParams("--profile-wait", milliSec);
    }
    
    public static final int DEFAULT_COUNT = 1000;
    
    private long count = DEFAULT_COUNT;
    
    public Builder setCount(long count) {
        this.count = count;
        return this;
    }

    /*简化*/
    
    public Builder setIgnoreAll() {
        setIgnoreCrashes();
        setIgnoreNativeCrashes();
        setIgnoreSecurityExceptions();
        setIgnoreTimeouts();
        return this;
    }
    
    public Builder setTotalTime(long totalMilliSec) {
        return setTotalTime(totalMilliSec, 50);
    }
    
    public Builder setTotalTime(long totalMilliSec, long throttleMilliSec) {
        if (totalMilliSec <= 0 || throttleMilliSec <= 0) {
            throw new IllegalStateException("IllegalTime：totalMilliSec " + totalMilliSec + "  throttleMilliSec " + throttleMilliSec);
        }
        
        setThrottle(throttleMilliSec);
        setCount(totalMilliSec / throttleMilliSec);
        
        return this;
    }
    
    public Builder setCount(long count, long throttleMilliSec) {
        setCount(count);
        setThrottle(throttleMilliSec);
        return this;
    }
    
    public Builder addLogPrinter(LogPrinter logPrinter) {
        if (this.logPrinter == null) {
            this.logPrinter = logPrinter;
        } else if (this.logPrinter instanceof MultiLogPrinter) {
            ((MultiLogPrinter) this.logPrinter).add(logPrinter);
        } else {
            MultiLogPrinter multiLogger = new MultiLogPrinter(this.logPrinter);
            this.logPrinter = multiLogger;
            multiLogger.add(logPrinter);
        }
        return this;
    }
    
    public Builder addLogFile(String logPath) {
        return addLogFile(new File(logPath));
    }
    
    public Builder addLogFile(File log) {
        return addLogPrinter(new FilePrinter(log));
    }
    
    public Builder addLogFile() {
        return addLogFile(LogManager.getInstance().newLogFile());
    }
    
    public Builder addConsolePrinter() {
        return addLogPrinter(new ConsolePrinter());
    }
    
}

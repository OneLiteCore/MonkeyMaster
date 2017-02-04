package core.plugin.monkey.core;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.plugin.monkey.util.TextUtil;

/**
 * @author DrkCore
 * @since 2017-02-04
 */
public class Monkey {
    
    public static final int INFINITE = -1;
    
    private final String cmd;
    private final int times;
    
    public String getCmd() {
        return cmd;
    }
    
    public int getTimes() {
        return times;
    }
    
    public boolean isInfinite(){
        return times == INFINITE;
    }
    
    private Monkey(String cmd, int times) {
        this.cmd = cmd;
        this.times = times;
        if (times < INFINITE || times == 0) {
            throw new IllegalArgumentException();
        }
    }
    
    private static class CmdBuilder {
        
        private final StringBuilder builder = new StringBuilder();
        
        void appendNumberEx(String key, Number num) {
            if (num != null) {
                appendParam(key, num);
            }
        }
        
        void appendListEx(String key, List<String> list) {
            if (list != null) {
                list.forEach(s -> appendStringEx(key, s));
            }
        }
        
        void appendStringEx(String key, String str) {
            if (!TextUtil.isEmpty(str)) {
                appendParam(key, str);
            }
        }
        
        void appendBooleanEx(String key, boolean value) {
            if (value) {
                appendKey(key);
            }
        }
        
        void appendKey(String key) {
            appendParam(key, null);
        }
        
        void appendParam(String key, @Nullable Object param) {
            builder.append(key).append(" ");
            if (param != null) {
                builder.append(param).append(" ");
            }
        }
        
        void append(Object str) {
            builder.append(str);
        }
        
        boolean contain(String str) {
            return builder.indexOf(str) != -1;
        }
        
        @Override
        public String toString() {
            return builder.toString();
        }
    }
    
    public static class Builder implements Serializable, Cloneable {
        
        private static final String KEY_MONKEY = "monkey";
        
        public Monkey build() {
            CmdBuilder builder = new CmdBuilder();
            builder.appendKey(KEY_MONKEY);
            
            builder.appendListEx(KEY_ALLOWED_PACKAGE, allowedPackages);
            builder.appendListEx(KEY_MAIN_CATEGORY, mainCategories);
            
            builder.appendBooleanEx(KEY_IGNORE_CRASHES, ignoreCrashes);
            builder.appendBooleanEx(KEY_IGNORE_TIMEOUTS, ignoreTimeouts);
            builder.appendBooleanEx(KEY_IGNORE_SECURITY_EXCEPTIONS, ignoreSecurityExceptions);
            builder.appendBooleanEx(KEY_MONITOR_NATIVE_CRASHES, monitorNativeCrashes);
            builder.appendBooleanEx(KEY_IGNORE_NATIVE_CRASHES, ignoreNativeCrashes);
            
            builder.appendBooleanEx(KEY_KILL_PROCESS_AFTER_ERROR, killProcessAfterError);
            builder.appendBooleanEx(KEY_HPROF, hprof);
            
            builder.appendNumberEx(KEY_PCT_TOUCH, pctTouch);
            builder.appendNumberEx(KEY_PCT_MOTION, pctMotion);
            builder.appendNumberEx(KEY_PCT_TRACKBALL, pctTrackball);
            builder.appendNumberEx(KEY_PCT_SYSKEYS, pctSyskeys);
            builder.appendNumberEx(KEY_PCT_NAV, pctNav);
            builder.appendNumberEx(KEY_PCT_MAJORNAV, pctMajornav);
            builder.appendNumberEx(KEY_PCT_APPSWITCH, pctAppswitch);
            builder.appendNumberEx(KEY_PCT_FLIP, pctFlip);
            builder.appendNumberEx(KEY_PCT_ANYEVENT, pctAnyevent);
            builder.appendNumberEx(KEY_PCT_PINCHZOOM, pctPinchzoom);
            
            builder.appendStringEx(KEY_PKG_BLACKLIST_FILE, packageBlacklistFile);
            builder.appendStringEx(KEY_PKG_WHITELIST_FILE, packageWhitelistFile);
            
            builder.appendBooleanEx(KEY_WAIT_DBG, waitDbg);
            builder.appendBooleanEx(KEY_DBG_NO_EVENTS, dbgNoEvents);
            
            if (scriptFiles != null) {
                scriptFiles.forEach(s -> {
                    if (!TextUtil.isEmpty(s)) {
                        if (!builder.contain(KEY_SCRIPTFILE)) {
                            builder.appendParam(KEY_SCRIPTFILE, s);
                        } else {
                            builder.appendParam(KEY_SCRIPTFILE_TAG, s);
                        }
                    }
                });
            }
            
            builder.appendNumberEx(KEY_PORT, port);
            builder.appendNumberEx(KEY_SEED, seed);
            
            if (logLevel != null) {
                builder.appendKey(logLevel.getParams());
            }
            
            builder.appendNumberEx(KEY_THROTTLE, throttle);
            
            builder.appendBooleanEx(KEY_RANDOMIZE_THROTTLE, randomizeThrottle);
            
            builder.appendNumberEx(KEY_PROFILE_WAIT, profileWait);
            builder.appendNumberEx(KEY_DEVICE_SLEEP_TIME, deviceSleepTime);
            
            builder.appendBooleanEx(KEY_RANDOMIZE_SCRIPT, randomizeScript);
            builder.appendBooleanEx(KEY_SCRIPT_LOG, scriptLog);
            builder.appendBooleanEx(KEY_BUGREPORT, bugreport);
            builder.appendBooleanEx(KEY_PERIODIC_BUGREPORT, periodicBugreport);
            
            builder.append(count);
            
            String cmd = builder.toString();
            return new Monkey(cmd, times);
        }
    
        /*Monkey参数*/
        
        private static final String KEY_ALLOWED_PACKAGE = "-p";
        private ArrayList<String> allowedPackages;
        
        public Builder setAllowedPackages(String... pkgs) {
            List<String> list = pkgs != null ? Arrays.asList(pkgs) : null;
            return setAllowedPackages(list);
        }
        
        public Builder setAllowedPackages(List<String> pkgs) {
            this.allowedPackages = pkgs != null ? new ArrayList<>(pkgs) : null;
            return this;
        }
        
        public Builder addAllowedPackages(String pkg) {
            if (this.allowedPackages == null) {
                this.allowedPackages = new ArrayList<>();
            }
            this.allowedPackages.add(pkg);
            return this;
        }
        
        public ArrayList<String> getAllowedPackages() {
            return allowedPackages;
        }
        
        private static final String KEY_MAIN_CATEGORY = "-c";
        private ArrayList<String> mainCategories;
        
        public Builder setMainCategories(String... categories) {
            List<String> list = categories != null ? Arrays.asList(categories) : null;
            return setMainCategories(list);
        }
        
        public Builder setMainCategories(List<String> categories) {
            this.mainCategories = categories != null ? new ArrayList<>(categories) : null;
            return this;
        }
        
        public Builder addMainCategories(String category) {
            if (this.mainCategories == null) {
                this.mainCategories = new ArrayList<>();
            }
            this.mainCategories.add(category);
            return this;
        }
        
        public ArrayList<String> getMainCategories() {
            return mainCategories;
        }
        
        private static final String KEY_SCRIPTFILE = "--setup";
        private static final String KEY_SCRIPTFILE_TAG = "-f";
        private ArrayList<String> scriptFiles;
        
        public Builder setScriptFiles(ArrayList<String> scriptFiles) {
            this.scriptFiles = scriptFiles;
            return this;
        }
        
        public Builder addScriptFiles(String scriptFile) {
            if (this.scriptFiles == null) {
                this.scriptFiles = new ArrayList<>();
            }
            this.scriptFiles.add(scriptFile);
            return this;
        }
        
        public ArrayList<String> getScriptFiles() {
            return scriptFiles;
        }
        
        public static final String LEVEL_SIMPLE = "-v";
        public static final String LEVEL_NORMAL = "-v -v";
        public static final String LEVEL_ALL = "-v -v -v";
        
        private LogLevel logLevel;
        
        public Builder setLogLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }
        
        public LogLevel getLogLevel() {
            return logLevel;
        }
        
        private static final String KEY_IGNORE_CRASHES = "--ignore-crashes";
        private boolean ignoreCrashes;
        
        public boolean isIgnoreCrashes() {
            return ignoreCrashes;
        }
        
        public Builder setIgnoreCrashes(boolean ignoreCrashes) {
            this.ignoreCrashes = ignoreCrashes;
            return this;
        }
        
        private static final String KEY_IGNORE_TIMEOUTS = "--ignore-timeouts";
        private boolean ignoreTimeouts;
        
        public boolean isIgnoreTimeouts() {
            return ignoreTimeouts;
        }
        
        public Builder setIgnoreTimeouts(boolean ignoreTimeouts) {
            this.ignoreTimeouts = ignoreTimeouts;
            return this;
        }
        
        private static final String KEY_IGNORE_SECURITY_EXCEPTIONS = "--ignore-security-exceptions";
        private boolean ignoreSecurityExceptions;
        
        public boolean isIgnoreSecurityExceptions() {
            return ignoreSecurityExceptions;
        }
        
        public Builder setIgnoreSecurityExceptions(boolean ignoreSecurityExceptions) {
            this.ignoreSecurityExceptions = ignoreSecurityExceptions;
            return this;
        }
        
        private static final String KEY_MONITOR_NATIVE_CRASHES = "--monitor-native-crashes";
        private boolean monitorNativeCrashes;
        
        public boolean isMonitorNativeCrashes() {
            return monitorNativeCrashes;
        }
        
        public Builder setMonitorNativeCrashes(boolean monitorNativeCrashes) {
            this.monitorNativeCrashes = monitorNativeCrashes;
            return this;
        }
        
        private static final String KEY_IGNORE_NATIVE_CRASHES = "--ignore-native-crashes";
        private boolean ignoreNativeCrashes;
        
        public boolean isIgnoreNativeCrashes() {
            return ignoreNativeCrashes;
        }
        
        public Builder setIgnoreNativeCrashes(boolean ignoreNativeCrashes) {
            this.ignoreNativeCrashes = ignoreNativeCrashes;
            return this;
        }
        
        private static final String KEY_KILL_PROCESS_AFTER_ERROR = "--kill-process-after-error";
        private boolean killProcessAfterError;
        
        public boolean isKillProcessAfterError() {
            return killProcessAfterError;
        }
        
        public Builder setKillProcessAfterError(boolean killProcessAfterError) {
            this.killProcessAfterError = killProcessAfterError;
            return this;
        }
        
        private static final String KEY_HPROF = "--hprof";
        private boolean hprof;
        
        public boolean isHprof() {
            return hprof;
        }
        
        public Builder setHprof(boolean hprof) {
            this.hprof = hprof;
            return this;
        }
        
        private static final String KEY_WAIT_DBG = "--wait-dbg";
        private boolean waitDbg;
        
        public boolean isWaitDbg() {
            return waitDbg;
        }
        
        public Builder setWaitDbg(boolean waitDbg) {
            this.waitDbg = waitDbg;
            return this;
        }
        
        private static final String KEY_DBG_NO_EVENTS = "--dbg-no-events";
        private boolean dbgNoEvents;
        
        public boolean isDbgNoEvents() {
            return dbgNoEvents;
        }
        
        public Builder setDbgNoEvents(boolean dbgNoEvents) {
            this.dbgNoEvents = dbgNoEvents;
            return this;
        }
        
        private static final String KEY_RANDOMIZE_THROTTLE = "--randomize-throttle";
        private boolean randomizeThrottle;
        
        public boolean isRandomizeThrottle() {
            return randomizeThrottle;
        }
        
        public Builder setRandomizeThrottle(boolean randomizeThrottle) {
            this.randomizeThrottle = randomizeThrottle;
            return this;
        }
        
        private static final String KEY_RANDOMIZE_SCRIPT = "--randomize-script";
        private boolean randomizeScript;
        
        public boolean isRandomizeScript() {
            return randomizeScript;
        }
        
        public Builder setRandomizeScript(boolean randomizeScript) {
            this.randomizeScript = randomizeScript;
            return this;
        }
        
        private static final String KEY_SCRIPT_LOG = "--script-log";
        private boolean scriptLog;
        
        public boolean isScriptLog() {
            return scriptLog;
        }
        
        public Builder setScriptLog(boolean scriptLog) {
            this.scriptLog = scriptLog;
            return this;
        }
        
        private static final String KEY_BUGREPORT = "--bugreport";
        private boolean bugreport;
        
        public boolean isBugreport() {
            return bugreport;
        }
        
        public Builder setBugreport(boolean bugreport) {
            this.bugreport = bugreport;
            return this;
        }
        
        private static final String KEY_PERIODIC_BUGREPORT = "--periodic-bugreport";
        private boolean periodicBugreport;
        
        public boolean isPeriodicBugreport() {
            return periodicBugreport;
        }
        
        public Builder setPeriodicBugreport(boolean periodicBugreport) {
            this.periodicBugreport = periodicBugreport;
            return this;
        }
        
        private static final String KEY_PCT_TOUCH = "--pct-touch";
        private Float pctTouch;
        
        public Builder setPctTouch(Float percent) {
            this.pctTouch = percent;
            return this;
        }
        
        public Float getPctTouch() {
            return pctTouch;
        }
        
        private static final String KEY_PCT_MOTION = "--pct-motion";
        private Float pctMotion;
        
        public Builder setPctMotion(Float percent) {
            this.pctMotion = percent;
            return this;
        }
        
        public Float getPctMotion() {
            return pctMotion;
        }
        
        private static final String KEY_PCT_TRACKBALL = "--pct-trackball";
        private Float pctTrackball;
        
        public Builder setPctTrackball(Float percent) {
            this.pctTrackball = percent;
            return this;
        }
        
        public Float getPctTrackball() {
            return pctTrackball;
        }
        
        private static final String KEY_PCT_SYSKEYS = "--pct-syskeys";
        private Float pctSyskeys;
        
        public Builder setPctSyskeys(Float percent) {
            this.pctSyskeys = percent;
            return this;
        }
        
        public Float getPctSyskeys() {
            return pctSyskeys;
        }
        
        private static final String KEY_PCT_NAV = "--pct-nav";
        private Float pctNav;
        
        public Builder setPctNav(Float percent) {
            this.pctNav = percent;
            return this;
        }
        
        public Float getPctNav() {
            return pctNav;
        }
        
        private static final String KEY_PCT_MAJORNAV = "--pct-majornav";
        private Float pctMajornav;
        
        public Builder setPctMajornav(Float percent) {
            this.pctMajornav = percent;
            return this;
        }
        
        public Float getPctMajornav() {
            return pctMajornav;
        }
        
        private static final String KEY_PCT_APPSWITCH = "--pct-appswitch";
        private Float pctAppswitch;
        
        public Builder setPctAppswitch(Float percent) {
            this.pctAppswitch = percent;
            return this;
        }
        
        public Float getPctAppswitch() {
            return pctAppswitch;
        }
        
        private static final String KEY_PCT_FLIP = "--pct-flip";
        private Float pctFlip;
        
        public Builder setPctFlip(Float percent) {
            this.pctFlip = percent;
            return this;
        }
        
        public Float getPctFlip() {
            return pctFlip;
        }
        
        private static final String KEY_PCT_ANYEVENT = "--pct-anyevent";
        private Float pctAnyevent;
        
        public Builder setPctAnyevent(Float percent) {
            this.pctAnyevent = percent;
            return this;
        }
        
        public Float getPctAnyevent() {
            return pctAnyevent;
        }
        
        private static final String KEY_PCT_PINCHZOOM = "--pct-pinchzoom";
        private Float pctPinchzoom;
        
        public Builder setPctPinchzoom(Float percent) {
            this.pctPinchzoom = percent;
            return this;
        }
        
        public Float getPctPinchzoom() {
            return pctPinchzoom;
        }
        
        private static final String KEY_PKG_BLACKLIST_FILE = "--pkg-blacklist-file";
        private String packageBlacklistFile;
        
        public Builder setPackageBlacklistFile(String packageBlacklistFile) {
            this.packageBlacklistFile = packageBlacklistFile;
            return this;
        }
        
        public String getPackageBlacklistFile() {
            return packageBlacklistFile;
        }
        
        private static final String KEY_PKG_WHITELIST_FILE = "--pkg-whitelist-file";
        private String packageWhitelistFile;
        
        public String getPackageWhitelistFile() {
            return packageWhitelistFile;
        }
        
        public Builder setPackageWhitelistFile(String packageWhitelistFile) {
            this.packageWhitelistFile = packageWhitelistFile;
            return this;
        }
        
        private static final String KEY_PORT = "--port";
        private Integer port;
        
        public Integer getPort() {
            return port;
        }
        
        public Builder setPort(Integer port) {
            this.port = port;
            return this;
        }
        
        private static final String KEY_SEED = "-s";
        private Long seed;
        
        public Long getSeed() {
            return seed;
        }
        
        public Builder setSeed(Long seed) {
            this.seed = seed;
            return this;
        }
        
        private static final String KEY_THROTTLE = "--throttle";
        private Long throttle;
        
        public Long getThrottle() {
            return throttle;
        }
        
        public Builder setThrottle(Long milliSec) {
            this.throttle = milliSec;
            return this;
        }
        
        private static final String KEY_PROFILE_WAIT = "--profile-wait";
        private Long profileWait;
        
        public Long getProfileWait() {
            return profileWait;
        }
        
        public Builder setProfileWait(Long millisSec) {
            this.profileWait = millisSec;
            return this;
        }
        
        private static final String KEY_DEVICE_SLEEP_TIME = "--device-sleep-time";
        private Long deviceSleepTime;
        
        public Long getDeviceSleepTime() {
            return deviceSleepTime;
        }
        
        public Builder setDeviceSleepTime(Long milliSec) {
            this.deviceSleepTime = milliSec;
            return this;
        }
        
        public static final long DEFAULT_COUNT = 1000;
        private long count = DEFAULT_COUNT;
        
        public long getCount() {
            return count;
        }
        
        public Builder setCount(long count) {
            this.count = count;
            return this;
        }
        
        /*额外参数*/
        
        private int times = 1;
        
        public Builder setTimes(int times) {
            if (times == 0) {
                throw new IllegalArgumentException();
            } else if (times < 0) {
                times = INFINITE;
            }
            this.times = times;
            return this;
        }
        
        public int getTimes() {
            return times;
        }
        
        /*简化*/
        
        public Builder setLogAll() {
            return setLogLevel(LogLevel.ALL);
        }
        
        public Builder setIgnoreAll() {
            setIgnoreCrashes(true);
            setIgnoreNativeCrashes(true);
            setIgnoreSecurityExceptions(true);
            setIgnoreTimeouts(true);
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
        
        /*Clone*/
        
        public Builder clone() {
            Builder builder = null;
            try {
                builder = (Builder) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return builder;
        }
    }
}

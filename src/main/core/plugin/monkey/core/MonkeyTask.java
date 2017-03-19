package core.plugin.monkey.core;

import org.jetbrains.annotations.Nullable;

import java.io.File;

import core.plugin.monkey.log.ILogCreator;

/**
 * @author DrkCore
 * @since 2017/2/4
 */
public class MonkeyTask extends CmdTask<MonkeyTask.Params, File, Void> {
    
    private static final int INFINITE = Monkey.INFINITE;
    
    public static class Params {
        
        final String cmd;
        final int times;
        final String device;
        final ILogCreator logCreator;
        
        public Params(Monkey monkey, @Nullable Device device, @Nullable ILogCreator logCreator) {
            this(monkey.getCmd(), monkey.getTimes(), device != null ? device.getDevice() : null, logCreator);
        }
        
        public Params(String cmd, int times, @Nullable String device, @Nullable ILogCreator logCreator) {
            if (times == 0) {
                throw new IllegalArgumentException();
            } else if (times < 0) {
                times = INFINITE;
            }
            
            this.cmd = cmd;
            this.times = times;
            this.device = device;
            this.logCreator = logCreator;
        }
    }
    
    @Override
    protected Void doInBack(Params params) throws Exception {
        int leftTimes = params.times;
        while (!isCancelled() && (params.times == Monkey.INFINITE || leftTimes-- > 0)) {
            File logfile = params.logCreator != null ? params.logCreator.newLogFile() : null;
            if (logfile != null) {
                publishProgress(logfile);
            }
            execShell(params.cmd, params.device, logfile).waitFor();
        }
        return null;
    }
    
}

package core.plugin.monkey.core;

import java.io.ByteArrayOutputStream;

/**
 * @author DrkCore
 * @since 2017/2/4
 */
public class MonkeyTask extends CommandTask<MonkeyTask.Params, ByteArrayOutputStream> {
    
    private static final int INFINITE = Monkey.INFINITE;
    
    public static class Params {
        
        final String cmd;
        final int times;
        final String device;
        
        public Params(Monkey monkey, Device device) {
            this(monkey.getCmd(), monkey.getTimes(), device.getDevice());
        }
        
        public Params(String cmd, int times, String device) {
            if (times == 0) {
                throw new IllegalArgumentException();
            } else if (times < 0) {
                times = INFINITE;
            }
            
            this.cmd = cmd;
            this.times = times;
            this.device = device;
        }
    }
    
    @Override
    protected ByteArrayOutputStream doInBack(Params params) throws Exception {
        int leftTimes = params.times;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (!isCancelled() && (params.times == Monkey.INFINITE || leftTimes-- > 0)) {
            execShell(params.cmd, params.device, out);
        }
        return out;
    }
    
}

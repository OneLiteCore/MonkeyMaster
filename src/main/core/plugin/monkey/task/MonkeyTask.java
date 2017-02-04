package core.plugin.monkey.task;

import java.io.ByteArrayOutputStream;

/**
 * @author DrkCore
 * @since 2017/2/4
 */
public class MonkeyTask extends CommandTask<MonkeyTask.Params, ByteArrayOutputStream> {

    public static final int INFINITE = -1;

    public static class Params {

        private final String cmd;
        private final int times;

        public Params(String cmd, int times) {
            this.cmd = cmd;
            this.times = times;
            if (times < INFINITE || times == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    @Override
    protected ByteArrayOutputStream doInBack(Params params) throws Exception {
        int times = params.times;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (!isCancelled() && (params.times == INFINITE || times-- > 0)) {
            execCommand(params.cmd, out);
        }
        return out;
    }

}

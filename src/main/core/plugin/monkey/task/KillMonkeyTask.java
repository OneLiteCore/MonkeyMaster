package core.plugin.monkey.task;

import java.io.ByteArrayOutputStream;

/**
 * @author DrkCore
 * @since 2017/2/4
 */
public class KillMonkeyTask extends CommandTask<String, Void> {

    @Override
    protected Void doInBack(String device) throws Exception {
        ensureAdb();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        execShell("\"ps|grep monkey\"", device, out);
        String info = out.toString();
        //root      9542  76    1234904 40084 ffffffff b7726369 S com.android.commands.monkey
        String[] items = info.split(" ");
        String pid = null;
        for (int i = 0, count = 2, len = items.length; i < len; i++) {
            String item = items[i];
            if (item != null && !"".equals(item)) {
                if (--count == 0) {
                    pid = item;
                    break;
                }
            }
        }

        if (pid != null) {
            execShell("kill " + pid, device);
        }

        return null;
    }
}

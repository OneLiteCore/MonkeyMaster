package core.plugin.monkey.task;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import core.plugin.monkey.util.IOUtil;

/**
 * @author DrkCore
 * @since 2017-02-04
 */
public class FindDevicesTask extends CommandTask<Void,List<String>> {
    
    @Override
    protected List<String> doInBack(Void aVoid) throws Exception {
        ensureAdb();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        execCommand("cmd.exe /c adb devices",out);
        String info = out.toString();
        String[] lines = info.split("\r|\n");
    
        int len = lines.length;
        List<String> devices = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            String line = lines[i];
            if (line != null && !"".equals(line) && !"List of devices attached".equals(line)) {
                int idx = line.indexOf('\t');
                if (idx != -1) {
                    line = line.substring(0, idx);
                }
                devices.add(line);
            }
        }
        return devices;
    }
}

package core.plugin.monkey.task;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import core.plugin.monkey.util.IOUtil;

/**
 * @author DrkCore
 * @since 2017-02-04
 */
public class FindDevicesTask extends StartAdbTask<List<String>> {
    
    @Override
    protected List<String> doInBack(Void aVoid) throws Exception {
        super.doInBack(aVoid);
        Process process = Runtime.getRuntime().exec("cmd.exe /c adb devices");
        String info = IOUtil.readQuietly(process.getInputStream(), Charset.defaultCharset());
        String[] lines = info != null ? info.split("\r|\n") : null;
    
        int len = lines != null ? lines.length : 0;
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

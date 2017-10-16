package core.plugin.monkey.core;

import java.io.File;
import java.util.List;

import core.plugin.monkey.util.task.SimpleTask;
import core.plugin.monkey.util.TextUtil;

/**
 * @author DrkCore
 * @since 2017-03-19
 */
public class FindSdkTask extends SimpleTask<String> {
    
    private static final String PROPERTIES = "local.properties";
    private static final File PROPERTIES_FILE = new File(PROPERTIES);
    
    private static final String SDK_KEY = "sdk.dir=";
    
    @Override
    protected String doInBack(Void aVoid) throws Exception {
        List<String> lines = PROPERTIES_FILE.isFile() ? TextUtil.readLines(PROPERTIES_FILE) : null;
        String sdkDir = null;
        if (lines != null) {
            for (String line : lines) {
                if (line.startsWith(SDK_KEY)) {
                    sdkDir = line.substring(SDK_KEY.length());
                    if(sdkDir.contains("\\")){
                        sdkDir = sdkDir.replace("\\","/");
                        sdkDir = sdkDir.replace("//","/");
                    }
                    break;
                }
            }
        }
        return sdkDir;
    }
    
}

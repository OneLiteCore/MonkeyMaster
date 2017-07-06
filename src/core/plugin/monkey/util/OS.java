package core.plugin.monkey.util;

/**
 * @author DrkCore
 * @since 2017-03-19
 */
public enum OS {
    
    WIN("\r\n", "cmd.exe /c"),
    LINUX("\n","/bin/sh -c"   ),
    OS_X("\r", "");
    
    public final String lineSeperator;
    public final String execCmd;
    
    OS(String lineSeperator, String execCmd) {
        this.lineSeperator = lineSeperator;
        this.execCmd = execCmd;
    }
    
    public static OS get() {
        return from(System.lineSeparator());
    }
    
    public static OS from(String lineSeperator) {
        OS result = null;
        for (OS os : OS.values()) {
            if (os.lineSeperator.equals(lineSeperator)) {
                result = os;
            }
        }
        return result;
    }
}

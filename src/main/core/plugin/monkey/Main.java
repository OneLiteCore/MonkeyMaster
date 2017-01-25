package core.plugin.monkey;

import core.plugin.monkey.cmd.Monkey;
import core.plugin.monkey.cmd.Runner;
import core.plugin.monkey.log.LogManager;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class Main {
    
    public static void main(String[] args) throws Throwable {
        Monkey.getInstance().submit(Runner.newBuilder()
                .setAllowedPackage("core.demo")
                .setIgnoreAll()
                .setLogAll()
                .addLogFile(LogManager.getInstance().newLogFile())
                .addConsoleLog()
                .setTotalTime(60 * 1000).build());
        
        Thread.sleep(1000L);
        
        Monkey.getInstance().submit(Runner.newBuilder()
                .setAllowedPackage("core.novel")
                .setIgnoreAll()
                .setLogAll()
                .addLogFile(LogManager.getInstance().newLogFile())
                .addConsoleLog()
                .setTotalTime(60 * 1000).build());
    }
    
}

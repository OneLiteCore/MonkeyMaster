package core.plugin.monkey;

import core.plugin.monkey.core.Monkey;
import core.plugin.monkey.core.Runner;
import core.plugin.monkey.core.LogManager;

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
                .addConsolePrinter()
                .setTotalTime(60 * 1000).build());
        
        Thread.sleep(1000L);
        
        Monkey.getInstance().submit(Runner.newBuilder()
                .setAllowedPackage("core.novel")
                .setIgnoreAll()
                .setLogAll()
                .addLogFile(LogManager.getInstance().newLogFile())
                .addConsolePrinter()
                .setTotalTime(60 * 1000).build());
    }
    
}

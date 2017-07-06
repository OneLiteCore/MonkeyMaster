package core.plugin.monkey.log;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class ConsolePrinter extends LogfilePublisher {
    
    public ConsolePrinter() {
    }
    
    public ConsolePrinter(@Nullable Executor executor) {
        super(executor);
    }
    
    @Override
    public void onLog(String line) {
        System.out.println(line);
    }
}

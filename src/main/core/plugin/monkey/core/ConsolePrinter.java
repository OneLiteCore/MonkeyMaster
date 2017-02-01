package core.plugin.monkey.core;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class ConsolePrinter extends SimpleRunnerListener {
    
    @Override
    public void print(String line) {
        super.print(line);
        System.out.println(line);
    }
}

package core.plugin.monkey.core;

import core.plugin.monkey.util.OnTaskListenerImpl;

import java.io.ByteArrayOutputStream;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class ConsolePrinter extends OnTaskListenerImpl<String, ByteArrayOutputStream> {

    @Override
    public void onProgress(String line) {
        super.onProgress(line);
        System.out.println(line);
    }
}

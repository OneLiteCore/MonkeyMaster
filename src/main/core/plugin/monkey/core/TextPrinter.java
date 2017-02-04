package core.plugin.monkey.core;

import core.plugin.monkey.util.OnTaskListenerImpl;

import javax.swing.*;
import java.io.ByteArrayOutputStream;

/**
 * @author DrkCore
 * @since 2017-01-26
 */
public class TextPrinter extends OnTaskListenerImpl<String, ByteArrayOutputStream> {

    private final JTextArea textArea;
    private boolean autoScroll = true;

    public boolean isAutoScroll() {
        return autoScroll;
    }

    public TextPrinter setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
        return this;
    }

    public TextPrinter(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void onProgress(String line) {
        super.onProgress(line);
        textArea.append(line);
        textArea.append("\n");
        if (autoScroll) {
            textArea.setCaretPosition(textArea.getText().length());
        }
    }

    public synchronized void clearLog() {
        textArea.setText("");
    }
}

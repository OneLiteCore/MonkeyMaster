package core.plugin.monkey.core;

import java.io.IOException;

import javax.swing.JTextArea;

/**
 * @author DrkCore
 * @since 2017-01-26
 */
public class TextPrinter extends SimplePrinter {
    
    private JTextArea textArea;
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
    public synchronized void print(String line) throws IOException {
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

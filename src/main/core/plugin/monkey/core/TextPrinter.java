package core.plugin.monkey.core;

import com.intellij.openapi.application.ApplicationManager;

import java.io.ByteArrayOutputStream;

import javax.swing.JTextArea;

import core.plugin.monkey.util.OnTaskListenerImpl;

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
        ApplicationManager.getApplication().invokeLater(() -> {
            synchronized (textArea) {
                textArea.append(line);
                textArea.append("\n");
                if (autoScroll) {
                    textArea.setCaretPosition(textArea.getText().length());
                }
            }
        });
    }
    
    public void clearLog() {
        ApplicationManager.getApplication().invokeLater(() -> {
            synchronized (textArea) {
                textArea.setText("");
            }
        });
    }
}

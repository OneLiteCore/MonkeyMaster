package core.plugin.monkey.log;

import com.intellij.openapi.application.ApplicationManager;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;

import javax.swing.JTextArea;

/**
 * @author DrkCore
 * @since 2017-01-26
 */
public class TextPrinter extends LogfilePublisher {
    
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
    
    public TextPrinter(@Nullable Executor executor, JTextArea textArea) {
        super(executor);
        this.textArea = textArea;
    }
    
    @Override
    public void onLog(String line) {
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

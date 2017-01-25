package core.plugin.monkey.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author DrkCore
 * @since 2017-01-24
 */
public class FileLog implements Log {
    
    private final File file;
    private final boolean append;
    
    public FileLog(File file) {
        this(file, true);
    }
    
    public FileLog(File file, boolean append) {
        this.file = file;
        this.append = append;
    }
    
    private BufferedWriter writer;
    
    private BufferedWriter getWriter() throws FileNotFoundException {
        if (writer == null) {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append)));
        }
        return writer;
    }
    
    @Override
    public synchronized void flush() throws IOException {
        if (writer != null) {
            writer.flush();
        }
    }
    
    @Override
    public synchronized void write(String line) throws IOException {
        getWriter().write(line);
        getWriter().write("\n");
    }
    
    @Override
    public synchronized void close() throws IOException {
        if (writer != null) {
            writer.close();
            writer = null;
        }
    }
    
}

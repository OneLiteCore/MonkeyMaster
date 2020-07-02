package core.plugin.monkey.util;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * 专门用于处理IO操作的工具类
 *
 * @author DrkCore
 * @since 2015年9月27日15:35:27
 */
public final class IOUtil {
    
    private IOUtil() {
    }
    
    public static BufferedReader open(File file) throws FileNotFoundException {
        return open(file, null);
    }
    
    public static BufferedReader open(File file, @Nullable Charset charset) throws FileNotFoundException {
        charset = charset != null ? charset : Charset.defaultCharset();
        FileInputStream in = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(in, charset);
        return new BufferedReader(reader);
    }
    
    /**
     * 静默关闭输入流。
     * 如果closeable同时
     *
     * @param closeable
     */
    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        
        if (closeable instanceof Flushable) {
            try {//如果是可flush的，就先flush一下
                Flushable flushable = (Flushable) closeable;
                flushable.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        try {//静默关闭
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String readQuietly(@Nullable InputStream in, @Nullable Charset charset) {
        byte[] bytes = readQuietly(in);
        String str = null;
        if (bytes != null) {
            if (charset == null) {
                charset = Charset.defaultCharset();
            }
            str = new String(bytes, charset);
        }
        return str;
    }
    
    public static void waste(@Nullable InputStream in) {
        if (in != null) {
            try {
                byte[] buffer = new byte[1024];
                while (in.read(buffer) != -1) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(in);
            }
        }
    }
    
    public static byte[] readQuietly(@Nullable InputStream in) {
        byte[] bytes = null;
        if (in != null) {
            try {
                bytes = read(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }
    
    public static String read(Process process) throws IOException {
        return read(process.getInputStream(), Charset.defaultCharset());
    }
    
    public static String read(InputStream in, Charset charset) throws IOException {
        return new String(read(in), charset);
    }
    
    public static byte[] read(InputStream in) throws IOException {
        ByteArrayOutputStream byteArrOut = null;
        try {
            byteArrOut = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                byteArrOut.write(buffer, 0, len);
            }
            byteArrOut.flush();
            return byteArrOut.toByteArray();
        } finally {
            close(byteArrOut);
            close(in);
        }
    }
    
    /**
     * 默认使用系统编码
     */
    public static void write(OutputStream out, String content) throws IOException {
        write(out, content, Charset.defaultCharset());
    }
    
    public static void write(OutputStream out, String content, Charset charset) throws IOException {
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(out, charset);
            writer.write(content);
            writer.flush();
        } finally {
            close(writer);
            close(out);
        }
    }
    
    public static void write(OutputStream out, InputStream in) throws IOException {
        // 创建临时变量准备输入
        byte[] data = new byte[1024];
        int len;
        // 将数据写入指定的文件
        try {
            while ((len = in.read(data)) != -1) {
                out.write(data, 0, len);
            }
        } finally {
            close(in);
            close(out);
        }
    }
}

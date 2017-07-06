package core.plugin.monkey.util;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DrkCore
 * @since 2017-01-26
 */
public class TextUtil {
    
    public static String readText(File txtFile) throws IOException {
        return readText(txtFile, null);
    }
    
    /**
     * 读取指定文件中的文本。当指定文本编码为null时使用默认编码，当换行符为null时使用系统默认换行符。
     *
     * @param txtFile
     * @param charset 指定的文本编码，
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String readText(File txtFile, @Nullable Charset charset) throws IOException {
        if (!txtFile.isFile()) {// 不是文件
            throw new FileNotFoundException("文件不可用");
        } else if (txtFile.length() == 0) {
            return "";// 空文件直接返回
        }
        
        InputStream in = null;
        InputStreamReader inputReader = null;
        BufferedReader buffReader = null;
        try {
            StringBuilder builder = new StringBuilder();
            in = new FileInputStream(txtFile);
            charset = charset != null ? charset : Charset.defaultCharset();
            inputReader = new InputStreamReader(in, charset);
            buffReader = new BufferedReader(inputReader);
            
            // 读取文本
            String temp;
            String separator = OS.get().lineSeperator;
            while ((temp = buffReader.readLine()) != null) {
                builder.append(temp).append(separator);
            }
            if (builder.length() > separator.length()) {// 有长度则必定末尾有一个多余的\n符
                builder.deleteCharAt(builder.length() - separator.length());
            }
            return builder.toString();
        } finally {
            IOUtil.close(buffReader);
            IOUtil.close(inputReader);
            IOUtil.close(in);
        }
        
    }
    
    public static List<String> readLines(File txtFile) throws IOException {
        return readLines(txtFile, null);
    }
    
    /**
     * 分行读取输入流。如果charset为null则使用默认编码。
     *
     * @param txtFile
     * @param charset
     * @return
     * @throws IOException
     */
    public static List<String> readLines(File txtFile, @Nullable Charset charset) throws IOException {
        InputStream in = null;
        InputStreamReader inReader = null;
        BufferedReader buffReader = null;
        try {
            in = new FileInputStream(txtFile);
            charset = charset != null ? charset : Charset.defaultCharset();
            inReader = new InputStreamReader(in, charset);
            buffReader = new BufferedReader(inReader);
            ArrayList<String> list = new ArrayList<>();
            String line;
            while ((line = buffReader.readLine()) != null) {
                list.add(line);
            }
            return list;
        } finally {
            IOUtil.close(buffReader);
            IOUtil.close(inReader);
            IOUtil.close(in);
        }
    }

	/*其他*/
    
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
    
    public static boolean isAllEmpty(String... strs) {
        if (strs != null) {
            for (String tmp : strs) {
                if (!isEmpty(tmp)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean isAnyEmpty(String... strs) {
        if (strs != null) {
            for (String tmp : strs) {
                if (isEmpty(tmp)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    public static String getEmptyIfNull(String str) {
        return str != null ? str : "";
    }
    
    public static CharSequence getEmptyIfNull(CharSequence str) {
        return str != null ? str : "";
    }
    
    /**
     * 获取字符串的下标从0到length的子串。如果str的长度不足length则直接返回str。
     *
     * @param str
     * @param length
     * @return
     */
    public static String subString(String str, int length) {
        int srcLen = str.length();
        if (srcLen <= length) {
            return str;
        } else {
            return str.substring(0, length);
        }
    }
    
}

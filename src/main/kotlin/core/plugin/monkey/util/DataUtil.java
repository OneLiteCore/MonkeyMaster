package core.plugin.monkey.util;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * 用于数组操作的工具集
 *
 * @author DrkCore
 * @since 2016年2月17日17:43:03
 */
public final class DataUtil {
    
    private DataUtil() {
    }
    
    public static void reverse(byte[] array) {
        int len = array.length;
        if (len <= 1) {
            return;
        }
        
        int halfLen = len / 2;
        byte tmp;
        for (int i = 0; i < halfLen; i++) {
            tmp = array[i];
            array[i] = array[len - i - 1];
            array[len - i - 1] = tmp;
        }
    }
    
    public static void reverse(boolean[] array) {
        int len = array.length;
        if (len <= 1) {
            return;
        }
        
        int halfLen = len / 2;
        boolean tmp;
        for (int i = 0; i < halfLen; i++) {
            tmp = array[i];
            array[i] = array[len - i - 1];
            array[len - i - 1] = tmp;
        }
    }
    
    public static void reverse(char[] array) {
        int len = array.length;
        if (len <= 1) {
            return;
        }
        
        int halfLen = len / 2;
        char tmp;
        for (int i = 0; i < halfLen; i++) {
            tmp = array[i];
            array[i] = array[len - i - 1];
            array[len - i - 1] = tmp;
        }
    }
    
    public static void reverse(int[] array) {
        int len = array.length;
        if (len <= 1) {
            return;
        }
        
        int halfLen = len / 2;
        int tmp;
        for (int i = 0; i < halfLen; i++) {
            tmp = array[i];
            array[i] = array[len - i - 1];
            array[len - i - 1] = tmp;
        }
    }
    
    public static void reverse(long[] array) {
        int len = array.length;
        if (len <= 1) {
            return;
        }
        
        int halfLen = len / 2;
        long tmp;
        for (int i = 0; i < halfLen; i++) {
            tmp = array[i];
            array[i] = array[len - i - 1];
            array[len - i - 1] = tmp;
        }
    }
    
    public static void reverse(float[] array) {
        int len = array.length;
        if (len <= 1) {
            return;
        }
        
        int halfLen = len / 2;
        float tmp;
        for (int i = 0; i < halfLen; i++) {
            tmp = array[i];
            array[i] = array[len - i - 1];
            array[len - i - 1] = tmp;
        }
    }
    
    public static void reverse(double[] array) {
        int len = array.length;
        if (len <= 1) {
            return;
        }
        
        int halfLen = len / 2;
        double tmp;
        for (int i = 0; i < halfLen; i++) {
            tmp = array[i];
            array[i] = array[len - i - 1];
            array[len - i - 1] = tmp;
        }
    }
    
    public static <T> void reverse(T[] array) {
        int len = array.length;
        if (len <= 1) {
            return;
        }
        
        int halfLen = len / 2;
        T tmp;
        for (int i = 0; i < halfLen; i++) {
            tmp = array[i];
            array[i] = array[len - i - 1];
            array[len - i - 1] = tmp;
        }
    }
    
    public static int indexOf(char[] array, char value) {
        for (int i = 0, len = getSize(array); i < len; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(short[] array, short value) {
        for (int i = 0, len = getSize(array); i < len; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(int[] array, int value) {
        for (int i = 0, len = getSize(array); i < len; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(long[] array, long value) {
        for (int i = 0, len = getSize(array); i < len; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(float[] array, float value) {
        for (int i = 0, len = getSize(array); i < len; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(double[] array, double value) {
        for (int i = 0, len = getSize(array); i < len; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public static <T> int indexOf(T[] array, T value) {
        for (int i = 0, len = getSize(array); i < len; i++) {
            if ((array[i] != null && array[i].equals(value)) || (array[i] == null && value == null)) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(char[] array, char value) {
        for (int len = getSize(array), i = len - 1; i >= 0; i--) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(short[] array, short value) {
        for (int len = getSize(array), i = len - 1; i >= 0; i--) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(int[] array, int value) {
        for (int len = getSize(array), i = len - 1; i >= 0; i--) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(long[] array, long value) {
        for (int len = getSize(array), i = len - 1; i >= 0; i--) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(float[] array, float value) {
        for (int len = getSize(array), i = len - 1; i >= 0; i--) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(double[] array, double value) {
        for (int len = getSize(array), i = len - 1; i >= 0; i--) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public static <T> int lastIndexOf(T[] array, T value) {
        for (int len = getSize(array), i = len - 1; i >= 0; i--) {
            if ((array[i] != null && array[i].equals(value)) || (array[i] == null && value == null)) {
                return i;
            }
        }
        return -1;
    }
    
    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
    
    /**
     * 当array为null，或者长度为0时返回true。
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }
    
    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }
    
    @Nullable
    public static <T> T getQuietly(@Nullable List<T> list, int location) {
        if (location < 0) {
            throw new IllegalArgumentException("location不允许为负数");
        }
        return list != null && list.size() > location ? list.get(location) : null;
    }
    
    @Nullable
    public static <T> T getFirstQuietly(@Nullable List<T> list) {
        return getQuietly(list, 0);
    }
    
    @Nullable
    public static <T> T getLastQuietly(@Nullable List<T> list) {
        return getQuietly(list, list != null ? list.size() - 1 : 0);
    }
    
    public static <T> int getSize(@Nullable Collection<T> collection) {
        return collection != null ? collection.size() : 0;
    }
    
    public static <T> int getSize(@Nullable T[] array) {
        return array != null ? array.length : 0;
    }
    
    public static int getSize(@Nullable boolean[] array) {
        return array != null ? array.length : 0;
    }
    
    public static int getSize(@Nullable byte[] array) {
        return array != null ? array.length : 0;
    }
    
    public static int getSize(@Nullable char[] array) {
        return array != null ? array.length : 0;
    }
    
    public static int getSize(@Nullable int[] array) {
        return array != null ? array.length : 0;
    }
    
    public static int getSize(@Nullable short[] array) {
        return array != null ? array.length : 0;
    }
    
    public static int getSize(@Nullable long[] array) {
        return array != null ? array.length : 0;
    }
    
    public static int getSize(@Nullable float[] array) {
        return array != null ? array.length : 0;
    }
    
    public static int getSize(@Nullable double[] array) {
        return array != null ? array.length : 0;
    }
    
    public static byte parseByte(String str) {
        return parseByte(str, (byte) 0);
    }
    
    public static byte parseByte(String str, byte defVal) {
        if (TextUtil.isEmpty(str)) {
            return defVal;
        }
        try {
            return Byte.parseByte(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defVal;
        }
    }
    
    public static boolean parseBoolean(String str) {
        return parseBoolean(str, false);
    }
    
    public static boolean parseBoolean(String str, boolean defVal) {
        if (TextUtil.isEmpty(str)) {
            return defVal;
        }
        try {
            return Boolean.parseBoolean(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defVal;
        }
    }
    
    public static int parseInt(String str) {
        return parseInt(str, 0);
    }
    
    public static int parseInt(String str, int defVal) {
        if (TextUtil.isEmpty(str)) {
            return defVal;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defVal;
        }
    }
    
    public static short parseShort(String str) {
        return parseShort(str, (short) 0);
    }
    
    public static short parseShort(String str, short defVal) {
        if (TextUtil.isEmpty(str)) {
            return defVal;
        }
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defVal;
        }
    }
    
    public static long parseLong(String str) {
        return parseLong(str, 0);
    }
    
    public static long parseLong(String str, long defVal) {
        if (TextUtil.isEmpty(str)) {
            return defVal;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defVal;
        }
    }
    
    public static float parseFloat(String str) {
        return parseFloat(str, 0);
    }
    
    public static float parseFloat(String str, float defVal) {
        if (TextUtil.isEmpty(str)) {
            return defVal;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defVal;
        }
    }
    
    public static double parseDouble(String str) {
        return parseDouble(str, 0);
    }
    
    public static double parseDouble(String str, double defVal) {
        if (TextUtil.isEmpty(str)) {
            return defVal;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defVal;
        }
    }
}

package core.plugin.monkey.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * 文件系统工具集。
 * file代表文件；
 * dir代表目录；
 * item代表文件或者目录；
 *
 * @author DrkCore
 * @since 2015年9月24日21:04:08
 */
public final class FileUtil {
    
    private FileUtil() {
    }

	/* 创建项目 */
    
    /**
     * 创建文件，具体实现请参阅{@link #createFile(File)}
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static File createFile(String filePath) throws IOException {
        return createFile(new File(filePath));
    }
    
    /**
     * 创建文件。当其上级目录不存在时，将会创建所有上级目录。
     * 当且仅当文件不存在且成功创建文件时返回创建好的文件，否则将抛出异常。
     *
     * @param file
     * @return
     * @throws IOException
     * @throws IOException
     */
    public static File createFile(File file) throws IOException {
        if (file.exists()) {
            throw new IOException();
        }
        
        // 创建父目录
        File parentDir = file.getParentFile();
        if (parentDir != null) {
            parentDir.mkdirs();
        }
        // 创建新文件
        file.createNewFile();
        if (file.isFile()) {
            return file;
        } else {
            throw new IOException("无法创建文件");
        }
    }
    
    /**
     * 创建目录。具体实现请参阅{@link #createDir(File)}
     *
     * @param dirPath
     * @return
     * @throws IOException
     */
    public static File createDir(String dirPath) throws IOException {
        return createDir(new File(dirPath));
    }
    
    /**
     * 创建目录。当其上级目录不存在时会创建所有上级目录。
     * 当且仅当dir不存在并且创建成功时返回dir，否则抛出异常。
     *
     * @param dir
     * @return
     * @throws IOException
     */
    public static File createDir(File dir) throws IOException {
        if (dir.exists()) {
            throw new IOException();
        }
        dir.mkdirs();
        if (dir.isDirectory()) {
            return dir;
        } else {
            throw new IOException("无法创建目录");
        }
    }
    
    /**
     * 获取或创建指定路径下的文件，具体实现请参阅{@link #getOrCreateFile(File)}
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static File getOrCreateFile(String filePath) throws IOException {
        return getOrCreateFile(new File(filePath));
    }
    
    /**
     * 获取或创建指定路径下的文件。
     * <li/>文件存在时直接返回；
     * <li/>文件不存在时创建并返回，创建的方法参阅
     * {@link #createFile(File)}；
     * <li/>存在同名目录时抛出异常。
     *
     * @param file
     * @return
     * @throws IOException
     * @throws IOException
     */
    public static File getOrCreateFile(File file) throws IOException {
        if (file.isFile()) {
            return file;
        } else if (!file.exists() && createFile(file).isFile()) {// 文件不存在但创建文件成功
            return file;
        } else if (file.isDirectory()) {
            throw new IOException("同名目录已存在");
        } else {// 其他情况，包括指定路径为dir，或者文件不存在但创建失败
            throw new IOException("无法获取或创建文件：" + file);
        }
    }
    
    /**
     * 获取或创建指定路径下的目录，具体实现请参阅{@link #getOrCreateDir(File)}。
     *
     * @param dirPath
     * @return
     * @throws IOException
     */
    public static File getOrCreateDir(String dirPath) throws IOException {
        return getOrCreateDir(new File(dirPath));
    }
    
    /**
     * 获取或创建指定路径下的目录。
     * <li/>目录存在时直接返回；
     * <li/>目录不存在时创建并返回，创建方法参阅{@link #createDir(File)}；
     * <li/>存在同名的文件时排除异常。
     *
     * @param dir
     * @return
     * @throws IOException
     */
    public static File getOrCreateDir(File dir) throws IOException {
        if (dir.isDirectory()) {
            return dir;
        } else if (!dir.exists() && createDir(dir).isDirectory()) {// 目录不存在但创建目录成功
            return dir;
        } else if (dir.isFile()) {
            throw new IOException("同名文件已存在");
        } else {// 其他情况
            throw new IOException("无法获取或创建目录：" + dir);
        }
    }
    
    /**
     * {@link #confirmFile(File)}
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static File confirmFile(String filePath) throws IOException {
        return confirmFile(new File(filePath));
    }
    
    /**
     * 强制确保路径为文件
     *
     * @param file 需要确保的文件
     * @return 确保为文件的File实例
     * @throws IOException 当文件不存在且无法创建，或者路径指向了目录却无法删除再创建为文件，抛出该异常
     */
    public static File confirmFile(File file) throws IOException {
        if (file.isFile()) {// 是文件
            return file;
        } else if (!file.exists() && createFile(file).isFile()) {// 不存在但是创建成功
            return file;
        } else if (file.isDirectory() && delete(file) && createFile(file).isFile()) {// 是目录，但是删除后创建文件成功
            return file;
        } else {
            throw new IOException("文件不可用");
        }
    }
    
    /**
     * {@link #confirmDir(File)}
     *
     * @param dirPath
     * @return
     * @throws FileNotFoundException
     */
    public static File confirmDir(String dirPath) throws FileNotFoundException {
        return confirmDir(new File(dirPath));
    }
    
    /**
     * 强制确保路径为目录
     *
     * @param dir 需要确保的目录
     * @return 确认为目录的File实例
     * @throws FileNotFoundException 当dir不存在且无法创建，或者是文件却无法删除后再创建为文件，抛出改异常
     */
    public static File confirmDir(File dir) throws FileNotFoundException {
        if (dir.isDirectory()) {// 是目录
            return dir;
        } else if (!dir.exists() && dir.mkdirs()) {// 不存在但是创建成功
            return dir;
        } else if (dir.isFile() && dir.delete() && dir.mkdirs()) {// 是文件，但是删除后创建目录成功
            return dir;
        } else {
            throw new FileNotFoundException("目录不可用");
        }
    }
    
    public static boolean canRead(@Nullable File file) {
        return file != null && file.isFile() && file.canRead();
    }
    
    public static final String NO_MEDIA = ".nomedia";
    
    /**
     * 在指定目录中创建.nomedia文件来表明目录之下不存在媒体数据。
     *
     * @param inDir
     * @return
     * @throws IOException
     */
    public static File confirmNoMediaFile(File inDir) throws IOException {
        return confirmFile(getNoMediaFile(inDir));
    }
    
    public static File getNoMediaFile(File inDir) {
        return new File(inDir, NO_MEDIA);
    }
    
    /**
     * 判断指定目录中是否存在.nomedia文件
     *
     * @param inDir
     * @return
     */
    public static boolean containNoMediaFile(File inDir) {
        return getNoMediaFile(inDir).exists();
    }
    
    public static File[] toItems(String... paths) {
        int len = paths.length;
        File[] itemArr = new File[len];
        for (int i = 0; i < len; i++) {
            itemArr[i] = new File(paths[i]);
        }
        return itemArr;
    }
    
    public static List<File> toItems(Collection<String> paths) {
        List<File> files = new ArrayList<>(paths.size());
        for (String path : paths) {
            files.add(new File(path));
        }
        return files;
    }
    
    public static String[] toPaths(File... items) {
        int len = items.length;
        String[] paths = new String[len];
        for (int i = 0; i < len; i++) {
            paths[i] = items[i].getAbsolutePath();
        }
        return paths;
    }
    
    public static List<String> toPaths(Collection<File> items) {
        List<String> paths = new ArrayList<>(items.size());
        for (File item : items) {
            paths.add(item.getAbsolutePath());
        }
        return paths;
    }

	/* 项目名和路径处理 */
    
    /**
     * {@link #getExt(String)}
     *
     * @param file
     * @return
     */
    public static String getExt(File file) {
        return getExt(file.getAbsolutePath());
    }
    
    /**
     * 获取文件的拓展名。注意，这里是以路径来判断的，如果path指向的是目录只要符合规则一样能返回拓展名。
     * 在使用时请多加判断。
     *
     * @param path 文件的路径。该路径分隔符以当前系统为准，即File.separator
     * @return 小写的文件拓展名的字符串，不包括点号，比如“txt”。<br>
     * 如果不存在符合规则的拓展名，则返回null。
     */
    public static String getExt(String path) {
        // 获取文件名称
        String fileName = getName(path);
        // 获取小数点所在位置
        int index = fileName.lastIndexOf('.');
        /*
         * 在win操作系统上不允许比如 “.jpg” 的文件名存在。
		 * 但是在android中，“.jpg” 的文件可以存在。并且会被视为隐藏文件。
		 * 这里将 lastIndexOf('.') = 0 的情况为拓展名不存在
		 * 对于 “.jpg” 来说，文件名即为jpg而拓展名不存在
		 * 而对于 “.fileName.txt” 这种情，因为 lastIndexOf('.')
		 * 的值不为0，所以合法
		 * 奇葩的是，在win和android上，“..txt” 的情况都是合法的，不过此时拓展名视为存在
		 * 因而“lastIndexOf('.') = 0 视为拓展名不存在”依然适用
		 */
        if (index > 0) {
            /*
             * 只在小数点存在，并且位置正确时执行
			 * 无论是在win上还是在android上，将文件重命名为比如
			 * “文件名.”时，会默认去掉小数点，即会变成“文件名”
			 * 也就是说，当小数点存在时，index + 1 绝对不会越界
			 */
            return fileName.substring(index + 1).toLowerCase(Locale.getDefault());
        } else {
            return null;
        }
    }
    
    /**
     * @param file
     * @return
     * @see #getAbsoluteName(String)
     */
    public static String getAbsoluteName(File file) {
        return getAbsoluteName(file.getAbsolutePath());
    }
    
    /**
     * 获取去除了拓展名后的文件名。
     *
     * @param path 文件路径。该路径分隔符以当前系统为准，即File.separator
     * @return 去除了拓展名后的名称，比如"C:\fileName.TXT"，将返回“fileName”。
     */
    public static String getAbsoluteName(String path) {
        String fileName = getName(path);
        String type = getExt(path);
        if (type != null) {
            // 移动拓展名
            // 因为截取的type是不带小数点的，所以要多减去1位
            return fileName.substring(0, fileName.length() - type.length() - 1);
        } else {
            return fileName;
        }
    }
    
    /**
     * {@link #getName(String)}
     *
     * @param item
     * @return
     */
    public static String getName(File item) {
        return getName(item.getAbsolutePath());
    }
    
    /**
     * 获取项目的名称。
     * 作用效果相当于File.getName()。
     *
     * @param filePath 项目路径。该路径分隔符以当前系统为准，即File.separator
     * @return 文件的名称<br>
     * （如“C:\ABC\”将返回ABC，“C:\1.txt”返回“1.txt”） <br>
     * 如果项目名称为“/”，即Linux或者unix的根目录，则直接返回"/"
     */
    public static String getName(String filePath) {
        //统一分隔符
        filePath = filePath.contains("\\") ? filePath.replace('\\', '/') : filePath;
        if (filePath.equals("/")) {
            return "/";
        }
        if (filePath.endsWith("/")) { // 如果目录路径以"/"结尾，则先去除末尾的"/"
            filePath = filePath.substring(0, filePath.length() - 1);
        }
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }
    
    /**
     * 判断项目item是否是dir的下级目录。
     * 如果 item不存在或者dir不为目录，必定返回null。
     *
     * @param dir
     * @param item
     * @return
     */
    public static boolean isInDir(File dir, File item) {
        if (item.exists() && dir.isDirectory()) {
            String itemPath = item.getAbsolutePath();
            String dirPath = dir.getAbsolutePath();
            if (!dirPath.endsWith(File.separator)) {// 目录路径不是以文件分隔符结尾的，就添加一个上去
                dirPath += File.separator;
            }
            return itemPath.length() > dirPath.length() && itemPath.startsWith(dirPath);
        }
        return false;
    }
    
    /**
     * 判断项目item是是dir目录的直属项目。
     * 如果item不存在或者dir不是目录，必定返回false。
     *
     * @param dir
     * @param item
     * @return
     */
    public static boolean isInDirDirectly(File dir, File item) {
        if (item.exists() && dir.isDirectory()) {
            String itemPath = item.getAbsolutePath();
            String dirPath = dir.getAbsolutePath();
            if (itemPath.length() > dirPath.length() && itemPath.startsWith(dirPath)) {
                String subPath = itemPath.substring(dirPath.length());
                if (subPath.startsWith(File.separator)) {// 去掉开头可能带有的文件分隔符
                    subPath = subPath.substring(1);
                }
                return !subPath.contains(File.separator);
            }
        }
        return false;
    }
    
    /**
     * 判断两个File是否指向同一个位置
     *
     * @param dstItem
     * @param srcItem
     * @return
     */
    public static boolean isSame(File dstItem, File srcItem) {
        return srcItem.getAbsolutePath().equals(dstItem.getAbsolutePath());
    }
    
    /**
     * 广度优先递归遍历目录下的所有文件。默认忽略隐藏的目录。
     *
     * @param item
     * @return
     */
    public static List<File> dumpFile(File item) {
        return dumpFile(item, null, null, false);
    }
    
    /**
     * 广度优先递归遍历目录下的所有文件。默认忽略隐藏的目录。
     *
     * @param item   如果item本身是文件并且符合filter的要求，也会被添加到list中
     * @param filter
     * @return
     */
    @NotNull
    public static List<File> dumpFile(File item, @Nullable FileFilter filter) {
        return dumpFile(item, null, filter, false);
    }
    
    /**
     * 广度优先递归遍历目录下的所有文件，并填入指定的List之中。
     *
     * @param item           如果item本身是文件并且符合filter的要求，也会被添加到list中
     * @param result         用于保存找到文件的列表，可以为null
     * @param filter
     * @param allowHiddenDir 是否查找{@link File#isHidden()}为true的目录。
     * @return 如果result参数不为空，返回result。否则返回新建的List实例。
     */
    @NotNull
    public static List<File> dumpFile(File item, @Nullable List<File> result, @Nullable FileFilter filter, boolean allowHiddenDir) {
        if (result == null) {
            result = new ArrayList<>();
        }
        
        if (item.isFile()) {
            if (filter == null || filter.accept(item)) {
                result.add(item);
            }
        } else if (item.isDirectory() && (!item.isHidden() || allowHiddenDir)) {// 是目录，不是隐藏目录，或者允许隐藏目录
            File[] files = item.listFiles();
            for (int i = 0, len = DataUtil.getSize(files); i < len; i++) {
                dumpFile(files[i], result, filter, allowHiddenDir);
            }
        }
        
        return result;
    }
    
    /**
     * 删除项目。
     * 在删除整个目录时，若有一个子项目删除失败，则会返回false。
     *
     * @param item
     * @return
     */
    private static boolean delete(File item) {
        boolean flag = true;
        if (item.isDirectory()) {// 是目录则处理子文件
            File[] files = item.listFiles();
            for (int i = 0, len = DataUtil.getSize(files); i < len; i++) {
                if (!delete(files[i])) { // 只要有一个失败就立刻不再继续
                    flag = false;
                    break;
                }
            }
        }
        flag &= item.delete();
        return flag;
    }
    
    /**
     * 批量删除文件。
     *
     * @param items
     * @return
     */
    public static boolean[] delete(File... items) {
        boolean[] resultArr = new boolean[items.length];
        for (int i = 0, len = items.length; i < len; i++) {
            resultArr[i] = delete(items[i]);
        }
        return resultArr;
    }
}
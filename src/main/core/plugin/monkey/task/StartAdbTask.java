package core.plugin.monkey.task;

import core.plugin.monkey.util.IOUtil;
import core.plugin.monkey.util.SimpleTask;

/**
 * @author DrkCore
 * @since 2017-02-04
 */
public class StartAdbTask<Result> extends SimpleTask<Result> {
    
    @Override
    protected Result doInBack(Void aVoid) throws Exception {
        Process process = Runtime.getRuntime().exec("cmd.exe /c adb start-server");
        //读出缓冲区数据并等待进结束
        IOUtil.waste(process.getInputStream());
        IOUtil.waste(process.getErrorStream());
        process.waitFor();
        return null;
    }
}

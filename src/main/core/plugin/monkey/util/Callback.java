package core.plugin.monkey.util;

/**
 * @author DrkCore
 * @since 2016/12/22
 */
public interface Callback<Result> {

    void onCall(Result result);

}

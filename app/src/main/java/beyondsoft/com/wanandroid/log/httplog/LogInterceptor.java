package beyondsoft.com.wanandroid.log.httplog;


import beyondsoft.com.wanandroid.utils.LogUtils;

/**
 * Logger接口的实现类 重写log方法完成打印
 */
public class LogInterceptor implements HttpLoggingInterceptorM.Logger {

    public static String INTERCEPTOR_TAG_STR = "OkHttp";

    public LogInterceptor() {
    }

    public LogInterceptor(String tag) {
        INTERCEPTOR_TAG_STR = tag;
    }

    @Override
    public void log(String message, @LogUtils.LogType int type) {
        LogUtils.printLog(false, type, INTERCEPTOR_TAG_STR, message);
    }
}

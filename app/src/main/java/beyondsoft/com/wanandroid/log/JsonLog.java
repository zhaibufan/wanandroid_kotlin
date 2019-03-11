package beyondsoft.com.wanandroid.log;

import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import beyondsoft.com.wanandroid.utils.LogUtils;

/**
 * 将json数据格式化然后打印
 */
public class JsonLog {

    public static void printJson(String tag, String msg, String headString) {

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(LogUtils.JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(LogUtils.JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        LogUtilHelper.printLine(tag, true);
        message = headString + LogUtils.LINE_SEPARATOR + message;
        String[] lines = message.split(LogUtils.LINE_SEPARATOR);
        for (String line : lines) {
            Log.d(tag, "║ " + line);
        }
        LogUtilHelper.printLine(tag, false);
    }
}

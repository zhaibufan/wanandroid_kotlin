package beyondsoft.com.wanandroid.utils;

import android.content.Context;
import android.util.TypedValue;


/**
 * Created by Pei on 2016-12-28.
 */
public class DpToPxUtils {
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
    public static int sp2px(Context context, int spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}

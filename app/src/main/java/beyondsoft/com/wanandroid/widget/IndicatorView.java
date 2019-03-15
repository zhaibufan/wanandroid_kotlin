package beyondsoft.com.wanandroid.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import beyondsoft.com.wanandroid.R;
import beyondsoft.com.wanandroid.utils.DpToPxUtils;

/**
 * 自定义ViewPager的指示器
 */
public class IndicatorView extends LinearLayout {

    private Context mContext;
    private int pointSize;//指示器的大小
    private int marginLeft;//间距
    private int size = 8;
    private int marginSize = 8;
    private ArrayList<View> mImageViews;//所有指示器集合
    private int selectResId, unSelectResId;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        pointSize = DpToPxUtils.dp2px(mContext, size);
        marginLeft = DpToPxUtils.dp2px(mContext, marginSize);
        selectResId = R.drawable.dot_red;
        unSelectResId = R.drawable.dot_white;
    }

    // 使用默认指示点
    public void initIndicator(int count) {
        mImageViews = new ArrayList<>();
        this.removeAllViews();
        LayoutParams params;
        for (int i = 0; i < count; i++) {
            ImageView iv = new ImageView(mContext);
            params = new LayoutParams(pointSize, pointSize);
            if (i == 0) {
                iv.setImageResource(selectResId);
            } else {
                params.leftMargin = marginLeft;
                iv.setImageResource(unSelectResId);
            }
            iv.setLayoutParams(params);
            mImageViews.add(iv);
            this.addView(iv);
        }
    }

    // 自定义的指示点形状
    public void initIndicator(int count, int selectResId, int unSelectResId) {
        this.selectResId = selectResId;
        this.unSelectResId = unSelectResId;
        mImageViews = new ArrayList<>();
        this.removeAllViews();
        LayoutParams params;
        for (int i = 0; i < count; i++) {
            ImageView iv = new ImageView(mContext);
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                iv.setImageResource(selectResId);
            } else {
                params.leftMargin = marginLeft;
                iv.setImageResource(unSelectResId);
            }
            iv.setLayoutParams(params);
            mImageViews.add(iv);
            this.addView(iv);
        }
    }

    /**
     * 滑动时移动指示点
     *
     * @param startPosition 上一个点
     * @param nextPosition  下一个点
     */
    public void playByStartPointToNext(int startPosition, int nextPosition) {
        if (startPosition < 0 || nextPosition < 0 || nextPosition == startPosition) {
            return;
        }
        final ImageView ivStart = (ImageView) mImageViews.get(startPosition);
        final ImageView ivNext = (ImageView) mImageViews.get(nextPosition);
        ivNext.setImageResource(selectResId);
        ivStart.setImageResource(unSelectResId);
    }
}

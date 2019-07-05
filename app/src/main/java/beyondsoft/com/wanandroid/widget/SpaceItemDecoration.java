package beyondsoft.com.wanandroid.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author zhaixiaofan
 * @date 2019/7/5 9:54 PM
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    //间距大小
    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) {
            //item是第一个的时候不设置间距
            outRect.left = 0;
        }else {
            outRect.left = space;
        }
    }
}

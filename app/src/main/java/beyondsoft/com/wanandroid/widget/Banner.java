package beyondsoft.com.wanandroid.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import beyondsoft.com.wanandroid.R;

public class Banner extends LinearLayout {

    private static final String TAG = "Banner";
    private Context mContext;
    private ViewPager mViewPager;
    private IndicatorView indicatorView;
    private HeaderAdapter mAdapter;
    private List<View> mData = new ArrayList<>();
    private int startPosition = 0;
    private OnPagerItemClickListener mListener;
    private MyHandler mHandler = new MyHandler(this);
    private static final int MSG_LOOP = 0x001;
    private static final int DELAYED_TIME = 4*1000;

    private static class MyHandler extends Handler {
        WeakReference<Banner> reference;
        public MyHandler(Banner banner) {
            reference = new WeakReference<>(banner);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Banner banner = reference.get();
            if (msg.what == MSG_LOOP) {
                int currentItem = banner.mViewPager.getCurrentItem();
                Log.e(TAG, "currentItem = " + currentItem);
                currentItem ++;
                banner.mViewPager.setCurrentItem(currentItem);
                banner.mHandler.sendEmptyMessageDelayed(MSG_LOOP, DELAYED_TIME);
            }
        }
    }

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.banner, null);
        mViewPager = view.findViewById(R.id.vp_banner);
        indicatorView = view.findViewById(R.id.indicator);
        mAdapter = new HeaderAdapter(mContext);
        mViewPager.setAdapter(mAdapter);
        addView(view);
        setGravity(Gravity.CENTER);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int currentPosition = i % mData.size();
                indicatorView.playByStartPointToNext(startPosition, currentPosition);
                startPosition = currentPosition;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * pager的点击事件
     *
     * @param listener
     */
    public void setOnPagerItemClickListener(OnPagerItemClickListener listener) {
        mListener = listener;
    }

    /**
     * 设置Banner数据
     * @param data
     */
    public void updateBanner(List<View> data) {
        if (data != null) {
            mData = data;
            mAdapter.updateData(data);
            indicatorView.initIndicator(data.size());
        }
    }

    /**
     * 设置当前位置 不建议设置Integer.Max,设置太大会造成ANR
     * 因为setCurrentItem时测量页数太大，绘制UI频繁而阻塞主线程ANR
     *
     * 建议设置为data.size()的整数倍 比如:banner.size*10
     * 必须在updateBanner之后调用
     * @param page
     */
    public void setCurrentItem(int page) {
        startPosition = page % mData.size();
        mViewPager.setCurrentItem(page, true);
    }

    /**
     * 开启循环
     */
    public void startLoop() {
        mHandler.sendEmptyMessageDelayed(MSG_LOOP, DELAYED_TIME);
    }

    /**
     * 关闭循环
     */
    public void stopLoop() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public class HeaderAdapter extends PagerAdapter {

        private List<View> dataList = new ArrayList<>();
        private Context mContext;

        public HeaderAdapter(Context context) {
            this.mContext = context;
        }

        public HeaderAdapter(Context context, ArrayList<View> dataList) {
            this.mContext = context;
            this.dataList = dataList;
        }

        public void updateData(List<View> dataList) {
            this.dataList = dataList;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if(dataList.size() == 0){
                return 0;
            }else {
                return Integer.MAX_VALUE;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= dataList.size();
            if (position<0){
                position = dataList.size()+position;
            }
            ImageView iv = (ImageView) dataList.get(position);
            ViewParent vp =iv.getParent();
            if (vp!=null){
                ViewGroup parent = (ViewGroup)vp;
                parent.removeView(iv);
            }
            container.addView(iv);
            final int finalPosition = position;
            iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onPagerItemClick(finalPosition);
                }
            });
            return iv;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }

    public interface OnPagerItemClickListener {
        void onPagerItemClick(int position);
    }
}

package beyondsoft.com.wanandroid.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import beyondsoft.com.wanandroid.R;
import beyondsoft.com.wanandroid.base.BaseActivity;
import beyondsoft.com.wanandroid.ui.adapter.TestAdapter;
import beyondsoft.com.wanandroid.widget.SpaceItemDecoration;

/**
 * @author zhaixiaofan
 * @date 2019/7/5 9:21 PM
 */
public class TestActivity extends BaseActivity {

    private RecyclerView mRecycler;

    private List<String> data = new ArrayList<>();
    @Override
    public void getData() {

    }

    @Override
    public void initView() {
        mRecycler = findViewById(R.id.rv);
        TestAdapter adapter = new TestAdapter(this, data);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.addItemDecoration(new SpaceItemDecoration(80));
        mRecycler.setAdapter(adapter);
    }

    @Override
    public void initData() {
        data.add("1");
        data.add("1");
        data.add("1");

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_test;
    }
}

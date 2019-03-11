package beyondsoft.com.wanandroid

import android.util.Log
import beyondsoft.com.wanandroid.base.BaseMvpActivity
import beyondsoft.com.wanandroid.mvp.contract.HomeContract
import beyondsoft.com.wanandroid.mvp.model.bean.Banner
import beyondsoft.com.wanandroid.mvp.presenter.HomePresenter

const val TAG = "MainActivity"
class MainActivity : BaseMvpActivity<HomeContract.View, HomeContract.Presenter>() , HomeContract.View {

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun getData() {
        mPresenter?.getBanner()
    }

    override fun initData() {
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun reload() {
    }

    override fun setBanner(banner: ArrayList<Banner>) {
        Log.e(TAG, "banner = $banner")
    }
}

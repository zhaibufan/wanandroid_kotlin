package beyondsoft.com.wanandroid.ui.activity

import beyondsoft.com.wanandroid.R
import beyondsoft.com.wanandroid.base.BaseMvpActivity
import beyondsoft.com.wanandroid.mvp.contract.NavigationContract
import beyondsoft.com.wanandroid.mvp.presenter.NavigationPresenter

class SearchActivity : BaseMvpActivity<NavigationContract.View, NavigationContract.Presenter>() {

    override fun createPresenter(): NavigationContract.Presenter = NavigationPresenter()
    override fun getData() {
    }

    override fun initData() {
    }

    override fun getLayoutRes() : Int = R.layout.activity_search

    override fun reload() {
    }

}
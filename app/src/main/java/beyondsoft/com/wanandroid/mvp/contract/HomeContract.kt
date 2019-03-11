package beyondsoft.com.wanandroid.mvp.contract

import beyondsoft.com.wanandroid.base.IPresenter
import beyondsoft.com.wanandroid.base.IView
import beyondsoft.com.wanandroid.mvp.model.bean.Banner

interface HomeContract {

    interface Presenter : IPresenter<View> {
        fun getBanner()
    }

    interface View : IView {
        fun setBanner(banner: ArrayList<Banner>)
    }
}
package beyondsoft.com.wanandroid.mvp.contract

import beyondsoft.com.wanandroid.base.IPresenter
import beyondsoft.com.wanandroid.base.IView

class SearchContract {

    interface View : IView {

    }

    interface Presenter : IPresenter<View> {

    }
}
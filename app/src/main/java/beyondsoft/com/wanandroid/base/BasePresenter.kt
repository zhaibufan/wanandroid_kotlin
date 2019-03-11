package beyondsoft.com.wanandroid.base

import beyondsoft.com.wanandroid.api.RxManager

open class BasePresenter<T : IView> : IPresenter<T> {

    var mView: T? = null

    var mRxManager = RxManager()

    override fun attachView(view: T) {
        mView = view
    }

    override fun detachView() {
        mView = null
        mRxManager.clear()
    }

    override fun isAttachView(): Boolean {
        return mView != null
    }
}
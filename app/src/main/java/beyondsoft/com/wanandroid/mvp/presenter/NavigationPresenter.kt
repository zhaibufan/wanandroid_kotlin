package beyondsoft.com.wanandroid.mvp.presenter

import android.util.Log
import beyondsoft.com.wanandroid.api.ApiService
import beyondsoft.com.wanandroid.api.ApiStore
import beyondsoft.com.wanandroid.api.Transformer
import beyondsoft.com.wanandroid.base.BasePresenter
import beyondsoft.com.wanandroid.mvp.contract.NavigationContract
import beyondsoft.com.wanandroid.mvp.model.bean.HttpResult
import beyondsoft.com.wanandroid.mvp.model.bean.Navigation
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author zhaixiaofan
 * @date 2019/3/17 7:29 PM
 */
class NavigationPresenter : BasePresenter<NavigationContract.View>() , NavigationContract.Presenter {

    private val TAG = "NavigationPresenter"

    override fun getNavigation() {
        ApiStore.createApi(ApiService::class.java)
                ?.getNavigationData()
                ?.compose(Transformer.switchSchedulers())
                ?.subscribe(object : Observer<HttpResult<MutableList<Navigation>>> {

                    override fun onSubscribe(d: Disposable) {
                        mRxManager.add(d)
                    }

                    override fun onNext(t: HttpResult<MutableList<Navigation>>) {
                        Log.e(TAG, "result = ${t.toString()}" )
                        if (isAttachView()) {
                            if (t.errorCode == 0) {
                                mView?.setNavigation(t.data)
                            } else {
                                mView?.showError(t.errorMsg)
                            }
                        }
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                        if ( isAttachView()) {
                            mView?.showError(e.message.toString())
                        }
                    }
                })
    }

}
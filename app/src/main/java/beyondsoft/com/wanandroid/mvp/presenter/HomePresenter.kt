package beyondsoft.com.wanandroid.mvp.presenter

import android.util.Log
import beyondsoft.com.wanandroid.api.ApiService
import beyondsoft.com.wanandroid.api.ApiStore
import beyondsoft.com.wanandroid.api.Transformer
import beyondsoft.com.wanandroid.base.BasePresenter
import beyondsoft.com.wanandroid.mvp.contract.HomeContract
import beyondsoft.com.wanandroid.mvp.model.bean.Banner
import beyondsoft.com.wanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter{

    private val TAG = "HomePresenter"

    override fun getBanner() {
        ApiStore.createApi(ApiService::class.java)
                ?.getBanners()
                ?.compose(Transformer.switchSchedulers())
                ?.subscribe(object : Observer<HttpResult<ArrayList<Banner>>>{

                    override fun onSubscribe(d: Disposable) {
                        mRxManager.add(d)
                    }

                    override fun onNext(t: HttpResult<ArrayList<Banner>>) {
                        Log.e(TAG, "result = ${t.toString()}" )
                        if (isAttachView()) {
                            if (t.errorCode == 0) {
                                mView?.setBanner(t.data)
                            }
                        }
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }
}



package beyondsoft.com.wanandroid.mvp.presenter

import android.util.Log
import beyondsoft.com.wanandroid.api.ApiService
import beyondsoft.com.wanandroid.api.ApiStore
import beyondsoft.com.wanandroid.api.Transformer
import beyondsoft.com.wanandroid.base.BasePresenter
import beyondsoft.com.wanandroid.mvp.contract.HomeContract
import beyondsoft.com.wanandroid.mvp.model.bean.Article
import beyondsoft.com.wanandroid.mvp.model.bean.ArticleResponseBody
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

    override fun getTopArticle() {
        ApiStore.createApi(ApiService::class.java)
                ?.getTopArticles()
                ?.compose(Transformer.switchSchedulers())
                ?.subscribe(object : Observer<HttpResult<MutableList<Article>>>{
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                        mRxManager.add(d)
                    }

                    override fun onNext(t: HttpResult<MutableList<Article>>) {
                        if (isAttachView()) {
                            if (t.errorCode == 0) {
                                mView?.setTopArticleList(t.data)
                            } else {
                                mView?.showError(t.errorMsg)
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (isAttachView()) {
                            mView?.showError(e.message.toString())
                        }
                    }
                })
    }

    override fun getArticleList(page: Int) {
        ApiStore.createApi(ApiService::class.java)
                ?.getArticles(page)
                ?.compose(Transformer.switchSchedulers())
                ?.subscribe(object : Observer<HttpResult<ArticleResponseBody>>{
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                        mRxManager.add(d)
                    }

                    override fun onNext(t: HttpResult<ArticleResponseBody>) {
                        if (isAttachView()) {
                            if (t.errorCode == 0) {
                                mView?.setArticleList(t.data.datas)
                            } else {
                                mView?.showError(t.errorMsg)
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (isAttachView()) {
                            mView?.showError(e.message.toString())
                        }
                    }
                })
    }
}



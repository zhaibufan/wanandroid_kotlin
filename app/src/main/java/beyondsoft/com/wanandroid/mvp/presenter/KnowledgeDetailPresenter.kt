package beyondsoft.com.wanandroid.mvp.presenter

import beyondsoft.com.wanandroid.api.ApiService
import beyondsoft.com.wanandroid.api.ApiStore
import beyondsoft.com.wanandroid.api.Transformer
import beyondsoft.com.wanandroid.base.BasePresenter
import beyondsoft.com.wanandroid.mvp.contract.KnowledgeDetailContract
import beyondsoft.com.wanandroid.mvp.model.bean.ArticleResponseBody
import beyondsoft.com.wanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author zhaixiaofan
 * @date 2019/6/22 8:07 PM
 */
class KnowledgeDetailPresenter : BasePresenter<KnowledgeDetailContract.View>(), KnowledgeDetailContract.Presenter{

    override fun requestKnowledgeList(page: Int, cid: Int) {
        ApiStore.createApi(ApiService::class.java)
                ?.getKnowledgeList(page, cid)
                ?.compose(Transformer.switchSchedulers())
                ?.subscribe(object : Observer<HttpResult<ArticleResponseBody>> {

                    override fun onSubscribe(d: Disposable) {
                        mRxManager.add(d)
                    }

                    override fun onNext(t: HttpResult<ArticleResponseBody>) {
                        if (isAttachView()) {
                            if (t.errorCode == 0) {
                                mView?.setKnowledgeList(t.data)
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
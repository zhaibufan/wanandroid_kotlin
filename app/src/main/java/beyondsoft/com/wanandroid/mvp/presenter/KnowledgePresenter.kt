package beyondsoft.com.wanandroid.mvp.presenter

import beyondsoft.com.wanandroid.api.ApiService
import beyondsoft.com.wanandroid.api.ApiStore
import beyondsoft.com.wanandroid.api.Transformer
import beyondsoft.com.wanandroid.base.BasePresenter
import beyondsoft.com.wanandroid.constant.Constant
import beyondsoft.com.wanandroid.mvp.contract.KnowledgeTreeContract
import beyondsoft.com.wanandroid.mvp.model.bean.HttpResult
import beyondsoft.com.wanandroid.mvp.model.bean.KnowledgeTreeBody
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class KnowledgePresenter : BasePresenter<KnowledgeTreeContract.View>(), KnowledgeTreeContract.Presenter {

    override fun requestKnowledgeTree() {
        ApiStore.createApi(ApiService::class.java)
                ?.getKnowledgeTree()
                ?.compose(Transformer.switchSchedulers())
                ?.subscribe(object : Observer<HttpResult<List<KnowledgeTreeBody>>>{
                    override fun onSubscribe(d: Disposable) {
                        mRxManager.add(d)
                    }
                    override fun onNext(t: HttpResult<List<KnowledgeTreeBody>>) {
                        if (t.errorCode == Constant.RESPOSE_CODE_SUCCESS) {
                            if (isAttachView()) {
                                mView?.setKnowledgeTree(t.data)
                            }
                        } else {
                            mView?.showError(t.errorMsg)
                        }
                    }
                    override fun onComplete() {
                    }
                    override fun onError(e: Throwable) {
                        if (isAttachView()) {
                            mView?.showError(e.message.toString())
                        }
                    }
                })
    }
}
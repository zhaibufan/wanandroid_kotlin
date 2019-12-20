package beyondsoft.com.wanandroid.mvp.presenter

import beyondsoft.com.wanandroid.App
import beyondsoft.com.wanandroid.api.ApiService
import beyondsoft.com.wanandroid.api.ApiStore
import beyondsoft.com.wanandroid.api.Transformer
import beyondsoft.com.wanandroid.base.BasePresenter
import beyondsoft.com.wanandroid.constant.Constant
import beyondsoft.com.wanandroid.disklrucache.DiskCacheManager
import beyondsoft.com.wanandroid.mvp.contract.KnowledgeTreeContract
import beyondsoft.com.wanandroid.mvp.model.bean.HttpResult
import beyondsoft.com.wanandroid.mvp.model.bean.KnowledgeResult
import beyondsoft.com.wanandroid.mvp.model.bean.KnowledgeTreeBody
import beyondsoft.com.wanandroid.utils.LogUtils
import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function

const val TAG = "KnowledgePresenter"

class KnowledgePresenter : BasePresenter<KnowledgeTreeContract.View>(), KnowledgeTreeContract.Presenter {

    override fun requestKnowledgeTree() {
//        ApiStore.createApi(ApiService::class.java)
//                ?.getKnowledgeTree()
//                ?.compose(Transformer.switchSchedulers())
//                ?.subscribe(object : Observer<HttpResult<List<KnowledgeTreeBody>>>{
//                    override fun onSubscribe(d: Disposable) {
//                        mRxManager.add(d)
//                    }
//                    override fun onNext(t: HttpResult<List<KnowledgeTreeBody>>) {
//                        if (t.errorCode == Constant.RESPOSE_CODE_SUCCESS) {
//                            if (isAttachView()) {
//                                mView?.setKnowledgeTree(t.data)
//                            }
//                        } else {
//                            mView?.showError(t.errorMsg)
//                        }
//                    }
//                    override fun onComplete() {
//                    }
//                    override fun onError(e: Throwable) {
//                        if (isAttachView()) {
//                            mView?.showError(e.message.toString())
//                        }
//                    }
//                })



//        ApiStore.createApi(ApiService::class.java)
//                ?.getKnowledgeTree()
//                ?.map(object : Function<HttpResult<List<KnowledgeTreeBody>>, HttpResult<List<KnowledgeTreeBody>>> {
//                    override fun apply(t: HttpResult<List<KnowledgeTreeBody>>): HttpResult<List<KnowledgeTreeBody>> {
//                        LogUtils.d(TAG, "map data = $t")
//                        val diskCacheManager = DiskCacheManager(App.context, Constant.KNOWLEDGE_FILE)
//                        diskCacheManager.put(Constant.KNOWLEDGE_CACHE, t)
//                        return t
//                    }
//                })
//                ?.compose(Transformer.switchSchedulers())
//                ?.subscribe(object : Observer<HttpResult<List<KnowledgeTreeBody>>> {
//                    override fun onSubscribe(d: Disposable) {
//                        LogUtils.d(TAG, "onSubscribe")
//                        mRxManager.add(d)
//                    }
//
//                    override fun onNext(t: HttpResult<List<KnowledgeTreeBody>>) {
//                        LogUtils.d(TAG, "onNext")
//                        if (t.errorCode == Constant.RESPOSE_CODE_SUCCESS) {
//                            if (isAttachView()) {
//                                mView?.setKnowledgeTree(t.data)
//                            }
//                        } else {
//                            mView?.showError(t.errorMsg)
//                        }
//                    }
//
//                    override fun onComplete() {
//                        LogUtils.d(TAG, "onComplete")
//                    }
//
//                    override fun onError(e: Throwable) {
//                        LogUtils.d(TAG, "onError")
//                        if (isAttachView()) {
//                            mView?.showError(e.message.toString())
//                        }
//                    }
//                })


        ApiStore.createApi(ApiService::class.java)
                ?.getKnowledgeTree()
                //使用map进行转换，参数1：转换前的类型，参数2：转换后的类型
                ?.map(object : Function<HttpResult<List<KnowledgeTreeBody>>, List<KnowledgeTreeBody>> {
                    override fun apply(t: HttpResult<List<KnowledgeTreeBody>>): List<KnowledgeTreeBody> {
                        LogUtils.d(TAG, "map data = $t")
                        val diskCacheManager = DiskCacheManager(App.context, Constant.WANANDROID_CACHE_FILE)
                        diskCacheManager.put(Constant.KNOWLEDGE_CACHE, t)
                        diskCacheManager.put(Constant.HOME_BANNER_CACHE, "test data")
                        return t.data
                    }
                })
                ?.compose(Transformer.switchSchedulers())
                ?.subscribe(object : Observer<List<KnowledgeTreeBody>> {

                    override fun onNext(t: List<KnowledgeTreeBody>) {
                        LogUtils.d(TAG, "onNext")
                        if (isAttachView()) {
                            mView?.setKnowledgeTree(t)
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        LogUtils.d(TAG, "onSubscribe")
                        mRxManager.add(d)
                    }

                    override fun onComplete() {
                        LogUtils.d(TAG, "onComplete")
                    }

                    override fun onError(e: Throwable) {
                        LogUtils.d(TAG, "onError")
                        if (isAttachView()) {
                            mView?.showError(e.message.toString())
                        }
                    }
                })

    }

    override fun loadCache() {
        val diskCacheManager = DiskCacheManager(App.context, Constant.WANANDROID_CACHE_FILE)
        Observable.create(object : ObservableOnSubscribe<HttpResult<List<KnowledgeTreeBody>>> {
            override fun subscribe(emitter: ObservableEmitter<HttpResult<List<KnowledgeTreeBody>>>) {
                LogUtils.e(TAG, "create subscribe")
                val result = diskCacheManager.getSerializable<HttpResult<List<KnowledgeTreeBody>>>(Constant.KNOWLEDGE_CACHE)
                LogUtils.d(TAG, "result = $result")
                val testData = diskCacheManager.getString(Constant.HOME_BANNER_CACHE)
                LogUtils.e(TAG, "testData = $testData")
                emitter.onNext(result)
            }
        })
                .subscribe(object : Observer<HttpResult<List<KnowledgeTreeBody>>> {
                    override fun onComplete() {
                        LogUtils.e(TAG, "onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        LogUtils.e(TAG, "onSubscribe")
                    }

                    override fun onNext(t: HttpResult<List<KnowledgeTreeBody>>) {
                        LogUtils.e(TAG, "onNext")
                        if (t.errorCode == Constant.RESPOSE_CODE_SUCCESS) {
                            if (isAttachView()) {
                                mView?.setKnowledgeTree(t.data)
                            }
                        } else {
                            mView?.showError(t.errorMsg)
                        }
                    }

                    override fun onError(e: Throwable) {
                        LogUtils.e(TAG, "onError")
                        if (isAttachView()) {
                            mView?.showError(e.message.toString())
                        }
                    }
                })
    }
}





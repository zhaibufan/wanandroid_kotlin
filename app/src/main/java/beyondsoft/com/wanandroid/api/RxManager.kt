package beyondsoft.com.wanandroid.api

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class RxManager {

    var compositeDisposable = CompositeDisposable()

    fun add(d : Disposable) {
        compositeDisposable.add(d)
    }

    fun clear() {
        compositeDisposable.clear()
    }
}
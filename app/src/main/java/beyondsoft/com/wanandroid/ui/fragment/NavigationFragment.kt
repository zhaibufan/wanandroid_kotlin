package beyondsoft.com.wanandroid.ui.fragment

import android.annotation.SuppressLint
import android.util.Log
import beyondsoft.com.wanandroid.R
import beyondsoft.com.wanandroid.base.BaseFragment
import io.reactivex.Observable
import io.reactivex.Observer
import beyondsoft.com.wanandroid.test.Test.Student
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import java.util.function.Function


class NavigationFragment : BaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_navigation
    }

    override fun getData() {
    }

    override fun initView() {
    }

    @SuppressLint("CheckResult")
    override fun initData() {

        Observable.create(ObservableOnSubscribe<String> { emitter ->
            emitter.onNext("1")
            emitter.onNext("1")
            emitter.onNext("1")

        })
                .flatMap(object : io.reactivex.functions.Function<String, ObservableSource<String>> {
                    override fun apply(t: String): ObservableSource<String> {
                        val list = mutableListOf<String>()
                        for (index in 0..3){
                            list.add(index.toString())
                        }
                        return Observable.fromIterable(list)
                    }
                })
                .subscribe { s -> Log.i("NPL", "accept:$s") }


    }

}
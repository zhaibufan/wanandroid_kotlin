package beyondsoft.com.wanandroid.base

import android.app.ActivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import beyondsoft.com.wanandroid.utils.ActivityManagers

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        ActivityManagers.push(this)
        initData()
        initView()
        getData()
    }

    abstract fun getData()

    abstract fun initView()

    abstract fun initData()

    abstract fun getLayoutRes(): Int

    override fun onDestroy() {
        super.onDestroy()
        ActivityManagers.remove(this)
    }
}
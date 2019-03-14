package beyondsoft.com.wanandroid.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {

    var mActivity: Activity? = null
    var mContext: Context? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = this.activity
        mContext = this.context
        initData()
        initView()
        getData()
    }

    abstract fun getLayoutRes(): Int

    abstract fun getData()

    abstract fun initView()

    abstract fun initData()
}



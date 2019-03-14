package beyondsoft.com.wanandroid.base

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import beyondsoft.com.wanandroid.R

abstract class BaseMvpFragment<V : IView, P : IPresenter<V>> : BaseFragment(), IView {

    private val TAG = "BaseMvpFragment"

    private val NORMAL_STATE = 0
    private val LOADING_STATE = 1
    private val ERROR_STATE = 2
    private val EMPTY_STATE = 3
    private var currentState = NORMAL_STATE

    private var mErrorView: View? = null
    private var mLoadingView: View? = null
    private var mEmptyView: View? = null
    private var mNormalView: View? = null

    var mPresenter : P? = null


    override fun initView() {
        mNormalView = mActivity?.findViewById(R.id.normal_view)
        if (mNormalView == null) {
            throw IllegalStateException("There must be no mNormalView in the activity")
        }
        if (mNormalView?.parent !is ViewGroup) {
            throw IllegalStateException("The parent layout of mNormalView must belong to the viewgroup")
        }
        val parent = mNormalView?.parent as ViewGroup
        View.inflate(mActivity, R.layout.view_empty, parent)
        View.inflate(mActivity, R.layout.view_error, parent)
        View.inflate(mActivity, R.layout.view_loading, parent)

        mLoadingView = parent.findViewById(R.id.loading_group)
        mErrorView = parent.findViewById(R.id.error_group)
        mEmptyView = parent.findViewById(R.id.empty_group)

        // 重新加载
        parent.findViewById<TextView>(R.id.tv_reload).setOnClickListener {
            reload()
        }

        mEmptyView?.visibility = View.GONE
        mErrorView?.visibility = View.GONE
        mLoadingView?.visibility = View.GONE

        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    abstract fun createPresenter(): P

    override fun showNormal() {
        hideCurrentView()
        currentState = NORMAL_STATE
        mNormalView?.visibility = View.VISIBLE
    }

    override fun showLoading() {
        hideCurrentView()
        currentState = LOADING_STATE
        mLoadingView?.visibility = View.VISIBLE
    }

    override fun showEmpty() {
        hideCurrentView()
        currentState = EMPTY_STATE
        mEmptyView?.visibility = View.VISIBLE
    }

    override fun showError(err: String) {
        Toast.makeText(mActivity, err, Toast.LENGTH_SHORT).show()
        hideCurrentView()
        currentState = ERROR_STATE
        mErrorView?.visibility = View.VISIBLE
    }

    private fun hideCurrentView() {
        when (currentState) {
            NORMAL_STATE -> mNormalView?.visibility = View.GONE
            ERROR_STATE -> mErrorView?.visibility = View.GONE
            EMPTY_STATE -> mEmptyView?.visibility = View.GONE
            LOADING_STATE -> mLoadingView?.visibility = View.GONE
            else -> {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
        mPresenter = null
    }
}
package beyondsoft.com.wanandroid.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import beyondsoft.com.wanandroid.R
import beyondsoft.com.wanandroid.base.BaseMvpFragment
import beyondsoft.com.wanandroid.mvp.contract.HomeContract
import beyondsoft.com.wanandroid.mvp.model.bean.Article
import beyondsoft.com.wanandroid.mvp.model.bean.Banner
import beyondsoft.com.wanandroid.mvp.presenter.HomePresenter
import beyondsoft.com.wanandroid.ui.adapter.ArticleAdapter
import beyondsoft.com.wanandroid.utils.LogUtils
import com.zhouyou.recyclerview.XRecyclerView
import com.zhouyou.recyclerview.refresh.ProgressStyle

class HomeFragment : BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    private val TAG = "HomeFragment"

    var pageSize = 0  // 文章的页码
    var mRecyclerView: XRecyclerView? = null
    var mAdapter: ArticleAdapter? = null
    var mLayoutManager: LinearLayoutManager? = null
    var mArticleData = mutableListOf<Article>()
    var loadBannerFinished = false
    var loadArticleFinished = false
    var loadTopArticleFinished = false

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun getData() {
        mPresenter?.getBanner()
        mPresenter?.getTopArticle()
        mPresenter?.getArticleList(pageSize)
    }

    override fun initView() {
        super.initView()
        showLoading()
        mRecyclerView = mActivity?.findViewById(R.id.recyclerView)
        mLayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mLayoutManager?.isAutoMeasureEnabled = true
        mRecyclerView?.layoutManager = mLayoutManager
        mAdapter = ArticleAdapter(mContext)
        mRecyclerView?.adapter = mAdapter

        addHeader()
        mRecyclerView?.setRefreshProgressStyle(ProgressStyle.LineScaleParty)
        mRecyclerView?.setLoadingMoreProgressStyle(ProgressStyle.LineScaleParty)

        mRecyclerView?.isLoadingMoreEnabled = true
        mRecyclerView?.isPullRefreshEnabled = true
        mRecyclerView?.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                Log.e(TAG, "onLoadMore")
            }

            override fun onRefresh() {
                Log.e(TAG, "onRefresh")
            }
        })
    }

    private fun addHeader() {
        val headerView = layoutInflater.inflate(R.layout.layout_home_banner, null)
        mRecyclerView?.addHeaderView(headerView)
    }

    override fun initData() {
    }

    override fun setBanner(banner: ArrayList<Banner>) {
        LogUtils.d(TAG, "banner=$banner")
        loadBannerFinished = true
        isAllLoadFinished()
    }

    override fun setArticleList(data: MutableList<Article>) {
        LogUtils.d(TAG, "data=$data")
        loadArticleFinished = true
        isAllLoadFinished()
        mArticleData.addAll(data)
        mAdapter?.setData(mArticleData)
    }

    override fun setTopArticleList(data: MutableList<Article>) {
        loadTopArticleFinished = true
        isAllLoadFinished()
        mArticleData.addAll(0, data)
    }

    /**
     * 判断所有数据是否加载完成 若加载完成就cancelLoading 显示normalView
     */
    private fun isAllLoadFinished() {
        if (loadBannerFinished && loadArticleFinished && loadTopArticleFinished) {
            showNormal()
        }
    }

    override fun reload() {
    }
}
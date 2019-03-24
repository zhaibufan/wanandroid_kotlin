package beyondsoft.com.wanandroid.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import beyondsoft.com.wanandroid.R
import beyondsoft.com.wanandroid.base.BaseMvpFragment
import beyondsoft.com.wanandroid.mvp.contract.HomeContract
import beyondsoft.com.wanandroid.mvp.model.bean.Article
import beyondsoft.com.wanandroid.mvp.model.bean.Banner
import beyondsoft.com.wanandroid.mvp.presenter.HomePresenter
import beyondsoft.com.wanandroid.ui.adapter.ArticleAdapter
import beyondsoft.com.wanandroid.ui.adapter.OnItemViewClickListener
import beyondsoft.com.wanandroid.utils.LogUtils
import com.bumptech.glide.Glide
import com.zhouyou.recyclerview.XRecyclerView
import com.zhouyou.recyclerview.refresh.ProgressStyle

class HomeFragment : BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    private val TAG = "HomeFragment"

    var pageSize = 0  // 文章的页码
    var mRecyclerView: XRecyclerView? = null
    var mAdapter: ArticleAdapter? = null
    var mLayoutManager: LinearLayoutManager? = null
    var mArticleData = mutableListOf<Article>() //列表的数据
    var temporaryData = mutableListOf<Article>() //临时的数据集合 用于保存列表临时数据
    var loadBannerFinished = false
    var loadArticleFinished = false
    var loadTopArticleFinished = false

    var mBanner: beyondsoft.com.wanandroid.widget.Banner? = null
    var bannerData = arrayListOf<ImageView>()

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
    }

    override fun initView() {
        super.initView()
        showLoading()
        mRecyclerView = mActivity?.findViewById(R.id.normal_view)
        mLayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mLayoutManager?.isAutoMeasureEnabled = true
        mRecyclerView?.layoutManager = mLayoutManager
        mAdapter = ArticleAdapter(mContext!!)
        mRecyclerView?.adapter = mAdapter

        addHeader()
        mRecyclerView?.setRefreshProgressStyle(ProgressStyle.LineScaleParty)
        mRecyclerView?.setLoadingMoreProgressStyle(ProgressStyle.LineScaleParty)

        mRecyclerView?.isLoadingMoreEnabled = true
        mRecyclerView?.isPullRefreshEnabled = true
        mRecyclerView?.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                loadMore()
            }

            override fun onRefresh() {
                Log.e(TAG, "onRefresh")
                refreshData()
            }
        })

        mAdapter?.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onLikeClick(position: Int) {
                Log.e(TAG, "收藏  position = $position")
            }
        })
        mAdapter?.setOnItemClickListener { view, item, position ->
            Log.e(TAG, "position = $position item=$item")
        }
    }

    override fun getData() {
        mPresenter?.getBanner()
        mPresenter?.getTopArticle()
        mPresenter?.getArticleList(pageSize)
    }

    override fun onStart() {
        super.onStart()
        mBanner?.startLoop()
    }

    override fun onStop() {
        super.onStop()
        mBanner?.stopLoop()
    }

    /**
     * 加载更多
     */
    private fun loadMore() {
        pageSize++
        mPresenter?.getArticleList(pageSize)
    }

    /**
     * 刷新最新数据
     */
    private fun refreshData() {
        pageSize = 0
        //保存列表数据(避免刷新失败后之前列表数据被清空导致页面没数据，所以当请求失败后将
        // temporaryData数据再赋值给mArticleData，详见showError方法)
        temporaryData.clear()
        temporaryData.addAll(mArticleData)
        //清空列表数据
        mArticleData.clear()
        //刷新数据
        mPresenter?.getArticleList(pageSize)
        mPresenter?.getTopArticle()
        loadTopArticleFinished = false
        loadArticleFinished = false

    }

    private fun addHeader() {
        val headerView = layoutInflater.inflate(R.layout.layout_home_banner, null)
        mBanner = headerView.findViewById(R.id.view_banner)
        mRecyclerView?.addHeaderView(headerView)
    }

    override fun setBanner(banner: ArrayList<Banner>) {
        LogUtils.d(TAG, "banner=$banner")
        loadBannerFinished = true
        isAllLoadFinished()
        for (ban in banner) {
            val iv = ImageView(mContext)
            Glide.with(this)
                    .load(ban.imagePath)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(iv)
            bannerData.add(iv)
        }
        mBanner?.updateBanner(bannerData as List<View>?)
        mBanner?.setCurrentItem(banner.size * 10)
        mBanner?.setOnPagerItemClickListener {
            Log.e(TAG, "position=$it")
        }
    }

    override fun setArticleList(data: MutableList<Article>) {
        LogUtils.d(TAG, "data=$data")
        loadArticleFinished = true
        if (pageSize == 0) { //首次加载 第一页
            isRefreshFinished()
            isAllLoadFinished()
            mArticleData.addAll(data)
            mAdapter?.setListAll(mArticleData)
        } else {
            if (data.size > 0) { //更多数据
                mArticleData.addAll(data)
                mAdapter?.setListAll(mArticleData)
                mRecyclerView?.loadMoreComplete()
                mRecyclerView?.isLoadingMoreEnabled = true
            } else { //已加载所有 没有数据了
                mRecyclerView?.setNoMore(true)
            }
        }
    }

    override fun setTopArticleList(data: MutableList<Article>) {
        loadTopArticleFinished = true
        isRefreshFinished()
        isAllLoadFinished()
        mArticleData.addAll(0, data)
        mAdapter?.setListAll(mArticleData)
    }

    /**
     * 判断所有数据是否加载完成 若加载完成就cancelLoading 显示normalView
     */
    private fun isAllLoadFinished() {
        if (loadBannerFinished && loadArticleFinished && loadTopArticleFinished) {
            showNormal()
        }
    }

    /**
     * 判断数据刷新是否完成
     */
    private fun isRefreshFinished() {
        if (loadArticleFinished && loadTopArticleFinished) {
            if (mRecyclerView!!.isRefreshing) {
                mRecyclerView?.refreshComplete()
            }
        }
    }

    /**
     * 重写showError方法 当刷新失败后再将临时数据赋给列表数据
     */
    override fun showError(err: String) {
        super.showError(err)
        mArticleData.addAll(temporaryData)
    }

    override fun reload() {
    }

    fun scrollToTop() {
        mRecyclerView?.run {
            if (mLayoutManager!!.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }
}
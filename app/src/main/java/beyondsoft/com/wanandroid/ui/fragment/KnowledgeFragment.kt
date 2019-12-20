package beyondsoft.com.wanandroid.ui.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import beyondsoft.com.wanandroid.App
import beyondsoft.com.wanandroid.R
import beyondsoft.com.wanandroid.base.BaseMvpFragment
import beyondsoft.com.wanandroid.constant.Constant
import beyondsoft.com.wanandroid.disklrucache.DiskCacheManager
import beyondsoft.com.wanandroid.mvp.contract.KnowledgeTreeContract
import beyondsoft.com.wanandroid.mvp.model.bean.KnowledgeTreeBody
import beyondsoft.com.wanandroid.mvp.presenter.KnowledgePresenter
import beyondsoft.com.wanandroid.ui.activity.KnowledgeActivity
import beyondsoft.com.wanandroid.ui.adapter.KnowledgeAdapter
import beyondsoft.com.wanandroid.utils.LogUtils
import com.zhouyou.recyclerview.XRecyclerView

class KnowledgeFragment : BaseMvpFragment<KnowledgeTreeContract.View, KnowledgeTreeContract.Presenter>(), KnowledgeTreeContract.View {

    val TAG = "KnowledgeFragment"
    private var recyclerView: XRecyclerView? = null
    private val mData = mutableListOf<KnowledgeTreeBody>()
    private val mAdapter: KnowledgeAdapter by lazy { KnowledgeAdapter(context!!) }

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    override fun createPresenter(): KnowledgeTreeContract.Presenter = KnowledgePresenter()

    override fun getLayoutRes(): Int = R.layout.fragment_knowledge

    override fun getData() {
        //mPresenter?.loadCache()
        mPresenter?.requestKnowledgeTree()
    }

    override fun initData() {
    }

    override fun initView() {
        super.initView()
        recyclerView = view!!.findViewById(R.id.normal_view)
        showLoading()
        recyclerView!!.run {
            adapter = mAdapter
            layoutManager = linearLayoutManager
        }
        recyclerView?.isLoadingMoreEnabled = false
        recyclerView?.isPullRefreshEnabled = false

        val function: (View, Any, Int) -> Unit = { view, item, position ->
            val knowledgeTreeBody = mData[position]
            Intent(activity, KnowledgeActivity::class.java).run {
                putExtra(Constant.CONTENT_TITLE_KEY, knowledgeTreeBody.name)
                putExtra(Constant.CONTENT_DATA_KEY, knowledgeTreeBody)
                startActivity(this)
            }
            mPresenter?.loadCache()
        }
        mAdapter.setOnItemClickListener(function)
    }

    override fun scrollToTop() {
    }

    override fun setKnowledgeTree(lists: List<KnowledgeTreeBody>) {
        showNormal()
        mData.addAll(lists)
        mAdapter.setListAll(mData)
    }
}
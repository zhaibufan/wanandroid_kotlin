package beyondsoft.com.wanandroid.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import beyondsoft.com.wanandroid.R
import beyondsoft.com.wanandroid.base.BaseMvpFragment
import beyondsoft.com.wanandroid.mvp.contract.KnowledgeTreeContract
import beyondsoft.com.wanandroid.mvp.model.bean.KnowledgeTreeBody
import beyondsoft.com.wanandroid.mvp.presenter.KnowledgePresenter
import beyondsoft.com.wanandroid.ui.adapter.KnowledgeAdapter
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
    }

    override fun scrollToTop() {
    }

    override fun setKnowledgeTree(lists: List<KnowledgeTreeBody>) {
        showNormal()
        mData.addAll(lists)
        mAdapter.setListAll(mData)
    }
}
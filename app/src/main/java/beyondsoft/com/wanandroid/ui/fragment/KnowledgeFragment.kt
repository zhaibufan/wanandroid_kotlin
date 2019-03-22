package beyondsoft.com.wanandroid.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import beyondsoft.com.wanandroid.R
import beyondsoft.com.wanandroid.base.BaseMvpFragment
import beyondsoft.com.wanandroid.mvp.contract.KnowledgeTreeContract
import beyondsoft.com.wanandroid.mvp.model.bean.KnowledgeTreeBody
import beyondsoft.com.wanandroid.mvp.presenter.KnowledgePresenter
import beyondsoft.com.wanandroid.ui.adapter.KnowledgeAdapter
import kotlinx.android.synthetic.main.fragment_knowledge.*

class KnowledgeFragment : BaseMvpFragment<KnowledgeTreeContract.View, KnowledgeTreeContract.Presenter>(), KnowledgeTreeContract.View {

    private val mData = mutableListOf<KnowledgeTreeBody>()
    private val mAdapter : KnowledgeAdapter by lazy { KnowledgeAdapter(context!!) }
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
        recyclerView.run {
            adapter = mAdapter
            layoutManager = linearLayoutManager
        }
        showLoading()
    }

    override fun scrollToTop() {
    }

    override fun setKnowledgeTree(lists: List<KnowledgeTreeBody>) {
        showNormal()
        mData.addAll(lists)
        mAdapter.setListAll(mData)
    }
}
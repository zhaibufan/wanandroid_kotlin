package beyondsoft.com.wanandroid.ui.fragment

import beyondsoft.com.wanandroid.R
import beyondsoft.com.wanandroid.base.BaseFragment
import beyondsoft.com.wanandroid.base.BaseMvpFragment
import beyondsoft.com.wanandroid.mvp.contract.KnowledgeTreeContract
import beyondsoft.com.wanandroid.mvp.model.bean.KnowledgeTreeBody
import beyondsoft.com.wanandroid.mvp.presenter.KnowledgePresenter

class ProjectFragment : BaseMvpFragment<KnowledgeTreeContract.View, KnowledgeTreeContract.Presenter>(), KnowledgeTreeContract.View {
    override fun createPresenter(): KnowledgeTreeContract.Presenter = KnowledgePresenter()

    override fun scrollToTop() {
    }

    override fun setKnowledgeTree(lists: List<KnowledgeTreeBody>) {
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_project
    }

    override fun getData() {
    }

    override fun initView() {
        super.initView()
    }

    override fun initData() {
    }

}
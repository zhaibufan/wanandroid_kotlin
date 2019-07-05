package beyondsoft.com.wanandroid.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import beyondsoft.com.wanandroid.R
import beyondsoft.com.wanandroid.base.BaseMvpFragment
import beyondsoft.com.wanandroid.constant.Constant
import beyondsoft.com.wanandroid.mvp.contract.KnowledgeDetailContract
import beyondsoft.com.wanandroid.mvp.model.bean.Article
import beyondsoft.com.wanandroid.mvp.model.bean.ArticleResponseBody
import beyondsoft.com.wanandroid.mvp.presenter.KnowledgeDetailPresenter
import beyondsoft.com.wanandroid.ui.adapter.ArticleAdapter
import beyondsoft.com.wanandroid.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_knowledge_detail.*

/**
 * @author zhaixiaofan
 * @date 2019/6/22 7:59 PM
 */
class KnowledgeDetailFragment : BaseMvpFragment<KnowledgeDetailContract.View, KnowledgeDetailContract.Presenter>(), KnowledgeDetailContract.View {

    override fun createPresenter(): KnowledgeDetailContract.Presenter = KnowledgeDetailPresenter()

    private var cid: Int = 0
    var mArticleData = mutableListOf<Article>() //列表的数据

    /**
     * mAdapter必须设置成成员变量 否则加载数据不显示
     */
    private val mAdapter: ArticleAdapter by lazy {
        ArticleAdapter(this!!.mContext!!)
    }

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(mContext)
    }

    companion object {
        fun getInstance(cid: Int): KnowledgeDetailFragment {
            val fragment = KnowledgeDetailFragment()
            val args = Bundle()
            args.putInt(Constant.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }


    override fun getLayoutRes(): Int = R.layout.fragment_knowledge_detail

    override fun getData() {
        requestData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
//            requestData()
        }
    }

    fun requestData() {
        showLoading()
        mPresenter?.requestKnowledgeList(0, cid)
    }

    override fun initView() {
        super.initView()
        LogUtils.e("TAG", "initView" )
        linearLayoutManager?.isAutoMeasureEnabled = true
        normal_view?.layoutManager = linearLayoutManager
        normal_view?.adapter = mAdapter

    }

    override fun initData() {
        cid = arguments?.getInt(Constant.CONTENT_CID_KEY) ?: 0
    }

    override fun setKnowledgeList(articles: ArticleResponseBody) {
        showNormal()
        mArticleData.clear()
        mArticleData.addAll(articles.datas)
        mAdapter.setListAll(mArticleData)
    }

}
package beyondsoft.com.wanandroid.mvp.contract

import beyondsoft.com.wanandroid.base.IPresenter
import beyondsoft.com.wanandroid.base.IView
import beyondsoft.com.wanandroid.mvp.model.bean.ArticleResponseBody

/**
 * @author zhaixiaofan
 * @date 2019/6/22 8:01 PM
 */
class KnowledgeDetailContract {

    interface View : IView {
        fun setKnowledgeList(articles: ArticleResponseBody)
    }

    interface Presenter : IPresenter<View> {
        fun requestKnowledgeList(page: Int, cid: Int)
    }
}
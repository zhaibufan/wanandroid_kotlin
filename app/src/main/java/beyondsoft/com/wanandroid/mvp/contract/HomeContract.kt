package beyondsoft.com.wanandroid.mvp.contract

import beyondsoft.com.wanandroid.base.IPresenter
import beyondsoft.com.wanandroid.base.IView
import beyondsoft.com.wanandroid.mvp.model.bean.Article
import beyondsoft.com.wanandroid.mvp.model.bean.ArticleResponseBody
import beyondsoft.com.wanandroid.mvp.model.bean.Banner

interface HomeContract {

    interface Presenter : IPresenter<View> {
        fun getBanner()
        fun getTopArticle()
        fun getArticleList(page : Int)
    }

    interface View : IView {
        fun setBanner(banner: ArrayList<Banner>)
        fun setArticleList(data : MutableList<Article>)
        fun setTopArticleList(data : MutableList<Article>)
    }
}
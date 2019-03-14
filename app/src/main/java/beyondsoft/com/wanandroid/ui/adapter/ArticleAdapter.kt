package beyondsoft.com.wanandroid.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import beyondsoft.com.wanandroid.R
import beyondsoft.com.wanandroid.mvp.model.bean.Article
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleAdapter(val context:Context?) : RecyclerView.Adapter<ArticleAdapter.ArticleHolder>(){

    var mData : MutableList<Article>? = null

    fun setData(data:MutableList<Article>) {
        mData = data
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ArticleHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_article, p0, false)
        return ArticleHolder(view)
    }

    override fun getItemCount(): Int {
        return if (mData == null) 0 else mData!!.size
    }

    override fun onBindViewHolder(p0: ArticleHolder, p1: Int) {
        val article = mData!![p1]
        p0.itemView.run {
            tv_author.text = article.author
            tv_date.text = article.niceDate
            tv_title.text = article.title
            tv_type.text = article.superChapterName + "/" + article.chapterName
            if (article.type != 1) {
                tv_article_top.visibility = View.GONE
            } else {
                tv_article_top.visibility = View.VISIBLE
            }
        }
    }

    inner class ArticleHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var tv_article_top : TextView? = null
        var tv_author : TextView?= null
        var tv_date : TextView?= null
        var tv_title : TextView?= null
        var tv_type : TextView?= null
        var iv_like : ImageView?= null

        init {
            tv_article_top = itemView.findViewById(R.id.tv_article_top)
            tv_author = itemView.findViewById(R.id.tv_author)
            tv_date = itemView.findViewById(R.id.tv_date)
            tv_title = itemView.findViewById(R.id.tv_title)
            tv_type = itemView.findViewById(R.id.tv_type)
            iv_like = itemView.findViewById(R.id.iv_like)
        }
    }

}

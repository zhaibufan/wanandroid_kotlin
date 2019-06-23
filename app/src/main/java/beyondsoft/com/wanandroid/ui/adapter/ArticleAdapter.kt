package beyondsoft.com.wanandroid.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import beyondsoft.com.wanandroid.R
import beyondsoft.com.wanandroid.mvp.model.bean.Article
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder

class ArticleAdapter(context: Context) : HelperRecyclerViewAdapter<Article>(context, R.layout.item_article) {

    var mListener: OnItemViewClickListener? = null

    fun setOnItemViewClickListener(listener: OnItemViewClickListener) {
        mListener = listener
    }

    /**
     * 绑定数据
     */
    override fun HelperBindData(viewHolder: HelperRecyclerViewHolder?, position: Int, item: Article?) {
//        val data = getData(position)
        viewHolder?.run {
            setText(R.id.tv_author, item?.author)
            setText(R.id.tv_date, item?.niceDate)
            setText(R.id.tv_title, item?.title)
            setText(R.id.tv_type, item?.superChapterName + "/" + item?.chapterName)
            val topView = getView<TextView>(R.id.tv_article_top)
            if (item?.type != 1) {
                topView.visibility = View.GONE
            } else {
                topView.visibility = View.VISIBLE
            }
        }
    }

    /**
     * 绑定事件
     */
    override fun setListener(viewHolder: HelperRecyclerViewHolder?, position: Int, item: Article?) {
        super.setListener(viewHolder, position, item)
        viewHolder?.setOnClickListener(R.id.iv_like) {
            mListener?.onLikeClick(position)
        }
    }

}

/**
 * Item上面部分点击事件
 */
interface OnItemViewClickListener {
    /**
     * 收藏的点击事件
     */
    fun onLikeClick(position: Int)
}


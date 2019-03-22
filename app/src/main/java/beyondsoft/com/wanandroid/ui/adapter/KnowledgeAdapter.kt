package beyondsoft.com.wanandroid.ui.adapter

import android.content.Context
import android.text.Html
import beyondsoft.com.wanandroid.R
import beyondsoft.com.wanandroid.mvp.model.bean.KnowledgeTreeBody
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder

class KnowledgeAdapter(context: Context) : HelperRecyclerViewAdapter<KnowledgeTreeBody>(context, R.layout.adapter_knowledge) {

    override fun HelperBindData(viewHolder: HelperRecyclerViewHolder?, position: Int, item: KnowledgeTreeBody?) {
        viewHolder?.run {
            setText(R.id.title_first, item?.name)
            item?.children.let {
                 setText(R.id.title_second, it?.joinToString("      ", transform = {
                     Html.fromHtml(it.name)
                 }))
            }
        }
    }
}
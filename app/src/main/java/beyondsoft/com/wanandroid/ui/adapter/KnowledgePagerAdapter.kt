package beyondsoft.com.wanandroid.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.text.Html
import android.view.ViewGroup
import beyondsoft.com.wanandroid.mvp.model.bean.Knowledge
import beyondsoft.com.wanandroid.ui.fragment.KnowledgeDetailFragment

/**
 * Created by chenxz on 2018/5/10.
 */
class KnowledgePagerAdapter(val list: List<Knowledge>, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments = mutableListOf<Fragment>()

    init {
        fragments.clear()
        list.forEach {
            fragments.add(KnowledgeDetailFragment.getInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? = Html.fromHtml(list[position].name)

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }

}
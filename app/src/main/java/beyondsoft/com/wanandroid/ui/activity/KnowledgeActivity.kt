package beyondsoft.com.wanandroid.ui.activity

import beyondsoft.com.wanandroid.R
import beyondsoft.com.wanandroid.base.BaseActivity
import beyondsoft.com.wanandroid.constant.Constant
import beyondsoft.com.wanandroid.mvp.model.bean.Knowledge
import beyondsoft.com.wanandroid.mvp.model.bean.KnowledgeTreeBody
import beyondsoft.com.wanandroid.ui.adapter.KnowledgePagerAdapter
import beyondsoft.com.wanandroid.widget.xtablayout.XTabLayout
import kotlinx.android.synthetic.main.activity_knowledge.*

/**
 * @author zhaixiaofan
 * @date 2019/6/22 7:30 PM
 */
class KnowledgeActivity : BaseActivity() {

    private lateinit var mTitleName : String
    private var knowledges = mutableListOf<Knowledge>()
    /**
     * ViewPagerAdapter
     */
    private val viewPagerAdapter: KnowledgePagerAdapter by lazy {
        KnowledgePagerAdapter(knowledges, supportFragmentManager)
    }

    override fun getData() {
    }

    override fun initView() {
        toolbars.run {
            title = mTitleName
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        viewPager.adapter = viewPagerAdapter
//        viewPager.offscreenPageLimit = knowledges.size
        tabs.setupWithViewPager(viewPager)

        tabs.addOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: XTabLayout.Tab?) {
                tab?.let {
                    viewPager.setCurrentItem(it.position, false)
                }
            }

            override fun onTabUnselected(tab: XTabLayout.Tab?) {
            }

            override fun onTabReselected(tab: XTabLayout.Tab?) {
            }
        })

        viewPager.currentItem = 0

    }

    override fun initData() {
        intent.extras.let {
            mTitleName = it.getString(Constant.CONTENT_TITLE_KEY) ?: ""
            it.getSerializable(Constant.CONTENT_DATA_KEY).let {
                val data = it as KnowledgeTreeBody
                data.children.let {
                    knowledges.addAll(it)
                }
            }

        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_knowledge

}
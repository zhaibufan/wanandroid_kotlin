package beyondsoft.com.wanandroid.ui.activity

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
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


    private val fragments = mutableListOf<Fragment>()
    private var mFm: FragmentManager? = null
    private var mFromFragment: Fragment? = null


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
        initViewPager()
//        initFragment()
    }

    private fun initFragment() {
        switchFragment(mFromFragment, fragments[0])
        tabs.addOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener{
            override fun onTabUnselected(tab: XTabLayout.Tab?) {
            }

            override fun onTabReselected(tab: XTabLayout.Tab?) {
            }

            override fun onTabSelected(tab: XTabLayout.Tab?) {
                tab?.let {
                    switchFragment(mFromFragment, fragments[it.position])
                }
            }
        })
    }

    private fun initViewPager() {
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

//        mFm = this.supportFragmentManager
//        fragments.clear()
//        knowledges.forEach {
//            fragments.add(KnowledgeDetailFragment.getInstance(it.id))
//            tabs.addTab(tabs.newTab().setText(it.name))
//        }

    }

    override fun getLayoutRes(): Int = R.layout.activity_knowledge


    @SuppressLint("CommitTransaction")
    private fun switchFragment(from: Fragment?, to: Fragment?) {
        val transaction = mFm?.beginTransaction()
        if (from == null) {
            transaction!!.add(R.id.fl_contain, to!!).commit()
        } else if (to != null) {
            if (!to.isAdded) {
                transaction!!.hide(from).add(R.id.fl_contain, to).commit()
            } else {
                transaction!!.hide(from).show(to).commit()
            }
        }
        mFromFragment = to
    }
}
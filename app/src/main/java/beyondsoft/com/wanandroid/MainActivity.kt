package beyondsoft.com.wanandroid

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import beyondsoft.com.wanandroid.base.BaseActivity
import beyondsoft.com.wanandroid.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

const val TAG = "MainActivity"

class MainActivity : BaseActivity() {

    private var mHomeFragment: HomeFragment? = null
    private var mKnowledgeFragment: KnowledgeFragment? = null
    private var mPublicResFragment: PublicResFragment? = null
    private var mNavigationFragment: NavigationFragment? = null
    private var mProjectFragment: ProjectFragment? = null
    private var mFromFragment: Fragment? = null
    private var mFm: FragmentManager? = null

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }

    override fun initView() {
        toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }
        initFragment()
        switchNavigation()
        switchFragment(mFromFragment, mHomeFragment)
    }
    override fun getData() {
    }

    private fun initFragment() {
        mHomeFragment = HomeFragment()
        mKnowledgeFragment = KnowledgeFragment()
        mPublicResFragment = PublicResFragment()
        mNavigationFragment = NavigationFragment()
        mProjectFragment = ProjectFragment()
        mFm = this.supportFragmentManager
    }

    private fun switchNavigation() {
        bottom_navigation.run {
            // 以前使用 BottomNavigationViewHelper.disableShiftMode(this) 方法来设置底部图标和字体都显示并去掉点击动画
            // 升级到 28.0.0 之后，官方重构了 BottomNavigationView ，目前可以使用 labelVisibilityMode = 1 来替代
            // BottomNavigationViewHelper.disableShiftMode(this)
            labelVisibilityMode = 1
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.action_home -> {
                        if(mFromFragment == mHomeFragment) true else {
                            toolbar.title = getString(R.string.home)
                            switchFragment(mFromFragment, if (mHomeFragment == null) HomeFragment() else mHomeFragment)
                            mFromFragment = mHomeFragment
                            true
                        }
                    }
                    R.id.action_knowledge_system -> {
                        if(mFromFragment == mKnowledgeFragment) true else {
                            toolbar.title = getString(R.string.knowledge_system)
                            switchFragment(mFromFragment, if (mKnowledgeFragment == null) KnowledgeFragment() else mKnowledgeFragment)
                            mFromFragment = mKnowledgeFragment
                            true
                        }

                    }
                    R.id.action_wechat -> {
                        if(mFromFragment == mPublicResFragment) true else {
                            toolbar.title = getString(R.string.wechat)
                            switchFragment(mFromFragment, if (mPublicResFragment == null) PublicResFragment() else mPublicResFragment)
                            mFromFragment = mPublicResFragment
                            true
                        }
                    }
                    R.id.action_navigation -> {
                        if(mFromFragment == mNavigationFragment) true else {
                            toolbar.title = getString(R.string.navigation)
                            switchFragment(mFromFragment, if (mNavigationFragment == null) NavigationFragment() else mNavigationFragment)
                            mFromFragment = mNavigationFragment
                            true
                        }
                    }
                    R.id.action_project -> {
                        if(mFromFragment == mProjectFragment) true else {
                            toolbar.title = getString(R.string.project)
                            switchFragment(mFromFragment, if (mProjectFragment == null) ProjectFragment() else mProjectFragment)
                            true
                        }
                    }
                    else -> {
                        true
                    }
                }
            }
        }
    }

    private fun switchFragment(from: Fragment?, to: Fragment?) {
        val transaction = mFm?.beginTransaction()
        if (from == null) {
            transaction!!.add(R.id.container, to!!).commitAllowingStateLoss()
        } else if (to != null) {
            if (!to.isAdded) {
                transaction!!.hide(from).add(R.id.container, to).commitAllowingStateLoss()
            } else {
                transaction!!.hide(from).show(to).commitAllowingStateLoss()
            }
        }
    }
}

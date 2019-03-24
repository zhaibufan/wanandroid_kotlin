package beyondsoft.com.wanandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatDelegate
import android.view.Menu
import android.view.MenuItem
import android.view.View
import beyondsoft.com.wanandroid.base.BaseActivity
import beyondsoft.com.wanandroid.ui.activity.SearchActivity
import beyondsoft.com.wanandroid.ui.fragment.*
import beyondsoft.com.wanandroid.utils.SettingUtil
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

    private val onFABClickListener = View.OnClickListener {
        when (mFromFragment) {
            mHomeFragment -> {
                mHomeFragment?.scrollToTop()
                true
            }
            null -> {
                mHomeFragment?.scrollToTop()
                true
            }
            else -> {
                true
            }
        }
    }

    /**
     * NavigationView 监听
     */
    private val onDrawerNavigationItemSelectedListener =
            NavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_night_mode -> {
                        if (SettingUtil.getIsNightMode()) {
                            SettingUtil.setIsNightMode(false)
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        } else {
                            SettingUtil.setIsNightMode(true)
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        }
//                        recreate()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                        true
                    }
                    else -> true
                }
            }

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
        initDrawerLayout()
        initFragment()
        switchNavigation()
        switchFragment(mFromFragment, mHomeFragment)
        mFromFragment = mHomeFragment
        floating_action_btn.run {
            setOnClickListener(onFABClickListener)
        }
        nav_view.setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
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


    /**
     * DrawerLayout和ActionBarDrawerToggle实现侧滑效果并有图标改变效果
     */
    private fun initDrawerLayout() {
        drawer_layout.run {
            var toggle = ActionBarDrawerToggle(
                    this@MainActivity,
                    this,
                    toolbar
                    , R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close)
            addDrawerListener(toggle)
            toggle.syncState()
        }
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
                        if (mFromFragment == mHomeFragment) true else {
                            floating_action_btn.show()
                            toolbar.title = getString(R.string.home)
                            switchFragment(mFromFragment, if (mHomeFragment == null) HomeFragment() else mHomeFragment)
                            mFromFragment = mHomeFragment
                            true
                        }
                    }
                    R.id.action_knowledge_system -> {
                        if (mFromFragment == mKnowledgeFragment) true else {
                            floating_action_btn.hide()
                            toolbar.title = getString(R.string.knowledge_system)
                            switchFragment(mFromFragment, if (mKnowledgeFragment == null) KnowledgeFragment() else mKnowledgeFragment)
                            mFromFragment = mKnowledgeFragment
                            true
                        }

                    }
                    R.id.action_wechat -> {
                        if (mFromFragment == mPublicResFragment) true else {
                            floating_action_btn.hide()
                            toolbar.title = getString(R.string.wechat)
                            switchFragment(mFromFragment, if (mPublicResFragment == null) PublicResFragment() else mPublicResFragment)
                            mFromFragment = mPublicResFragment
                            true
                        }
                    }
                    R.id.action_navigation -> {
                        if (mFromFragment == mNavigationFragment) true else {
                            floating_action_btn.hide()
                            toolbar.title = getString(R.string.navigation)
                            switchFragment(mFromFragment, if (mNavigationFragment == null) NavigationFragment() else mNavigationFragment)
                            mFromFragment = mNavigationFragment
                            true
                        }
                    }
                    R.id.action_project -> {
                        if (mFromFragment == mProjectFragment) true else {
                            floating_action_btn.hide()
                            toolbar.title = getString(R.string.project)
                            switchFragment(mFromFragment, if (mProjectFragment == null) ProjectFragment() else mProjectFragment)
                            mFromFragment = mProjectFragment
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

    @SuppressLint("CommitTransaction")
    private fun switchFragment(from: Fragment?, to: Fragment?) {
        val transaction = mFm?.beginTransaction()
        if (from == null) {
            transaction!!.add(R.id.container, to!!).commit()
        } else if (to != null) {
            if (!to.isAdded) {
                transaction!!.hide(from).add(R.id.container, to).commit()
            } else {
                transaction!!.hide(from).show(to).commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                Intent(this@MainActivity, SearchActivity::class.java).run {
                    startActivity(this) //this指代对象 it指代参数(只有一个参数时使用it)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

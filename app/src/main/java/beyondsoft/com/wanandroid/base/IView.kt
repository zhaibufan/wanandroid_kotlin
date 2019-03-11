package beyondsoft.com.wanandroid.base

interface IView {
    /**
     * Hide loading 隐藏加载动画 显示正常布局
     */
    fun showNormal()

    /**
     * Show loading 显示加载动画
     */
    fun showLoading()

    /**
     * Show error 当请求数据失败时的错误布局
     */
    fun showError(err: String)

    /**
     * Show empty 当没有数据时显示空布局
     */
    fun showEmpty()

    /**
     * Reload 当加载出错或超时等情况下重新加载
     */
    fun reload()
}
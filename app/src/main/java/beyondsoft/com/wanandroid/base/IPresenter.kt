package beyondsoft.com.wanandroid.base

interface IPresenter<T> {
    /**
     * 注入View
     *
     * @param view view
     */
    fun attachView(view: T)

    /**
     * 回收View
     */
    fun detachView()

    /**
     * view 是否被回收我们
     *
     * @return true没回收
     */
    fun isAttachView(): Boolean
}
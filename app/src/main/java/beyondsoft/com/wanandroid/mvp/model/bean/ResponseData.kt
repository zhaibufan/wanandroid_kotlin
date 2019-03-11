package beyondsoft.com.wanandroid.mvp.model.bean

/**
 * 返回的基础数据
 */
data class HttpResult<T>(val data : T, val errorCode : Int, val errorMsg : String)

/**
 * 轮播图
 */
data class Banner(val desc: String, val id: Int, val imagePath: String, val isVisible: Int,
                  val order: Int, val title: String, val type: Int, val url: String)

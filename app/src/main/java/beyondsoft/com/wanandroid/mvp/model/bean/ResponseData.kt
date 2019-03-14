package beyondsoft.com.wanandroid.mvp.model.bean

/**
 * 返回的基础数据
 */
data class HttpResult<T>(val data: T, val errorCode: Int, val errorMsg: String)

/**
 * 轮播图
 */
data class Banner(val desc: String, val id: Int, val imagePath: String, val isVisible: Int,
                  val order: Int, val title: String, val type: Int, val url: String)

/**
 * 置顶文章
 */
data class Article(val apkLink: String, val author: String, val chapterId: Int, val chapterName: String,
                   var collect: Boolean, val courseId: Int, val desc: String, val envelopePic: String,
                   val fresh: Boolean, val id: Int, val link: String, val niceDate: String,
                   val origin: String, val projectLink: String, val publishTime: Long, val superChapterId: Int,
                   val superChapterName: String, val tags: MutableList<Tag>, val title: String, val type: Int,
                   val userId: Int, val visible: Int, val zan: Int, var top: String
)
data class Tag(val name: String, val url: String)

/**
 * 首页文章列表数据
 */
data class ArticleResponseBody(val curPage: Int, var datas: MutableList<Article>, val offset: Int,
                               val over: Boolean, val pageCount: Int, val size: Int, val total: Int
)

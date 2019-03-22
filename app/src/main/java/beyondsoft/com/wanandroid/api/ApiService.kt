package beyondsoft.com.wanandroid.api

import beyondsoft.com.wanandroid.mvp.model.bean.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.collections.ArrayList

interface ApiService {

    /**
     * 获取首页置顶Banner数据
     */
    @GET("banner/json")
    fun getBanners() : Observable<HttpResult<ArrayList<Banner>>>

    /**
     * 获取首页置顶文章列表
     * http://www.wanandroid.com/article/top/json
     */
    @GET("article/top/json")
    fun getTopArticles(): Observable<HttpResult<MutableList<Article>>>

    /**
     * 获取文章列表
     * http://www.wanandroid.com/article/list/0/json
     * @param pageNum
     */
    @GET("article/list/{pageNum}/json")
    fun getArticles(@Path("pageNum") pageNum: Int): Observable<HttpResult<ArticleResponseBody>>


    /**
     * 获取知识体系
     * http://www.wanandroid.com/tree/json
     */
    @GET("tree/json")
    fun getKnowledgeTree(): Observable<HttpResult<List<KnowledgeTreeBody>>>

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0/json?cid=168
     * @param page
     * @param cid
     */
    @GET("article/list/{page}/json")
    fun getKnowledgeList(@Path("page") page: Int, @Query("cid") cid: Int): Observable<HttpResult<ArticleResponseBody>>





    /**
     * 获取导航数据
     */
    @GET("navi/json")
    fun getNavigationData() : Observable<HttpResult<MutableList<Navigation>>>
}
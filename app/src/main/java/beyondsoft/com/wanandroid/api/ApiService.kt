package beyondsoft.com.wanandroid.api

import beyondsoft.com.wanandroid.mvp.model.bean.Banner
import beyondsoft.com.wanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observable
import retrofit2.http.GET
import kotlin.collections.ArrayList

interface ApiService {
    @GET("banner/json")
    fun getBanners() : Observable<HttpResult<ArrayList<Banner>>>
}
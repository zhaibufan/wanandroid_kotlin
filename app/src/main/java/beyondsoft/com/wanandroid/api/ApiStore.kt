package beyondsoft.com.wanandroid.api

import beyondsoft.com.wanandroid.BuildConfig
import beyondsoft.com.wanandroid.constant.Constant
import beyondsoft.com.wanandroid.log.httplog.HttpLoggingInterceptorM
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiStore {

    private var retrofit: Retrofit? = null

    fun <T> createApi(service: Class<T>): T? {
        createProxy()
        return retrofit?.create(service)
    }

    /**
     * 创建 retrofit 客户端
     */
    private fun createProxy() {
        if (retrofit == null) {
            synchronized(ApiStore::class.java) {
                if (retrofit == null) {
                    val gs = GsonBuilder().setDateFormat("yyyy.MM.dd HH:mm:ss").create()
                    retrofit = Retrofit.Builder()
                            .baseUrl(Constant.URL_BASE_TEST)
                            .addConverterFactory(GsonConverterFactory.create(gs))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getOkHttpClient())
                            .build()
                }
            }
        }
    }


    /**
     * 获取 OkHttpClient
     */
    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        val httpLoggingInterceptor = HttpLoggingInterceptorM()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level =HttpLoggingInterceptorM.Level.BODY
        } else {
            httpLoggingInterceptor.level =HttpLoggingInterceptorM.Level.NONE
        }

        builder.run {
            addInterceptor(httpLoggingInterceptor)
            connectTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true) // 错误重连
        }
        return builder.build()
    }
}
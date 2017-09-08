package bin.kotlin.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by Administrator on 2017/9/7 0007.
 */
internal class OkHttpProvider{
    companion object{
        /**
         * 自定义配置OkHttpClient
         */
        fun createOkHttpClient(): OkHttpClient {
            var  builder= OkHttpClient.Builder()
            var  loggingInterceptor= HttpLoggingInterceptor()
            loggingInterceptor.level= HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)

            return builder.build()
        }
    }
}
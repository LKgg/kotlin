package bin.kotlin.http

import android.content.Context
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.LibraryGlideModule
import java.io.InputStream

/**
 * Created by Administrator on 2017/9/7 0007.
 */
@GlideModule
internal class CustomOkHttpGlideModule : LibraryGlideModule(){
    override fun registerComponents(context: Context?, registry: Registry) {

        registry.replace(GlideUrl::class.java,
                InputStream::class.java,OkHttpUrlLoader.factory)
    }
}
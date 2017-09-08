package bin.kotlin.http

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import java.io.InputStream
import java.util.regex.Pattern

/**
 * Created by Administrator on 2017/9/7 0007.
 */
internal class CustomBaseGlideUrlLoader(concreteLoader: ModelLoader<GlideUrl, InputStream>, modelCache: ModelCache<String, GlideUrl>): BaseGlideUrlLoader<String>(concreteLoader,modelCache){
    override fun handles(model: String?)=true
    /**
     * Url的匹配规则
     */
    val patern= Pattern.compile("__w-((?:-?\\d+)+)__")

    /**
     * 控制需要加载图片的尺寸大小
     */
    override fun getUrl(model: String, width: Int, height: Int, options: Options?): String {
        var  m=patern.matcher(model)
        var bestBucket=0
        if (m.find()){
            var  found=m.group(1).split("-")
            for (item in found){
                bestBucket=item.toInt()
                if (bestBucket>=width) break
            }
        }
        return model
    }


    companion object{
        val urlCache= ModelCache<String, GlideUrl>(150)
        val factory=object: ModelLoaderFactory<String, InputStream> {
            override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
                return CustomBaseGlideUrlLoader(multiFactory.build(GlideUrl::class.java, InputStream::class.java), urlCache)
            }
            override fun teardown() {
            }
        }
    }
}
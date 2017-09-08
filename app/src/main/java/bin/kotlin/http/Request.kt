package bin.kotlin.http

import android.util.Log
import java.net.URL

/**
 * Created by Administrator on 2017/9/7 0007.
 */
public class Request(val url : String){

    public fun get(): String{
        val foreData : String  = URL(url).readText()
        return foreData
    }
}
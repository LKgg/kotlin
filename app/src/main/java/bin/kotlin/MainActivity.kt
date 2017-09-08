package bin.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import bin.kotlin.http.GlideApp
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*;
import okhttp3.*
import org.jetbrains.anko.doAsync
import java.io.IOException
import okhttp3.FormBody
import org.jetbrains.anko.uiThread
import android.widget.Toast
import bin.kotlin.VO.UserVO
import okhttp3.OkHttpClient
import okhttp3.RequestBody






class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlideApp.with(this)
                .load("http://192.168.0.120:8081/afd.jpg")
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(imageView)

        button.setOnClickListener{
//            doAsync {
//               val re = bin.kotlin.http.Request("http://192.168.0.120:8081/getUserList").get()
//                uiThread {
//                    Log.d(javaClass.simpleName,re.toString())
//                }
//            }
            postJson()
        }
    }

    /**
     * 异步GET请求
     */
    private fun getAsynHttp() {
        var mOkHttpClient = OkHttpClient()
        val requestBuilder = Request.Builder().url("http://www.baidu.com")
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null)
        val request = requestBuilder.build()
        val mcall = mOkHttpClient.newCall(request)
        mcall.enqueue(object : Callback {
           override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (null != response.cacheResponse()) {
                    val str = response.cacheResponse().toString()
                    Log.i("getAsynHttp", "cache---" + str)
                } else {
                    response.body().toString()
                    val str = response.networkResponse().toString()
                    Log.i("getAsynHttp", "network---" + str)
                }
                runOnUiThread { Toast.makeText(this@MainActivity, "请求成功", Toast.LENGTH_SHORT).show() }
            }
        })
    }

    /**
     * 异步POST请求
     */
    private fun postAsynHttp() {
        var mOkHttpClient = OkHttpClient()
        val formBody = FormBody.Builder()
                .add("id", "10")
                .build()
        val request = Request.Builder()
                .url("http://192.168.0.120:8081/getUserById")
                .post(formBody)
                .build()
        val call = mOkHttpClient.newCall(request)
        call.enqueue(object : Callback {
           override fun onFailure(call: Call, e: IOException) {
               Log.i("IOException", e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val str :String = response.body()!!.string()
                Log.i("postAsynHttp", str)
                var user = Gson().fromJson(str, UserVO::class.java)
                runOnUiThread {
                    Toast.makeText(this@MainActivity,  "名称：${user.real_name} 电话：${user.phone}", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    /**
     * post请求 提交json数据到服务器
     */
    @Throws(IOException::class)
    private fun postJson() {
        val url = "http://192.168.0.120:8081/getUser"
        val json = "{\"id\":13}";

        val client = OkHttpClient()
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        val request = Request.Builder()
                .url(url)
                .post(body)
                .build()
        client.newCall(request).enqueue(object : Callback {
           override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val str :String = response.body()!!.string()
                Log.i("postJson", str)
                var user = Gson().fromJson(str, UserVO::class.java)
                runOnUiThread {
                    Toast.makeText(this@MainActivity,  "名称：${user.real_name} 电话：${user.phone}", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}

package bin.kotlin.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast

import bin.kotlin.R
import bin.kotlin.VO.UserList
import bin.kotlin.adapter.MyReLiistAdapter
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import kotlinx.android.synthetic.main.activity_list.*
class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        postAsynHttp()
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
                .url("http://119.23.234.169:8081/getUserList")
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
                Log.i("postAsynHttp", "{\"dataList\":"+str+"}")
                var user :UserList = Gson().fromJson("{\"dataList\":"+str+"}", UserList::class.java)
                runOnUiThread {
                    Toast.makeText(this@ListActivity,  "名称：${user.dataList[0].real_name} 电话：${user.dataList[0].phone}", Toast.LENGTH_SHORT).show()
                    var adapterData = MyReLiistAdapter(this@ListActivity,user.dataList)
                    recyclerView.layoutManager = LinearLayoutManager(this@ListActivity)
                    recyclerView.adapter=adapterData
                }
            }

        })
    }
}

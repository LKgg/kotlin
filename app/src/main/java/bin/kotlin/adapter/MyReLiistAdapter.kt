package bin.kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import bin.kotlin.R
import bin.kotlin.VO.UserVO

/**
 * Created by Administrator on 2017/9/11 0011.
 */
class MyReLiistAdapter : RecyclerView.Adapter<MyReLiistAdapter.MyViewHolder> {
    private var context: Context? = null
    private var list:List<UserVO>? = null
    constructor(context:Context,list:List<UserVO>){
        this.context = context
        this.list = list
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(p0: MyViewHolder?, p1: Int) {
        p0?.name!!.text = list?.get(p1)?.real_name
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): MyViewHolder {

        return  MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_user, p0,
                false))

    }

    override fun getItemCount(): Int {
        return list?.size as Int
    }


    class MyViewHolder : RecyclerView.ViewHolder {
        constructor(view: View) : super(view) {
            name = view.findViewById(R.id.name) as TextView
        }
        var name:TextView
    }
}
package xyz.mercs.libcommon.base.mvp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


open abstract class BaseListAdapter<M,T: BaseListAdapter.BaseViewHolder>: RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    protected var mList:ArrayList<M>?=null

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder
        = getViewHolder(LayoutInflater.from(parent.context).inflate(getLayout(),parent,false))


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        init(viewHolder as T, mList!![pos],pos)
    }

    override fun getItemCount(): Int  = mList?.size?:0

    abstract fun getLayout():Int

    abstract fun getViewHolder(v:View):T

    abstract fun init(viewHolder:T,bean:M,pos:Int)

    open class BaseViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
}


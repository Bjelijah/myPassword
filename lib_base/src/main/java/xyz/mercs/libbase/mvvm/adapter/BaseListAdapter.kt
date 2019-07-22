package xyz.mercs.libcommon.ui.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open abstract class BaseListAdapter<M,B: ViewDataBinding>(var mContext:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var items: ObservableArrayList<M>?=null

    fun setData(l:ObservableArrayList<M>){
        items = l
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding:B = DataBindingUtil.inflate(LayoutInflater.from(mContext),getLayout(viewType),viewGroup,false)
        return BaseBindingViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, pos: Int) {
        val binding: B = DataBindingUtil.getBinding(viewHolder.itemView)!!
        onBindItem(binding,items?.get(pos),pos)
    }

    override fun getItemCount(): Int = items?.size?:0

    @LayoutRes
    protected abstract fun getLayout(viewType: Int):Int
    protected abstract fun onBindItem(binding:B,item:M?,pos:Int)

    inner class BaseBindingViewHolder(v: View) :RecyclerView.ViewHolder(v)
}
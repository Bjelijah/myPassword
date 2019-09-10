package xyz.mercs.libcommon.base.mvvm.vm

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import xyz.mercs.libcommon.base.mvvm.adapter.BaseListAdapter

object BinderHelper {
    @BindingAdapter("updateBindingListData")
    @JvmStatic
    fun <M> setUpdateBindListData(v: RecyclerView?, l: ObservableArrayList<M>) {
        if(v?.adapter==null)return
        (v?.adapter as BaseListAdapter<M, ViewDataBinding>)?.setData(l)
    }
    @BindingAdapter("updateRefresh")
    @JvmStatic
    fun setUpdateRefresh(v: SwipeRefreshLayout?, isRefresh:Boolean?){
        Log.i("123","setUpdateRefresh=  $isRefresh")
        v?.isRefreshing = isRefresh?:false
    }
}
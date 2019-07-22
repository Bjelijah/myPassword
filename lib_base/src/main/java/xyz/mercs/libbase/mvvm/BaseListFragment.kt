package xyz.mercs.libbase.mvvm

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import xyz.mercs.libbase.R
import xyz.mercs.libbase.mvvm.vm.BaseViewModel
import xyz.mercs.libcommon.ui.adapter.BaseListAdapter
import xyz.mercs.libcommon.ui.widget.EndLessOnScrollListener


open abstract class BaseListFragment <M,B: ViewDataBinding>: BaseFragment(), SwipeRefreshLayout.OnRefreshListener{

    protected var mScrollListener: RecyclerView.OnScrollListener?=null

    var mRv:RecyclerView?=null
    var mSrl:SwipeRefreshLayout?=null

    override fun getLayout(): Int = R.layout.layout_base_list

    override fun bind(bind: ViewDataBinding, vm: BaseViewModel) {

        if(vm==null){
            removeRefreshListener()
            removeScrollListener()
            return
        }
        mRv = getRv()
        mSrl = getSrl()
        if(mRv?.layoutManager==null){
            mRv?.layoutManager = getLayoutManager()
        }
        if(mRv?.adapter==null){
            mRv?.adapter = getAdapter<M,B>()
        }
        enableRefresh(isEnableRefresh())
        enableLoad(isEnableScroll())
    }

    private fun addRefreshListener(){
        mSrl?.setOnRefreshListener(this)
        mSrl?.isEnabled = true
    }

    private fun removeRefreshListener(){
        mSrl?.setOnRefreshListener(null)
        mSrl?.isEnabled = false
    }

    private fun addScrollListener(){
        if(mScrollListener==null){
            mScrollListener = object : EndLessOnScrollListener() {
                override fun onLoadMore(currentPage: Int) {
                    onLoad(currentPage)
                }
            }
            mRv?.addOnScrollListener(mScrollListener!!)
        }
    }
    private fun removeScrollListener(){
        if(mScrollListener!=null) {
            mRv?.removeOnScrollListener(mScrollListener!!)
            mScrollListener = null
        }

    }
    protected fun refreshBegin(){
        mSrl?.isRefreshing = true
    }

    protected fun refreshFinish(){
        mSrl?.isRefreshing = false
    }

    override fun onRefresh() {
        if(mScrollListener is EndLessOnScrollListener){
            (mScrollListener as EndLessOnScrollListener).reset()
        }
    }

    protected fun enableRefresh(b:Boolean){
        if(b){
            addRefreshListener()
        }else{
            removeRefreshListener()
        }
    }

    protected fun enableLoad(b:Boolean){
        if(b){
            addScrollListener()
        }else{
            removeScrollListener()
        }
    }

    protected fun fillLayoutManager():RecyclerView.LayoutManager{
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        return layoutManager
    }

    abstract fun isEnableRefresh():Boolean
    abstract fun isEnableScroll():Boolean
    abstract fun getLayoutManager():RecyclerView.LayoutManager
    abstract fun <M,B:ViewDataBinding>getAdapter(): BaseListAdapter<M, B>
    abstract fun getRv():RecyclerView
    abstract fun getSrl():SwipeRefreshLayout
    protected open fun onLoad(curPage:Int){
        Log.i("123","onLoad curPage=$curPage")


    }

}
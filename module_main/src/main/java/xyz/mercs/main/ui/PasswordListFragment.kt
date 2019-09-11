package xyz.mercs.main.ui

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import xyz.mercs.libcommon.base.mvvm.BaseListFragment
import xyz.mercs.libcommon.base.mvvm.adapter.BaseListAdapter
import xyz.mercs.libcommon.base.mvvm.vm.BaseViewModel
import xyz.mercs.libcommon.databinding.LayoutBaseListMvvmBinding
import xyz.mercs.main.vm.PasswordListViewModel
import xyz.mercs.modulemain.R

class PasswordListFragment<M,B:ViewDataBinding>:BaseListFragment<M,B>() {
    override fun isEnableRefresh(): Boolean = true

    override fun isEnableScroll(): Boolean = true

    override fun getLayoutManager(): RecyclerView.LayoutManager = fillLayoutManager()

    override fun <M, B : ViewDataBinding> getAdapter(): BaseListAdapter<M, B> {
    }

    override fun getRv(): RecyclerView = (mBinder as LayoutBaseListMvvmBinding).baseListRv

    override fun getSrl(): SwipeRefreshLayout  = (mBinder as LayoutBaseListMvvmBinding).baseListSrl

    override fun getViewModel(): BaseViewModel = ViewModelProviders.of(this).get(PasswordListViewModel::class.java)

    override fun getLayout(): Int = R.layout.layout_base_list_mvvm
}
package xyz.mercs.libcommon.base.mvvm.vm

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField


open class BaseListViewModel<T>(app:Application) : BaseViewModel(app) {
    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    val mList: ObservableArrayList<T> = ObservableArrayList()
    val mIsRefresh: ObservableField<Boolean> = ObservableField(false)

    open fun onRefresh(){}

    open fun onLoad(){}

}
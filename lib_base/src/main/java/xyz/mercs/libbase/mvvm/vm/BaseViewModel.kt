package xyz.mercs.libbase.mvvm.vm

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open abstract class BaseViewModel(app: Application):AndroidViewModel(app) {

    init {
        onCreate()
    }
    var mContext: Context?=null

    fun setContext(c:Context):BaseViewModel{
        mContext = c
        return this
    }
    var mMessage:MutableLiveData<String>? = null
        get() {
            if(field==null){
                field = MutableLiveData()
            }
            return field
        }



    private var mCompositeDisposable: CompositeDisposable?=null
    protected fun addDisposable(subscription: Disposable){
        if (mCompositeDisposable?.isDisposed != false){
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(subscription)
    }

    protected fun dispose() = mCompositeDisposable?.dispose()


    abstract fun onCreate()
    abstract fun onDestroy()

    override fun onCleared() {
        super.onCleared()
        dispose()
        onDestroy()
    }

}
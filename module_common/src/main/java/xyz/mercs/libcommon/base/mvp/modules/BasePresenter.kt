package xyz.mercs.libcommon.base.mvp.modules

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter {
    private var mCompositeDisposable: CompositeDisposable?=null

    protected fun addDisposable(subscription: Disposable){
        if (mCompositeDisposable?.isDisposed != false){
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(subscription)
    }

    protected fun dispose() = mCompositeDisposable?.dispose()
}
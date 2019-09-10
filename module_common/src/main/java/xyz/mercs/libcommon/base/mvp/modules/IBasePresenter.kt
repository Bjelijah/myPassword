package xyz.mercs.libcommon.base.mvp.modules

interface IBasePresenter {
    fun bindView(v: IBaseView)
    fun unbindView()
}
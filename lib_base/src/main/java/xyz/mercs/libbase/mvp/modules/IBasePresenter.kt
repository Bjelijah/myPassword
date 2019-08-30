package xyz.mercs.libbase.mvp.modules

interface IBasePresenter {
    fun bindView(v:IBaseView)
    fun unbindView()
}
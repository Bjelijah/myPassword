package xyz.mercs.main.ui

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import xyz.mercs.libcommon.base.mvvm.BaseActivity
import xyz.mercs.libcommon.base.mvvm.vm.BaseViewModel
import xyz.mercs.main.vm.LoginViewModel
import xyz.mercs.modulemain.BR
import xyz.mercs.modulemain.R

class LoginActivity :BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_login

    override fun getViewModel(): BaseViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

    override fun bind(bind: ViewDataBinding, vm: BaseViewModel) {
        bind.setVariable(BR.vm,vm)
        var loginVm = vm as LoginViewModel
        loginVm.mContext = this

        loginVm.mFingerRes?.observe(this, Observer {
            if(it.isOk){
                loginVm.stop()

            }
        })
        loginVm.start()
    }
}